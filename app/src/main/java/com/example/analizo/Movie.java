package com.example.analizo;

public class Movie {
    //Attributes for each movie
    private String movieId;    //Firebase generated id
    private int tag;    //Tag associated with each movie in xml
    private String stringId;    //string id containing name (image id in xml, name for storing movie in firebase)
    private String name;
    private int year;   //Year of release
    private String genre;
    private String synopsis;
    private int imageId;    //Image integer id in drawable
    private int posReviews;
    private int negReviews;

    //Constructors
    public Movie(){
    }

    public Movie(String movieId, int tag, String stringId, String name, int year, String genre, String synopsis, int imageId, int posReviews, int negReviews) {
        this.movieId = movieId;
        this.tag = tag;
        this.stringId = stringId;
        this.name = name;
        this.year = year;
        this.genre = genre;
        this.synopsis = synopsis;
        this.imageId = imageId;
        this.posReviews = posReviews;
        this.negReviews = negReviews;
    }

    //Getters and setters
    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public String getStringId() {
        return stringId;
    }

    public void setStringId(String stringId) {
        this.stringId = stringId;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getPosReviews() {
        return posReviews;
    }

    public void setPosReviews(int posReviews) {
        this.posReviews = posReviews;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public int getNegReviews() {
        return negReviews;
    }

    public void setNegReviews(int negReviews) {
        this.negReviews = negReviews;
    }
}
