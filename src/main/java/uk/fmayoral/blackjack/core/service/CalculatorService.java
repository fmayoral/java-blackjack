package uk.fmayoral.blackjack.core.service;

import uk.fmayoral.blackjack.core.models.Card;

import java.util.List;

public interface CalculatorService {
  int playerHandTotal(List<Card> hand);

  int dealerHandTotal(List<Card> hand);
}
