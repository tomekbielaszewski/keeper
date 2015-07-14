package org.grizz.keeper.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created by Grizz on 2015-07-14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface Entry {
    String getKey();

    String getValue();

    Long getDate();
}
