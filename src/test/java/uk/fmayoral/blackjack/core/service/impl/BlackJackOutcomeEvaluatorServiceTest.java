package uk.fmayoral.blackjack.core.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import uk.fmayoral.blackjack.core.models.BlackJackGame;
import uk.fmayoral.blackjack.core.models.GameStatus;
import uk.fmayoral.blackjack.core.models.Hand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class BlackJackOutcomeEvaluatorServiceTest {

  private BlackJackOutcomeEvaluatorService outcomeEvaluatorService;
  private BlackJackGame game;
  private Hand playerHand;
  private Hand dealerHand;

  @BeforeEach
  void setUp() {
    outcomeEvaluatorService = new BlackJackOutcomeEvaluatorService();

    game = Mockito.mock(BlackJackGame.class);
    playerHand = Mockito.mock(Hand.class);
    dealerHand = Mockito.mock(Hand.class);

    when(game.getPlayerHand()).thenReturn(playerHand);
    when(game.getDealerHand()).thenReturn(dealerHand);
  }

  @DisplayName("Evaluate final outcomes")
  @ParameterizedTest(name = "Player hand: {0}, Dealer hand: {1}, Expected outcome: {2}")
  @CsvSource({
      "22, 20, DEALER_WINS",
      "20, 22, PLAYER_WINS",
      "20, 20, TIE",
      "20, 18, PLAYER_WINS",
      "18, 20, DEALER_WINS"
  })
  void evaluateFinalOutcome(int playerTotal, int dealerTotal, GameStatus expectedOutcome) {
    when(playerHand.getTotal()).thenReturn(playerTotal);
    when(dealerHand.getTotal()).thenReturn(dealerTotal);

    GameStatus outcome = outcomeEvaluatorService.determineFinalOutcome(game);

    assertEquals(expectedOutcome, outcome);
  }

  @DisplayName("Evaluate player hit outcomes")
  @ParameterizedTest(name = "Player hand: {0}, Expected outcome: {1}")
  @CsvSource({
      "22, DEALER_WINS",
      "20, IN_PROGRESS"
  })
  void determinePlayerHitOutcome(int playerTotal, GameStatus expectedOutcome) {
    when(playerHand.getTotal()).thenReturn(playerTotal);

    GameStatus outcome = outcomeEvaluatorService.determinePlayerHitOutcome(game);

    assertEquals(expectedOutcome, outcome);
  }
}
