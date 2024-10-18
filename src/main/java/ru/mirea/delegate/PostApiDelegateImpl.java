package ru.mirea.delegate;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.mirea.api.PostApiDelegate;
import ru.mirea.dto.PostDto;
import ru.mirea.dto.PostRqDto;
import ru.mirea.service.PostService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostApiDelegateImpl implements PostApiDelegate {

    private final PostService postService;

    @Override
    public ResponseEntity<List<PostDto>> getPosts() {
        return ResponseEntity.ok(postService.findAll());
    }

    @Override
    public ResponseEntity<PostDto> createPost(@Valid PostRqDto postRqDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postService.createPost(postRqDto));
    }

    @Override
    public ResponseEntity<PostDto> getPost(Long postId) {
        return ResponseEntity.ok(postService.findById(postId));
    }

    @Override
    public ResponseEntity<Void> deletePost(Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<PostDto> updatePost(Long postId, @Valid PostRqDto postRqDto) {
        return ResponseEntity.ok(postService.updatePost(postId, postRqDto));
    }
}
