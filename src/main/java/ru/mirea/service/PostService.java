package ru.mirea.service;

import ru.mirea.domain.Post;
import ru.mirea.dto.PostRqDto;

import java.util.List;

public interface PostService {
    List<Post> findAll();
    Post findById(Long postId);
    Post createPost(PostRqDto requestBody);
    void deletePost(Long postId);
    Post updatePost(Long postId, PostRqDto requestBody);
}
