package com.home.post.entity

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("posts")
data class Posts(
    @Id
    val id: ObjectId = ObjectId(),
    val title: String = "",
    val body: String = "",
    val category: String = "",
    val likes: Int = 0,
    val tags: List<String> = emptyList(),
    val date: Date = Date()
)
