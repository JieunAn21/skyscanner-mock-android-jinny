package com.jeahn.skyscanner.src.flights.flightsSearchTab;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jeahn.skyscanner.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoundTripFragment extends Fragment {


    public RoundTripFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_round_trip, container, false);

        return view;
    }

}