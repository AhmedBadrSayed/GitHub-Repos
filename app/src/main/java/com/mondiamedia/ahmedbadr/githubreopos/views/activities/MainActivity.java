package com.mondiamedia.ahmedbadr.githubreopos.views.activities;

import android.arch.lifecycle.Observer;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mondiamedia.ahmedbadr.githubreopos.Application;
import com.mondiamedia.ahmedbadr.githubreopos.R;
import com.mondiamedia.ahmedbadr.githubreopos.databinding.ActivityMainBinding;
import com.mondiamedia.ahmedbadr.githubreopos.databinding.ContentMainBinding;
import com.mondiamedia.ahmedbadr.githubreopos.models.GitRepo;
import com.mondiamedia.ahmedbadr.githubreopos.view_models.RepositoriesViewModel;
import com.mondiamedia.ahmedbadr.githubreopos.views.adapters.ReposRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mActivityMainBinding;
    ContentMainBinding mContentMainBinding;

    @Inject
    public RepositoriesViewModel mRepositoriesViewModel;
    private Observer<List<GitRepo>> mLiveDataObserver;

    private ReposRecyclerViewAdapter mReposRecyclerViewAdapter;
    private List<GitRepo> mReposList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((Application) getApplicationContext()).applicationComponent.inject(this);
        super.onCreate(savedInstanceState);
        mActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mContentMainBinding = DataBindingUtil.setContentView(this, R.layout.content_main);
        setSupportActionBar(mActivityMainBinding.toolbar);

        initViews();
    }

    private void initViews() {
        startShimmerEffect();
        setupReposRecyclerView();

        mLiveDataObserver = listLiveData -> {
            stopShimmerEffect();
            mContentMainBinding.swipeContainer.setRefreshing(false);
            mReposList.clear();

            if (listLiveData == null) {
                mContentMainBinding.reposRecyclerView.setVisibility(View.GONE);
                mContentMainBinding.emptyViewLayout.setVisibility(View.VISIBLE);
                return;
            }

            mReposList.addAll(listLiveData);
            mReposRecyclerViewAdapter.notifyDataSetChanged();
            mContentMainBinding.reposRecyclerView.setVisibility(View.VISIBLE);
            mContentMainBinding.emptyViewLayout.setVisibility(View.GONE);
        };

        mRepositoriesViewModel.init();

        getAndBindTrendingRepos();

        mContentMainBinding.swipeContainer.setOnRefreshListener(() -> {
            startShimmerEffect();
            mContentMainBinding.reposRecyclerView.setVisibility(View.GONE);
            refreshedRepos();
        });
    }

    void startShimmerEffect() {
        mContentMainBinding.shimmerFrameLayout.startShimmer();
        mContentMainBinding.shimmerFrameLayout.setVisibility(View.VISIBLE);
    }

    void stopShimmerEffect() {
        mContentMainBinding.shimmerFrameLayout.stopShimmer();
        mContentMainBinding.shimmerFrameLayout.setVisibility(View.GONE);
    }

    private void setupReposRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mContentMainBinding.reposRecyclerView.setLayoutManager(layoutManager);

        mReposRecyclerViewAdapter = new ReposRecyclerViewAdapter(mReposList, this);
        mContentMainBinding.reposRecyclerView.setAdapter(mReposRecyclerViewAdapter);
    }

    private void getAndBindTrendingRepos() {
        mRepositoriesViewModel.getRepos().observe(this, mLiveDataObserver);
    }

    public void refreshedRepos(View view) {
        refreshedRepos();
    }

    private void refreshedRepos() {
        mRepositoriesViewModel.refreshRepos().observe(this, mLiveDataObserver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
