package uk.fmayoral.blackjack.core.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.fmayoral.blackjack.core.models.BlackJackGame;
import uk.fmayoral.blackjack.core.models.GameStatus;
import uk.fmayoral.blackjack.core.models.Hand;
import uk.fmayoral.blackjack.core.models.StandardDeck;
import uk.fmayoral.blackjack.web.api.v1.models.Card;
import uk.fmayoral.blackjack.web.api.v1.models.Game;
import uk.fmayoral.blackjack.web.api.v1.models.Player;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.fmayoral.blackjack.core.models.Card.Suit.CLUBS;
import static uk.fmayoral.blackjack.core.models.Card.Suit.DIAMONDS;
import static uk.fmayoral.blackjack.core.models.Card.Suit.HEARTS;
import static uk.fmayoral.blackjack.core.models.Card.Suit.SPADES;
import static uk.fmayoral.blackjack.core.models.GameStatus.IN_PROGRESS;

class BlackJackGameMapperTest {

  private BlackJackGameMapper mapper;

  @BeforeEach
  void setUp() {
    mapper = new BlackJackGameMapper();
  }

  private Hand createHand() {
    return new Hand((Arrays.asList(
        new uk.fmayoral.blackjack.core.models.Card(2, "2", HEARTS),
        new uk.fmayoral.blackjack.core.models.Card(3, "3", DIAMONDS))), 5);
  }

  @Test
  @DisplayName("Map initial game reveals the correct cards")
  void testMapInitialGame() {
    BlackJackGame blackJackGame = BlackJackGame.builder()
        .deck(new StandardDeck())
        .playerHand(createHand())
        .dealerHand(createHand())
        .status(IN_PROGRESS)
        .build();

    Game game = mapper.mapInitialGame(blackJackGame);

    assertEquals(2, game.getPlayer().getHand().size());
    assertEquals(5, game.getPlayer().getTotal());
    assertEquals(1, game.getDealer().getHand().size());
    assertEquals(2, game.getDealer().getTotal());
  }

  @Test
  @DisplayName("Map full game reveals the correct cards")
  void testMap() {
    BlackJackGame blackJackGame = BlackJackGame.builder()
        .id("test")
        .deck(new StandardDeck())
        .playerHand(createHand())
        .dealerHand(createHand())
        .status(IN_PROGRESS)
        .build();

    Game game = mapper.map(blackJackGame);

    assertEquals("test", game.getGameId());
    assertEquals(2, game.getPlayer().getHand().size());
    assertEquals(5, game.getPlayer().getTotal());
    assertEquals(2, game.getDealer().getHand().size());
    assertEquals(5, game.getDealer().getTotal());
    assertEquals(Game.Status.IN_PROGRESS, game.getStatus());
  }

  @Test
  @DisplayName("Map hand results in the correct hand")
  void testMapHand() {
    Hand hand = createHand();
    Player player = mapper.mapHand(hand);

    assertEquals(5, player.getTotal());
    assertEquals(2, player.getHand().size());
  }

  @Test
  @DisplayName("Map game status results in the correct status")
  void testMapGameStatus() {
    for (GameStatus status : GameStatus.values()) {
      Game.Status mappedStatus = mapper.map(status);
      assertEquals(Game.Status.valueOf(status.name()), mappedStatus);
    }
  }

  @Test
  @DisplayName("Map cards results in the correct cards")
  void testMapCards() {
    List<uk.fmayoral.blackjack.core.models.Card> coreCards = Arrays.asList(
        new uk.fmayoral.blackjack.core.models.Card(2, "2", SPADES),
        new uk.fmayoral.blackjack.core.models.Card(3, "3", CLUBS)
    );

    List<Card> apiCards = mapper.map(coreCards);

    assertEquals(2, apiCards.size());
    assertEquals("SPADES", apiCards.get(0).getSuit());
    assertEquals("2", apiCards.get(0).getRank());
    assertEquals(2, apiCards.get(0).getValue());
    assertEquals("CLUBS", apiCards.get(1).getSuit());
    assertEquals("3", apiCards.get(1).getRank());
    assertEquals(3, apiCards.get(1).getValue());
  }
}