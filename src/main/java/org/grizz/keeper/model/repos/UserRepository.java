package org.grizz.keeper.model.repos;

import org.grizz.keeper.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByLogin(String login);
}
