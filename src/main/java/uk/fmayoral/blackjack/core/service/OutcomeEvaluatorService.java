package uk.fmayoral.blackjack.core.service;

import uk.fmayoral.blackjack.core.models.BlackJackGame;
import uk.fmayoral.blackjack.core.models.GameStatus;

public interface OutcomeEvaluatorService {
  GameStatus determinePlayerHitOutcome(BlackJackGame game);

  GameStatus determineFinalOutcome(BlackJackGame game);
}
