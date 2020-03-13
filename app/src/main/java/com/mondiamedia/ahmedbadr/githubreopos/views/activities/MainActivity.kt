package com.mondiamedia.ahmedbadr.githubreopos.views.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mondiamedia.ahmedbadr.githubreopos.Application
import com.mondiamedia.ahmedbadr.githubreopos.R
import com.mondiamedia.ahmedbadr.githubreopos.models.GitRepo
import com.mondiamedia.ahmedbadr.githubreopos.view_models.RepositoriesViewModel
import com.mondiamedia.ahmedbadr.githubreopos.views.adapters.ReposRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mRepositoriesViewModel: RepositoriesViewModel
    private lateinit var mLiveDataObserver: Observer<List<GitRepo>>

    private lateinit var mReposRecyclerViewAdapter: ReposRecyclerViewAdapter
    private val mReposList = ArrayList<GitRepo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as Application).applicationComponent.inject(this@MainActivity)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        initViews()
    }

    private fun initViews() {
        shimmerFrameLayout.startShimmer()
        setupReposRecyclerView()

        mLiveDataObserver = Observer { listLiveData ->
            shimmerFrameLayout.stopShimmer()
            shimmerFrameLayout.visibility = View.GONE
            swipeContainer.isRefreshing = false
            mReposList.clear()
            if (listLiveData != null) {
                mReposList.addAll(listLiveData)
                mReposRecyclerViewAdapter.notifyDataSetChanged()
                reposRecyclerView.visibility = View.VISIBLE
                emptyViewLayout.visibility = View.GONE
            } else {
                reposRecyclerView.visibility = View.GONE
                emptyViewLayout.visibility = View.VISIBLE
            }
        }

        mRepositoriesViewModel.init()

        getAndBindTrendingRepos()

        swipeContainer.setOnRefreshListener {
            shimmerFrameLayout.startShimmer()
            shimmerFrameLayout.visibility = View.VISIBLE
            reposRecyclerView.visibility = View.GONE
            refreshedRepos()
        }

        retryBtn.setOnClickListener { refreshedRepos() }
    }

    private fun setupReposRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        reposRecyclerView.layoutManager = layoutManager

        mReposRecyclerViewAdapter = ReposRecyclerViewAdapter(mReposList, this)
        reposRecyclerView.adapter = mReposRecyclerViewAdapter
    }

    private fun getAndBindTrendingRepos() {
        mRepositoriesViewModel.repos?.observe(this, mLiveDataObserver)
    }

    private fun refreshedRepos() {
        mRepositoriesViewModel.refreshRepos()?.observe(this, mLiveDataObserver)
    }
}
