package ru.mirea.service;

import ru.mirea.dto.PostDto;
import ru.mirea.dto.PostRqDto;

import java.util.List;
import java.util.Optional;

public interface PostService {
    List<PostDto> findAll();
    Optional<PostDto> findById(Long postId);
    PostDto createPost(PostRqDto requestBody);
    void deletePost(Long postId);
    Optional<PostDto> updatePost(Long postId, PostRqDto requestBody);
}
