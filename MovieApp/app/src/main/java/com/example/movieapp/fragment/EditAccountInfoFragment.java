package com.example.movieapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieapp.databinding.FragmentEditAccountInfoBinding;

public class EditAccountInfoFragment extends Fragment {

    FragmentEditAccountInfoBinding binding;

    public EditAccountInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEditAccountInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}