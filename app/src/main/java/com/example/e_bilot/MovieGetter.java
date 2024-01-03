package com.example.e_bilot;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MovieGetter {
    FirebaseFirestore database;
    public MovieGetter() {
        database = FirebaseFirestore.getInstance();
    }

    public interface MovieGetterCallback{
        View onMovieReceived(Movie movie);
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
}
