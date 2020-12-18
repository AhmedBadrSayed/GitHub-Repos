package com.mondiamedia.ahmedbadr.githubreopos.views.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mondiamedia.ahmedbadr.githubreopos.Application
import com.mondiamedia.ahmedbadr.githubreopos.R
import com.mondiamedia.ahmedbadr.githubreopos.models.GitRepo
import com.mondiamedia.ahmedbadr.githubreopos.view_models.RepositoriesViewModel
import com.mondiamedia.ahmedbadr.githubreopos.views.adapters.ReposRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var repositoriesViewModel: RepositoriesViewModel
    private lateinit var liveDataObserver: Observer<List<GitRepo>>

    private lateinit var reposRecyclerViewAdapter: ReposRecyclerViewAdapter
    private val reposList = ArrayList<GitRepo>()

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

        liveDataObserver = Observer { listLiveData ->
            shimmerFrameLayout.stopShimmer()
            shimmerFrameLayout.visibility = View.GONE
            swipeContainer.isRefreshing = false
            reposList.clear()
            if (listLiveData != null) {
                reposList.addAll(listLiveData)
                reposRecyclerViewAdapter.notifyDataSetChanged()
                reposRecyclerView.visibility = View.VISIBLE
                emptyViewLayout.visibility = View.GONE
            } else {
                reposRecyclerView.visibility = View.GONE
                emptyViewLayout.visibility = View.VISIBLE
            }
        }

        repositoriesViewModel.init()

        observeReposLiveData()

        swipeContainer.setOnRefreshListener {
            refreshedRepos()
        }

        retryBtn.setOnClickListener {
            refreshedRepos()
        }
    }

    private fun setupReposRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        reposRecyclerView.layoutManager = layoutManager

        reposRecyclerViewAdapter = ReposRecyclerViewAdapter(reposList, this)
        reposRecyclerView.adapter = reposRecyclerViewAdapter
    }

    private fun observeReposLiveData() {
        lifecycleScope.launch {
            repositoriesViewModel.reposList?.observe(this@MainActivity, liveDataObserver)
        }
    }

    private fun refreshedRepos() {
        shimmerFrameLayout.startShimmer()
        shimmerFrameLayout.visibility = View.VISIBLE
        reposRecyclerView.visibility = View.GONE

        lifecycleScope.launch {
            repositoriesViewModel.refreshRepos()
        }
    }
}
