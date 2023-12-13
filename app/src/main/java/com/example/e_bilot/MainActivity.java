package com.example.e_bilot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.e_bilot.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    final int HOME_ID = R.id.home;
    final int SAVED_MOVIES_ID = R.id.saved_movies;
    final int MOVIES_ID = R.id.movies;
    final int SETTINGS_ID = R.id.settings;
    final int PROFILE_ID = R.id.profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == HOME_ID){
                replaceFragment(new MovieDetailFragment());
            }
            else if(itemId == SAVED_MOVIES_ID){
                replaceFragment(new SeatChoosingFragment());
            }
            else if(itemId == MOVIES_ID){
                replaceFragment(new PaymentFragment());
            }
            else if(itemId == SETTINGS_ID){

            }
            else if(itemId == PROFILE_ID){

            }

            return true;
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

}