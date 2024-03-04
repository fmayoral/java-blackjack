package uk.fmayoral.blackjack.core.factory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.fmayoral.blackjack.core.models.BlackJackGame;
import uk.fmayoral.blackjack.core.models.Card;
import uk.fmayoral.blackjack.core.models.Deck;
import uk.fmayoral.blackjack.core.models.GameStatus;
import uk.fmayoral.blackjack.core.models.Hand;
import uk.fmayoral.blackjack.core.models.StandardDeck;
import uk.fmayoral.blackjack.core.service.CalculatorService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.ToIntFunction;

/**
 * Game factory creates blackjack games and results in a game fully initialised and ready to play.
 *
 * @see <a href="{@docRoot}/docs/patterns/Factory.md">Factory</a>.
 */
@Slf4j
@Service
public class GameFactoryImpl implements GameFactory {

  private final CalculatorService calculatorService;

  @Autowired
  public GameFactoryImpl(CalculatorService calculatorService) {
    this.calculatorService = calculatorService;
  }

  @Override
  public BlackJackGame createNewGame() {
    final String gameId = UUID.randomUUID().toString();
    final Deck deck = new StandardDeck();
    log.debug("Game-{} - Shuffled deck", gameId);

    final Hand playerHand = initialiseHand(deck, calculatorService::playerHandTotal);
    log.debug("Game-{} - Dealt player hand {}", gameId, playerHand);

    final Hand dealerHand = initialiseHand(deck, calculatorService::dealerHandTotal);
    log.debug("Game-{} - Dealt dealer hand {}", gameId, dealerHand);

    return BlackJackGame.builder()
        .id(gameId)
        .status(GameStatus.IN_PROGRESS)
        .deck(deck)
        .playerHand(playerHand)
        .dealerHand(dealerHand)
        .build();
  }

  private Hand initialiseHand(Deck deck, ToIntFunction<List<Card>> handCalculatorFunction) {
    final List<Card> cards = new ArrayList<>();
    cards.add(deck.draw());
    cards.add(deck.draw());
    return Hand.builder()
        .cards(cards)
        .total(handCalculatorFunction.applyAsInt(cards))
        .build();
  }

}
