package com.example.e_bilot;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.Arrays;

public class SeatChoosingFragment extends Fragment {
    private Movie selectedMovie;
    private EditText userInput;

    public SeatChoosingFragment() {
        // Required empty public constructor
    }
    public static SeatChoosingFragment newInstance(Movie movie) {
        SeatChoosingFragment fragment = new SeatChoosingFragment();
        Bundle args = new Bundle();
        args.putParcelable("movieData", movie);
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
        View view = inflater.inflate(R.layout.fragment_seat_choosing, container, false);

        Bundle bundle = getArguments();
        if (bundle != null){
            selectedMovie = bundle.getParcelable("movieData");
        } else {
            Log.e("SeatChoosingFragment", "Movie data is empty");
        }

        Log.d("SeatChoosingFragment", selectedMovie.getOccupiedSeats());
        String[] occupiedSeats = selectedMovie.getOccupiedSeats().split(",");
        String[] rowNames = new String[]{"A","B","C","D","E","F","G","H"};
        TableLayout tableLayout = view.findViewById(R.id.seatsTable);

        for (String seat : occupiedSeats) {
            char row = seat.charAt(0);
            int column = Integer.parseInt(seat.substring(1));

            int rowIndex = Arrays.asList(rowNames).indexOf(String.valueOf(row));
            int columnIndex = column - 1;

            if(tableLayout.getChildAt(rowIndex) != null){
                TableRow rowLayout = (TableRow) tableLayout.getChildAt(rowIndex);
                View seatView = rowLayout.getChildAt(columnIndex+1);

                if (!(seatView instanceof ImageView)) {
                    continue;
                }

                ImageView seatImageView = (ImageView) seatView;
                seatImageView.setImageResource(R.drawable.baseline_event_seat_reserved_24);
            }
        }

        Button nextButton = view.findViewById(R.id.seatChoosingButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInput = view.findViewById(R.id.seatInput);
                String inputText = userInput.getText().toString();

                if (inputText.equals("")){
                    Toast.makeText(getActivity().getApplicationContext(), "Please select a seat.", Toast.LENGTH_LONG).show();
                } else {
                    if (isSelectedSeatValid(inputText,selectedMovie)){
                        PaymentFragment paymentFragment = new PaymentFragment();
                        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

                        Bundle bundleToPass = new Bundle();
                        bundleToPass.putParcelable("movieData",selectedMovie);
                        bundleToPass.putString("selectedSeats", inputText);
                        paymentFragment.setArguments(bundleToPass);

                        transaction.replace(R.id.fragment_seat_choosing, paymentFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    } else{
                        Toast.makeText(getActivity().getApplicationContext(), "Selected seat is already occupied.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        return view;
    }

    private boolean isSelectedSeatValid(String selectedSeats, Movie selectedMovie){
        String[] occupiedSeats = selectedMovie.getOccupiedSeats().split(",");
        String[] seats = selectedSeats.split(",");
        for (String occupiedSeat : occupiedSeats) {
            for (String selectedSeat : seats) {
                if (occupiedSeat.equals(selectedSeat)){
                    return false;
                }
            }
        }
        return true;
    }
}