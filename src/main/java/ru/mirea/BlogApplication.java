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
        return args -> {
            postRepository.save(Post.builder()
                    .title("The Journey Begins")
                    .content("This is the first step in a long journey. Every great adventure starts somewhere.")
                    .author("Aidar Khalilov")
                    .createdAt(LocalDateTime.now())
                    .build());
            postRepository.save(Post.builder()
                    .title("Understanding Persistence")
                    .content("Persistence in databases is a key concept for managing data across sessions.")
                    .author("Aidar Khalilov")
                    .createdAt(LocalDateTime.now())
                    .build());
            postRepository.save(Post.builder()
                    .title("Mastering JPA")
                    .content("Java Persistence API (JPA) is an essential tool for mapping Java objects to database tables.")
                    .author("Aidar Khalilov")
                    .createdAt(LocalDateTime.now())
                    .build());
        };
    }

}
