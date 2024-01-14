package com.example.e_bilot;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.e_bilot.MovieGetter.MovieGetterImageCallback;

import org.w3c.dom.Text;

import java.util.List;

public class MovieAdapter extends BaseAdapter {
    private List<Movie> movies;
    private LayoutInflater inflater;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.movies = movies;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_movie_list_item, parent, false);
            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.movieImageView);
            holder.textView = convertView.findViewById(R.id.movieNameTextView);
            holder.imdbScoreTextView = convertView.findViewById(R.id.imdbScoreTextView);
            holder.idGenre = convertView.findViewById(R.id.idGenre);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Movie movie = movies.get(position);
        holder.textView.setText(movie.getName());
        holder.imdbScoreTextView.setText(String.valueOf(movie.getImdbScore()));
        holder.idGenre.setText(String.valueOf(movie.getGenres()));

        // Resmi yükle
        if (movie.getBannerPath() != null && !movie.getBannerPath().isEmpty()) {
            loadMovieImage(movie.getBannerPath(), holder.imageView);
        }

        return convertView;
    }

    private static class ViewHolder {
        ImageView imageView;
        TextView textView;
        TextView idGenre;
        TextView imdbScoreTextView;
    }

    private void loadMovieImage(String path, ImageView imageView) {
        MovieGetter movieGetter = new MovieGetter();
        movieGetter.downloadImage(path, new MovieGetterImageCallback() {
            @Override
            public void onImageDownloaded(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }

            @Override
            public void onFailure(String errorMessage) {
                // Hata durumunda buraya düşer
            }
        });
    }
}
