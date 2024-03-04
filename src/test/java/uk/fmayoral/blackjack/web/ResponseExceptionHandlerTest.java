package uk.fmayoral.blackjack.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;
import uk.fmayoral.blackjack.core.exception.GameFinishedException;
import uk.fmayoral.blackjack.core.exception.GameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class ResponseExceptionHandlerTest {

  @Mock
  private WebRequest webRequest;

  private ResponseExceptionHandler responseExceptionHandler;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    responseExceptionHandler = new ResponseExceptionHandler();
  }

  @Test
  @DisplayName("GameNotFoundException returns NOT_FOUND")
  void handleGameNotFoundException_ReturnsNotFound() {
    GameNotFoundException exception = new GameNotFoundException("Game not found");

    ResponseEntity<Object> responseEntity = responseExceptionHandler.handleGameNotFoundException(exception, webRequest);

    assertNotNull(responseEntity);
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    assertEquals("Resource not found", responseEntity.getBody());
  }

  @Test
  @DisplayName("GameFinishedException returns BAD_REQUEST")
  void handleGameFinishedException_ReturnsBadRequest() {
    GameFinishedException exception = new GameFinishedException("Game finished");

    ResponseEntity<Object> responseEntity = responseExceptionHandler.handleGameFinishedException(exception, webRequest);

    assertNotNull(responseEntity);
    assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    assertEquals("Game finished", responseEntity.getBody());
  }

  @Test
  @DisplayName("IllegalStateException returns INTERNAL_SERVER_ERROR")
  void handleIllegalStateException_ReturnsInternalServerError() {
    IllegalStateException exception = new IllegalStateException("Illegal state");

    ResponseEntity<Object> responseEntity = responseExceptionHandler.handleResourceNotFoundException(exception, webRequest);

    assertNotNull(responseEntity);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    assertEquals("Server error", responseEntity.getBody());
  }
}