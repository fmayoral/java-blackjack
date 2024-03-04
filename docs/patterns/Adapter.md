# Understanding the Adapter Pattern

## Introduction

The Adapter Pattern is a structural design pattern that allows objects with incompatible interfaces to collaborate. It acts as a bridge between two incompatible interfaces by converting the interface of a class into another interface clients expect. This pattern is especially useful in systems where new classes cannot be modified to be compatible with existing classes or interfaces.

## The BlackJackGameMapper Class: An Adapter in Action

The `BlackJackGameMapper` class in the `uk.fmayoral.blackjack.core.mapper` package serves as a practical implementation of the Adapter Pattern within a Blackjack game application. Its primary role is to convert or map domain model objects (`BlackJackGame`, `Hand`, and `GameStatus` from the `uk.fmayoral.blackjack.core.models` package) into API model objects (`Game`, `Player`, and `Card` from the `uk.fmayoral.blackjack.web.api.v1.models` package) that are designed for external API consumption.

### Key Functions

- **`mapInitialGame(BlackJackGame blackJackGame)` Method:** This method specifically adapts the initial game state from the domain model to the API model, applying a specific rule (hiding the dealer's second card) to fit the requirements of the game's initial state presentation.
- **`map(BlackJackGame blackJackGame)` Method:** It converts a `BlackJackGame` object into a `Game` object, mapping the detailed state of the game, including player and dealer hands, from the core domain to the API layer.
- **`mapHand(Hand hand)` Method:** This auxiliary method maps a `Hand` object from the domain model to a `Player` object in the API model, focusing on converting the hand's cards and total value.
- **`map(GameStatus status)` and `map(List<uk.fmayoral.blackjack.core.models.Card> cards)` Methods:** These methods adapt the game status and list of cards from the domain models to their corresponding representations in the API model.

### Implementation of Adapter Pattern

The `BlackJackGameMapper` class is an adapter that mediates between the core domain model and the API model, ensuring that the two can interact seamlessly:

- **Interface Incompatibility:** The core and API models have different structures and purposes. The core model is designed for the game's logic and internal state management, while the API model is structured to provide information in a format suitable for external interfaces or client applications.
- **Adaptation Process:** Through its mapping methods, `BlackJackGameMapper` translates the complex domain objects into simpler, more client-friendly structures without altering the original data models. This process allows for flexible and extendable code, as changes to the core model do not directly impact the API consumers and vice versa.
- **Seamless Integration:** By abstracting the conversion logic inside the mapper, other parts of the application can remain agnostic of the differences between the domain and API models. This leads to cleaner, more maintainable code and easier integration of new features or changes.

## Conclusion

The `BlackJackGameMapper` class exemplifies the Adapter Pattern by facilitating communication between the application's core domain and its external API. Through its mapping methods, it enables incompatible interfaces to work together, enhancing the application's modularity and flexibility. This pattern proves invaluable in complex systems where adapting to changes or integrating disparate components without rewriting existing code is crucial.

## Additional resources
* [Adapter](https://refactoring.guru/design-patterns/adapter)