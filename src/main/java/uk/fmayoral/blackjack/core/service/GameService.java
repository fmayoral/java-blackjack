package uk.fmayoral.blackjack.core.service;

import uk.fmayoral.blackjack.web.api.v1.models.Game;
import uk.fmayoral.blackjack.web.api.v1.models.PlayerAction;

public interface GameService {
  Game startNewGame();

  Game getGameById(String gameId);

  Game performAction(String gameId, PlayerAction action);
}
