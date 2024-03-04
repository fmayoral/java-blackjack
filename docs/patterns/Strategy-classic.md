# Implementing the Strategy Pattern in a Blackjack Game

## Overview

The Strategy Pattern is a design pattern that enables an algorithm's behavior to be selected at runtime. Within the context of a Blackjack game, this pattern is particularly beneficial for encapsulating the logic of various in-game actions (e.g., "hit" and "stand") that can be performed by a player. This document explores the implementation of the Strategy Pattern through the `ActionHandlerStrategy` interface and its concrete implementations, `HitActionHandlerStrategy` and `StandActionHandlerStrategy`, within the `uk.fmayoral.blackjack.core.service.impl` package.

## Strategy Pattern Components

### Strategy Interface

- **`ActionHandlerStrategy`**: This interface defines a common protocol for all concrete strategies. Each strategy implements the `handleAction(BlackJackGame blackJackGame)` method, which encapsulates the unique algorithm for handling a specific player action within the game.

### Concrete Strategies

- **`HitActionHandlerStrategy`**: Implements the `ActionHandlerStrategy` for the "hit" action. It encapsulates the logic required when a player decides to take another card from the deck. This includes dealing a card to the player and updating the game's status based on the new hand.

- **`StandActionHandlerStrategy`**: Implements the `ActionHandlerStrategy` for the "stand" action. This strategy defines the behavior when a player decides to end their turn without taking additional cards. It involves playing out the dealer's turn according to the game's rules and then determining the final outcome of the game.

### Context

- **`BlackjackActionHandlingService`**: Acts as the context in the Strategy Pattern. It maintains a mapping of `ActionType` enums to `ActionHandlerStrategy` instances. When an action is performed (`performAction` method), the service uses this map to find and delegate the action handling to the appropriate strategy based on the player's action type. This design allows for the dynamic selection of algorithms at runtime.

## Benefits of the Strategy Pattern in this Context

1. **Flexibility and Extensibility**: New actions can be easily added to the game by implementing the `ActionHandlerStrategy` interface and updating the `BlackjackActionHandlingService` to include the new strategy. This makes the system highly extensible with minimal changes to existing code.

2. **Decoupling of Algorithms from Their Context**: By encapsulating the action handling logic within separate strategy classes, the `BlackjackActionHandlingService` is decoupled from the specific algorithms for each action. This separation of concerns simplifies maintenance and evolution of the game logic.

3. **Dynamic Behavior**: The pattern allows the game to dynamically select the appropriate action handling algorithm at runtime based on the player's choice. This dynamic behavior is essential for a game like Blackjack, where the flow of the game is determined by the players' actions.

## Conclusion

The use of the Strategy Pattern, in conjunction with Dependency Injection provided by the Spring Framework, offers a robust solution for managing the complexities of player actions in a Blackjack game. This design enhances the system's flexibility, maintainability, and scalability, making it easier to extend and modify the game's behavior as needed.

## Additional resources
* [Strategy](https://refactoring.guru/design-patterns/strategy)