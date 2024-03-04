package uk.fmayoral.blackjack.core.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.fmayoral.blackjack.core.models.BlackJackGame;
import uk.fmayoral.blackjack.core.models.Card;
import uk.fmayoral.blackjack.core.models.Deck;
import uk.fmayoral.blackjack.core.models.Hand;
import uk.fmayoral.blackjack.core.service.CalculatorService;
import uk.fmayoral.blackjack.core.service.OutcomeEvaluatorService;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.fmayoral.blackjack.core.models.Card.Suit.CLUBS;
import static uk.fmayoral.blackjack.core.models.GameStatus.IN_PROGRESS;

@ExtendWith(MockitoExtension.class)
class HitActionHandlerStrategyTest {
  private CalculatorService calculatorService;
  private OutcomeEvaluatorService evaluatorService;
  private BlackJackGame game;
  private Deck deck;
  private Hand playerHand;

  private HitActionHandlerStrategy hitActionHandlerStrategy;


  @BeforeEach
  void setUp() {
    calculatorService = mock(CalculatorService.class);
    evaluatorService = mock(OutcomeEvaluatorService.class);
    game = mock(BlackJackGame.class);
    deck = mock(Deck.class);
    playerHand = mock(Hand.class);

    when(game.getDeck()).thenReturn(deck);
    when(game.getPlayerHand()).thenReturn(playerHand);
    when(deck.draw()).thenReturn(new Card(11, "A", CLUBS));

    hitActionHandlerStrategy = new HitActionHandlerStrategy(calculatorService, evaluatorService);
  }

  @Test
  @DisplayName("Perform Hit Action")
  void testPerformAction() {
    // given
    when(calculatorService.playerHandTotal(anyList())).thenReturn(11);
    when(evaluatorService.determinePlayerHitOutcome(game)).thenReturn(IN_PROGRESS);

    // when
    hitActionHandlerStrategy.handleAction(game);

    // then
    verify(playerHand).addCard(any(Card.class));
    verify(playerHand).setTotal(11);
    verify(game).setStatus(IN_PROGRESS);
  }
}
