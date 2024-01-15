package com.example.e_bilot;

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

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        MovieGetter movieGetter = new MovieGetter();
        movieGetter.getMovieById("movies", "1", new MovieGetter.MovieGetterCallback() {
            @Override
            public View onMovieReceived(Movie movie) {
                ImageView recommended1Image = view.findViewById(R.id.recommendedMovie1);
                Button reccomend1Button = view.findViewById(R.id.buttonRecommended1);
                reccomend1Button.setText(movie.getName());

                movieGetter.downloadImage(movie.getBannerPath(), new MovieGetter.MovieGetterImageCallback() {
                    @Override
                    public void onImageDownloaded(Bitmap bitmap) {
                        recommended1Image.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        Log.e("HomeFragment", errorMessage);
                    }
                });

                reccomend1Button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
                        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

                        Bundle bundle = new Bundle();
                        bundle.putInt("argMovieId", movie.getMovieId());
                        movieDetailFragment.setArguments(bundle);

                        transaction.replace(R.id.frame_layout_home, movieDetailFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });

                return null;
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("HomeFragment", errorMessage);
            }
        });
        movieGetter.getMovieById("movies", "2", new MovieGetter.MovieGetterCallback() {
            @Override
            public View onMovieReceived(Movie movie) {
                ImageView recommended2Image = view.findViewById(R.id.recommendedMovie2);
                Button reccomend2Button = view.findViewById(R.id.buttonRecommended2);
                reccomend2Button.setText(movie.getName());

                movieGetter.downloadImage(movie.getBannerPath(), new MovieGetter.MovieGetterImageCallback() {
                    @Override
                    public void onImageDownloaded(Bitmap bitmap) {
                        recommended2Image.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        Log.e("HomeFragment", errorMessage);
                    }
                });

                reccomend2Button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
                        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

                        Bundle bundle = new Bundle();
                        bundle.putInt("argMovieId", movie.getMovieId());
                        movieDetailFragment.setArguments(bundle);

                        transaction.replace(R.id.frame_layout_home, movieDetailFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });

                return null;
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("HomeFragment", errorMessage);
            }
        });
        movieGetter.getMovieById("movies", "3", new MovieGetter.MovieGetterCallback() {
            @Override
            public View onMovieReceived(Movie movie) {
                ImageView recommended3Image = view.findViewById(R.id.recommendedMovie3);
                Button reccomend3Button = view.findViewById(R.id.buttonRecommended3);
                reccomend3Button.setText(movie.getName());

                movieGetter.downloadImage(movie.getBannerPath(), new MovieGetter.MovieGetterImageCallback() {
                    @Override
                    public void onImageDownloaded(Bitmap bitmap) {
                        recommended3Image.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        Log.e("HomeFragment", errorMessage);
                    }
                });

                reccomend3Button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
                        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

                        Bundle bundle = new Bundle();
                        bundle.putInt("argMovieId", movie.getMovieId());
                        movieDetailFragment.setArguments(bundle);

                        transaction.replace(R.id.frame_layout_home, movieDetailFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });

                return null;
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("HomeFragment", errorMessage);
            }
        });
        movieGetter.getMovieById("movies", "4", new MovieGetter.MovieGetterCallback() {
            @Override
            public View onMovieReceived(Movie movie) {
                ImageView recommended4Image = view.findViewById(R.id.recommendedMovie4);
                Button reccomend4Button = view.findViewById(R.id.buttonRecommended4);
                reccomend4Button.setText(movie.getName());

                movieGetter.downloadImage(movie.getBannerPath(), new MovieGetter.MovieGetterImageCallback() {
                    @Override
                    public void onImageDownloaded(Bitmap bitmap) {
                        recommended4Image.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        Log.e("HomeFragment", errorMessage);
                    }
                });

                reccomend4Button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
                        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

                        Bundle bundle = new Bundle();
                        bundle.putInt("argMovieId", movie.getMovieId());
                        movieDetailFragment.setArguments(bundle);

                        transaction.replace(R.id.frame_layout_home, movieDetailFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });

                return null;
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("HomeFragment", errorMessage);
            }
        });

        ImageButton buttonAboutUs = view.findViewById(R.id.buttonAboutUs);
        buttonAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutUs aboutUsFragment = new AboutUs();

                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout_home, aboutUsFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }
}