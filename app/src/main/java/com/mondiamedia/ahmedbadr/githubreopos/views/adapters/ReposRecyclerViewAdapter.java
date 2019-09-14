package com.mondiamedia.ahmedbadr.githubreopos.views.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mondiamedia.ahmedbadr.githubreopos.R;
import com.mondiamedia.ahmedbadr.githubreopos.models.GitHubRepository;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ReposRecyclerViewAdapter extends RecyclerView.Adapter<ReposRecyclerViewAdapter.ReposViewHolder> {


    private List<GitHubRepository> mGitHubRepositoryList;
    private Context mContext;

    public ReposRecyclerViewAdapter(List<GitHubRepository> repositories, Context context) {
        this.mGitHubRepositoryList = repositories;
        this.mContext = context;
    }

    @Override
    public ReposViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ReposViewHolder reposViewHolder = new ReposViewHolder(v);
        return reposViewHolder;
    }

    @Override
    public void onBindViewHolder(ReposViewHolder holder, int position) {
        GitHubRepository gitHubRepository = mGitHubRepositoryList.get(position);

        Picasso.with(mContext).load(gitHubRepository.getAvatar()).into(holder.authorImage);
        holder.authorImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        holder.authorName.setText(gitHubRepository.getAuthor());
        holder.repoTitle.setText(gitHubRepository.getName());
        holder.repoDescription.setText(gitHubRepository.getDescription());
        holder.language.setText(gitHubRepository.getLanguage());
        holder.starsCount.setText(gitHubRepository.getStars());
        holder.forksCount.setText(gitHubRepository.getForks());
    }

    @Override
    public int getItemCount() {
        return mGitHubRepositoryList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class ReposViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cardView;
        ImageView authorImage;
        TextView authorName;
        TextView repoTitle;
        TextView repoDescription;
        TextView language;
        TextView starsCount;
        TextView forksCount;

        public ReposViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.repo_card);
            authorImage = itemView.findViewById(R.id.author_image);
            authorName = itemView.findViewById(R.id.author_name);
            repoTitle = itemView.findViewById(R.id.repo_title);
            repoDescription = itemView.findViewById(R.id.repo_description);
            language = itemView.findViewById(R.id.language);
            starsCount = itemView.findViewById(R.id.stars_count);
            forksCount = itemView.findViewById(R.id.forks_count);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
