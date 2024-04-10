package com.home.post.service

import com.home.post.config.RabbitMQConfig
import com.home.post.entity.Posts
import com.home.post.models.PostModel
import com.home.post.repository.PostsRepository
import com.rabbitmq.client.Channel
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class PostsService(
    private val postsRepository: PostsRepository,
    private val channel: Channel,
    private val rabbitMQConfig: RabbitMQConfig
) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(this::class.java)
    }

    fun getPostsByBody(body: String): List<Posts> {
        logger.info("PostsService:getPostsByBody execution started")
        return postsRepository.findPostsByBody(body).takeIf { it.isNotEmpty() } ?: emptyList()
    }

    fun getPostsById(id: String): Optional<Posts> {
        logger.info("PostsService:getPostsById execution started")
        return postsRepository.findById(id).takeIf { it.isPresent } ?: Optional.empty()
    }

    fun savePosts(postModel: PostModel): Posts {
        logger.info("PostsService:savePosts execution started")
        val posts = postsRepository.save(
            Posts(
                title = postModel.title,
                body = postModel.body,
                category = postModel.category,
                likes = postModel.likes,
                tags = postModel.tags,
                date = postModel.date
            )
        )
        publishToRabbitMQ(posts)
        logger.info("PostsService:savePosts execution ended")
        return posts
    }

    fun updatePosts(postModel: PostModel, id: String): Posts {
        logger.info("PostsService:updatePosts execution started")
        val posts = postsRepository.findById(id).orElseThrow { IllegalArgumentException("Invalid post ID") }
        posts.apply {
            title = postModel.title
            body = postModel.body
            category = postModel.category
            likes = postModel.likes
            tags = postModel.tags
            date = postModel.date
        }
        val updatedPosts = postsRepository.save(posts)
        publishToRabbitMQ(updatedPosts)
        logger.info("PostsService:updatePosts execution ended")
        return updatedPosts
    }

    fun deletePosts(id: String) {
        logger.info("PostsService:deletePosts execution started")
        if (!postsRepository.existsById(id)) {
            throw NoSuchElementException("Post with ID $id not found")
        }
        postsRepository.deleteById(id)
        logger.info("PostsService:deletePosts execution ended")
    }

    fun getAllPosts(): List<Posts> {
        logger.info("PostsService:getAllPosts execution started")
        return postsRepository.findAll().takeIf { it.isNotEmpty() } ?: emptyList()
    }

    fun getPostsByBodyAndTitleAndCategory(body: String, title: String, category: String): Optional<Posts> {
        return postsRepository.findPostsByBodyAndTitleAndCategory(body, title, category).takeIf { it.isPresent } ?: Optional.empty()
    }

    private fun publishToRabbitMQ(posts: Posts) {
        val message = posts.toString().toByteArray()
        channel.basicPublish("", rabbitMQConfig.postsQueue, null, message)
    }

    @Suppress("MagicNumber")
    private fun setUp() {
        val xMessageTTL = 172800000
        val queueArgs = mapOf("x-message-ttl" to xMessageTTL)
        channel.queueDeclareNoWait(rabbitMQConfig.postsQueue, false, false, false, queueArgs)
    }
}
