package org.grizz.keeper.model.repos;

import java.util.List;

/**
 * Created by Grizz on 2015-08-24.
 */
public interface EntryRepositoryCustom {
    List findUserOwnedKeys(String login);
}
