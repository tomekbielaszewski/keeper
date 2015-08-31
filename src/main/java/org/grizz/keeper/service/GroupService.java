package org.grizz.keeper.service;

import org.grizz.keeper.model.Entry;
import org.grizz.keeper.model.Group;
import org.grizz.keeper.model.impl.GroupEntity;

import java.util.List;

/**
 * Created by Grizz on 2015-08-31.
 */
public interface GroupService {
    Group get(String name);

    List<? extends Group> getCurrentUserGroups();

    Group add(GroupEntity group);

    Group update(GroupEntity group);

    List<? extends Entry> getEntries(String name);
}
