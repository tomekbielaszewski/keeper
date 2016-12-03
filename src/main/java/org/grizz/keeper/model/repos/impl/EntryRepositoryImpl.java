package org.grizz.keeper.model.repos.impl;

import com.mongodb.BasicDBObject;
import org.grizz.keeper.model.repos.EntryRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

public class EntryRepositoryImpl implements EntryRepositoryCustom {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List findUserOwnedKeys(String login) {
        List distinct = mongoTemplate.getCollection("entries").distinct("key", new BasicDBObject("owner", login));
        return distinct;
    }
}
