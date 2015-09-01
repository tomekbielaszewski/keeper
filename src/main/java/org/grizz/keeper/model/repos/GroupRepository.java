package org.grizz.keeper.model.repos;

import org.grizz.keeper.model.impl.GroupEntity;
import org.grizz.keeper.model.impl.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by tomasz.bielaszewski on 2015-09-01.
 */
public interface GroupRepository extends MongoRepository<GroupEntity, String> {
    GroupEntity findByName(String name);
    List<GroupEntity> findByOwner(String login);
}
