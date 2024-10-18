package ru.mirea.service;

import ru.mirea.dto.PostDto;
import ru.mirea.dto.PostRqDto;

import java.util.List;

public interface PostService {
    List<PostDto> findAll();
    PostDto findById(Long postId);
    PostDto createPost(PostRqDto requestBody);
    void deletePost(Long postId);
    PostDto updatePost(Long postId, PostRqDto requestBody);
}
