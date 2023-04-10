package com.example.collegenews.ui.fragments.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.collegenews.R;
import com.example.collegenews.model.NewsModel;
import com.example.collegenews.ui.activities.DetailsActivity;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    List<NewsModel> newsList;
    Context context;

    public NewsAdapter(List<NewsModel> newsList, Context context) {
        this.newsList = newsList;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View listitem = layoutInflater.inflate(R.layout.itemnews, parent, false);
        RecyclerView.ViewHolder viewHolder = new MyViewHolder(listitem);
        return (MyViewHolder) viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        NewsModel news = newsList.get(position);
        holder.newsTitle.setText(news.getTitle());
        holder.newsAuthor.setText(news.getAuthor());
        holder.timeStamp.setText(news.getUploadedat());
        holder.category.setText(news.getCategory());


        // image ...
        Glide.with(context).load(news.getUrlimage()).into(holder.image);

        // share button
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent i = new Intent(context, DetailsActivity.class);
//                i.putExtra("url", marticle.getUrl());
//                context.startActivity(i);


            }
        });


        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetailsActivity.class);
                i.putExtra("newsItem", news);
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        ImageView image;
        TextView newsTitle, newsAuthor, timeStamp, category;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.ivItemNewsImage);
            newsTitle = itemView.findViewById(R.id.tvItemNewsTitle);
            newsAuthor = itemView.findViewById(R.id.tvItemNewsSource);
            timeStamp = itemView.findViewById(R.id.tvItemNewsTimeStamp);
            category = itemView.findViewById(R.id.tvItemNewscategory);


        }
    }

}
