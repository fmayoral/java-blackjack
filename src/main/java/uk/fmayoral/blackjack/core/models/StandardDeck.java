package uk.fmayoral.blackjack.core.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.Stack;


@Setter
@Getter
public class StandardDeck implements Deck {
  private static final int FIRST_CARD_VALUE = 2;
  private static final int LAST_CARD_VALUE = 10;
  private static final int ACE_DEFAULT_CARD_VALUE = 11;

  private Stack<Card> cards;

  public StandardDeck() {
    this.cards = this.initialize();
  }

  private Stack<Card> initialize() {
    final Stack<Card> cardStack = new Stack<>();

    for (Card.Suit suit : Card.Suit.values()) {
      for (int i = FIRST_CARD_VALUE; i <= LAST_CARD_VALUE; i++) {
        cardStack.add(new Card(i, String.valueOf(i), suit));
      }
      cardStack.add(new Card(ACE_DEFAULT_CARD_VALUE, "Ace", suit));
      cardStack.add(new Card(LAST_CARD_VALUE, "King", suit));
      cardStack.add(new Card(LAST_CARD_VALUE, "Queen", suit));
      cardStack.add(new Card(LAST_CARD_VALUE, "Jack", suit));
    }
    Collections.shuffle(cardStack);

    return cardStack;
  }

  @Override
  public Card draw() {
    return cards.pop();
  }

}