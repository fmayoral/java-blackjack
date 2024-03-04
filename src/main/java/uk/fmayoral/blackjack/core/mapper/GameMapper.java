package uk.fmayoral.blackjack.core.mapper;

import uk.fmayoral.blackjack.core.models.BlackJackGame;
import uk.fmayoral.blackjack.web.api.v1.models.Game;

public interface GameMapper {
  Game mapInitialGame(BlackJackGame blackJackGame);

  Game map(BlackJackGame blackJackGame);
}
