package uk.fmayoral.blackjack.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import uk.fmayoral.blackjack.core.service.GameService;
import uk.fmayoral.blackjack.web.api.v1.BlackjackAPIv1;
import uk.fmayoral.blackjack.web.api.v1.models.Game;
import uk.fmayoral.blackjack.web.api.v1.models.PlayerAction;

/**
 * Blackjack REST resource implementation.
 *
 * @see <a href="{@docRoot}/docs/architecture/REST.md">REST</a>.
 */
@Slf4j
@RestController
public class BlackjackResource implements BlackjackAPIv1 {

  private final GameService gameService;

  @Autowired
  public BlackjackResource(GameService gameService) {
    this.gameService = gameService;
  }

  @Override
  public Game startNewGame() {
    log.info("Start new game invoked");
    return gameService.startNewGame();
  }

  @Override
  public Game playerAction(String gameId, PlayerAction action) {
    log.info("Player action invoked for gameId:{} action:{}", gameId, action.getAction());
    return gameService.performAction(gameId, action);
  }

  @Override
  public Game getGame(String gameId) {
    log.info("Get game invoked for gameId:{}", gameId);
    return gameService.getGameById(gameId);
  }
}
