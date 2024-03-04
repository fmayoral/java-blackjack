package uk.fmayoral.blackjack.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BlackJackGameTest {

  private BlackJackGame blackJackGame;

  @BeforeEach
  void setUp() {
    blackJackGame = BlackJackGame.builder()
        .id("2")
        .playerHand(new Hand(Collections.emptyList(), 0))
        .dealerHand(new Hand(Collections.emptyList(), 0))
        .status(GameStatus.IN_PROGRESS)
        .deck(new StandardDeck())
        .build();
  }

  @Test
  @DisplayName("Test checking game progress")
  void testIsInProgress() {
    // Execution & Verification
    assertTrue(blackJackGame.isInProgress());

    // Update status to DEALER_WINS
    blackJackGame.setStatus(GameStatus.DEALER_WINS);

    // Verification
    assertFalse(blackJackGame.isInProgress());
  }

  @Test
  @DisplayName("Test builder pattern")
  void testBuilderPattern() {
    // Execution
    BlackJackGame game = BlackJackGame.builder()
        .id("2")
        .playerHand(new Hand(Collections.emptyList(), 0))
        .dealerHand(new Hand(Collections.emptyList(), 0))
        .status(GameStatus.PLAYER_WINS)
        .deck(new StandardDeck())
        .build();

    // Verification
    assertEquals("2", game.getId());
    assertEquals(GameStatus.PLAYER_WINS, game.getStatus());
  }
}
