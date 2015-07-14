package org.grizz.keeper.springconfig;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

/**
 * Created by Grizz on 2015-07-13.
 */
@Slf4j
@Configuration
public class MongoConfig extends AbstractMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        log.info("Using mongo DB: \"keeper\"");
        return "keeper";
    }

    @Override
    public Mongo mongo() throws Exception {
        log.info("Initializing mongo client...");
        return new MongoClient();
    }
}
