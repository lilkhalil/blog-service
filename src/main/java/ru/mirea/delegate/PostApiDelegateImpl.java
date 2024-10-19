package ru.mirea.delegate;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.mirea.api.PostApiDelegate;
import ru.mirea.domain.Post;
import ru.mirea.dto.PostDto;
import ru.mirea.dto.PostRqDto;
import ru.mirea.mapper.PostMapper;
import ru.mirea.service.PostService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostApiDelegateImpl implements PostApiDelegate {

    private final PostService postService;
    private final PostMapper postMapper;

    @Override
    public ResponseEntity<List<PostDto>> getPosts() {
        return postService.findAll().stream()
                .map(postMapper::postToPostDto)
                .collect(Collectors.collectingAndThen(Collectors.toList(), ResponseEntity::ok));
    }

    @Override
    public ResponseEntity<PostDto> createPost(@Valid PostRqDto postRqDto) {
        Post post = postService.createPost(postRqDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postMapper.postToPostDto(post));
    }

    @Override
    public ResponseEntity<PostDto> getPost(Long postId) {
        Post post = postService.findById(postId);
        return ResponseEntity.ok(postMapper.postToPostDto(post));
    }

    @Override
    public ResponseEntity<Void> deletePost(Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<PostDto> updatePost(Long postId, @Valid PostRqDto postRqDto) {
        Post post = postService.updatePost(postId, postRqDto);
        return ResponseEntity.ok(postMapper.postToPostDto(post));
    }
}
