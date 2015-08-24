package org.grizz.keeper.model.repos;

import org.grizz.keeper.model.impl.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Grizz on 2015-07-14.
 */
public interface UserRepository extends MongoRepository<UserEntity, String> {
    UserEntity findByLogin(String login);
}
