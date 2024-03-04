package uk.fmayoral.blackjack.core.factory;

import uk.fmayoral.blackjack.core.models.BlackJackGame;

public interface GameFactory {
  BlackJackGame createNewGame();
}