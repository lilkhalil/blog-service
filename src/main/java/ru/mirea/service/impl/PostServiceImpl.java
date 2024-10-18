package ru.mirea.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.domain.Post;
import ru.mirea.dto.PostRqDto;
import ru.mirea.exception.PostNotFoundException;
import ru.mirea.repository.PostRepository;
import ru.mirea.service.PostService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Post findById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));
    }

    @Override
    public Post createPost(PostRqDto requestBody) {

        Post post = Post.builder()
                .createdAt(LocalDateTime.now())
                .title(requestBody.getTitle())
                .content(requestBody.getContent())
                .author(requestBody.getAuthor())
                .build();

        postRepository.saveAndFlush(post);

        return post;
    }

    @Override
    public void deletePost(Long postId) {
        postRepository.findById(postId).ifPresentOrElse(postRepository::delete, () -> {
            throw new PostNotFoundException(postId);
        });
    }

    @Override
    public Post updatePost(Long postId, PostRqDto requestBody) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));

        post.setUpdatedAt(LocalDateTime.now())
                .setTitle(requestBody.getTitle())
                .setContent(requestBody.getContent())
                .setAuthor(requestBody.getAuthor());

        postRepository.saveAndFlush(post);

        return post;
    }
}
