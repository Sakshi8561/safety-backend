package com.sakshi.chatapp.safety_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sakshi.chatapp.safety_backend.service.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //Create Post
    @PostMapping
    public ResponseEntity<?> createPost(@RequestParam Long authorId,
                                        @RequestParam String content) {

        String result = postService.createPost(authorId, content);
        return ResponseEntity.ok(result);
    }

    //Add Comment
    @PostMapping("/{postId}/comments")
    public ResponseEntity<?> addComment(@PathVariable Long postId,
                                        @RequestParam Long authorId,
                                        @RequestParam int depthLevel,
                                        @RequestParam boolean isBot) {

        String result = postService.addComment(postId, authorId, depthLevel, isBot);

        //Handle failures properly
        if (result.contains("limit") || result.contains("Cooldown") || result.contains("Max depth")) {
            return ResponseEntity.status(429).body(result);
        }

        return ResponseEntity.ok(result);
    }

    //Like Post
    @PostMapping("/{postId}/like")
    public ResponseEntity<?> likePost(@PathVariable Long postId) {

        String result = postService.likePost(postId);
        return ResponseEntity.ok(result);
    }
}