package uk.fmayoral.blackjack.core.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import uk.fmayoral.blackjack.core.models.Card;
import uk.fmayoral.blackjack.core.service.CalculatorService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.fmayoral.blackjack.core.models.Card.Suit.CLUBS;
import static uk.fmayoral.blackjack.core.models.Card.Suit.DIAMONDS;
import static uk.fmayoral.blackjack.core.models.Card.Suit.HEARTS;
import static uk.fmayoral.blackjack.core.models.Card.Suit.SPADES;

class BlackJackCalculatorServiceTest {

  CalculatorService calculatorService = new BlackJackCalculatorService();

  @DisplayName("Player hand total calculation")
  @ParameterizedTest(name = "When cards are {0}, total should be {1}")
  @MethodSource("providePlayerHands")
  void testPlayerHandTotal(List<Card> hand, int expectedTotal) {
    assertEquals(expectedTotal, calculatorService.playerHandTotal(hand));
  }

  static Stream<Arguments> providePlayerHands() {
    Card ace = new Card(11, "A", DIAMONDS);
    Card two = new Card(2, "2", CLUBS);
    Card three = new Card(3, "3", HEARTS);
    Card ten = new Card(10, "10", SPADES);

    return Stream.of(
        Arguments.of(Arrays.asList(ace, ten), 21),        // Ace should be 11 here, total: 21
        Arguments.of(Arrays.asList(ace, ten, ten), 21),   // Ace adjustment: Ace should be 1 here, total: 21
        Arguments.of(Arrays.asList(two, three), 5),       // Regular cards, total: 5
        Arguments.of(Arrays.asList(ace, ace, ten), 12)   // Ace adjustment: Two aces - One should be 1, the other 11, total: 12
    );
  }

  @DisplayName("Dealer hand total calculation")
  @ParameterizedTest(name = "When cards are {0}, total should be {1}")
  @MethodSource("provideDealerHands")
  void testDealerHandTotal(List<Card> hand, int expectedTotal) {
    assertEquals(expectedTotal, calculatorService.dealerHandTotal(hand));
  }

  static Stream<Arguments> provideDealerHands() {
    Card ace = new Card(11, "A", DIAMONDS);
    Card two = new Card(2, "2", CLUBS);
    Card six = new Card(6, "6", HEARTS);
    Card seven = new Card(7, "7", HEARTS);
    Card ten = new Card(10, "10", SPADES);

    return Stream.of(
        Arguments.of(Arrays.asList(ace, two, six), 19),    // Ace should be treated as 11, total: 9
        Arguments.of(Arrays.asList(ace, six), 7),        // Ace should be treated as 11, total: 17
        Arguments.of(Arrays.asList(ace, seven, six), 14), // Ace should be treated as 1, total: 14
        Arguments.of(Arrays.asList(ace, ten), 21),        // Ace should be 1 here, total: 11
        Arguments.of(Arrays.asList(ace, ace, ten), 12),  // Ace adjustment: Two aces - One should be 1, the other 11, total: 22
        Arguments.of(Arrays.asList(ten, six), 16)  ,       // No Aces, total: 16
        Arguments.of(Arrays.asList(ace, ten, ten, ten), 31) // Aces, total: 21
    );
  }
}
