package ru.mirea.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.domain.Like;
import ru.mirea.domain.Post;
import ru.mirea.exception.LikeNotFoundException;
import ru.mirea.repository.LikeRepository;
import ru.mirea.service.LikeService;
import ru.mirea.service.PostService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final PostService postService;
    private final LikeRepository likeRepository;

    @Override
    public void submitLike(Long postId) {
        Post post = postService.findById(postId);

        Like like = Like.builder()
                .post(post)
                .submittedAt(LocalDateTime.now())
                .build();

        likeRepository.save(like);
    }

    @Override
    public void unsumbitLike(Long postId) {
        Post post = postService.findById(postId);

        likeRepository.findByPost(post).stream()
                .findAny()
                .ifPresentOrElse(likeRepository::delete, () -> {
                    throw new LikeNotFoundException(postId);
                });
    }

    @Override
    public Integer countLikesByPost(Post post) {
        return likeRepository.countByPost(post);
    }
}
