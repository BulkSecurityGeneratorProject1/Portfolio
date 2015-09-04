package eu.thinking_aloud.portfolio.repository;

import eu.thinking_aloud.portfolio.domain.Quicksort;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Quicksort entity.
 */
public interface QuicksortRepository extends MongoRepository<Quicksort,String> {

}
