package com.mondiamedia.ahmedbadr.githubreopos.views.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mondiamedia.ahmedbadr.githubreopos.R;
import com.mondiamedia.ahmedbadr.githubreopos.databinding.RepositoriesDataBinding;
import com.mondiamedia.ahmedbadr.githubreopos.models.GitRepo;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ReposRecyclerViewAdapter extends RecyclerView.Adapter<ReposRecyclerViewAdapter.ReposViewHolder> {

    private List<GitRepo> mGitRepoList;
    private List<Boolean> mCollapsingStatesList;
    private Context mContext;

    public ReposRecyclerViewAdapter(List<GitRepo> repositories, Context context) {
        this.mContext = context;
        this.mGitRepoList = repositories;
        this.mCollapsingStatesList = new ArrayList<>(mGitRepoList.size());
    }

    @NotNull
    @Override
    public ReposViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RepositoriesDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_item, parent, false);
        return new ReposViewHolder(binding.getRoot(), binding);
    }

    @Override
    public void onBindViewHolder(@NotNull ReposViewHolder holder, int position) {
        GitRepo gitRepo = mGitRepoList.get(position);

        mCollapsingStatesList.clear();
        while (mCollapsingStatesList.size() < mGitRepoList.size()) {
            mCollapsingStatesList.add(false);
        }

        holder.mRepositoriesDataBinding.setGitRepo(gitRepo);
        Picasso.with(mContext).load(gitRepo.getAvatar()).placeholder(R.drawable.placeholder)
                .into(holder.getmRepositoriesDataBinding().authorImage);
        holder.getmRepositoriesDataBinding().authorImage.setScaleType(ImageView.ScaleType.CENTER_CROP);

        holder.getmRepositoriesDataBinding().repoCard.setOnClickListener(v -> {
            updateViews(holder, !mCollapsingStatesList.get(position));
            mCollapsingStatesList.set(position, !mCollapsingStatesList.get(position));
        });
    }

    private void updateViews(ReposViewHolder holder, boolean show) {
        holder.getmRepositoriesDataBinding().repoDescription.setVisibility(show ? View.VISIBLE : View.GONE);
        holder.getmRepositoriesDataBinding().languageIcon.setVisibility(show ? View.VISIBLE : View.GONE);
        holder.getmRepositoriesDataBinding().language.setVisibility(show ? View.VISIBLE : View.GONE);
        holder.getmRepositoriesDataBinding().starIcon.setVisibility(show ? View.VISIBLE : View.GONE);
        holder.getmRepositoriesDataBinding().starsCount.setVisibility(show ? View.VISIBLE : View.GONE);
        holder.getmRepositoriesDataBinding().forkIcon.setVisibility(show ? View.VISIBLE : View.GONE);
        holder.getmRepositoriesDataBinding().forksCount.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return mGitRepoList.size();
    }

    public class ReposViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RepositoriesDataBinding mRepositoriesDataBinding;

        ReposViewHolder(View itemView, RepositoriesDataBinding repositoriesDataBinding) {
            super(itemView);
            this.mRepositoriesDataBinding = repositoriesDataBinding;
        }

        public RepositoriesDataBinding getmRepositoriesDataBinding() {
            return mRepositoriesDataBinding;
        }

        @Override
        public void onClick(View v) {

        }
    }
}
