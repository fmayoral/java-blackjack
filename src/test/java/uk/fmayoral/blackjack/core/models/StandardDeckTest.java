package uk.fmayoral.blackjack.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StandardDeckTest {

  private StandardDeck standardDeck;

  @BeforeEach
  void setUp() {
    standardDeck = new StandardDeck();
  }

  @Test
  @DisplayName("Test StandardDeck initialization")
  void testInitialize() {
    // Verification: Check the total number of cards in a deck (4 suits * (9 number cards + 4 face cards) = 52)
    assertEquals(52, standardDeck.getCards().size());
  }

  @Test
  @DisplayName("Test drawing a card from StandardDeck")
  void testDraw() {
    // Setup: Store the initial size of the deck
    int initialDeckSize = standardDeck.getCards().size();

    // Execution: Draw a card from the deck
    Card drawnCard = standardDeck.draw();

    // Verification: Check the deck size has been reduced by 1 and the drawn card is not null
    assertEquals(initialDeckSize - 1, standardDeck.getCards().size());
    assertNotNull(drawnCard);
  }

  @Test
  @DisplayName("Test drawing all cards from StandardDeck")
  void testDrawAllCards() {
    // Execution: Draw all cards from the deck
    Stack<Card> drawnCards = standardDeck.getCards();
    while (!drawnCards.isEmpty()) {
      standardDeck.draw();
    }

    // Verification: Check the deck is empty
    assertTrue(standardDeck.getCards().isEmpty());
  }
}
