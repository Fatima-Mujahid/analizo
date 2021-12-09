package com.example.analizo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProductReviews extends AppCompatActivity {

    public static ArrayList<Integer> imageTag;
    private NoInternet ni;

    private Reviews review,review2;
    private ArrayList<Reviews> reviews;
    private DatabaseReference ref;
    private String movieName;
    int total;
    private BroadcastReceiver broadcastReceiver;
    private ArrayList<ExampleItem> mExampleList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar progressBar;

    private TextView reviews_total;
    private Button buttonInsert;
    private Button buttonRemove;
    private EditText editTextInsert;
    private EditText editTextRemove;

    //Check internet throughout the activity using BroadcastReceiver
    public void checkInternetConnectivity(){

        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        broadcastReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(ni.isConnectedToInternet(ProductReviews.this)==true){
                    return;
                }

                //If no intenet connection then display NoInternet Activity
                else{
                    Intent intent3 = new Intent(ProductReviews.this, NoInternet.class);
                    intent3.putExtra("class",ProductReviews.class);
                    startActivity(intent3);
                    finish();
                }

            }
        };
        registerReceiver(broadcastReceiver,intentFilter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_reviews);
        ni=new NoInternet();

        //Check internet
        checkInternetConnectivity();

        progressBar=findViewById(R.id.progressBar);
        mRecyclerView=findViewById(R.id.recyclerView);

        Bundle bundle = getIntent().getExtras();
        movieName=bundle.getString("movieName");
        total=Integer.parseInt(bundle.getString("totalReviews"));

        reviews_total=findViewById(R.id.reviews_total);
        reviews_total.setText(Integer.toString(total));

        imageTag=new ArrayList<>();
        reviews=new ArrayList<>();


        //Extract data from firebase using DatabaseReference
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        ref=database.getReference();

        //Give path to fetch data
        ref.child(movieName+"_reviews").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Iterate over all the data items in the specified path and store all items in array of objects of respective class
                Iterable<DataSnapshot> children=dataSnapshot.getChildren();

                for (DataSnapshot child:children){

                    review=child.getValue(Reviews.class);
                    reviews.add(review);

                }

                //Pass the data to xml components
                createExampleList();
                buildRecyclerView();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//
//        buttonInsert = findViewById(R.id.button_insert);
//        buttonRemove = findViewById(R.id.button_remove);
//        editTextInsert = findViewById(R.id.editText_insert);
//        editTextRemove = findViewById(R.id.editText_remove);
//
//        buttonInsert.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = Integer.parseInt(editTextInsert.getText().toString());
//                insertItem(position);
//            }
//        });
//
//        buttonRemove.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = Integer.parseInt(editTextRemove.getText().toString());
//                removeItem(position);
//            }
//        });

    }

//    public void insertItem(int position){
//        mExampleList.add(position, new ExampleItem(R.drawable.image, "New Item At Position"+ position, "This is line 2"));
//        mAdapter.notifyItemInserted(position);
//    }
//
//    public void removeItem(int position){
//        mExampleList.remove(position);
//        mAdapter.notifyItemRemoved(position);
//    }

    public void createExampleList(){

        //mExampleList uses the array (containing data fetched from firebase (here array contains review objects)) to store the data required to be
        // displayed to the user as ExampleItem objects (only the attributes to be displayed in xml are stored in ExampleItem objects)

        mExampleList = new ArrayList<>();

        //Latest review on top
        for(int i=reviews.size()-1;i>=0;i--){
            review2=reviews.get(i);

            //imageTag array size= number of reviews for a movie
            //Position 0 for review1, position 2 for review2,...
            //imageTag is used to set the colour of sentiment in xml (a tag corresponding to each review)

            if(review2.getPos_sentiment()>review2.getNeg_sentiment()){
                mExampleList.add(new ExampleItem(R.drawable.pos,review2.getReview(),String.format("%.2f%% Positive",100*review2.getPos_sentiment())));
                imageTag.add(1);    //+ve
            }
            else{
                mExampleList.add(new ExampleItem(R.drawable.neg,review2.getReview(),String.format("%.2f%% Negative",100*review2.getNeg_sentiment())));
                imageTag.add(-1);   //-ve
            }
        }

//        mExampleList.add(new ExampleItem(R.drawable.image,"Line 1", "Line 2"));
//        mExampleList.add(new ExampleItem(R.drawable.image,"Line 3", "Line 4"));

    }

    //Create recycler view
    public void buildRecyclerView(){
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExampleAdapter(mExampleList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        progressBar.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}
