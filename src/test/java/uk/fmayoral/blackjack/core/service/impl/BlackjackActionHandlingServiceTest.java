package uk.fmayoral.blackjack.core.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import uk.fmayoral.blackjack.core.exception.GameFinishedException;
import uk.fmayoral.blackjack.core.exception.GameNotFoundException;
import uk.fmayoral.blackjack.core.mapper.GameMapper;
import uk.fmayoral.blackjack.core.models.BlackJackGame;
import uk.fmayoral.blackjack.core.models.GameStatus;
import uk.fmayoral.blackjack.core.repository.GameRepository;
import uk.fmayoral.blackjack.web.api.v1.models.Game;
import uk.fmayoral.blackjack.web.api.v1.models.PlayerAction;
import uk.fmayoral.blackjack.web.api.v1.models.PlayerAction.ActionType;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class BlackjackActionHandlingServiceTest {

  private static final String GAME_ID = "gameId";
  @Mock
  private GameRepository gameRepository;

  @Mock
  private GameMapper gameMapper;

  @Mock
  private HitActionHandlerStrategy hitHandler;

  @Mock
  private StandActionHandlerStrategy standHandler;

  @InjectMocks
  private BlackjackActionHandlingService service;


  @BeforeEach
  void setUp() {
    // Mock the dependencies
    gameRepository = mock(GameRepository.class);
    gameMapper = mock(GameMapper.class);
    hitHandler = mock(HitActionHandlerStrategy.class);
    standHandler = mock(StandActionHandlerStrategy.class);

    // Initialize the service with mocked dependencies
    service = new BlackjackActionHandlingService(gameRepository, gameMapper, hitHandler, standHandler);
  }


  @Test
  @DisplayName("Perform action for non-existing game")
  void performAction_GameNotFound() {
    PlayerAction playerAction = new PlayerAction(ActionType.HIT);

    when(gameRepository.findById(GAME_ID)).thenReturn(Optional.empty());

    assertThrows(GameNotFoundException.class, () -> service.performAction(GAME_ID, playerAction));
  }

  @ParameterizedTest
  @DisplayName("Perform action for finished game")
  @EnumSource(value = GameStatus.class, mode = EnumSource.Mode.EXCLUDE, names = {"IN_PROGRESS"})
  void performAction_GameFinished(GameStatus status) {
    BlackJackGame blackJackGame = BlackJackGame.builder()
        .id(GAME_ID)
        .status(status)
        .build();

    PlayerAction playerAction = new PlayerAction(ActionType.HIT);

    when(gameRepository.findById(GAME_ID)).thenReturn(Optional.of(blackJackGame));

    assertThrows(GameFinishedException.class, () -> service.performAction(GAME_ID, playerAction));
  }

  @Test
  @DisplayName("Perform unknown action")
  void performAction_UnknownAction() {
    BlackJackGame blackJackGame = BlackJackGame.builder()
        .id(GAME_ID)
        .status(GameStatus.IN_PROGRESS)
        .build();

    PlayerAction playerAction = new PlayerAction(null);

    when(gameRepository.findById(GAME_ID)).thenReturn(Optional.of(blackJackGame));

    assertThrows(IllegalStateException.class, () -> service.performAction(GAME_ID, playerAction));
  }

  @Test
  @DisplayName("Perform hit action results in correct handler used")
  void performAction_SuccessHit() {
    BlackJackGame blackJackGame = BlackJackGame.builder()
        .id(GAME_ID)
        .status(GameStatus.IN_PROGRESS)
        .build();
    when(gameRepository.findById(GAME_ID)).thenReturn(Optional.of(blackJackGame));
    when(gameRepository.save(any(BlackJackGame.class))).thenReturn(blackJackGame);
    when(hitHandler.handleAction(blackJackGame)).thenReturn(blackJackGame);
    when(gameMapper.mapInitialGame(blackJackGame)).thenReturn(Game.builder().build());

    Game result = service.performAction(GAME_ID, new PlayerAction(ActionType.HIT));
    assertNotNull(result);
    verify(hitHandler, times(1)).handleAction(any());
    verify(standHandler, never()).handleAction(any());
    verify(gameMapper).mapInitialGame(blackJackGame);
    verify(gameRepository).save(any(BlackJackGame.class));
  }

  @ParameterizedTest
  @DisplayName("Perform stand action results in correct handler used")
  @EnumSource(value = GameStatus.class, mode = EnumSource.Mode.EXCLUDE, names = {"IN_PROGRESS"})
  void performAction_SuccessStand(GameStatus status) {
    BlackJackGame blackJackGame = BlackJackGame.builder()
        .id(GAME_ID)
        .status(GameStatus.IN_PROGRESS)
        .build();

    BlackJackGame blackJackGameFinished = BlackJackGame.builder()
        .id(GAME_ID)
        .status(status)
        .build();

    when(gameRepository.findById(GAME_ID)).thenReturn(Optional.of(blackJackGame));
    when(gameRepository.save(any(BlackJackGame.class))).thenReturn(blackJackGameFinished);
    when(standHandler.handleAction(blackJackGame)).thenReturn(blackJackGameFinished);
    when(gameMapper.map(blackJackGameFinished)).thenReturn(Game.builder().build());

    Game result = service.performAction(GAME_ID, new PlayerAction(ActionType.STAND));

    assertNotNull(result);
    verify(standHandler, times(1)).handleAction(any());
    verify(hitHandler, never()).handleAction(any());
    verify(gameMapper, never()).mapInitialGame(blackJackGameFinished);
    verify(gameMapper).map(blackJackGameFinished);
    verify(gameRepository).save(any(BlackJackGame.class));
  }
}
