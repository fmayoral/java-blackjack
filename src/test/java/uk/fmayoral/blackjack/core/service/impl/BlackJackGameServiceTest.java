package uk.fmayoral.blackjack.core.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import uk.fmayoral.blackjack.core.factory.GameFactory;
import uk.fmayoral.blackjack.core.mapper.GameMapper;
import uk.fmayoral.blackjack.core.models.BlackJackGame;
import uk.fmayoral.blackjack.core.models.GameStatus;
import uk.fmayoral.blackjack.core.models.Hand;
import uk.fmayoral.blackjack.core.repository.GameRepository;
import uk.fmayoral.blackjack.core.service.ActionHandlingService;
import uk.fmayoral.blackjack.web.api.v1.models.Game;
import uk.fmayoral.blackjack.web.api.v1.models.PlayerAction;
import uk.fmayoral.blackjack.web.api.v1.models.PlayerAction.ActionType;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.fmayoral.blackjack.web.api.v1.models.PlayerAction.ActionType.HIT;

class BlackJackGameServiceTest {

  private GameRepository gameRepository;
  private GameMapper gameMapper;
  private GameFactory gameFactory;
  private ActionHandlingService actionHandlingService;
  private BlackJackGameService gameService;

  @BeforeEach
  void setUp() {
    // Mock the dependencies
    gameRepository = mock(GameRepository.class);
    gameMapper = mock(GameMapper.class);
    gameFactory = mock(GameFactory.class);
    actionHandlingService = mock(ActionHandlingService.class);

    // Initialize the service with mocked dependencies
    gameService = new BlackJackGameService(gameRepository, gameMapper, gameFactory, actionHandlingService);
  }

  private void setupCommonMockBehaviour(String gameId) {
    when(gameRepository.findById(any(String.class))).thenReturn(Optional.of(BlackJackGame.builder()
        .status(GameStatus.IN_PROGRESS)
        .build()));
    when(gameRepository.save(any(BlackJackGame.class))).thenReturn(BlackJackGame.builder().build());
    when(gameMapper.map(any(BlackJackGame.class))).thenReturn(Game.builder().build());
    when(gameMapper.mapInitialGame(any(BlackJackGame.class))).thenReturn(Game.builder().build());
    when(actionHandlingService.performAction(any(String.class), any(PlayerAction.class))).thenReturn(Game.builder().build());
  }

  @ParameterizedTest
  @EnumSource(value = ActionType.class)
  @DisplayName("Perform Action invokes the action handling service")
  void performActionCallsActionHandling(ActionType actionType) {
    // Given
    String gameId = "sampleGameId";
    PlayerAction action = new PlayerAction(actionType);
    setupCommonMockBehaviour(gameId);

    // When
    Game resultGame = gameService.performAction(gameId, action);

    // Then
    assertNotNull(resultGame);
    verify(actionHandlingService).performAction(gameId, action);
  }

  private void verifyCommonInteractions(String gameId) {
    verify(gameRepository).findById(gameId);
  }

  @Test
  @DisplayName("Retrieving the game results in a repository read")
  void getGameById() {
    when(gameRepository.findById(any(String.class))).thenReturn(Optional.of(BlackJackGame.builder().build()));  // Save returns the saved game
    when(gameMapper.map(any(BlackJackGame.class))).thenReturn(Game.builder().build());  // Map to an empty game for simplicity

    // Given
    String gameId = "sampleGameId";

    // When
    Game resultGame = gameService.getGameById(gameId);

    // Then
    assertNotNull(resultGame);
    verify(gameRepository).findById(gameId);
  }

  @Test
  @DisplayName("Creating a new game results in a repository write and an initial game mapping")
  void startNewGameTest() {
    // Given: Expected behaviors
    when(gameRepository.save(any(BlackJackGame.class))).thenAnswer(inv -> inv.getArgument(0));  // Save returns the saved game
    when(gameMapper.mapInitialGame(any(BlackJackGame.class))).thenReturn(Game.builder().build());  // Map to an empty game for simplicity
    Hand hand = Hand.builder()
        .cards(new ArrayList<>())
        .total(15).build();
    BlackJackGame blackJackGame = BlackJackGame.builder()
        .playerHand(hand)
        .dealerHand(hand)
        .build();
    when(gameFactory.createNewGame()).thenReturn(blackJackGame);

    // When: Calling the method to be tested
    Game result = gameService.startNewGame();

    // Then: Verifying the interactions and result
    // Ensure a new game is saved
    verify(gameRepository, times(1)).save(any(BlackJackGame.class));

    // Ensure a saved game is mapped
    verify(gameMapper, times(1)).mapInitialGame(any(BlackJackGame.class));

    // Check return value is not null (additional specific checks depend on mapping logic)
    assertNotNull(result);
  }
}
