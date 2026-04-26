package com.sakshi.chatapp.safety_backend.service;

import com.sakshi.chatapp.safety_backend.entity.Post;
import com.sakshi.chatapp.safety_backend.entity.Comment;
import com.sakshi.chatapp.safety_backend.repository.PostRepository;
import com.sakshi.chatapp.safety_backend.repository.CommentRepository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final BotLimitService botLimitService;
    private final ViralityService viralityService;
    private final NotificationService notificationService;

    public PostService(PostRepository postRepository,
                       CommentRepository commentRepository,
                       RedisTemplate<String, Object> redisTemplate,
                       BotLimitService botLimitService,
                       ViralityService viralityService,
                       NotificationService notificationService) {

        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.redisTemplate = redisTemplate;
        this.botLimitService = botLimitService;
        this.viralityService = viralityService;
        this.notificationService = notificationService;
    }

   
    public String createPost(Long authorId, String content) {

        Post post = new Post();
        post.setAuthorId(authorId);
        post.setContent(content);
        post.setCreatedAt(LocalDateTime.now());

        postRepository.save(post);

        return "Post created ✅";
    }

   
    public String addComment(Long postId,
                             Long authorId,
                             int depthLevel,
                             boolean isBot) {

       
        if (depthLevel > 20) {
            return "Max depth reached";
        }

        // Fetch post
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Long postOwnerId = post.getAuthorId(); // human

        if (isBot) {

            if (!botLimitService.canBotInteract(postId)) {
                return "Bot limit reached";
            }

            //Cooldow
            String cooldownKey =
                    "cooldown:bot_" + authorId + ":human_" + postOwnerId;

            Boolean exists = redisTemplate.hasKey(cooldownKey);

            if (Boolean.TRUE.equals(exists)) {
                return "Cooldown active";
            }

            // set cooldown
            redisTemplate.opsForValue().set(
                    cooldownKey,
                    "1",
                    10,
                    TimeUnit.MINUTES
            );

            // Virality (bot reply = +1)
            viralityService.increaseScore(postId, 1);

            //Notification
            notificationService.handleNotification(postOwnerId, authorId);
        }

       
        else {
            viralityService.increaseScore(postId, 50);
        }

        
        // SAVE COMMENT (AFTER ALL CHECKS)
        Comment comment = new Comment();
        comment.setPostId(postId);
        comment.setAuthorId(authorId);
        comment.setDepthLevel(depthLevel);
        comment.setContent("Sample comment");
        comment.setCreatedAt(LocalDateTime.now());

        commentRepository.save(comment);

        return "Comment added ✅";
    }

    //LIKE POST
    public String likePost(Long postId) {

        viralityService.increaseScore(postId, 20);

        return "Post liked ✅";
    }
}