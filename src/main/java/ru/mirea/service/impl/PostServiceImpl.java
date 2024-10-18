package ru.mirea.service.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.domain.Post;
import ru.mirea.dto.PostDto;
import ru.mirea.dto.PostRqDto;
import ru.mirea.exception.PostNotFoundException;
import ru.mirea.mapper.PostMapper;
import ru.mirea.repository.PostRepository;
import ru.mirea.service.PostService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Override
    public List<PostDto> findAll() {
        return postRepository.findAll().stream()
                .map(postMapper::postToPostDto)
                .toList();
    }

    @Override
    public PostDto findById(Long postId) {
        return postRepository.findById(postId)
                .map(postMapper::postToPostDto)
                .orElseThrow(() -> new PostNotFoundException(postId));
    }

    @Override
    public PostDto createPost(@Valid PostRqDto requestBody) {
        Post post = postMapper.createPostRqDtoToPost(requestBody);
        post.setCreatedAt(LocalDateTime.now());
        postRepository.saveAndFlush(post);
        return postMapper.postToPostDto(post);
    }

    @Override
    public void deletePost(Long postId) {
        postRepository.findById(postId).ifPresentOrElse(postRepository::delete, () -> {
            throw new PostNotFoundException(postId);
        });
    }

    @Override
    public PostDto updatePost(Long postId, @Valid PostRqDto requestBody) {
        return postRepository.findById(postId)
                .map(post -> post.setUpdatedAt(LocalDateTime.now())
                        .setTitle(requestBody.getTitle())
                        .setContent(requestBody.getContent())
                        .setAuthor(requestBody.getAuthor()))
                .map(postRepository::saveAndFlush)
                .map(postMapper::postToPostDto)
                .orElseThrow(() -> new PostNotFoundException(postId));
    }
}
