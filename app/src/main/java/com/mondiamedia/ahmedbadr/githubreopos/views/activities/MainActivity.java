package com.mondiamedia.ahmedbadr.githubreopos.views.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

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

    private ReposRecyclerViewAdapter mReposRecyclerViewAdapter;
    private List<GitHubRepository> mReposList = new ArrayList<>();
    private RepositoriesViewModel repositoriesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mReposRecyclerView.setLayoutManager(layoutManager);

        mReposRecyclerViewAdapter = new ReposRecyclerViewAdapter(mReposList, this);
        mReposRecyclerView.setAdapter(mReposRecyclerViewAdapter);

        repositoriesViewModel = ViewModelProviders.of(this).get(RepositoriesViewModel.class);
        repositoriesViewModel.init();
        repositoriesViewModel.getRepos().observe(this, reposResponse -> {
            mReposList = reposResponse;
            mReposRecyclerViewAdapter.notifyDataSetChanged();
        });

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
