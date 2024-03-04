package uk.fmayoral.blackjack.web.api.v1;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import uk.fmayoral.blackjack.web.api.v1.models.Game;
import uk.fmayoral.blackjack.web.api.v1.models.PlayerAction;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/blackjack")
public interface BlackjackAPIv1 {
  @PostMapping("/games")
  @ResponseStatus(HttpStatus.CREATED)
  Game startNewGame();

  @PostMapping("/games/{gameId}")
  @ResponseStatus(HttpStatus.CREATED)
  Game playerAction(@PathVariable String gameId, @RequestBody PlayerAction action);

  @GetMapping("/games/{gameId}")
  @ResponseStatus(HttpStatus.OK)
  Game getGame(@PathVariable String gameId);
}
