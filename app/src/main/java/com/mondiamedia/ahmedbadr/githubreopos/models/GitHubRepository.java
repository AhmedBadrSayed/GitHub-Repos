package com.mondiamedia.ahmedbadr.githubreopos.models;

public class GitHubRepository {

    private String author;
    private String name;
    private String avatar;
    private String description;
    private String language;
    private String languageColor;
    private String stars;
    private String forks;

    public GitHubRepository(String author, String name, String avatar, String description, String language, String languageColor, String stars, String forks) {
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
}
