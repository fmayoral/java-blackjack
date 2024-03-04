package uk.fmayoral.blackjack.web.api.v1.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Game {
  private String gameId;
  private Player dealer;
  private Player player;
  private Status status;

  public enum Status {
    IN_PROGRESS, DEALER_WINS, PLAYER_WINS, TIE
  }
}
