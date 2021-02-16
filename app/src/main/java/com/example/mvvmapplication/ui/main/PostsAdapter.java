package com.example.mvvmapplication.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvmapplication.BR;
import com.example.mvvmapplication.pojo.PostResponse;

import java.util.ArrayList;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.GenericViewHolder> {


    private int layoutId;
    private List<PostResponse> dataModelList = new ArrayList<>();
    private PostViewModel viewModel;

    public PostsAdapter(@LayoutRes int layoutId, PostViewModel viewModel) {
        this.layoutId = layoutId;
        this.viewModel = viewModel;
    }
    @NonNull
    @Override
    public GenericViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false);
        return new GenericViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(GenericViewHolder holder, int position) {
        holder.bind(viewModel, position);
//        holder.itemRowBinding.setItemClickListener(this);
    }
    @Override
    public int getItemViewType(int position) {
        return getLayoutIdForPosition(position);
    }

    private int getLayoutIdForPosition(int position) {
        return layoutId;
    }
    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public void setDogBreeds(List<PostResponse> postResponseList) {
        this.dataModelList = postResponseList;
    }

    class GenericViewHolder extends RecyclerView.ViewHolder {
        final ViewDataBinding binding;

        GenericViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(PostViewModel viewModel, Integer position) {

            binding.setVariable(BR.viewModel, viewModel);
            binding.setVariable(BR.position, position);
            binding.executePendingBindings();
        }

    }

}
