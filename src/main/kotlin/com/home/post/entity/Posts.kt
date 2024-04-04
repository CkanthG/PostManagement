package com.home.post.entity

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("posts")
data class Posts(
    @Id
    val id: ObjectId = ObjectId(),
    var title: String = "",
    var body: String = "",
    var category: String = "",
    var likes: Int = 0,
    var tags: List<String> = emptyList(),
    var date: Date = Date()
)
