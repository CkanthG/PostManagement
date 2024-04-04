package com.home.post.service

import com.home.post.config.RabbitMQConfig
import com.home.post.entity.Posts
import com.home.post.models.PostModel
import com.home.post.repository.PostsRepository
import com.rabbitmq.client.Channel
import org.springframework.stereotype.Service
import java.util.*

@Service
class PostsService(
    val postsRepository: PostsRepository,
    private val channel: Channel,
    private val rabbitMQConfig: RabbitMQConfig
) {

    fun getPostsByBody(body: String): List<Posts> {
        return postsRepository.findPostsByBody(body);
    }

    fun getPostsById(id: String): Optional<Posts> {
        return postsRepository.findById(id);
    }

    fun savePosts(postModel: PostModel): Posts {
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
        setUp()
        channel.basicPublish("", rabbitMQConfig.postsQueue, null, posts.toString().toByteArray())
        return posts
    }

    fun updatePosts(postModel: PostModel, id: String): Posts {
        val posts = postsRepository.findById(id).let {
            postsRepository.save(
                Posts(
                    it.get().id,
                    title = postModel.title,
                    body = postModel.body,
                    category = postModel.category,
                    likes = postModel.likes,
                    tags = postModel.tags,
                    date = postModel.date
                )
            )
        }
        setUp()
        channel.basicPublish("", rabbitMQConfig.postsQueue, null, posts.toString().toByteArray())
        return posts
    }

    fun deletePosts(id: String) {
        postsRepository.deleteById(id);
    }

    fun getAllPosts(): MutableList<Posts> {
        return postsRepository.findAll();
    }

    @SuppressWarnings("MagicNumber")
    fun setUp() {
        val xMessageTTL = 172800000
        val queueArgs = mapOf("x-message-ttl" to xMessageTTL)
        channel.queueDeclareNoWait(rabbitMQConfig.postsQueue, false, false, false, queueArgs)
    }
}
