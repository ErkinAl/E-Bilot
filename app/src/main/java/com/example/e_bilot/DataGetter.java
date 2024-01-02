package com.example.e_bilot;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListUtil;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.*;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DataGetter {
    FirebaseFirestore database;
    public DataGetter() {
        database = FirebaseFirestore.getInstance();
    }

    public void getMovieById(String document, String id){
        DocumentReference documentReference = database.collection(document).document(id);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot snapshot = task.getResult();
                    if (snapshot.exists()){
                        Movie movie = snapshot.toObject(Movie.class);
                        Log.d("DataGetter", movie.toString());
                    }else{
                        Log.d("DataGetter", "No data has been returned.");
                    }
                }
            }
        });
    }
}
