package ru.mirea.service;

import ru.mirea.domain.Post;

public interface LikeService {
    void submitLike(Long postId);
    void unsumbitLike(Long postId);
    Integer countLikesByPost(Post post);
}
