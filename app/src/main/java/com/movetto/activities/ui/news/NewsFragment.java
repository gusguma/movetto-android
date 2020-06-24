package com.movetto.activities.ui.news;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.movetto.R;
import com.movetto.adapters.NewsAdapter;
import com.movetto.dtos.NewsDto;
import com.movetto.view_models.NewsViewModel;

import java.util.List;

public class NewsFragment extends Fragment {

    private NewsViewModel newsViewModel;
    private View root;
    private NewsAdapter adapter;
    private List<NewsDto> news;
    private ProgressBar progressBar;

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setViewModels();
        setLayout(inflater,container);
        setComponents();
        setObservers();
        return root;
    }

    private void setViewModels(){
        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container){
        root = inflater.inflate(R.layout.fragment_news, container, false);
    }

    private void setComponents(){
        RecyclerView recyclerView = root.findViewById(R.id.news_recycler_view);
        progressBar = root.findViewById(R.id.news_progress_bar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new NewsAdapter();
        recyclerView.setAdapter(adapter);
    }
    private void setObservers(){
        newsViewModel.readNews().observe(getViewLifecycleOwner(), new Observer<List<NewsDto>>() {
            @Override
            public void onChanged(List<NewsDto> newsDtos) {
                news = newsDtos;
                adapter.setNews(news);
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
