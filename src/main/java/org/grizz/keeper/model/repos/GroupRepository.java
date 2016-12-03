package org.grizz.keeper.model.repos;

import org.grizz.keeper.model.Group;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GroupRepository extends MongoRepository<Group, String> {
    Group findByName(String name);

    List<Group> findByOwner(String login);
}
