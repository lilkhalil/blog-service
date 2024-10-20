package ru.mirea.service.impl;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mirea.domain.Post;
import ru.mirea.dto.PostDto;
import ru.mirea.dto.PostRqDto;
import ru.mirea.exception.PostNotFoundException;
import ru.mirea.mapper.PostMapper;
import ru.mirea.mapper.PostMapperImpl;
import ru.mirea.repository.PostRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl postService;

    private Post post;

    @BeforeEach()
    public void setUp() {
        post = Post.builder()
                .id(1L)
                .title("Title")
                .content("Content")
                .author("Aidar Khalilov")
                .build();
    }

    @Test
    public void test_findAll_expectNonEmptyArray() {
        when(postRepository.findAll()).thenReturn(List.of(post));

        List<Post> posts = postService.findAll();

        assertThat(posts)
                .hasSize(1)
                .contains(post);
    }

    @Test
    public void test_findById_expectValue() {
        when(postRepository.findById(any())).thenReturn(Optional.of(post));

        Post actual = postService.findById(1L);

        assertThat(actual)
                .isEqualTo(post);
    }

    @Test
    public void test_findById_expectThrowsPostNotFoundException() {
        when(postRepository.findById(any())).thenReturn(Optional.empty());

        assertThatPostNotFoundExceptionMatchesRegex(() -> postService.findById(1L));
    }

    @Test
    public void test_createPost_expectValue() {
        ArgumentCaptor<Post> expected = ArgumentCaptor.forClass(Post.class);

        Post actual = postService.createPost(new PostRqDto());

        verify(postRepository, times(1)).saveAndFlush(expected.capture());
        assertThat(actual)
                .isEqualTo(expected.getValue());
    }

    @Test
    public void test_deletePost_expectDeletion() {
        when(postRepository.findById(any())).thenReturn(Optional.of(post));

        postService.deletePost(1L);

        verify(postRepository, times(1)).delete(post);
    }

    @Test
    public void test_deletePost_expectThrowsPostNotFoundException() {
        when(postRepository.findById(any())).thenReturn(Optional.empty());

        assertThatPostNotFoundExceptionMatchesRegex(() -> postService.deletePost(1L));
    }

    @Test
    public void test_updatePost_expectValue() {
        ArgumentCaptor<Post> expected = ArgumentCaptor.forClass(Post.class);
        when(postRepository.findById(any())).thenReturn(Optional.of(post));

        Post actual = postService.updatePost(1L, new PostRqDto());

        verify(postRepository, times(1)).saveAndFlush(expected.capture());

        assertThat(actual)
                .isEqualTo(expected.getValue());
    }

    @Test
    public void test_updatePostw_expectThrowsPostNotFoundException() {
        when(postRepository.findById(any())).thenReturn(Optional.empty());

        assertThatPostNotFoundExceptionMatchesRegex(() -> postService.updatePost(1L, new PostRqDto()));
    }

    private void assertThatPostNotFoundExceptionMatchesRegex(ThrowingCallable callable) {
        assertThatThrownBy(callable)
                .isInstanceOf(PostNotFoundException.class)
                .hasMessageMatching("Post with id=\\d+ was not found!");
    }
}