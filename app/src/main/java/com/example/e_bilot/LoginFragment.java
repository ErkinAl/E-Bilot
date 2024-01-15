package com.example.e_bilot;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button loginButton;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user;

    public LoginFragment() {
        // Required empty public constructor
    }
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        editTextEmail = view.findViewById(R.id.loginEmail);
        editTextPassword = view.findViewById(R.id.loginPassword);
        loginButton = view.findViewById(R.id.buttonLoginConfirm);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                loginUser(email, password);
                UserGetter userGetter = new UserGetter();
                Bundle bundle = new Bundle();
                HomeFragment homeFragment = new HomeFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

                userGetter.getUserById(firebaseAuth.getCurrentUser().getUid(), new UserGetter.UserGetterCallback() {
                    @Override
                    public void onUserReceived(User user) {
                        Log.d("LoginFragment", user.toString());
                        bundle.putParcelable("userData", user);
                        homeFragment.setArguments(bundle);
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        Log.d("LoginFragment", errorMessage);
                    }
                });

                transaction.replace(R.id.fragment_login, homeFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }

    private void loginUser(String email, String password){
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()){
                        user = firebaseAuth.getCurrentUser();
                        Toast.makeText(getActivity().getApplicationContext(), user.getEmail() + " mailed user logged in.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireContext(), "Can not logged in. Please check your informations", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}