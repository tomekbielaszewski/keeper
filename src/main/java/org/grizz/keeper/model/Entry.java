package org.grizz.keeper.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Grizz on 2015-07-14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface Entry {
    String getKey();
    String getValue();

    String getOwner();
    Long getDate();
}
