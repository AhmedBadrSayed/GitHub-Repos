package com.mondiamedia.ahmedbadr.githubreopos.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class GitHubRepository extends RealmObject {

    @PrimaryKey
    private String url;
    private String author;
    private String name;
    private String avatar;
    private String description;
    private String language;
    private String languageColor;
    private String stars;
    private String forks;

    public GitHubRepository() {
    }

    public GitHubRepository(String url, String author, String name, String avatar, String description,
                            String language, String languageColor, String stars, String forks) {
        this.url = url;
        this.author = author;
        this.name = name;
        this.avatar = avatar;
        this.description = description;
        this.language = language;
        this.languageColor = languageColor;
        this.stars = stars;
        this.forks = forks;
    }

    public String getAuthor() {
        return author;
    }

    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getDescription() {
        return description;
    }

    public String getLanguage() {
        return language;
    }

    public String getLanguageColor() {
        return languageColor;
    }

    public String getStars() {
        return stars;
    }

    public String getForks() {
        return forks;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
