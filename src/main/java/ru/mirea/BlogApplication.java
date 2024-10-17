package ru.mirea;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import ru.mirea.domain.Post;
import ru.mirea.repository.PostRepository;

import java.time.LocalDateTime;

@SpringBootApplication
public class BlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

    @Bean
    @Profile("dev")
    public CommandLineRunner commandLineRunner(PostRepository postRepository) {
        return args -> postRepository.save(Post.builder()
                .title("Title")
                .content("Hello, World")
                .author("Aidar Khalilov")
                .createdAt(LocalDateTime.now())
                .build());
    }

}
