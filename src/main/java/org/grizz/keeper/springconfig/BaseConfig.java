package org.grizz.keeper.springconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by Grizz on 2015-07-13.
 */
@Configuration
@Import(MongoConfig.class)
public class BaseConfig {
}
