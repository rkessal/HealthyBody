package com.iutbm.example.iutbm.couchot.healthybody.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iutbm.example.iutbm.couchot.healthybody.R;


public class BeginingFragment extends Fragment {
    private TextView tv;

    public BeginingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.begining, container, false);

        return rootView;
    }

}
