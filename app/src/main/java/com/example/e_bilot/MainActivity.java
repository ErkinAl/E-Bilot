package com.example.e_bilot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.e_bilot.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    final int HOME_ID = R.id.home;
    final int SAVED_MOVIES_ID = R.id.saved_movies;
    final int MOVIES_ID = R.id.movies;
    final int SETTINGS_ID = R.id.settings;
    final int PROFILE_ID = R.id.profile;
    FirebaseAuth firebaseAuth;
    UserGetter userGetter;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == HOME_ID){
                replaceFragment(new HomeFragment());
            }
            else if(itemId == SAVED_MOVIES_ID){
                replaceFragment(new MovieDetailFragment());
            }
            else if(itemId == MOVIES_ID){
                replaceFragment(new MovieList());
            }
            else if(itemId == SETTINGS_ID){
                replaceFragment(new AboutUs());
            }
            else if(itemId == PROFILE_ID){
                /*
                firebaseAuth = FirebaseAuth.getInstance();
                userGetter = new UserGetter();
                if (currentUser == null){
                    replaceFragment(new LoginFragment());
                    Toast.makeText(getApplicationContext(), "Please log in.", Toast.LENGTH_LONG).show();
                } else{
                    userGetter.getUserById(firebaseAuth.getCurrentUser().getUid(), new UserGetter.UserGetterCallback() {
                        @Override
                        public void onUserReceived(User user) {
                            currentUser = user;
                            Log.d("BBBBB", currentUser.toString());
                        }

                        @Override
                        public void onFailure(String errorMessage) {
                            Log.e("MainActivity", errorMessage);
                        }
                    });

                    replaceFragment(new ProfileFragment());
                }*/
                replaceFragment(new ProfileFragment());
            }
            return true;
        });
    }

    @Override
    protected void onDestroy() {
        Log.d("SIGN OUT", firebaseAuth.getCurrentUser().getUid() + " signed out");
        firebaseAuth.signOut();
        super.onDestroy();
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

}