package uk.fmayoral.blackjack.core.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.fmayoral.blackjack.core.models.BlackJackGame;
import uk.fmayoral.blackjack.core.models.Card;
import uk.fmayoral.blackjack.core.models.Deck;
import uk.fmayoral.blackjack.core.models.GameStatus;
import uk.fmayoral.blackjack.core.models.Hand;
import uk.fmayoral.blackjack.core.service.CalculatorService;
import uk.fmayoral.blackjack.core.service.OutcomeEvaluatorService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.fmayoral.blackjack.core.models.Card.Suit.CLUBS;
import static uk.fmayoral.blackjack.core.models.GameStatus.IN_PROGRESS;

class StandActionHandlerStrategyTest {


  private CalculatorService calculatorService;
  private OutcomeEvaluatorService evaluatorService;
  private StandActionHandlerStrategy standActionHandlerStrategy;
  private BlackJackGame game;
  private Deck deck;
  private Hand dealerHand;

  @BeforeEach
  public void setUp() {
    calculatorService = mock(CalculatorService.class);
    evaluatorService = mock(OutcomeEvaluatorService.class);
    standActionHandlerStrategy = new StandActionHandlerStrategy(calculatorService, evaluatorService);

    game = mock(BlackJackGame.class);
    deck = mock(Deck.class);
    dealerHand = mock(Hand.class);

    when(game.getDeck()).thenReturn(deck);
    when(game.getDealerHand()).thenReturn(dealerHand);
  }

  @Test
  @DisplayName("Perform Stand Action - Dealer draws")
  void testPerformActionDealerDraws() {
    // Given
    when(dealerHand.getTotal()).thenReturn(16, 17); // Making dealer draw one card.
    when(deck.draw()).thenReturn(new Card(7, "7", CLUBS));
    when(calculatorService.dealerHandTotal(anyList())).thenReturn(17);
    when(evaluatorService.determineFinalOutcome(game)).thenReturn(IN_PROGRESS);

    // When
    standActionHandlerStrategy.handleAction(game);

    // Then
    verify(dealerHand, times(1)).addCard(any(Card.class));
    verify(dealerHand).setTotal(17);
    verify(game).setStatus(IN_PROGRESS);
  }

  @Test
  @DisplayName("Perform Stand Action - Dealer does not draw")
  void testPerformActionDealerDoesNotDraw() {
    // Given
    when(dealerHand.getTotal()).thenReturn(17); // Dealer won't draw any cards.
    when(evaluatorService.determineFinalOutcome(game)).thenReturn(GameStatus.PLAYER_WINS);

    // When
    standActionHandlerStrategy.handleAction(game);

    // Then
    verify(dealerHand, never()).addCard(any(Card.class));
    verify(game).setStatus(GameStatus.PLAYER_WINS);
  }

}