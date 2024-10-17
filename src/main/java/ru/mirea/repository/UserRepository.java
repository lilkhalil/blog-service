package ru.mirea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
