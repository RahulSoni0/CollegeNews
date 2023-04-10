package com.example.collegenews.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.collegenews.databinding.FragmentSettingBinding;
import com.example.collegenews.ui.activities.AddNewsActivity;
import com.example.collegenews.ui.activities.AuthActivity;
import com.google.firebase.auth.FirebaseAuth;


public class SettingFragment extends Fragment {

    private FragmentSettingBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSettingBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnCreateNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), AddNewsActivity.class);
                getContext().startActivity(i);


            }
        });

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getActivity().getBaseContext(), AuthActivity.class);
                startActivity(i);
                Toast.makeText(getActivity().getBaseContext(), "Logged out Successfull", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        });


    }
}