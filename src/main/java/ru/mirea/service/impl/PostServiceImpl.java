package ru.mirea.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.mirea.domain.Post;
import ru.mirea.dto.PostDto;
import ru.mirea.dto.PostRqDto;
import ru.mirea.mapper.PostMapper;
import ru.mirea.repository.PostRepository;
import ru.mirea.service.PostService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    public Optional<PostDto> findById(Long postId) {
        return postRepository.findById(postId)
                .map(postMapper::postToPostDto);
    }

    @Override
    public PostDto createPost(PostRqDto requestBody) {
        Post post = postMapper.createPostRqDtoToPost(requestBody);
        post.setCreatedAt(LocalDateTime.now());
        postRepository.saveAndFlush(post);
        return postMapper.postToPostDto(post);
    }

    @Override
    public void deletePost(Long postId) {
        postRepository.findById(postId).ifPresentOrElse(postRepository::delete, () -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        });
    }

    @Override
    public Optional<PostDto> updatePost(Long postId, PostRqDto requestBody) {
        return postRepository.findById(postId)
                .map(post -> post.setUpdatedAt(LocalDateTime.now())
                        .setTitle(requestBody.getTitle())
                        .setContent(requestBody.getContent())
                        .setAuthor(requestBody.getAuthor()))
                .map(postRepository::saveAndFlush)
                .map(postMapper::postToPostDto);
    }
}
