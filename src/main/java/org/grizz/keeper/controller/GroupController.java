package org.grizz.keeper.controller;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.grizz.keeper.model.Entry;
import org.grizz.keeper.model.Group;
import org.grizz.keeper.model.impl.EntryEntity;
import org.grizz.keeper.model.impl.GroupEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Grizz on 2015-08-30.
 */
@Slf4j
@RestController
@RequestMapping("/groups")
public class GroupController {

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public Group getGroup(@PathVariable String name) {
        return GroupEntity.builder().build();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Group> getCurrentUserGroups() {
        return Lists.newArrayList(GroupEntity.builder().build());
    }

    @RequestMapping(value = "/entries/{name}", method = RequestMethod.GET)
    public List<Entry> getGroupedEntries(@PathVariable String name) {
        return Lists.newArrayList(EntryEntity.builder().build());
    }


}
