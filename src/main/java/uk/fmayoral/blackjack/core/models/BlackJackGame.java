package uk.fmayoral.blackjack.core.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Full representation of the blackjack game, containing both full hands, game status and the complete deck.
 */
@Builder
@Data
@Document(collection = "blackjack")
public class BlackJackGame {
  @Id
  private String id;
  private Hand dealerHand;
  private Hand playerHand;
  private GameStatus status;
  private Deck deck;

  public boolean isInProgress() {
    return this.status == GameStatus.IN_PROGRESS;
  }

}
