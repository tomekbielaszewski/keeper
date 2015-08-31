package org.grizz.keeper.service.impl;

import org.grizz.keeper.model.Entry;
import org.grizz.keeper.model.Group;
import org.grizz.keeper.model.impl.GroupEntity;
import org.grizz.keeper.service.GroupService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Grizz on 2015-08-31.
 */
@Service
public class GroupServiceImpl implements GroupService {
    @Override
    public Group get(String name) {
        return null;
    }

    @Override
    public List<? extends Group> getCurrentUserGroups() {
        return null;
    }

    @Override
    public Group add(GroupEntity group) {
        return null;
    }

    @Override
    public Group update(GroupEntity group) {
        return null;
    }

    @Override
    public List<? extends Entry> getEntries(String name) {
        return null;
    }
}
