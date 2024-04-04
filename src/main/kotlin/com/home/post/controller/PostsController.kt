package com.home.post.controller

import com.home.post.entity.Posts
import com.home.post.models.PostModel
import com.home.post.service.PostsService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/posts")
class PostsController(
    val postsService: PostsService
) {

    @PostMapping
    fun savePosts(@RequestBody postModel: PostModel): Posts {
        return postsService.savePosts(postModel)
    }

    @GetMapping("/{id}")
    fun getPostsById(@PathVariable id: String): Optional<Posts> {
        return postsService.getPostsById(id);
    }

    @GetMapping("/body/{body}")
    fun postsByBody(@PathVariable body: String): List<Posts> {
        return postsService.getPostsByBody(body)
    }

    @PutMapping
    fun updatePosts(@RequestBody postModel: PostModel, @RequestParam id: String): Posts {
        return postsService.updatePosts(postModel, id);
    }

    @DeleteMapping("/{id}")
    fun deletePosts(@PathVariable id: String) {
        postsService.deletePosts(id);
    }

    @GetMapping("/all")
    fun getAllPosts(): List<Posts> {
        return postsService.getAllPosts();
    }
}
