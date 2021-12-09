package com.example.analizo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ml.common.FirebaseMLException;
import com.google.firebase.ml.custom.FirebaseCustomLocalModel;
import com.google.firebase.ml.custom.FirebaseModelDataType;
import com.google.firebase.ml.custom.FirebaseModelInputOutputOptions;
import com.google.firebase.ml.custom.FirebaseModelInputs;
import com.google.firebase.ml.custom.FirebaseModelInterpreter;
import com.google.firebase.ml.custom.FirebaseModelInterpreterOptions;
import com.google.firebase.ml.custom.FirebaseModelOutputs;
import com.txusballesteros.widgets.FitChart;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class ProductDetail extends AppCompatActivity {

    //Animation for buttons
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    private NoInternet ni;

//    final List<Reviews> reviews=new ArrayList<Reviews>();
//    public Reviews[] review=new Reviews[3];

    // databaseReference for storing reviews data
    // reference1 for reading movie data
    // reference2 for updating movie data
    private DatabaseReference databaseReference,reference1,reference2,databaseReference2;
    private ImageView image;
    private ScrollView scroll;
    private RelativeLayout relative2;
    private TextView name,year,genre,synopsis_text,posNo,negNo,posPercent,negPercent,text;
    private EditText movie_review;
    private ProgressBar progressBar;
    private BroadcastReceiver broadcastReceiver;
    private float pos_sentiment,neg_sentiment,predicts;
    private String review_text;
    private FirebaseModelInterpreter interpreter;
    private FirebaseModelInputOutputOptions inputOutputOptions;
    private int tag,positivePercent,negativePercent,total;
    private String movieName;
    private Movie movie;

    //Reset and display the activity (xml) after the data is fetched from firebase (so that empty acivity is not displayed to the user)
    public void resetActivity(){
        int id = getResources().getIdentifier(movieName, "drawable", "com.example.analizo");
        image.setImageResource(id);
        name.setText(movie.getName());
        year.setText(Integer.toString(movie.getYear()));
        genre.setText(movie.getGenre());
        synopsis_text.setText(movie.getSynopsis());
        int pos=movie.getPosReviews();
        int neg=movie.getNegReviews();
        posNo.setText(Integer.toString(pos));
        negNo.setText(Integer.toString(neg));
        total=pos+neg;
        if(total>0){
            positivePercent=(int)((((double)pos)/total)*100);
            negativePercent=(int)((((double)neg)/total)*100);
        }
        else{
            positivePercent=0;
            negativePercent=0;
        }
        posPercent.setText(positivePercent+"%");
        negPercent.setText(negativePercent+"%");
        text = findViewById(R.id.text);

        final FitChart fitChart = findViewById(R.id.fitChart);
        fitChart.setMinValue(0f);
        fitChart.setMaxValue(100f);
        fitChart.setValue(positivePercent);
        text.setText(positivePercent+"%");

        progressBar.setVisibility(View.INVISIBLE);
        scroll.setVisibility(View.VISIBLE);
        relative2.setVisibility(View.VISIBLE);
    }

    //Check internet throughout the activity using BroadcastReceiver
    public void checkInternetConnectivity(){

        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        broadcastReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(ni.isConnectedToInternet(ProductDetail.this)==true){
                    return;
                }

                //If no intenet connection then display NoInternet Activity
                else{
                    Intent intent2 = new Intent(ProductDetail.this, NoInternet.class);
                    intent2.putExtra("class",ProductDetail.class);
                    startActivity(intent2);
                }

            }
        };
        registerReceiver(broadcastReceiver,intentFilter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_layout);
        ni=new NoInternet();

        //Check internet
        checkInternetConnectivity();

//        int positive=getResources().getIdentifier("pos","drawable","com.example.analizo");
//        int negative=getResources().getIdentifier("neg","drawable","com.example.analizo");
//        Log.i("Pos",positive+"   "+negative);

        progressBar=findViewById(R.id.progressBar);
        scroll=findViewById(R.id.scroll);
        relative2=findViewById(R.id.relative2);
        image=findViewById(R.id.image);
        name=findViewById(R.id.name);
        year=findViewById(R.id.year);
        genre=findViewById(R.id.genre);
        synopsis_text=findViewById(R.id.synopsis_text);
        posNo=findViewById(R.id.posNo);
        negNo=findViewById(R.id.negNo);
        posPercent=findViewById(R.id.posPercent);
        negPercent=findViewById(R.id.negPercent);
        movie_review=findViewById(R.id.movie_review);

        //Extract movie tag and id(name) from intent to identify which movie image was clicked and fetch respective data from firebase
        Bundle bundle = getIntent().getExtras();
//        tag = Integer.parseInt(bundle.getString("tag"));
        movieName=bundle.getString("movieId");

        //Extract data from firebase using DatabaseReference
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        reference1=database.getReference();

        //Give path to fetch data (in this case, movie name)
        reference1.child(movieName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Iterate over all the data items in the specified path and store all items in array of objects of respective class
                Iterable<DataSnapshot> children=dataSnapshot.getChildren();
                for (DataSnapshot child:children){

                    //In this case, one item in the path so no need for array, only a Movie object

//                    Reviews review=child.getValue(Reviews.class);
//                    reviews.add(review);
                    movie=child.getValue(Movie.class);

                }
//                int imageId=getResources().getIdentifier(movieName,"drawable","com.example.analizo");
//                Log.i("id",Integer.toString(imageId));

                //Display the activity to the user
                resetActivity();

//                review[1]=reviews.get(1);
//                Log.i("Review2",review[1].getReview());
//                Log.i("2Pos",Double.toString(review[1].getPos_sentiment()));
//                Log.i("2Neg",Double.toString(review[1].getNeg_sentiment()));
//                review[2]=reviews.get(2);
//                Log.i("Review3",review[2].getReview());
//                Log.i("3Pos",Double.toString(review[2].getPos_sentiment()));
//                Log.i("3Neg",Double.toString(review[2].getNeg_sentiment()));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        String url="https://firebasestorage.googleapis.com/v0/b/analizo-15357.appspot.com/o/1.jpg?alt=media&token=35e24fda-d201-4a28-b034-3120a1f6b3d6";
//        Glide.with(getApplicationContext()).load(url).into(image);
//        review_text=movie_review.getText().toString();

        //DatabaseReference to deal with movie reviews
        databaseReference= FirebaseDatabase.getInstance().getReference(movieName+"_reviews");

        //Code for storing movie data in firebase as object of Movie class and storing relevant attributes

//        databaseReference2= FirebaseDatabase.getInstance().getReference(movieName);
//        String id = databaseReference2.push().getKey();
//        int imageId=getResources().getIdentifier(movieName,"drawable","com.example.analizo");
//        String synopsis;
//        synopsis="Bob Parr (A.K.A. Mr. Incredible), and his wife Helen (A.K.A. Elastigirl), are the world's greatest famous crime-fighting superheroes in Metroville. Always saving lives and battling evil on a daily basis. But fifteen years later, they have been forced to adopt civilian identities and retreat to the suburbs where they have no choice but to retire as superheroes to live a \"normal life\" with their three children Violet, Dash and Jack-Jack (who were secretly born with superpowers). Itching to get back into action, Bob gets his chance when a mysterious communication summons him to a remote island for a top secret assignment. He soon discovers that it will take a super family effort to rescue the world from total destruction.";
//        Movie movie = new Movie(id,tag, movieName,"The Incredibles",2004,"Action/Adventure/Family",synopsis,imageId,0,0);
//        databaseReference2.child(id).setValue(movie);

        //Code for loading the ML tflite model for sentiment classification
        //As in firebase documentation
        try {
            FirebaseCustomLocalModel localModel = new FirebaseCustomLocalModel.Builder()
                    .setAssetFilePath("tflite_model.tflite")
                    .build();

            FirebaseModelInterpreterOptions options =
                    new FirebaseModelInterpreterOptions.Builder(localModel).build();
            interpreter = FirebaseModelInterpreter.getInstance(options);

            //Specifying input and output vector shapes for model
            inputOutputOptions =
                    new FirebaseModelInputOutputOptions.Builder()
                            .setInputFormat(0, FirebaseModelDataType.FLOAT32, new int[]{1, 250})
                            .setOutputFormat(0, FirebaseModelDataType.FLOAT32, new int[]{1, 1})
                            .build();
//            String review_text=add_review.getText().toString();

        } catch (FirebaseMLException e) {
            e.printStackTrace();
        }
    }

    //Preprocessing the review
    public float[][] preprocess(String add_review) throws FileNotFoundException, IOException {
        Map<String, Float> word_index = new HashMap<String, Float>();
        List<Float> encode = new ArrayList<Float>();
        List<String> review = new ArrayList<String>();
        String key;
        float value;
        BufferedReader br = new BufferedReader( new InputStreamReader(getAssets().open("dictionary1.txt"), "UTF-8"));
        String line;
        while ((line = br.readLine()) != null) {
            String[] embedding=line.split(" ");
            key=embedding[0];
            value=Integer.parseInt(embedding[1]);
            word_index.put(key, value);
        }
        word_index.put("<PAD>",(float)0);
        word_index.put("<START>",(float)1);
        word_index.put("<UNK>",(float)2);
        word_index.put("<UNUSED>",(float)3);
        String nline=add_review.replace(",", "").replace(".", "").replace("(", "").replace(")", "").replace(":", "").replace("\"","").replace("!", "");
        String[] reviews=nline.split(" ");
        for (int i=0;i<reviews.length;i++){
            review.add(reviews[i]);
        }

        //Encode the review after preprocessing
        encode=review_encode(review,word_index);
        float[][] final_review = new float[1][250];
        int array_count=0;
        Iterator itr=encode.iterator();
        while(itr.hasNext()){
            Float array_elements=(Float)itr.next();
            float array_element=array_elements.floatValue();
            final_review[0][array_count]=array_element;
            array_count++;
        }

        return final_review;
    }

    //Encode the user input review according to the dictionary
    public List review_encode(List s,Map word_index){
        int length=1;
        List<Float> encoded = new ArrayList<Float>();
        encoded.add((float)1);
        Iterator itr=s.iterator();
        while(itr.hasNext()){
            String word=(String)itr.next();
            word=word.toLowerCase();
            if(word_index.containsKey(word)){
                encoded.add((Float)(word_index.get(word)));
            }
            else{
                encoded.add((float)2);
            }
            length++;
        }
        for(int i=1;i<=250-length;i++){
            encoded.add((float)0);
        }
        return encoded;
    }

    //Enter button after typing review
    public void sendToDatabase(View view) {
        view.startAnimation(buttonClick);
        addReview();
    }

    public void addReview() {

        //Get review
        review_text=movie_review.getText().toString();
        float[][] review= new float[0][];
        try {

            //Preprocess and encode the review
            review = preprocess(review_text);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Pass the encoded review through the ML model
        FirebaseModelInputs inputs = null;
        try {
            inputs = new FirebaseModelInputs.Builder()
                    .add(review)  // add() as many input arrays as your model requires
                    .build();
        } catch (FirebaseMLException e) {
            e.printStackTrace();
        }

        //Obtain model results
        interpreter.run(inputs, inputOutputOptions)
                .addOnSuccessListener(
                        new OnSuccessListener<FirebaseModelOutputs>() {
                            @Override
                            public void onSuccess(FirebaseModelOutputs result) {

                                //Get the sentiment prediction
                                float[][] output = result.getOutput(0);
                                float[] probabilities = output[0];
                                predicts=probabilities[0];
                                String predict=String.valueOf(predicts);
//                                sentiment.setText(predict);
//                                Log.i("Predicts1",Float.toString(predicts));

                                pos_sentiment=predicts;
                                neg_sentiment=1.0f-pos_sentiment;

                                if(!TextUtils.isEmpty(review_text)) {

                                    //Store the review (text and sentiment) in firebase
                                    //Pass the review as Review object in DatabaseReference
                                    String id2 = databaseReference.push().getKey();

                                    Reviews movie_reviews = new Reviews(id2,review_text,pos_sentiment,neg_sentiment);

                                    databaseReference.child(id2).setValue(movie_reviews);
                                    movie_review.setText("");

                                    //Update the number of +ve or -ve reviews accordingly
                                    reference2= FirebaseDatabase.getInstance().getReference(movieName+"/"+movie.getMovieId());  //Specify path where the respective attribute is

                                    //Use a hashmap and put() and updateChildren() functions for updating data
                                    //Do not use setValue() because it creates a new item with only the specified attribute
                                    Map<String,Object> map=new HashMap<>();

                                    if(pos_sentiment>neg_sentiment){

                                        //movie is the current movie object being used
                                        movie.setPosReviews(movie.getPosReviews()+1);
                                        map.put("posReviews",movie.getPosReviews());
//                                        reference1.child(movieName).child(movie.getMovieId()).child("posReviews").setValue(movie.getPosReviews());

                                    }
                                    else{

                                        movie.setNegReviews(movie.getNegReviews()+1);
                                        map.put("negReviews",movie.getNegReviews());
//                                        reference1.child(movieName).child(movie.getMovieId()).child("negReviews").setValue(movie.getNegReviews());

                                    }
                                    //
                                    reference2.updateChildren(map);
                                    resetActivity();

                                    //Move to the next activity to display all reviews
                                    Intent intent = new Intent(ProductDetail.this, ProductReviews.class);
                                    total=movie.getPosReviews()+movie.getNegReviews();

                                    //Pass movie name and number of reviews with intent so that the movie data does not need oe retrieved again
                                    intent.putExtra("totalReviews", Integer.toString(total));
                                    intent.putExtra("movieName", movieName);
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(ProductDetail.this, "You cannot post an empty review.",Toast.LENGTH_LONG).show();
                                }

                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                e.printStackTrace();
                            }

                        });

    }

    //Display all reviews
    public void seeAllReviews(View view){
        view.startAnimation(buttonClick);
        Intent intent = new Intent(ProductDetail.this, ProductReviews.class);
        total=movie.getPosReviews()+movie.getNegReviews();
        intent.putExtra("totalReviews", Integer.toString(total));
        intent.putExtra("movieName", movieName);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}

//To convert to tflite model follow tensorflow doc after writing the output shape to the first layer in model, performed on google colab.