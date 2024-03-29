package com.example.e_bilot;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

// DENİZ BİLGİN
// This class is fundamental movie class of the algorithm
public class Movie implements Parcelable {
    private int movieId;
    private String name;
    private String genres;
    private float imdbScore;
    private String description;
    private String bannerPath;

    private String occupiedSeats;

    public Movie(){

    }

    // DENİZ BİLGİN
    public Movie(Parcel in) {
        movieId = in.readInt();
        name = in.readString();
        genres = in.readString();
        imdbScore = in.readFloat();
        description = in.readString();
        bannerPath = in.readString();
        occupiedSeats = in.readString();
    }

    // DENİZ BİLGİN
    @NonNull
    @Override
    public String toString() {
        return this.movieId + " - " + this.name + "named movie's imdb score is:" + this.imdbScore;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public float getImdbScore() {
        return imdbScore;
    }

    public void setImdbScore(float imdbScore) {
        this.imdbScore = imdbScore;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBannerPath() {
        return bannerPath;
    }

    public void setBannerPath(String bannerPath) {
        this.bannerPath = bannerPath;
    }

    public String getOccupiedSeats() {
        return occupiedSeats;
    }

    public void setOccupiedSeats(String selectedSeats) {
        this.occupiedSeats = selectedSeats;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // DENİZ BİLGİN
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(movieId);
        dest.writeString(name);
        dest.writeString(genres);
        dest.writeFloat(movieId);
        dest.writeString(description);
        dest.writeString(bannerPath);
    }
}
