package com.home.post.repository

import com.home.post.entity.Posts
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface PostsRepository : MongoRepository<Posts, String> {

    fun findPostsByBody(body: String): List<Posts>

    fun findPostsByBodyAndTitleAndCategory(body: String, title: String, category: String): Optional<Posts>
}