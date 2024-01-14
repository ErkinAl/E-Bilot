package com.example.e_bilot;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterFragment extends Fragment {

    EditText email;
    EditText password;
    EditText name;
    EditText surname;
    EditText age;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch isMale;

    private FirebaseFirestore database = FirebaseFirestore.getInstance();

    private CollectionReference userInfosReference = database.collection("userInfos");
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();



    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        email = view.findViewById(R.id.registerEmail);
        password = view.findViewById(R.id.registerPassword);
        name = view.findViewById(R.id.registerName);
        surname = view.findViewById(R.id.registerSurname);
        age = view.findViewById(R.id.registerAge);
        isMale = view.findViewById(R.id.registerSwitchGender);

        Button registerConfirmButton = view.findViewById(R.id.buttonRegisterConfirm);

        registerConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailString = email.getText().toString();
                String passwordString = password.getText().toString();
                String nameString = name.getText().toString();
                String surnameString = surname.getText().toString();
                int ageNum = Integer.parseInt(age.getText().toString());
                Boolean isMaleBoolean = isMale.isChecked();

                performRegistration(emailString, passwordString, nameString, surnameString, ageNum, isMaleBoolean);
            }
        });
        return view;
    }

    private void performRegistration(String emailS, String passwordS, String nameS, String surnameS, int ageI, boolean isMaleB){
        firebaseAuth.createUserWithEmailAndPassword(emailS, passwordS)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()){
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null){
                            addUserToFirestore(user.getUid(), emailS, passwordS,nameS,surnameS,ageI,isMaleB);
                            Toast.makeText(getActivity().getApplicationContext(), "Successfully registered.", Toast.LENGTH_SHORT).show();
                            loginUserFromRegisterFragment(emailS, passwordS);
                        }
                    } else{
                        Log.e("RegisterFragment", "User can not added to firestore. Error:"+task.getException().getMessage());
                    }
                });
    }

    private void addUserToFirestore(String userId, String email, String password, String name, String surname, int age, boolean isMale) {
        Map<String, Object> user = new HashMap<>();
        user.put("userId", userId);
        user.put("email", email);
        user.put("password", password);
        user.put("name", name);
        user.put("surname", surname);
        user.put("age", age);
        user.put("isMale", isMale);

        userInfosReference.add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("RegisterFragment", "User added to firestore");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("RegisterFragment", "User can not added to firestore. Error:"+ e.getMessage());
            }
        });
    }

    private void loginUserFromRegisterFragment(String email, String password){
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), task -> {
                    FirebaseUser user = firebaseAuth.getCurrentUser();

                    HomeFragment homeFragment = new HomeFragment();
                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

                    transaction.replace(R.id.fragment_register, homeFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                });
    }
}