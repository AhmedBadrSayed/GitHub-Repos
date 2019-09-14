package com.mondiamedia.ahmedbadr.githubreopos.views.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.mondiamedia.ahmedbadr.githubreopos.R;
import com.mondiamedia.ahmedbadr.githubreopos.models.GitHubRepository;
import com.mondiamedia.ahmedbadr.githubreopos.view_models.RepositoriesViewModel;
import com.mondiamedia.ahmedbadr.githubreopos.views.adapters.ReposRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout mSwipeContainer;
    @BindView(R.id.repos_recycler_view)
    RecyclerView mReposRecyclerView;
    @BindView(R.id.empty_view_layout)
    ConstraintLayout mEmptyViewLayout;
    @BindView(R.id.empty_view_retry_btn)
    Button mRetryBtn;

    private ReposRecyclerViewAdapter mReposRecyclerViewAdapter;
    private List<GitHubRepository> mReposList = new ArrayList<>();
    private RepositoriesViewModel mRepositoriesViewModel;
    private Observer<List<GitHubRepository>> mLiveDataObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        initViews();
    }

    private void initViews() {
        setupReposRecyclerView();

        mLiveDataObserver = listLiveData -> {
            mSwipeContainer.setRefreshing(false);
            mReposList.clear();
            if (listLiveData != null) {
                mReposList.addAll(listLiveData);
                mReposRecyclerViewAdapter.notifyDataSetChanged();
                mReposRecyclerView.setVisibility(View.VISIBLE);
                mEmptyViewLayout.setVisibility(View.GONE);
            } else {
                mReposRecyclerView.setVisibility(View.GONE);
                mEmptyViewLayout.setVisibility(View.VISIBLE);
            }
        };

        mRepositoriesViewModel = ViewModelProviders.of(this).get(RepositoriesViewModel.class);
        mRepositoriesViewModel.init();

        getAndBindTrendingRepos();

        mSwipeContainer.setOnRefreshListener(() -> refreshedRepos());

        mRetryBtn.setOnClickListener(v -> refreshedRepos());
    }

    private void setupReposRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mReposRecyclerView.setLayoutManager(layoutManager);

        mReposRecyclerViewAdapter = new ReposRecyclerViewAdapter(mReposList, this);
        mReposRecyclerView.setAdapter(mReposRecyclerViewAdapter);
    }

    private void getAndBindTrendingRepos() {
        mRepositoriesViewModel.getRepos().observe(this, mLiveDataObserver);
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
