package com.example.e_bilot;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieDetailFragment extends Fragment {
    private static final String ARG_MOVIE = "argMovie";

    private Movie movie;

    public MovieDetailFragment() {
        // Required empty public constructor
    }

    public static MovieDetailFragment newInstance(Movie movie) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_MOVIE, movie);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movie = getArguments().getParcelable(ARG_MOVIE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        MovieGetter getter = new MovieGetter();

        getter.getMovieById("movies","4", new MovieGetter.MovieGetterCallback(){

            @Override
            public View onMovieReceived(Movie movie) {
                TextView movieName = view.findViewById(R.id.movieName);
                TextView genres = view.findViewById(R.id.genres);
                TextView imdbScore = view.findViewById(R.id.rateOfTheMovie);
                TextView description = view.findViewById(R.id.movieDescription);
                ImageView banner = view.findViewById(R.id.movieBanner);

                movieName.setText(movie.getName());
                genres.setText(movie.getGenres());
                imdbScore.setText(String.valueOf(movie.getImdbScore()));
                description.setText(movie.getDescription());

                getter.downloadImage(movie.getBannerPath(), new MovieGetter.MovieGetterImageCallback() {
                    @Override
                    public void onImageDownloaded(Bitmap bitmap) {
                        banner.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        Log.e("MovieGetter", errorMessage);
                    }
                });

                return view;

            }

            @Override
            public void onFailure(String errorMessage) {
                Log.d("MovieGetter", errorMessage);
            }
        });
        return view;
    }


}