package ru.mirea.exception;

public class PostNotFoundException extends RuntimeException {

    private final static String msg = "Post with id=%d was not found!";

    public PostNotFoundException(Long postId) {
        super(msg.formatted(postId));
    }
}
