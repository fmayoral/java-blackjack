package uk.fmayoral.blackjack.core.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class Hand {
  @NonNull
  private List<Card> cards;
  private int total;

  public Hand addCard(Card card) {
    this.cards.add(card);
    return this;
  }

  @Override
  public String toString() {
    return "{" +
        "cards=" + cards +
        ", total=" + total +
        '}';
  }
}
