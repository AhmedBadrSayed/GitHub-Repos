package com.mondiamedia.ahmedbadr.githubreopos;

import com.mondiamedia.ahmedbadr.githubreopos.local_db.LocalDataSource;
import com.mondiamedia.ahmedbadr.githubreopos.models.GitRepo;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class LocalDataSourceTest {

    @Test
    public void test_emptyReposList() {
        int expected = 0;

        LocalDataSource localDataSource = mock(LocalDataSource.class);
        when(localDataSource.getLocalRepos()).thenReturn(new ArrayList<>());

        int actual = localDataSource.getLocalRepos().size();

        assertEquals(expected, actual);
    }

    @Test
    public void test_singleReposList() {
        ArrayList<GitRepo> gitRepos = new ArrayList<>();
        gitRepos.add(new GitRepo("github.com", "Badr", "Test", "description",
                "java", "languageColor", "red", "20", "30"));
        int expected = 1;

        LocalDataSource localDataSource = mock(LocalDataSource.class);
        when(localDataSource.getLocalRepos()).thenReturn(gitRepos);
        int actual = localDataSource.getLocalRepos().size();

        assertEquals(expected, actual);
    }

    @Test
    public void test_multipleReposList() {
        ArrayList<GitRepo> gitRepos = new ArrayList<>();
        gitRepos.add(new GitRepo("github.com", "Badr", "Test", "description",
                "java", "languageColor", "red", "20", "30"));
        gitRepos.add(new GitRepo("github.com", "Badr", "Test2", "description",
                "kotlin", "languageColor", "blue", "31", "54"));
        gitRepos.add(new GitRepo("github.com", "Badr", "Test3", "description",
                "C++", "languageColor", "yellow", "65", "75"));

        int expected = 3;

        LocalDataSource localDataSource = mock(LocalDataSource.class);
        when(localDataSource.getLocalRepos()).thenReturn(gitRepos);
        int actual = localDataSource.getLocalRepos().size();

        assertEquals(expected, actual);
    }
}