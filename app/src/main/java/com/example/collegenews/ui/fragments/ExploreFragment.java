package com.example.collegenews.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegenews.databinding.FragmentExploreBinding;
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


public class ExploreFragment extends Fragment {

    private FragmentExploreBinding binding;

    NewsAdapter tnpadapter;
    NewsAdapter sportsadapter;
    NewsAdapter techfestadapter;
    NewsAdapter cultfestadapter;
    NewsAdapter studentwellfareadapter;
    List<NewsModel> newsList;
    List<NewsModel> tandPList;
    List<NewsModel> sportsList;
    List<NewsModel> techinal_FestsList;
    List<NewsModel> cultural_FestsList;
    List<NewsModel> studentWellfare;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentExploreBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        newsList = new ArrayList<>();
        tandPList = new ArrayList<>();
        sportsList = new ArrayList<>();
        techinal_FestsList = new ArrayList<>();
        cultural_FestsList = new ArrayList<>();
        studentWellfare = new ArrayList<>();


        tnpadapter = new NewsAdapter(tandPList, getContext());
        sportsadapter = new NewsAdapter(sportsList, getContext());
        techfestadapter = new NewsAdapter(techinal_FestsList, getContext());
        cultfestadapter = new NewsAdapter(cultural_FestsList, getContext());
        studentwellfareadapter = new NewsAdapter(studentWellfare, getContext());
        initData();


        binding.rvTandP.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        binding.rvTandP.setHasFixedSize(true);
        binding.rvTandP.setAdapter(tnpadapter);

        binding.rvTechinalFests.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        binding.rvTechinalFests.setHasFixedSize(true);
        binding.rvTechinalFests.setAdapter(techfestadapter);

        binding.rvCulturalFestss.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        binding.rvCulturalFestss.setHasFixedSize(true);
        binding.rvCulturalFestss.setAdapter(cultfestadapter);

        binding.rvSports.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        binding.rvSports.setHasFixedSize(true);
        binding.rvSports.setAdapter(sportsadapter);

        binding.rvStudentWellfare.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        binding.rvStudentWellfare.setHasFixedSize(true);
        binding.rvStudentWellfare.setAdapter(studentwellfareadapter);


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


                        if (mm.getCategory().equals("Training and Placements")) {
                            tandPList.add(mm);
                        } else if (mm.getCategory().equals("Sports")) {
                            sportsList.add(mm);

                        } else if (mm.getCategory().equals("Techinal Fests")) {
                            techinal_FestsList.add(mm);

                        } else if (mm.getCategory().equals("Cultural Fests")) {
                            cultural_FestsList.add(mm);


                        } else if (mm.getCategory().equals("Student Wellfare")) {
                            studentWellfare.add(mm);

                        }
                    }


                    tnpadapter.notifyDataSetChanged();
                    sportsadapter.notifyDataSetChanged();
                    techfestadapter.notifyDataSetChanged();
                    cultfestadapter.notifyDataSetChanged();
                    studentwellfareadapter.notifyDataSetChanged();
//                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }

}