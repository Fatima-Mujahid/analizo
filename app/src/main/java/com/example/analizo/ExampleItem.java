package com.example.analizo;

//Class associated with the xml for displaying review which will be repeated inside recycler view according to number of reviews
public class ExampleItem {
    private int mImageResource;
    private String mText1;
    private String mText2;

    public ExampleItem(int imageResource, String text1, String text2){
        mImageResource = imageResource;
        mText1 = text1;
        mText2 = text2;
    }

    public int getmImageResource(){
        return mImageResource;
    }

    public String getmText1() {
        return mText1;
    }

    public String getmText2() {
        return mText2;
    }


}
