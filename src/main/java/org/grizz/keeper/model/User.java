package org.grizz.keeper.model;

import java.util.Set;

/**
 * Created by tomasz.bielaszewski on 2015-08-24.
 */
public interface User {
    String getId();
    String getLogin();
    String getPasswordHash();
    Set<String> getRoles();
}
