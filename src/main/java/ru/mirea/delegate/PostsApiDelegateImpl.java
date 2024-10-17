package ru.mirea.delegate;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.mirea.api.PostsApiDelegate;
import ru.mirea.dto.PostDto;
import ru.mirea.dto.PostRqDto;
import ru.mirea.service.PostService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostsApiDelegateImpl implements PostsApiDelegate {

    private final PostService postService;

    @Override
    public ResponseEntity<List<PostDto>> getPosts() {
        return ResponseEntity.ok(postService.findAll());
    }

    @Override
    public ResponseEntity<PostDto> createPost(PostRqDto postRqDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postService.createPost(postRqDto));
    }

    @Override
    public ResponseEntity<PostDto> getPost(Long postId) {
        return ResponseEntity.of(postService.findById(postId));
    }

    @Override
    public ResponseEntity<Void> deletePost(Long postId) {
        try {
            postService.deletePost(postId);
            return ResponseEntity.noContent().build();
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).build();
        }
    }

    @Override
    public ResponseEntity<PostDto> updatePost(Long postId, PostRqDto postRqDto) {
        return ResponseEntity.of(postService.updatePost(postId, postRqDto));
    }
}
