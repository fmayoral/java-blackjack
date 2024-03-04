package uk.fmayoral.blackjack.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.fmayoral.blackjack.core.service.GameService;
import uk.fmayoral.blackjack.web.api.v1.models.Game;
import uk.fmayoral.blackjack.web.api.v1.models.PlayerAction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BlackjackResourceTest {

  @InjectMocks
  private BlackjackResource blackjackResource;

  @Mock
  private GameService gameService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Test starting a new game")
  void testStartNewGame() {
    // Setup
    Game game = Game.builder().build();
    when(gameService.startNewGame()).thenReturn(game);

    // Execution
    Game result = blackjackResource.startNewGame();

    // Verification
    assertEquals(game, result);
    verify(gameService, times(1)).startNewGame();
  }

  @Test
  @DisplayName("Test player action")
  void testPlayerAction() {
    // Setup
    String gameId = "1";
    PlayerAction action = new PlayerAction();
    Game game = Game.builder().build();
    when(gameService.performAction(gameId, action)).thenReturn(game);

    // Execution
    Game result = blackjackResource.playerAction(gameId, action);

    // Verification
    assertEquals(game, result);
    verify(gameService, times(1)).performAction(gameId, action);
  }

  @Test
  @DisplayName("Test getting a game")
  void testGetGame() {
    // Setup
    String gameId = "1";
    Game game = Game.builder().build();
    when(gameService.getGameById(gameId)).thenReturn(game);

    // Execution
    Game result = blackjackResource.getGame(gameId);

    // Verification
    assertEquals(game, result);
    verify(gameService, times(1)).getGameById(gameId);
  }
}
