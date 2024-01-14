package com.example.e_bilot;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class UserGetter {
    FirebaseFirestore database;

    public UserGetter() {
        database = FirebaseFirestore.getInstance();
    }

    public interface UserGetterCallback{
        void onUserReceived(User user);
        void onFailure(String errorMessage);
    }

    public void getUserById(String id, UserGetter.UserGetterCallback callback){
        if (id == null){
            return;
        }
        database.collection("userInfos").get().addOnCompleteListener(task -> {
            for (QueryDocumentSnapshot document : task.getResult()){
                if (document.getString("userId").equals(id)){
                    DocumentReference documentReference = database.collection("userInfos").document(document.getId());
                    documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()){
                                DocumentSnapshot snapshot = task.getResult();
                                if (snapshot.exists()){
                                    User user = snapshot.toObject(User.class);
                                    Log.d("UserGetter", user.toString());
                                    callback.onUserReceived(user);
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
        });
    }


}
