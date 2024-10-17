package ru.mirea.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.mirea.domain.Like;
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
        postRepository.findById(postId).ifPresentOrElse(post -> likeRepository.save(Like.builder()
                .post(post)
                .submittedAt(LocalDateTime.now())
                .build()), () -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        });
    }

    @Override
    public void unsumbitLike(Long postId) {
        postRepository.findById(postId)
                .map(likeRepository::findByPost)
                .ifPresentOrElse(likes -> likes.stream().findAny()
                        .ifPresentOrElse(likeRepository::delete, () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                        }), () -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                });
    }
}
