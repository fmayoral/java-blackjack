package uk.fmayoral.blackjack.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HandTest {

  private Hand hand;

  @BeforeEach
  void setUp() {
    hand = new Hand(new ArrayList<>(), 0);
  }

  @Test
  @DisplayName("Test adding a card to Hand")
  void testAddCard() {
    // Create a new card
    Card card = new Card(2, "2", Card.Suit.HEARTS);

    // Execution
    Hand updatedHand = hand.addCard(card);

    // Verification
    assertEquals(1, updatedHand.getCards().size());
    assertTrue(updatedHand.getCards().contains(card));
  }

  @Test
  @DisplayName("Test Hand toString method")
  void testToString() {
    // Add cards to hand
    hand.addCard(new Card(2, "2", Card.Suit.HEARTS));
    hand.addCard(new Card(3, "3", Card.Suit.SPADES));
    hand.setTotal(5);

    // Execution
    String handString = hand.toString();

    // Verification
    assertEquals("{cards=[2 of HEARTS(2), 3 of SPADES(3)], total=5}", handString);
  }
}
