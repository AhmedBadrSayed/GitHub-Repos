package com.mondiamedia.ahmedbadr.githubreopos.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class GitRepo : RealmObject {

    @PrimaryKey
    var url: String? = null
    var author: String? = null
    var name: String? = null
    var avatar: String? = null
    var description: String? = null
    var language: String? = null
    var languageColor: String? = null
    var stars: String? = null
    var forks: String? = null

    constructor() {}

    constructor(url: String, author: String, name: String, avatar: String, description: String,
                language: String, languageColor: String, stars: String, forks: String) {
        this.url = url
        this.author = author
        this.name = name
        this.avatar = avatar
        this.description = description
        this.language = language
        this.languageColor = languageColor
        this.stars = stars
        this.forks = forks
    }
}
