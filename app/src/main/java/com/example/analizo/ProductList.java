package com.example.analizo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;

import androidx.appcompat.app.AppCompatActivity;

public class ProductList extends AppCompatActivity {

    //Animation for image buttons
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
    }

    //Open the corresponding movie detail and reviews
    public void onClick(View view) {

        //Animation on click
        view.startAnimation(buttonClick);

        //Pass movie tag (1,2,3,...) and id(big_hero, frozen,...) with intent to be used in the next activity for data retrieval
        String tag=(String) view.getTag();

        //First get integer id (resource id) of image view which is clicked and then string id (which we assigned in xml) is retrieved using that integer id
        int id=view.getId();
        String movieId="";
        movieId=view.getResources().getResourceEntryName(id);

        //Open next activity
        Intent intent = new Intent(ProductList.this, ProductDetail.class);
//        intent.putExtra("tag", tag);
        intent.putExtra("movieId", movieId);
        startActivity(intent);
    }

//    public void onClick2(View view) {
//        Intent intent = new Intent(getApplicationContext(), ProductDetail.class);
//        Products product2=products.get(1);
//        key=product2.getProductId();
//        intent.putExtra(key,product2);
//        startActivity(intent);
//    }
//
//    public void onClick3(View view) {
//        Intent intent = new Intent(getApplicationContext(), ProductDetail.class);
//        Products product3=products.get(2);
//        key=product3.getProductId();
//        intent.putExtra(product3.getProductId(),product3);
//        startActivity(intent);
//    }


//    public void addOnClick(View view) {
//        Intent intent = new Intent(ProductList.this, AddProductToDatabase.class);
//        startActivity(intent);
//    }
}
