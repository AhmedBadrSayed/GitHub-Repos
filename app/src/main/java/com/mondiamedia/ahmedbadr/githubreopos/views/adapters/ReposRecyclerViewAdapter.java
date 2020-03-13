package com.mondiamedia.ahmedbadr.githubreopos.views.adapters;

import android.content.Context;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mondiamedia.ahmedbadr.githubreopos.R;
import com.mondiamedia.ahmedbadr.githubreopos.models.GitHubRepository;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReposRecyclerViewAdapter extends RecyclerView.Adapter<ReposRecyclerViewAdapter.ReposViewHolder> {

    private List<GitHubRepository> mGitHubRepositoryList;
    private List<Boolean> mCollapsingStatesList;
    private Context mContext;

    public ReposRecyclerViewAdapter(List<GitHubRepository> repositories, Context context) {
        this.mContext = context;
        this.mGitHubRepositoryList = repositories;
        this.mCollapsingStatesList = new ArrayList<>(mGitHubRepositoryList.size());
    }

    @NotNull
    @Override
    public ReposViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ReposViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NotNull ReposViewHolder holder, int position) {
        GitHubRepository gitHubRepository = mGitHubRepositoryList.get(position);

        mCollapsingStatesList.clear();
        while (mCollapsingStatesList.size() < mGitHubRepositoryList.size()) {
            mCollapsingStatesList.add(false);
        }

        Picasso.with(mContext).load(gitHubRepository.getAvatar()).into(holder.authorImage);
        holder.authorImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        holder.authorName.setText(gitHubRepository.getAuthor());
        holder.repoTitle.setText(gitHubRepository.getName());
        holder.repoDescription.setText(gitHubRepository.getDescription());
        holder.language.setText(gitHubRepository.getLanguage());
        holder.starsCount.setText(gitHubRepository.getStars());
        holder.forksCount.setText(gitHubRepository.getForks());

        holder.cardView.setOnClickListener(v -> {
            updateViews(holder, !mCollapsingStatesList.get(position));
            mCollapsingStatesList.set(position, !mCollapsingStatesList.get(position));
        });
    }

    private void updateViews(ReposViewHolder holder, boolean show) {
        holder.repoDescription.setVisibility(show ? View.VISIBLE : View.GONE);
        holder.languageIcon.setVisibility(show ? View.VISIBLE : View.GONE);
        holder.language.setVisibility(show ? View.VISIBLE : View.GONE);
        holder.starIcon.setVisibility(show ? View.VISIBLE : View.GONE);
        holder.starsCount.setVisibility(show ? View.VISIBLE : View.GONE);
        holder.forkIcon.setVisibility(show ? View.VISIBLE : View.GONE);
        holder.forksCount.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return mGitHubRepositoryList.size();
    }

    public class ReposViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.repo_card)
        CardView cardView;
        @BindView(R.id.author_image)
        ImageView authorImage;
        @BindView(R.id.author_name)
        TextView authorName;
        @BindView(R.id.repo_title)
        TextView repoTitle;
        @BindView(R.id.repo_description)
        TextView repoDescription;
        @BindView(R.id.language_icon)
        ImageView languageIcon;
        @BindView(R.id.language)
        TextView language;
        @BindView(R.id.star_icon)
        ImageView starIcon;
        @BindView(R.id.stars_count)
        TextView starsCount;
        @BindView(R.id.fork_icon)
        ImageView forkIcon;
        @BindView(R.id.forks_count)
        TextView forksCount;

        ReposViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
