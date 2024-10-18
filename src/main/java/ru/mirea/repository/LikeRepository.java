package ru.mirea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.domain.Like;
import ru.mirea.domain.Post;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findByPost(Post post);
    Integer countByPost(Post post);
}
