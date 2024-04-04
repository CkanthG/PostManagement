package com.home.post.models

import java.util.Date

data class PostModel(
    val title: String = "",
    val body: String = "",
    val category: String = "",
    val likes: Int = 0,
    val tags: List<String> = emptyList(),
    val date: Date = Date()
)