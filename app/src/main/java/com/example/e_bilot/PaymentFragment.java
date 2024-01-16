package com.example.e_bilot;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// DENİZ BİLGİN
public class PaymentFragment extends Fragment {
    private Movie selectedMovie;
    private String[] selectedSeats;
    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    private CollectionReference reservationsReference = database.collection("reservations");

    public PaymentFragment() {
        // Required empty public constructor
    }

    // DENİZ BİLGİN
    public static PaymentFragment newInstance(Movie movie) {
        PaymentFragment fragment = new PaymentFragment();
        Bundle args = new Bundle();
        args.putParcelable("movieData", movie);
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
        View view = inflater.inflate(R.layout.fragment_payment, container, false);

        // This code part handles catching info from the bundle
        Bundle bundle = getArguments();
        if (bundle != null){
            selectedMovie = bundle.getParcelable("movieData");
            selectedSeats = bundle.getString("selectedSeats").split(",");
        } else {
            Log.e("PaymentFragment", "Movie data is empty");
        }
        
        TextView selectedSeatsEditText = view.findViewById(R.id.selectedSeatsPayment);
        selectedSeatsEditText.setText(joinSelectedSeats(selectedSeats));

        Button buyButton = view.findViewById(R.id.buyButtonPayment);
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText ownerName = view.findViewById(R.id.cardOwnerNameSurname);
                EditText cardNumber = view.findViewById(R.id.cardNumber);
                EditText expirationMonth = view.findViewById(R.id.expirationMonth);
                EditText expirationYear = view.findViewById(R.id.expirationYear);
                EditText cvc = view.findViewById(R.id.cvc);

                Map<String, Object> reservation = new HashMap<>();
                reservation.put("ownerName", ownerName.getText().toString());
                reservation.put("cardNumber", Integer.parseInt(cardNumber.getText().toString()));
                reservation.put("expirationMoth", Integer.parseInt(expirationMonth.getText().toString()));
                reservation.put("expirationYear", Integer.parseInt(expirationYear.getText().toString()));
                reservation.put("cvc", Integer.parseInt(cvc.getText().toString()));

                Date currentDate = new Date();
                Timestamp timestamp = new Timestamp(currentDate);
                reservation.put("reservationDate", timestamp);
                reservation.put("movieId", selectedMovie.getMovieId());
                reservation.put("selectedSeats", bundle.getString("selectedSeats"));
                reservation.put("userId",1);

                // This code part implements adding reservation to the firebase firestore
                reservationsReference.add(reservation).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("PaymentFragment", "Reservation successfully added. ID: " + documentReference.getId());
                        showToastMessage("The ticket successfully reserved.");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("PaymentFragment", "Reservation can not added", e);
                        showToastMessage("There is an error.");
                    }
                });

                // This part implements replacing fragment to the movie detail fragment when buying ticket process is successful
                MovieGetter movieGetter = new MovieGetter();
                movieGetter.updateOccupiedSeats(bundle.getString("selectedSeats"), selectedMovie.getMovieId());

                MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_payment, movieDetailFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }

    // DENİZ BİLGİN
    // This function joins selectedSeats variable as a string
    private String joinSelectedSeats(String[] selectedSeats){
        String result = "";
        for (String seat : selectedSeats) {
            result += seat + "-";
        }
        result = result.substring(0, result.lastIndexOf("-"));
        return result;
    }

    // DENİZ BİLGİN
    // This function handles showing a toast message to the screen
    private void showToastMessage(String message){
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}