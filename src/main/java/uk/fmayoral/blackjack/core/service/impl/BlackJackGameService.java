package uk.fmayoral.blackjack.core.service.impl;

import io.opentelemetry.instrumentation.annotations.WithSpan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.fmayoral.blackjack.core.exception.GameNotFoundException;
import uk.fmayoral.blackjack.core.factory.GameFactory;
import uk.fmayoral.blackjack.core.mapper.GameMapper;
import uk.fmayoral.blackjack.core.models.BlackJackGame;
import uk.fmayoral.blackjack.core.repository.GameRepository;
import uk.fmayoral.blackjack.core.service.ActionHandlingService;
import uk.fmayoral.blackjack.core.service.GameService;
import uk.fmayoral.blackjack.web.api.v1.models.Game;
import uk.fmayoral.blackjack.web.api.v1.models.PlayerAction;

/**
 * Runs the blackjack game.
 *
 * @see <a href="{@docRoot}/docs/principles/SRP-OCP-DIP.md">Blackjack Game Service</a>.
 */
@Slf4j
@Service
public class BlackJackGameService implements GameService {

  private final GameRepository gameRepository;
  private final GameMapper gameMapper;
  private final GameFactory gameFactory;
  private final ActionHandlingService blackjackActionHandlingService;

  @Autowired
  public BlackJackGameService(GameRepository gameRepository,
                              GameMapper gameMapper,
                              GameFactory gameFactory,
                              ActionHandlingService blackjackActionHandlingService) {
    this.gameRepository = gameRepository;
    this.gameMapper = gameMapper;
    this.gameFactory = gameFactory;
    this.blackjackActionHandlingService = blackjackActionHandlingService;
  }

  @WithSpan
  @Override
  public Game startNewGame() {
    log.info("Creating new game");
    BlackJackGame blackJackGame = gameFactory.createNewGame();
    gameRepository.save(blackJackGame);
    log.info("Game-{} - Created", blackJackGame.getId());
    return gameMapper.mapInitialGame(blackJackGame);
  }

  @WithSpan
  @Override
  public Game getGameById(String gameId) {
    log.info("Game-{} - getting game by id {}", gameId, gameId);
    return this.gameRepository.findById(gameId)
        .map(gameMapper::map)
        .orElseThrow(() -> new GameNotFoundException(gameId));
  }

  @WithSpan
  @Override
  public Game performAction(String gameId, PlayerAction action) {
    log.debug("Game-{} - performing action {}", gameId, action.getAction());
    return blackjackActionHandlingService.performAction(gameId, action);
  }

}
