package uk.fmayoral.blackjack.core.models;

import lombok.Getter;

@Getter
public class Card {
  public enum Suit {HEARTS, DIAMONDS, CLUBS, SPADES}

  private final int value;
  private final String rank;
  private final Suit suit;

  public Card(int value, String rank, Suit suit) {
    this.value = value;
    this.rank = rank;
    this.suit = suit;
  }

  @Override
  public String toString() {
    return rank + " of " + suit + "(" + value + ")";
  }
}

