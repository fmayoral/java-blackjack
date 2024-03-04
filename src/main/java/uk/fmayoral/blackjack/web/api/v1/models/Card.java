package uk.fmayoral.blackjack.web.api.v1.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Card {
  private Integer value;
  private String rank;
  private String suit;
}
