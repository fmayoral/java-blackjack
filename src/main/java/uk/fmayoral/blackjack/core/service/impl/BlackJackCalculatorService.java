package uk.fmayoral.blackjack.core.service.impl;

import io.opentelemetry.instrumentation.annotations.WithSpan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uk.fmayoral.blackjack.core.models.Card;
import uk.fmayoral.blackjack.core.service.CalculatorService;

import java.util.List;

import static uk.fmayoral.blackjack.core.service.Constants.ACE_ADJUSTMENT;
import static uk.fmayoral.blackjack.core.service.Constants.BUST_THRESHOLD;
import static uk.fmayoral.blackjack.core.service.Constants.SOFT_17_THRESHOLD;


/**
 * Calculates the total value of a hand in a game of Blackjack
 *
 * @see <a href="{@docRoot}/docs/principles/DRY-SRP.md">DRY and SRP</a>.
 */
@Slf4j
@Service
public class BlackJackCalculatorService implements CalculatorService {

  @WithSpan
  @Override
  public int playerHandTotal(List<Card> hand) {
    log.trace("playerHandTotal invoked");
    int total = 0;
    int aces = 0;
    for (Card card : hand) {
      total += card.getValue();
      if ("A".equals(card.getRank())) {
        aces++;
      }
      log.trace("Calculated total before adjustment: {} - aces: {} for hand {}", total, aces, hand);
    }
    while (total > BUST_THRESHOLD && aces > 0) {
      total -= ACE_ADJUSTMENT;
      aces--;
    }
    log.trace("Calculated total after adjustment: {} for hand {}", total, hand);
    return total;
  }

  @WithSpan
  @Override
  public int dealerHandTotal(List<Card> hand) {
    log.trace("dealerHandTotal invoked");

    int total = hand.stream().mapToInt(Card::getValue).sum();
    long aceCount = hand.stream().filter(card -> "A".equals(card.getRank())).count();

    log.trace("Calculated total before adjustment: {} for hand {}", total, hand);

    while (total > BUST_THRESHOLD || (total == SOFT_17_THRESHOLD && aceCount > 0)) {
      if (aceCount > 0) {
        total -= ACE_ADJUSTMENT;
        aceCount--;
      } else {
        break;
      }
    }

    log.trace("Calculated total after adjustment: {} for hand {}", total, hand);
    return total;
  }
}
