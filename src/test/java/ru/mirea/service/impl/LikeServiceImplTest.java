package ru.mirea.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mirea.domain.Like;
import ru.mirea.domain.Post;
import ru.mirea.exception.LikeNotFoundException;
import ru.mirea.exception.PostNotFoundException;
import ru.mirea.repository.LikeRepository;
import ru.mirea.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LikeServiceImplTest {

    @Mock
    private LikeRepository likeRepository;
    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private LikeServiceImpl likeService;

    private Post post;
    private Like like;

    @BeforeEach()
    public void setUp() {
        post = Post.builder()
                .id(1L)
                .title("Title")
                .content("Content")
                .author("Aidar Khalilov")
                .build();

        like = Like.builder()
                .id(1L)
                .submittedAt(LocalDateTime.now())
                .post(post)
                .build();
    }

    @Test
    public void test_submitLike_expectLikeSubmitted() {
        when(postRepository.findById(any())).thenReturn(Optional.of(post));

        likeService.submitLike(1L);

        verify(likeRepository, times(1)).save(any());
    }

    @Test
    public void test_submitLike_expectThrowsPostNotFoundException() {
        when(postRepository.findById(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> likeService.submitLike(1L))
                .isInstanceOf(PostNotFoundException.class)
                .hasMessageMatching("Post with id=\\d+ was not found!");
    }

    @Test
    public void test_unsubmitLike_expectLikeUnsubmitted() {
        when(postRepository.findById(any())).thenReturn(Optional.of(post));
        when(likeRepository.findByPost(post)).thenReturn(List.of(like));

        likeService.unsumbitLike(1L);

        verify(likeRepository, times(1)).delete(like);
    }

    @Test
    public void test_unsubmitLike_expectThrowsPostNotFoundException() {
        when(postRepository.findById(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> likeService.unsumbitLike(1L))
                .isInstanceOf(PostNotFoundException.class)
                .hasMessageMatching("Post with id=\\d+ was not found!");
    }

    @Test
    public void test_unsubmitLike_expectThrowsLikeNotFoundException() {
        when(postRepository.findById(any())).thenReturn(Optional.of(post));
        when(likeRepository.findByPost(post)).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> likeService.unsumbitLike(1L))
                .isInstanceOf(LikeNotFoundException.class)
                .hasMessageMatching("Like on post with id=\\d+ was not found!");
    }
}
