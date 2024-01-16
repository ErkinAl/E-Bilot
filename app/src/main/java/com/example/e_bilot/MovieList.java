package com.example.e_bilot;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.e_bilot.MovieGetter.MoviesGetterCallback;

import java.util.Arrays;
import java.util.List;

// ERKİN ALKAN
public class MovieList extends Fragment {
    ListView listView;
    MovieAdapter adapter;
    Movie[] movieData;

    public MovieList() {
    }

    // ERKİN ALKAN
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MovieGetter movieGetter = new MovieGetter();
        movieGetter.getAllMovies("movies", new MoviesGetterCallback() {
            @Override
            public void onMoviesReceived(Movie[] movies) {
                movieData = movies;

                if (getActivity() != null) {
                    List<Movie> movieList = Arrays.asList(movieData);
                    adapter = new MovieAdapter(getActivity(), movieList);

                    if (listView != null && adapter != null) {
                        listView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("MovieList", errorMessage);
            }
        });
    }

    // ERKİN ALKAN
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        // This code part implements clicking a movie from the list then replaces the fragment to movie detail fragment
        listView = view.findViewById(R.id.movieListView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (adapter != null && movieData != null && position < movieData.length) {
                    int selectedMovieId = movieData[position].getMovieId();

                    showMovieDetails(selectedMovieId);
                }
            }
        });

        return view;
    }

    // ERKİN ALKAN
    // This code part handles routing to movie detail fragment by movie id
    private void showMovieDetails(int movieId) {
        MovieDetailFragment movieDetailFragment = MovieDetailFragment.newInstance(movieId);
        MovieDetailFragment currentFragment = MovieDetailFragment.newInstance(R.id.idMovieList);

        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.idMovieList, movieDetailFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}