package org.grizz.keeper.controller;

import org.grizz.keeper.model.Entry;
import org.grizz.keeper.model.impl.EntryEntity;
import org.grizz.keeper.repos.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by Grizz on 2015-07-13.
 */
@RestController
@RequestMapping("/val")
public class ValuesController {
    @Autowired
    private EntryRepository entryRepository;

    @RequestMapping(method = RequestMethod.GET)
    public Entry get() {
        Entry entry = entryRepository.findAll().stream().findFirst().orElse(EntryEntity.builder().date(new Date()).key("someValue").value("heheszki").build());
        return entry;
    }
}
