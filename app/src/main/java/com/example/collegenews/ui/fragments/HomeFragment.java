package com.example.collegenews.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.collegenews.databinding.FragmentHomeBinding;
import com.example.collegenews.model.NewsModel;
import com.example.collegenews.ui.fragments.adapters.NewsAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;


    List<NewsModel> newsList;


    NewsAdapter academicsadapter;
    NewsAdapter feesadapter;
    NewsAdapter messadapter;
    NewsAdapter hosteladapter;
    List<NewsModel> academicsList;
    List<NewsModel> feesList;
    List<NewsModel> messList;
    List<NewsModel> hostelList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // 4 recycler views
        // 1 - Academics
        // 2- Fee
        // 3- mess
        // 4- hostel
        newsList = new ArrayList<>();
        academicsList = new ArrayList<>();
        feesList = new ArrayList<>();
        hostelList = new ArrayList<>();
        messList = new ArrayList<>();


        academicsadapter = new NewsAdapter(academicsList, getContext());
        feesadapter = new NewsAdapter(feesList, getContext());
        hosteladapter = new NewsAdapter(hostelList, getContext());
        messadapter = new NewsAdapter(messList, getContext());


        initData();
        Toast.makeText(getContext(), "" + newsList.toString(), Toast.LENGTH_SHORT).show();


        //academics rv


        binding.rvAcademics.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvAcademics.setHasFixedSize(true);
        binding.rvAcademics.setAdapter(academicsadapter);


        binding.rvFee.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvFee.setHasFixedSize(true);
        binding.rvFee.setAdapter(feesadapter);


        binding.rvHostel.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvHostel.setHasFixedSize(true);
        binding.rvHostel.setAdapter(hosteladapter);


        binding.rvMess.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvMess.setHasFixedSize(true);
        binding.rvMess.setAdapter(messadapter);


    }

    private void initData() {

//         fetch the response from firebase realtime database/
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("news");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot != null && !snapshot.getValue().equals("")) {
                    List<NewsModel> mlist = new ArrayList<>();

                    List newsRemote = (List) snapshot.getValue();
//                Toast.makeText(getActivity().getBaseContext(), "" + newsRemote.toString(), Toast.LENGTH_SHORT).show();
                    if (newsRemote != null) {
                        for (int index = 0; index < newsRemote.size(); index++) {
                            HashMap<Integer, Object> tempData = new HashMap<>();
                            tempData.putAll((Map<? extends Integer, ? extends Object>) newsRemote.get(index));
                            if (tempData != null) {
                                NewsModel tempNews = new NewsModel();
                                tempNews.setTitle((String) tempData.get("title"));
                                tempNews.setAuthor((String) tempData.get("author"));
                                tempNews.setCategory((String) tempData.get("category"));
                                tempNews.setUploadedat((String) tempData.get("uploadedat"));
                                tempNews.setDescription((String) tempData.get("description"));
                                tempNews.setUrlimage((String) tempData.get("urlimage"));


                                newsList.add(tempNews);

                            }
                        }

                    }

                    for (NewsModel mm : newsList
                    ) {


                        if (mm.getCategory().equals("Academics")) {
                            academicsList.add(mm);
                        } else if (mm.getCategory().equals("Fee")) {
                            feesList.add(mm);

                        } else if (mm.getCategory().equals("Hostel")) {
                            hostelList.add(mm);

                        } else if (mm.getCategory().equals("Mess")) {
                            messList.add(mm);


                        }
                    }


                    academicsadapter.notifyDataSetChanged();
                    feesadapter.notifyDataSetChanged();
                    hosteladapter.notifyDataSetChanged();
                    messadapter.notifyDataSetChanged();

//                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }
}