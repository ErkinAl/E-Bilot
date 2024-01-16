package com.example.e_bilot;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

// ERKİN ALKAN, DENİZ BİLGİN
public class MovieDetailFragment extends Fragment {

    private static final String ARG_MOVIE_ID = "argMovieId";

    private int movieId;

    public MovieDetailFragment() {
        // Required empty public constructor
    }

    // DENİZ BİLGİN, ERKİN ALKAN
    public static MovieDetailFragment newInstance(int movieId) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_MOVIE_ID, movieId);
        fragment.setArguments(args);
        return fragment;
    }

    // ERKİN ALKAN
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieId = getArguments().getInt(ARG_MOVIE_ID);
        }
    }

    // DENİZ BİLGİN
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        // DENİZ BİLGİN
        // This code part uses thread subject, it show a loading screen to the screen while movie is loading
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Movie is loading..");
        progressDialog.show();

        MovieGetter getter = new MovieGetter();

        getter.getMovieById("movies", String.valueOf(movieId), new MovieGetter.MovieGetterCallback() {
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

                progressDialog.dismiss();

                // This function downloads image of the movie from firestore storage
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

                // This function sends the data to seat choosing fragment and replaces the fragment
                Button buyButton = view.findViewById(R.id.buyButtonMovieDetail);
                buyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SeatChoosingFragment seatChoosingFragment = new SeatChoosingFragment();
                        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

                        Bundle bundle = new Bundle();
                        bundle.putParcelable("movieData", movie);
                        seatChoosingFragment.setArguments(bundle);

                        transaction.replace(R.id.fragment_movie_detail, seatChoosingFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });

                // This code part implements movie saving process
                ImageButton bookMarkButton = view.findViewById(R.id.bookmarkIcon);
                bookMarkButton.setOnClickListener(new View.OnClickListener() {

                    // ERKİN ALKAN, DENİZ BİLGİN
                    @Override
                    public void onClick(View v) {
                        SavedFragment savedFragment = new SavedFragment();
                        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

                        Bundle bundle = new Bundle();
                        bundle.putInt("movieId",movieId);
                        savedFragment.setArguments(bundle);

                        transaction.replace(R.id.fragment_movie_detail, savedFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();

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