package com.mondiamedia.ahmedbadr.githubreopos.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.mondiamedia.ahmedbadr.githubreopos.R
import com.mondiamedia.ahmedbadr.githubreopos.models.GitRepo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item.view.*
import java.util.*

class ReposRecyclerViewAdapter(private val mGitRepoList: List<GitRepo>,
                               private val mContext: Context
) : RecyclerView.Adapter<ReposRecyclerViewAdapter.ReposViewHolder>() {

    private val mCollapsingStatesList: MutableList<Boolean>

    init {
        this.mCollapsingStatesList = ArrayList(mGitRepoList.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ReposViewHolder(v)
    }

    override fun onBindViewHolder(holder: ReposViewHolder, position: Int) {
        val gitHubRepository = mGitRepoList[position]

        mCollapsingStatesList.clear()
        while (mCollapsingStatesList.size < mGitRepoList.size) {
            mCollapsingStatesList.add(false)
        }

        holder.itemView.apply {
            Picasso.with(mContext).load(gitHubRepository.avatar).into(authorImage)
            authorImage.scaleType = ImageView.ScaleType.CENTER_CROP
            authorName.text = gitHubRepository.author
            repoTitle.text = gitHubRepository.name
            repoDescription.text = gitHubRepository.description
            language.text = gitHubRepository.language
            starsCount.text = gitHubRepository.stars
            forksCount.text = gitHubRepository.forks

            repoCard!!.setOnClickListener { v ->
                updateViews(holder, !mCollapsingStatesList[position])
                mCollapsingStatesList[position] = !mCollapsingStatesList[position]
            }
        }
    }

    private fun updateViews(holder: ReposViewHolder, show: Boolean) {
        holder.itemView.repoDescription.visibility = if (show) View.VISIBLE else View.GONE
        holder.itemView.language_icon.visibility = if (show) View.VISIBLE else View.GONE
        holder.itemView.language.visibility = if (show) View.VISIBLE else View.GONE
        holder.itemView.star_icon.visibility = if (show) View.VISIBLE else View.GONE
        holder.itemView.starsCount.visibility = if (show) View.VISIBLE else View.GONE
        holder.itemView.fork_icon.visibility = if (show) View.VISIBLE else View.GONE
        holder.itemView.forksCount.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun getItemCount(): Int {
        return mGitRepoList.size
    }

    inner class ReposViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView)
}
