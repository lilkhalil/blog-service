package ru.mirea.delegate;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.mirea.api.LikeApiDelegate;
import ru.mirea.service.LikeService;

@Service
@RequiredArgsConstructor
public class LikeApiDelegateImpl implements LikeApiDelegate {

    private final LikeService likeService;

    @Override
    public ResponseEntity<Void> submitLike(Long postId) {
        likeService.submitLike(postId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> unsumbitLike(Long postId) {
        likeService.unsumbitLike(postId);
        return ResponseEntity.noContent().build();
    }
}
