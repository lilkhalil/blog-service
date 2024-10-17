package ru.mirea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.domain.Like;
import ru.mirea.domain.Post;
import ru.mirea.domain.User;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndPost(User user, Post post);
    List<Like> findByPost(Post post);
}
