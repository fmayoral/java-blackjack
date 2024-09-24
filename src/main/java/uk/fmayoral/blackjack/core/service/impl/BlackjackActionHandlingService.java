package uk.fmayoral.blackjack.core.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import uk.fmayoral.blackjack.core.exception.GameFinishedException;
import uk.fmayoral.blackjack.core.exception.GameNotFoundException;
import uk.fmayoral.blackjack.core.mapper.GameMapper;
import uk.fmayoral.blackjack.core.models.BlackJackGame;
import uk.fmayoral.blackjack.core.models.GameStatus;
import uk.fmayoral.blackjack.core.repository.GameRepository;
import uk.fmayoral.blackjack.core.service.ActionHandlerStrategy;
import uk.fmayoral.blackjack.core.service.ActionHandlingService;
import uk.fmayoral.blackjack.web.api.v1.models.Game;
import uk.fmayoral.blackjack.web.api.v1.models.PlayerAction;
import uk.fmayoral.blackjack.web.api.v1.models.PlayerAction.ActionType;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

/**
 * Handles the player action for a given game.
 *
 * @see <a href="{@docRoot}/docs/patterns/Strategy-classic.md">Strategy</a>.
 */
@Slf4j
@Service
public class BlackjackActionHandlingService implements ActionHandlingService {
  private final Map<ActionType, ActionHandlerStrategy> actionHandlers = new EnumMap<>(ActionType.class);
  private final GameRepository gameRepository;

  private final GameMapper gameMapper;

  @Autowired
  public BlackjackActionHandlingService(GameRepository gameRepository,
                                        GameMapper gameMapper,
                                        @Qualifier("HitActionHandler") ActionHandlerStrategy hitHandler,
                                        @Qualifier("StandActionHandler") ActionHandlerStrategy standHandler) {
    this.gameRepository = gameRepository;
    this.gameMapper = gameMapper;
    actionHandlers.put(ActionType.HIT, hitHandler);
    actionHandlers.put(ActionType.STAND, standHandler);
  }

  @Override
  public Game performAction(String gameId, PlayerAction playerAction) {
    final BlackJackGame game = gameRepository.findById(gameId)
        .orElseThrow(() -> new GameNotFoundException(gameId));

    if (!game.isInProgress()) {
      log.warn("Game-{} - game is finished, no further actions can be taken", game.getId());
      throw new GameFinishedException(game.getId());
    }

    return Optional.ofNullable(actionHandlers.get(playerAction.getAction()))
        .map(strategy -> strategy.handleAction(game))
        .map(gameRepository::save)
        .map(this::mapGame)
        .orElseThrow(() -> new IllegalStateException("Unknown action: " + playerAction));
  }

  private Game mapGame(BlackJackGame blackJackGame) {
    if (GameStatus.IN_PROGRESS == blackJackGame.getStatus()) {
      return gameMapper.mapInitialGame(blackJackGame);
    }
    return gameMapper.map(blackJackGame);
  }

}
