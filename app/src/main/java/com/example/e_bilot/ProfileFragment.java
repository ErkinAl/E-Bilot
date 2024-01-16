package com.example.e_bilot;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

// DENİZ BİLGİN
public class ProfileFragment extends Fragment {
    private User currentUser;

    public ProfileFragment() {
        // Required empty public constructor
    }

    // DENİZ BİLGİN
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // DENİZ BİLGİN
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // This code part implements auth process (Because there are some bugs, we could not add the auth process to the app)

        /*FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        UserGetter userGetter = new UserGetter();
        userGetter.getUserById(firebaseAuth.getCurrentUser().getUid(), new UserGetter.UserGetterCallback() {
            @Override
            public void onUserReceived(User user) {
                currentUser = user;
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("ProfileFragment", errorMessage);
            }
        });

        TextView userNameTextView = view.findViewById(R.id.profileName);
        TextView userEmailTextView = view.findViewById(R.id.profileEmail);

        userNameTextView.setText(currentUser.getName().toString() + " " + currentUser.getSurname().toString());
        userEmailTextView.setText(currentUser.getEmail().toString());*/


        // This function handles logging out when button is clicked
        Button logOutButton = view.findViewById(R.id.logOutButton);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment homeFragment = new HomeFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

                transaction.replace(R.id.fragment_profile, homeFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;

    }
}