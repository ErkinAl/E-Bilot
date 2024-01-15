package com.example.e_bilot;

import android.os.Bundle;
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

public class MovieList extends Fragment {
    ListView listView;
    MovieAdapter adapter;
    Movie[] movieData;

    public MovieList() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Firebase'den filmleri getir ve listeyi güncelle
        MovieGetter movieGetter = new MovieGetter();
        movieGetter.getAllMovies("movies", new MoviesGetterCallback() {
            @Override
            public void onMoviesReceived(Movie[] movies) {
                movieData = movies;

                // Verileri aldıktan sonra adapter'ı oluştur ve ListView'a set et
                if (getActivity() != null) {
                    List<Movie> movieList = Arrays.asList(movieData); // Movie[]'yi List<Movie>'ye çevir
                    adapter = new MovieAdapter(getActivity(), movieList);

                    // ListView ve adapter null değilse set et
                    if (listView != null && adapter != null) {
                        listView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                // Hata
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        listView = view.findViewById(R.id.movieListView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Tıklanan filmin ID'sini al
                if (adapter != null && movieData != null && position < movieData.length) {
                    int selectedMovieId = movieData[position].getMovieId();

                    // MovieDetailFragment'ı başlat ve tıklanan filmin ID'sini ileterek göster
                    showMovieDetails(selectedMovieId);
                }
            }
        });

        return view;
    }

    private void showMovieDetails(int movieId) {
        MovieDetailFragment movieDetailFragment = MovieDetailFragment.newInstance(movieId);
        MovieDetailFragment currentFragment = MovieDetailFragment.newInstance(R.id.idMovieList);

        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.idMovieList, movieDetailFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}