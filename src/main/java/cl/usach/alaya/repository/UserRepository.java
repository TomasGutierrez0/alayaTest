package cl.usach.alaya.repository;

import cl.usach.alaya.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByMail(String email);
    boolean existsByMail(String email);
}
