package com.example.mvvmapplication.ui.main;

import android.util.Log;
import android.view.View;

import androidx.databinding.ObservableArrayMap;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mvvmapplication.R;
import com.example.mvvmapplication.data.PostsClient;
import com.example.mvvmapplication.pojo.PostResponse;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostViewModel extends ViewModel {

    private PostsAdapter adapter;
    public MutableLiveData<List<PostResponse>> selected;
    public ObservableArrayMap<String, String> images;
    public ObservableInt loading;
    public ObservableInt showEmpty;

    public void init() {
        selected = new MutableLiveData<>();
        adapter = new PostsAdapter(R.layout.item_recycler, this);
        images = new ObservableArrayMap<>();
        loading = new ObservableInt(View.GONE);
        showEmpty = new ObservableInt(View.GONE);
    }

    public void fetchList() {
        getPosts();
    }


    public PostsAdapter getAdapter() {
        return adapter;
    }

    public void setDogBreedsInAdapter(List<PostResponse> breeds) {
        this.adapter.setDogBreeds(breeds);
        this.adapter.notifyDataSetChanged();
    }

    public MutableLiveData<List<PostResponse>> getSelected() {
        return selected;
    }
    CompositeDisposable compositeDisposable;
    public void getPosts (){


        compositeDisposable = new CompositeDisposable();
        Single<List<PostResponse>> observable = PostsClient.getINSTANCE().getPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        compositeDisposable.add(observable.subscribe(o->selected.setValue(o), e-> Log.d("Sadek ", "getPosts: "+e)));
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
