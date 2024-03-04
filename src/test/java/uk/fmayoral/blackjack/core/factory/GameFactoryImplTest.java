package uk.fmayoral.blackjack.core.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.fmayoral.blackjack.core.models.BlackJackGame;
import uk.fmayoral.blackjack.core.models.GameStatus;
import uk.fmayoral.blackjack.core.service.CalculatorService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GameFactoryImplTest {

  @InjectMocks
  private GameFactoryImpl blackjackGameFactory;

  @Mock
  private CalculatorService calculatorService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Creating a new game results in a correct new game")
  void createNewGameTest() {
    // Arrange
    when(calculatorService.playerHandTotal(anyList())).thenReturn(10);
    when(calculatorService.dealerHandTotal(anyList())).thenReturn(15);

    // Act
    BlackJackGame newGame = blackjackGameFactory.createNewGame();

    // Assert
    assertNotNull(newGame, "New game should not be null");
    assertNotNull(newGame.getId(), "Game id should not be null");
    assertEquals(GameStatus.IN_PROGRESS, newGame.getStatus(), "Game status should be IN_PROGRESS");
    assertNotNull(newGame.getPlayerHand(), "Player hand should not be null");
    assertEquals(2, newGame.getPlayerHand().getCards().size(), "Player hand should have 2 cards");
    assertNotNull(newGame.getDealerHand(), "Dealer hand should not be null");
    assertEquals(2, newGame.getDealerHand().getCards().size(), "Dealer hand should have 2 cards");
    assertEquals(10, newGame.getPlayerHand().getTotal(), "Player hand total should be calculated");
    assertEquals(15, newGame.getDealerHand().getTotal(), "Dealer hand total should be calculated");
    verify(calculatorService, times(1)).playerHandTotal(anyList());
    verify(calculatorService, times(1)).dealerHandTotal(anyList());
  }
}