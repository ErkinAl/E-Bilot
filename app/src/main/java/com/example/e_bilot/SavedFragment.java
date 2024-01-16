package com.example.e_bilot;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.e_bilot.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// ERKİN ALKAN
public class SavedFragment extends Fragment {

    private int movieId;

    public SavedFragment() {
        // Required empty public constructor
    }

    // ERKİN ALKAN
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved_item, container, false);

        // This code part catchs the information that stored in bundle
        Bundle bundle = getArguments();
        if (bundle != null){
            movieId = bundle.getInt("movieId");
        } else {
            Log.e("SavedFragment", "Movie data is empty");
        }

        MovieGetter getter = new MovieGetter();
        getter.getMovieById("movies", String.valueOf(movieId), new MovieGetter.MovieGetterCallback() {

            // ERKİN ALKAN
            @Override
            public View onMovieReceived(Movie movie) {
                TextView movieName = view.findViewById(R.id.movieSavedTextView);
                TextView genres = view.findViewById(R.id.idSavedGenre);
                TextView imdbScore = view.findViewById(R.id.imdbSavedScoreTextView);
                ImageView banner = view.findViewById(R.id.movieSavedImageView);

                movieName.setText(movie.getName());
                genres.setText(movie.getGenres());
                imdbScore.setText(String.valueOf(movie.getImdbScore()));

                // This code part downloads the banner image of the movie
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

            // ERKİN ALKAN
            @Override
            public void onFailure(String errorMessage) {

            }
        });
        return view;
    }

}
