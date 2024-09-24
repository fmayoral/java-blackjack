package uk.fmayoral.blackjack.core.mapper;

import io.opentelemetry.instrumentation.annotations.WithSpan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import uk.fmayoral.blackjack.core.models.BlackJackGame;
import uk.fmayoral.blackjack.core.models.GameStatus;
import uk.fmayoral.blackjack.core.models.Hand;
import uk.fmayoral.blackjack.web.api.v1.models.Card;
import uk.fmayoral.blackjack.web.api.v1.models.Game;
import uk.fmayoral.blackjack.web.api.v1.models.Player;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Blackjack mapper adapts the internal blackjack game to the API representation of it.
 *
 * @see <a href="{@docRoot}/docs/patterns/Adapter.md">Adapter</a>.
 */
@Slf4j
@Component
public class BlackJackGameMapper implements GameMapper {

  @WithSpan
  @Override
  public Game mapInitialGame(BlackJackGame blackJackGame) {
    log.trace("mapInitialGame invoked");
    Game game = this.map(blackJackGame);
    // remove second card from dealer's hand, so it is not revealed to the player
    List<Card> dealerHand = game.getDealer().getHand();
    log.trace("removing Dealer's second card");
    dealerHand.remove(1);
    log.trace("adjusting Dealer's initial hand");
    game.getDealer().setTotal(dealerHand.get(0).getValue());
    return game;
  }

  @WithSpan
  @Override
  public Game map(BlackJackGame blackJackGame) {
    log.trace("map invoked");
    Player player = mapHand(blackJackGame.getPlayerHand());
    Player dealer = mapHand(blackJackGame.getDealerHand());

    log.trace("Building mapped Game");
    return Game.builder()
        .gameId(blackJackGame.getId())
        .player(player)
        .dealer(dealer)
        .status(map(blackJackGame.getStatus()))
        .build();
  }

  Player mapHand(Hand hand) {
    log.trace("mapHand invoked");
    final Player player = new Player();
    player.setHand(map(hand.getCards()));
    player.setTotal(hand.getTotal());
    return player;
  }

  Game.Status map(GameStatus status) {
    log.trace("map GameStatus invoked");
    switch (status) {
      case IN_PROGRESS:
        return Game.Status.IN_PROGRESS;
      case DEALER_WINS:
        return Game.Status.DEALER_WINS;
      case PLAYER_WINS:
        return Game.Status.PLAYER_WINS;
      case TIE:
        return Game.Status.TIE;
      default:
        throw new IllegalStateException("Unknown mapping for game status: " + status);
    }
  }

  List<Card> map(List<uk.fmayoral.blackjack.core.models.Card> cards) {
    log.trace("map Cards invoked");
    return cards.stream()
        .map(card -> new Card(card.getValue(), card.getRank(), card.getSuit().toString()))
        .collect(Collectors.toList());
  }
}
