package uk.fmayoral.blackjack.core.exception;

public class GameFinishedException extends RuntimeException{
  private static final String MESSAGE_FORMAT = "Game %s is finished";

  public GameFinishedException(String gameId) {
    super(String.format(MESSAGE_FORMAT, gameId));
  }
}
