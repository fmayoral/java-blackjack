# Implementing the Factory Method Pattern

## Introduction to Factory Method Pattern

The Factory Method Pattern is a creational design pattern that provides an interface for creating objects in a superclass but allows subclasses to alter the type of objects that will be created. This pattern is particularly useful when there is a need to encapsulate the instantiation process of a product, making a class delegate the responsibility of object creation to subclasses.

## Overview of GameFactoryImpl Class

The `GameFactoryImpl` class, as a part of the `uk.fmayoral.blackjack.core.factory` package, is an exemplary implementation of the Factory Method pattern within the context of a Blackjack game application. It defines a method, `createNewGame()`, which encapsulates the logic for creating a new instance of a `BlackJackGame`. This method serves as the factory method in this scenario.

## Key Components of GameFactoryImpl

### Dependencies and Annotations

- **Slf4j Annotation:** The class is annotated with `@Slf4j`, enabling it to use SLF4J for logging debug information, such as the creation of game objects and the dealing of hands.
- **Service Annotation:** It is marked with `@Service`, indicating that it's a Spring-managed service component, which makes it eligible for dependency injection and other Spring container features.
- **Autowired Constructor:** The class uses constructor-based dependency injection to obtain a reference to `CalculatorService`, demonstrating how external dependencies are managed in factory implementations.

### Factory Method: createNewGame

- **Instantiation Logic:** The `createNewGame` method contains the logic to instantiate a new `BlackJackGame` object. It generates a unique game ID, creates a shuffled deck (using `StandardDeck`), deals hands to the player and dealer, and initializes the game status.
- **Encapsulation of Object Creation:** By encapsulating the object creation logic within `createNewGame`, `GameFactoryImpl` abstracts the complexities involved in setting up a new Blackjack game. This includes deck creation, hand initialization, and application of game rules through the use of `CalculatorService` for calculating hand totals.

### Supporting Private Method: initialiseHand

- **Encapsulation of Hand Initialisation:** The `initialiseHand` method further encapsulates the creation of a `Hand` object, showing how factory patterns can use helper methods to break down the creation process into manageable parts. It draws cards from the deck and calculates the hand total using a passed function, showcasing flexibility in handling different types of hands (player vs. dealer).

## Conclusion

The `GameFactoryImpl` class illustrates a practical application of the Factory Method pattern in a Java application. By encapsulating the details of object creation and allowing for flexibility in the instantiation process, it demonstrates the benefits of using design patterns to enhance code modularity, readability, and maintainability. This approach not only simplifies the client code (which consumes the factory) by hiding the complex initialization logic but also provides a clear and concise way to extend and manage game creation processes within the application.

## Additional resources
* [Factory Method](https://refactoring.guru/design-patterns/factory-method)