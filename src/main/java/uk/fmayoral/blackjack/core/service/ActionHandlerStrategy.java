package uk.fmayoral.blackjack.core.service;

import uk.fmayoral.blackjack.core.models.BlackJackGame;

public interface ActionHandlerStrategy {
  BlackJackGame handleAction(BlackJackGame game);
}
