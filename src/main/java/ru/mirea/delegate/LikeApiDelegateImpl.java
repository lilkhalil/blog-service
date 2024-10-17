package ru.mirea.delegate;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.mirea.api.LikeApiDelegate;
import ru.mirea.service.LikeService;

@Service
@RequiredArgsConstructor
public class LikeApiDelegateImpl implements LikeApiDelegate {

    private final LikeService likeService;

    @Override
    public ResponseEntity<Void> submitLike(Long postId) {
        try {
            likeService.submitLike(postId);
            return ResponseEntity.ok().build();
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).build();
        }
    }

    @Override
    public ResponseEntity<Void> unsumbitLike(Long postId) {
        try {
            likeService.unsumbitLike(postId);
            return ResponseEntity.noContent().build();
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).build();
        }
    }
}
