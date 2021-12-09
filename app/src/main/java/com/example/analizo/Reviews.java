package com.example.analizo;

public class Reviews {

    //Attributes for each review
    private String reviewID;    //Firebase generated id
    private String review;
    private double pos_sentiment;
    private double neg_sentiment;

    //Constructors
    public Reviews() {
    }

    public Reviews(String reviewID, String review, double pos_sentiment, double neg_sentiment) {
        this.reviewID = reviewID;
        this.review = review;
        this.pos_sentiment = pos_sentiment;
        this.neg_sentiment = neg_sentiment;
    }

    //Getters and setters
    public String getReviewID() {
        return reviewID;
    }

    public void setReviewID(String reviewID) {
        this.reviewID = reviewID;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public double getPos_sentiment() {
        return pos_sentiment;
    }

    public void setPos_sentiment(double pos_sentiment) {
        this.pos_sentiment = pos_sentiment;
    }

    public double getNeg_sentiment() {
        return neg_sentiment;
    }

    public void setNeg_sentiment(double neg_sentiment) {
        this.neg_sentiment = neg_sentiment;
    }
}
