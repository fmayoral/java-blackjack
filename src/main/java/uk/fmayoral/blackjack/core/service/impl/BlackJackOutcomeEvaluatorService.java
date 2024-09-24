package uk.fmayoral.blackjack.core.service.impl;

import io.opentelemetry.instrumentation.annotations.WithSpan;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uk.fmayoral.blackjack.core.models.BlackJackGame;
import uk.fmayoral.blackjack.core.models.GameStatus;
import uk.fmayoral.blackjack.core.service.OutcomeEvaluatorService;

import java.util.Arrays;
import java.util.function.BiPredicate;

import static uk.fmayoral.blackjack.core.models.GameStatus.DEALER_WINS;
import static uk.fmayoral.blackjack.core.models.GameStatus.IN_PROGRESS;
import static uk.fmayoral.blackjack.core.models.GameStatus.PLAYER_WINS;
import static uk.fmayoral.blackjack.core.models.GameStatus.TIE;
import static uk.fmayoral.blackjack.core.service.Constants.BUST_THRESHOLD;
import static uk.fmayoral.blackjack.core.service.impl.BlackJackOutcomeEvaluatorService.Outcome.PLAYER_BUSTED;

/**
 * Determines the outcome of a Blackjack game based on the totals of the player's and dealer's hands
 *
 * @see <a href="{@docRoot}/docs/patterns/Strategy-and-Command.md">Strategy and Command</a>.
 */
@Slf4j
@Service
public class BlackJackOutcomeEvaluatorService implements OutcomeEvaluatorService {

  @WithSpan
  @Override
  public GameStatus determinePlayerHitOutcome(BlackJackGame game) {
    final int playerHandTotal = game.getPlayerHand().getTotal();
    final int dealerHandTotal = game.getDealerHand().getTotal();
    final GameStatus outcome = evaluatePlayerHitOutcome(playerHandTotal, dealerHandTotal);
    logOutcome(game, outcome);
    return outcome;
  }

  public GameStatus evaluatePlayerHitOutcome(int playerHandTotal, int dealerHandTotal) {
    return PLAYER_BUSTED.check(playerHandTotal, dealerHandTotal) ? DEALER_WINS : IN_PROGRESS;
  }

  @WithSpan
  @Override
  public GameStatus determineFinalOutcome(BlackJackGame game) {
    int playerHand = game.getPlayerHand().getTotal();
    int dealerHand = game.getDealerHand().getTotal();
    final GameStatus outcome = determineOutcome(playerHand, dealerHand);
    logOutcome(game, outcome);
    return outcome;
  }

  private void logOutcome(BlackJackGame game, GameStatus outcome) {
    int playerHand = game.getPlayerHand().getTotal();
    int dealerHand = game.getDealerHand().getTotal();

    log.info("Game-{} - player hand total is {} and dealer hand total is {} - resolving to {}",
        game.getId(), playerHand, dealerHand, outcome);
  }

  private GameStatus determineOutcome(int playerHandTotal, int dealerHandTotal) {
    return Arrays.stream(Outcome.values())
        .filter(outcome -> outcome.check(playerHandTotal, dealerHandTotal))
        .findFirst()
        .map(Outcome::getGameStatus)
        .orElseThrow(() -> new IllegalStateException("Unexpected game state"));
  }

  @Getter
  @AllArgsConstructor
  public enum Outcome {
    PLAYER_BUSTED((playerHandTotal, dealerHandTotal) -> playerHandTotal > BUST_THRESHOLD, DEALER_WINS),
    DEALER_BUSTED((playerHandTotal, dealerHandTotal) -> dealerHandTotal > BUST_THRESHOLD, PLAYER_WINS),
    GAME_DRAW((playerHandTotal, dealerHandTotal) -> playerHandTotal == dealerHandTotal, TIE),
    PLAYER_HIGHER((playerHandTotal, dealerHandTotal) -> playerHandTotal > dealerHandTotal, PLAYER_WINS),
    DEALER_HIGHER((playerHandTotal, dealerHandTotal) -> dealerHandTotal > playerHandTotal, DEALER_WINS);

    private final BiPredicate<Integer, Integer> condition;
    private final GameStatus gameStatus;

    public boolean check(Integer playerHand, Integer dealerHand) {
      return condition.test(playerHand, dealerHand);
    }
  }
}
