package ru.mirea.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.mirea.dto.ErrorDto;
import ru.mirea.exception.LikeNotFoundException;
import ru.mirea.exception.PostNotFoundException;

@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler({PostNotFoundException.class, LikeNotFoundException.class})
    public ResponseEntity<ErrorDto> handleNotFoundException(Throwable ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorDto.builder()
                        .code(404)
                        .message(ex.getMessage())
                        .build());
    }


    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        StringBuilder messageBuilder = new StringBuilder("Ошибка валидации данных:");
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            messageBuilder.append(String.format(" [Поле '%s': %s],", fieldName, errorMessage));
        });
        String message = messageBuilder.toString();
        return buildBadRequestErrorResponseEntity(message);
    }

    private ResponseEntity<ErrorDto> buildBadRequestErrorResponseEntity(String message) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorDto.builder()
                        .code(400)
                        .message(message)
                        .build());
    }
}
