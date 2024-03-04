package uk.fmayoral.blackjack.core.service.impl;

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

/**
 * Handles the player Hit action for a given game.
 *
 * @see <a href="{@docRoot}/docs/patterns/Strategy-classic.md">Strategy</a>.
 */
@Slf4j
@Component
@Qualifier("HitActionHandler")
public class HitActionHandlerStrategy implements ActionHandlerStrategy {
  private final CalculatorService calculatorService;
  private final OutcomeEvaluatorService evaluatorService;

  @Autowired
  public HitActionHandlerStrategy(CalculatorService calculatorService, OutcomeEvaluatorService evaluatorService) {
    this.calculatorService = calculatorService;
    this.evaluatorService = evaluatorService;
  }

  @Override
  public BlackJackGame handleAction(BlackJackGame blackJackGame) {
    log.info("Game-{} - performing hit action", blackJackGame.getId());
    dealCardToPlayer(blackJackGame);
    updateGameStatus(blackJackGame);
    return blackJackGame;
  }

  private void dealCardToPlayer(BlackJackGame blackJackGame) {
    final Deck deck = blackJackGame.getDeck();
    final Hand playerHand = blackJackGame.getPlayerHand();
    final Card card = deck.draw();
    log.debug("Game-{} - player draws {}", blackJackGame.getId(), card);
    playerHand.addCard(card);
    playerHand.setTotal(calculatorService.playerHandTotal(playerHand.getCards()));
  }

  private void updateGameStatus(BlackJackGame blackJackGame) {
    final GameStatus playerHitOutcome = evaluatorService.determinePlayerHitOutcome(blackJackGame);
    blackJackGame.setStatus(playerHitOutcome);
    log.info("Game-{} - new status after hit action: {}", blackJackGame.getId(), blackJackGame.getStatus());

  }
}
