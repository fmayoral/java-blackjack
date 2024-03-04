package uk.fmayoral.blackjack.web.api.v1.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerAction {
  private ActionType action;

  public enum ActionType {
    HIT("HIT"), STAND("STAND");

    private final String value;

    ActionType(String value) {
      this.value = value;
    }
  }
}
