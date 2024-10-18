package ru.mirea.exception;

public class LikeNotFoundException extends RuntimeException {

    private final static String msg = "Like on post with id=%d was not found!";

    public LikeNotFoundException(Long postId) {
        super(msg.formatted(postId));
    }
}
