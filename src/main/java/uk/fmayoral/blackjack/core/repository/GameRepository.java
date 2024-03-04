package uk.fmayoral.blackjack.core.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import uk.fmayoral.blackjack.core.models.BlackJackGame;

/**
 * Game repository implementation in this case is provided by spring-data mongo support
 */
@Repository
public interface GameRepository extends MongoRepository<BlackJackGame, String> {
}
