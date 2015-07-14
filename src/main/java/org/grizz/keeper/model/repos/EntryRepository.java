package org.grizz.keeper.model.repos;

import org.grizz.keeper.model.impl.EntryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Grizz on 2015-07-14.
 */
public interface EntryRepository extends MongoRepository<EntryEntity, String> {
}