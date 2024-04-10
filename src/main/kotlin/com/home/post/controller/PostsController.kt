package com.home.post.controller

import com.home.post.entity.Posts
import com.home.post.models.PostModel
import com.home.post.service.PostsService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/posts")
class PostsController(
    val postsService: PostsService
) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(this::class.java)
    }

    @PostMapping
    fun savePosts(@RequestBody postModel: PostModel): ResponseEntity<Posts> {
        logger.info("PostsController:savePosts execution started with input : $postModel")
        postsService.savePosts(postModel)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/{id}")
    fun getPostsById(@PathVariable id: String): ResponseEntity<Optional<Posts>> {
        logger.info("PostsController:getPostsById execution started with id : $id")
        return ResponseEntity.ok(postsService.getPostsById(id))
    }

    @GetMapping("/body/{body}")
    fun postsByBody(@PathVariable body: String): ResponseEntity<List<Posts>> {
        logger.info("PostsController:postsByBody execution started with input : $body")
        return ResponseEntity.ok(postsService.getPostsByBody(body))
    }

    @PutMapping
    fun updatePosts(@RequestBody postModel: PostModel, @RequestParam id: String): ResponseEntity<Posts> {
        logger.info("PostsController:updatePosts execution started with input : $postModel")
        return ResponseEntity.accepted().body(postsService.updatePosts(postModel, id))
    }

    @DeleteMapping("/{id}")
    fun deletePosts(@PathVariable id: String) {
        logger.info("PostsController:deletePosts execution started with id : $id")
        postsService.deletePosts(id)
        logger.info("deletePosts execution ended")
    }

    @GetMapping("/all")
    fun getAllPosts(): ResponseEntity<List<Posts>> {
        logger.info("PostsController:getAllPosts execution started")
        return ResponseEntity.ok().body(postsService.getAllPosts())
    }

    @GetMapping("/custom")
    fun getPostsByBodyAndTitleAndCategory(@RequestParam body: String, @RequestParam title: String, @RequestParam category: String): ResponseEntity<Optional<Posts>> {
        logger.info("PostsController:getAllPosts execution started")
        return ResponseEntity.ok().body(postsService.getPostsByBodyAndTitleAndCategory(body, title, category))
    }
}
