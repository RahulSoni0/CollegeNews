package com.example.collegenews.ui.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.collegenews.databinding.ActivityDetailsBinding;
import com.example.collegenews.model.NewsModel;

public class DetailsActivity extends AppCompatActivity {

    ActivityDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getIntent() != null) {
            NewsModel m = (NewsModel) getIntent().getSerializableExtra("newsItem");


            binding.tvNewsTitle.setText(m.getTitle());
            binding.tvNewsDescription.setText(m.getDescription());
            binding.tvNewsAuthor.setText(m.getAuthor());
            binding.tvNewsTimeStamp.setText(m.getUploadedat());

            Glide.with(this).load(m.getUrlimage()).into(binding.ivNewsImage);


        }


    }
}