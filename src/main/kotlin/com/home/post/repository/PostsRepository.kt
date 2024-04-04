package com.home.post.repository

import com.home.post.entity.Posts
import org.springframework.data.mongodb.repository.MongoRepository

interface PostsRepository : MongoRepository<Posts, String> {

    fun findPostsByBody(body: String): List<Posts>

}