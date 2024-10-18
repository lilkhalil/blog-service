package ru.mirea.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.domain.Like;
import ru.mirea.domain.Post;
import ru.mirea.exception.LikeNotFoundException;
import ru.mirea.exception.PostNotFoundException;
import ru.mirea.repository.LikeRepository;
import ru.mirea.repository.PostRepository;
import ru.mirea.service.LikeService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;

    @Override
    public void submitLike(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));

        Like like = Like.builder()
                .post(post)
                .submittedAt(LocalDateTime.now())
                .build();

        likeRepository.save(like);
    }

    @Override
    public void unsumbitLike(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        likeRepository.findByPost(post).stream()
                .findAny()
                .ifPresentOrElse(likeRepository::delete, () -> {
                    throw new LikeNotFoundException(postId);
                });
    }
}
