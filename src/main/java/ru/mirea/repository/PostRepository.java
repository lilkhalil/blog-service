package ru.mirea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
