package uk.fmayoral.blackjack.core.service;

import uk.fmayoral.blackjack.web.api.v1.models.Game;
import uk.fmayoral.blackjack.web.api.v1.models.PlayerAction;

public interface ActionHandlingService {
  Game performAction(String gameId, PlayerAction action);
}
