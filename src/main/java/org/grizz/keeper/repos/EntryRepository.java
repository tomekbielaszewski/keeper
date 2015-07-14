package org.grizz.keeper.repos;

import org.grizz.keeper.model.Entry;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Grizz on 2015-07-14.
 */
public interface EntryRepository extends MongoRepository<Entry, String> {
}
