package com.example.analizo;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//To connect all the xml and java code for recycler view
public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {

    static int position;
    private ArrayList<ExampleItem> mExampleList;

    public ExampleAdapter(ArrayList<ExampleItem> exampleList){
        mExampleList = exampleList;
        position=0;
    }
    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        ExampleItem currentItem = mExampleList.get(position);
        holder.mImageView.setImageResource(currentItem.getmImageResource());
        holder.mTextView1.setText(currentItem.getmText1());
        holder.mTextView2.setText(currentItem.getmText2());

    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;

        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            mTextView1 = itemView.findViewById(R.id.textView1);
            mTextView2 = itemView.findViewById(R.id.textView2);


//            String id=mImageView.getResources().getResourceEntryName(mImageView.getId());
//            Integer resourceId=mImageView.getResources().getIdentifier(id,"drawable","com.example.analizo");

            if(ProductReviews.imageTag.get(position)==1){
                mTextView2.setTextColor(Color.parseColor("#DA3D31"));
            }
            else if(ProductReviews.imageTag.get(position)==-1){
                mTextView2.setTextColor(Color.parseColor("#4CAF50"));
            }

            position++;

        }
    }


}
