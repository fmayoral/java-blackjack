# Strategy and Command Patterns

## Overview

The `BlackJackOutcomeEvaluatorService` within the `uk.fmayoral.blackjack.core.service.impl` package showcases a sophisticated implementation of the Strategy Pattern, utilizing Java enums in a manner that also reflects elements of the Command Pattern. This service is responsible for determining the outcome of a Blackjack game based on the totals of the player's and dealer's hands. The innovative use of enums to encapsulate different game outcome evaluation strategies offers a clear, maintainable, and flexible approach to handling game logic.

## Design Patterns Utilized

### Strategy Pattern through Enum

The Strategy Pattern is evident in how the `Outcome` enum encapsulates different strategies for evaluating game outcomes. Each enum constant represents a distinct outcome evaluation strategy, defined by a specific condition (a `BiPredicate<Integer, Integer>`).

- **Encapsulation of Strategies**: The `Outcome` enum defines multiple game outcome conditions and associates each with a corresponding `GameStatus`. This encapsulation allows for easy extension and modification of game logic.

- **Dynamic Strategy Selection**: The service dynamically selects the appropriate evaluation strategy based on the current state of the game. This selection is performed by streaming over the enum values and finding the first outcome whose condition matches the current game state, effectively applying the Strategy Pattern.

### Command Pattern Elements

While primarily demonstrating the Strategy Pattern, the structure and usage of the `Outcome` enum also incorporate aspects of the Command Pattern. Each enum constant acts similarly to a command object by encapsulating both an operation (the outcome evaluation logic) and its result (the corresponding `GameStatus`).

- **Command-Like Behavior**: The `check` method within each enum constant can be seen as executing the command. It applies its encapsulated logic to the current game state to determine if the condition for that outcome is met.

## Implementation Highlights

- **Functional Interface for Conditions**: The use of `BiPredicate<Integer, Integer>` as the type for the condition in each `Outcome` allows for concise and clear definition of the logic to determine each game outcome.

- **Stream API for Strategy Selection**: The `determineOutcome` method leverages the Java Stream API to elegantly select the appropriate game outcome strategy. This method filters through the `Outcome` constants, applying their conditions to the current game state and selecting the first match.

- **Enhanced Readability and Maintenance**: Grouping the outcome evaluation logic within an enum, paired with descriptive naming and the association of each outcome with a specific `GameStatus`, significantly enhances the readability and maintainability of the code.

## Benefits

1. **Reduced Complexity**: This approach simplifies the outcome evaluation process, making the logic for determining each possible game outcome clear and concise.

2. **Improved Maintainability**: Adding new outcomes or adjusting the conditions for existing outcomes is straightforward, facilitating easy updates and extensions to the game logic.

3. **Decoupled Evaluation Logic**: The separation of the outcome evaluation logic from the service logic results in a more modular and testable design, adhering to the principle of separation of concerns.

## Conclusion

The `BlackJackOutcomeEvaluatorService` class demonstrates an innovative and effective use of the Strategy Pattern, enhanced by elements of the Command Pattern, through the use of enums in Java. This approach to implementing game outcome evaluation logic in a Blackjack game highlights the power of design patterns in creating clear, flexible, and maintainable software architectures.

## Additional resources
* [Strategy](https://refactoring.guru/design-patterns/strategy)
* [Command](https://refactoring.guru/design-patterns/command)