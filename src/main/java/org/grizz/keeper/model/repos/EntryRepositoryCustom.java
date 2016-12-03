package org.grizz.keeper.model.repos;

import java.util.List;

public interface EntryRepositoryCustom {
    List findUserOwnedKeys(String login);
}
