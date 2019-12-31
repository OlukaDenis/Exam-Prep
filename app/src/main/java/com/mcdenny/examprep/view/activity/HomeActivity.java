package com.mcdenny.examprep.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.mcdenny.examprep.R;
import com.mcdenny.examprep.model.Movie;
import com.mcdenny.examprep.view.adapters.MovieAdapter;
import com.mcdenny.examprep.view.fragments.TrendingFragment;
import com.mcdenny.examprep.viewmodel.MovieViewModel;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
    TrendingFragment trendingFragment = null;
    private MovieViewModel viewModel;
    private RecyclerView recyclerView;
    private MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        viewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                initRecyclerview();
                adapter.notifyDataSetChanged();
            }
        });

    }

    private void initRecyclerview() {
        recyclerView = findViewById(R.id.trending_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new MovieAdapter(this, viewModel.getMovies().getValue());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
