package com.example.e_bilot;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class MovieGetter {
    FirebaseFirestore database;
    FirebaseStorage storage;
    public MovieGetter() {
        database = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
    }

    public interface MovieGetterCallback{
        View onMovieReceived(Movie movie);
        void onFailure(String errorMessage);
    }

    public interface MovieGetterImageCallback{
        void onImageDownloaded(Bitmap bitmap);
        void onFailure(String errorMessage);
    }

    public interface MoviesGetterCallback{
        void onMoviesReceived(Movie[] movies);
        void onFailure(String errorMessage);
    }

    public void getMovieById(String document, String id, MovieGetterCallback callback){
        DocumentReference documentReference = database.collection(document).document(id);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot snapshot = task.getResult();
                    if (snapshot.exists()){
                        Movie movie = snapshot.toObject(Movie.class);
                        Log.d("MovieGetter", movie.toString());
                        callback.onMovieReceived(movie);
                    }else{
                        callback.onFailure("No data has been returned.");
                    }
                } else{
                    callback.onFailure(task.getException().getMessage());
                }
            }
        });
    }

    public void getAllMovies(String document, MoviesGetterCallback callback){
        CollectionReference collectionReference = database.collection(document);
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    List<Movie> moviesList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Movie movie = document.toObject(Movie.class);
                        moviesList.add(movie);
                    }
                    Movie[] movies = moviesList.toArray(new Movie[0]);
                    callback.onMoviesReceived(movies);
                } else{
                    callback.onFailure(task.getException().getMessage());
                }
            }
        });
    }


    public void downloadImage(String path, MovieGetterImageCallback callback){
        StorageReference ref = storage.getReference(path);
        ref.getBytes(8*1024*1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                //imgView.setImageBitmap(bitmap);
                callback.onImageDownloaded(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("MovieGetter", "onFailure: ", e);
                callback.onFailure(e.getMessage());
            }
        });
    }
}
