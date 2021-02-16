package com.example.mvvmapplication.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.mvvmapplication.R;
import com.example.mvvmapplication.databinding.ActivityMainBinding;
import com.example.mvvmapplication.pojo.PostResponse;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    PostViewModel postViewModel;
    ActivityMainBinding activityMainBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        postViewModel.getPosts();
        final PostsAdapter adapter = new PostsAdapter();
        activityMainBinding.recycler.setLayoutManager(new LinearLayoutManager(this));
        activityMainBinding.recycler.setAdapter(adapter);

        activityMainBinding.setMyAdapter(adapter);
        activityMainBinding.setLifecycleOwner(this);
        *//*postViewModel.postResponseMutableLiveData.observe(this, new Observer<List<PostResponse>>() {
            @Override
            public void onChanged(List<PostResponse> postResponses) {
                adapter.setList(postResponses);
            }
        });*//*
*/


    setupBindings(savedInstanceState);
}

    private void setupBindings(Bundle savedInstanceState) {

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        if (savedInstanceState == null) {
            postViewModel.init();
        }
        activityMainBinding.listOfBreeds.setLayoutManager(new LinearLayoutManager(this));
        activityMainBinding.listOfBreeds.setAdapter(postViewModel.getAdapter());
        activityMainBinding.setLifecycleOwner(this);
        activityMainBinding.setModel(postViewModel);
        setupListUpdate();

    }

    private void setupListUpdate() {
        postViewModel.loading.set(View.VISIBLE);
        postViewModel.fetchList();
        postViewModel.selected.observe(this, new Observer<List<PostResponse>>() {
            @Override
            public void onChanged(List<PostResponse> dogBreeds) {
                postViewModel.loading.set(View.GONE);
                if (dogBreeds.size() == 0) {
                    postViewModel.showEmpty.set(View.VISIBLE);
                } else {
                    postViewModel.showEmpty.set(View.GONE);
                    postViewModel.setDogBreedsInAdapter(dogBreeds);
                }
            }
        });
    }

}