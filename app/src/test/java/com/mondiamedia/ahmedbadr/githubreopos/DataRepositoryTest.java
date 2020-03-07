package com.mondiamedia.ahmedbadr.githubreopos;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.MutableLiveData;

import com.mondiamedia.ahmedbadr.githubreopos.models.GitRepo;
import com.mondiamedia.ahmedbadr.githubreopos.repository.DataRepository;

import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DataRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Test
    public void test_emptyRepos() {
        int expected = 0;

        DataRepository dataRepository = mock(DataRepository.class);
        when(dataRepository.getRepos()).thenReturn(new MutableLiveData<>());
        int actual = dataRepository.getRepos().getValue() == null ? 0 : dataRepository.getRepos().getValue().size();

        assertEquals(expected, actual);
    }

    @Test
    public void test_singleRepo() {
        ArrayList<GitRepo> gitRepos = new ArrayList<>();
        gitRepos.add(new GitRepo("github.com", "Badr", "Test", "description",
                "java", "languageColor", "red", "20", "30"));
        MutableLiveData<List<GitRepo>> repoMutableLiveData = new MutableLiveData<>();
        repoMutableLiveData.setValue(gitRepos);
        int expected = 1;

        DataRepository dataRepository = mock(DataRepository.class);
        when(dataRepository.getRepos()).thenReturn(repoMutableLiveData);
        int actual = dataRepository.getRepos().getValue() == null ? 0 : dataRepository.getRepos().getValue().size();

        assertEquals(expected, actual);
    }

    @Test
    public void test_multipleRepos() {
        ArrayList<GitRepo> gitRepos = new ArrayList<>();
        gitRepos.add(new GitRepo("github.com", "Badr", "Test", "description",
                "java", "languageColor", "red", "20", "30"));
        gitRepos.add(new GitRepo("github.com", "Badr", "Test2", "description",
                "kotlin", "languageColor", "blue", "31", "54"));
        gitRepos.add(new GitRepo("github.com", "Badr", "Test3", "description",
                "C++", "languageColor", "yellow", "65", "75"));
        MutableLiveData<List<GitRepo>> repoMutableLiveData = new MutableLiveData<>();
        repoMutableLiveData.setValue(gitRepos);
        int expected = 3;

        DataRepository dataRepository = mock(DataRepository.class);
        when(dataRepository.getRepos()).thenReturn(repoMutableLiveData);
        int actual = dataRepository.getRepos().getValue() == null ? 0 : dataRepository.getRepos().getValue().size();

        assertEquals(expected, actual);
    }
}
