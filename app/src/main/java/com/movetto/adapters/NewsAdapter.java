package com.movetto.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.movetto.R;
import com.movetto.dtos.NewsDto;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {

    private List<NewsDto> newsDtoList = new ArrayList<>();

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item,parent,false);
        return new NewsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        NewsDto newsDto = newsDtoList.get(position);
        int start = newsDto.getContent().indexOf(">");
        String descriptionText = newsDto.getContent().substring(start + 1);
        holder.title.setText(newsDto.getTitle());
        holder.description.setText(descriptionText);
        if (!newsDto.getImages().isEmpty()){
            setNewsImage(holder.image, newsDto.getImages().get(0).getUrl());
        }
    }

    @Override
    public int getItemCount() {
        return newsDtoList.size();
    }

    public void setNews(List<NewsDto> news){
        this.newsDtoList = news;
        notifyDataSetChanged();
    }

    private void setNewsImage(ImageView image, String url){
        Picasso.get().load(url).into(image);
    }

    class NewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView title;
        private TextView description;
        private ImageView image;
        private Button buttonExpand;
        private Button buttonCollapse;

        public NewsHolder(@NonNull View itemView) {
            super(itemView);
            setComponents();
            setListeners();
        }

        private void setComponents(){
            title = itemView.findViewById(R.id.news_title);
            description = itemView.findViewById(R.id.news_description);
            image = itemView.findViewById(R.id.news_image);
            buttonExpand = itemView.findViewById(R.id.news_icon_button_expand);
            buttonCollapse = itemView.findViewById(R.id.news_icon_button_collapse);
        }

        private void setListeners(){
            itemView.setOnClickListener(this);
            buttonExpand.setOnClickListener(this);
            buttonCollapse.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.news_icon_button_expand){
                ViewGroup.LayoutParams params = description.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                description.setLayoutParams(params);
                buttonExpand.setVisibility(View.GONE);
                buttonCollapse.setVisibility(View.VISIBLE);
            }
            if (v.getId() == R.id.news_icon_button_collapse){
                ViewGroup.LayoutParams params = description.getLayoutParams();
                final float scale = v.getContext().getResources().getDisplayMetrics().density;
                params.height = (int) (150 * scale + 0.5f);
                description.setLayoutParams(params);
                buttonExpand.setVisibility(View.VISIBLE);
                buttonCollapse.setVisibility(View.GONE);
            }
        }
    }
}
