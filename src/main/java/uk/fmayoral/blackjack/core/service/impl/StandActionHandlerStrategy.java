package uk.fmayoral.blackjack.core.service.impl;

import io.opentelemetry.instrumentation.annotations.WithSpan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import uk.fmayoral.blackjack.core.models.BlackJackGame;
import uk.fmayoral.blackjack.core.models.Card;
import uk.fmayoral.blackjack.core.models.Deck;
import uk.fmayoral.blackjack.core.models.GameStatus;
import uk.fmayoral.blackjack.core.models.Hand;
import uk.fmayoral.blackjack.core.service.ActionHandlerStrategy;
import uk.fmayoral.blackjack.core.service.CalculatorService;
import uk.fmayoral.blackjack.core.service.OutcomeEvaluatorService;

import static uk.fmayoral.blackjack.core.service.Constants.SOFT_17_THRESHOLD;

/**
 * Handles the player Stand action for a given game.
 *
 * @see <a href="{@docRoot}/docs/patterns/Strategy-classic.md">Strategy</a>.
 */
@Slf4j
@Component
@Qualifier("StandActionHandler")
public class StandActionHandlerStrategy implements ActionHandlerStrategy {
  private final CalculatorService calculatorService;
  private final OutcomeEvaluatorService evaluatorService;
  @Autowired
  public StandActionHandlerStrategy(CalculatorService calculatorService, OutcomeEvaluatorService evaluatorService) {
    this.calculatorService = calculatorService;
    this.evaluatorService = evaluatorService;
  }

  @WithSpan
  @Override
  public BlackJackGame handleAction(BlackJackGame blackJackGame) {
    log.info("Game-{} - performing stand action", blackJackGame.getId());
    playDealerTurn(blackJackGame);
    updateGameStatus(blackJackGame);
    return blackJackGame;
  }

  private void playDealerTurn(BlackJackGame blackJackGame) {
    final Deck deck = blackJackGame.getDeck();
    final Hand dealerHand = blackJackGame.getDealerHand();
    while (dealerHand.getTotal() < SOFT_17_THRESHOLD) {
      final Card card = deck.draw();
      log.debug("Game-{} - dealer draws {}", blackJackGame.getId(), card);
      dealerHand.addCard(card);
      int newTotal = calculatorService.dealerHandTotal(dealerHand.getCards());
      dealerHand.setTotal(newTotal);
    }
  }

  private void updateGameStatus(BlackJackGame blackJackGame) {
    final GameStatus playerHitOutcome = evaluatorService.determineFinalOutcome(blackJackGame);
    blackJackGame.setStatus(playerHitOutcome);
    log.info("Game-{} - new status after stand action: {}", blackJackGame.getId(), blackJackGame.getStatus());

  }
}
