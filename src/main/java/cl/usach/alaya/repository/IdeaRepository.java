package cl.usach.alaya.repository;

import cl.usach.alaya.model.Idea;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface IdeaRepository extends MongoRepository<Idea, String>{
    Iterable<Idea> findAllByChallengeId(String challengeId);
    Iterable<Idea> findAllByUserId(String userId);
}
