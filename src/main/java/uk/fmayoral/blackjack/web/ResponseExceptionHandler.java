package uk.fmayoral.blackjack.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uk.fmayoral.blackjack.core.exception.GameFinishedException;
import uk.fmayoral.blackjack.core.exception.GameNotFoundException;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(GameNotFoundException.class)
  public ResponseEntity<Object> handleGameNotFoundException(GameNotFoundException ex, WebRequest request) {
    String bodyOfResponse = "Resource not found";
    return new ResponseEntity<>(bodyOfResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(GameFinishedException.class)
  public ResponseEntity<Object> handleGameFinishedException(GameFinishedException ex, WebRequest request) {
    String bodyOfResponse = "Game finished";
    return new ResponseEntity<>(bodyOfResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<Object> handleResourceNotFoundException(IllegalStateException ex, WebRequest request) {
    String bodyOfResponse = "Server error";
    return new ResponseEntity<>(bodyOfResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

}