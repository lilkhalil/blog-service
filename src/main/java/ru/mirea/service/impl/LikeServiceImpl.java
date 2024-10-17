package ru.mirea.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.mirea.domain.Like;
import ru.mirea.domain.Post;
import ru.mirea.domain.User;
import ru.mirea.repository.LikeRepository;
import ru.mirea.repository.PostRepository;
import ru.mirea.repository.UserRepository;
import ru.mirea.service.LikeService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;

    @Override
    public void submitLike(Long postId) {
        User user = userRepository.findById(1L)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        likeRepository.findByUserAndPost(user, post).ifPresent(l -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        });

        Like like = Like.builder()
                .user(user)
                .post(post)
                .submittedAt(LocalDateTime.now())
                .build();

        likeRepository.save(like);
    }

    @Override
    public void unsumbitLike(Long postId) {
        User user = userRepository.findById(1L)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        likeRepository.findByUserAndPost(user, post).ifPresentOrElse(likeRepository::delete, () -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        });
    }
}
