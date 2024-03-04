package uk.fmayoral.blackjack.core.exception;

public class GameNotFoundException extends RuntimeException{
  private static final String MESSAGE_FORMAT = "Unable to find game %s";

  public GameNotFoundException(String gameId) {
    super(String.format(MESSAGE_FORMAT, gameId));
  }
}
