package org.grizz.keeper.controller;

import org.grizz.keeper.model.Entry;
import org.grizz.keeper.model.impl.EntryEntity;
import org.grizz.keeper.model.repos.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Grizz on 2015-07-13.
 */
@RestController
@RequestMapping("/entry")
public class EntryController {
    @Autowired
    private EntryRepository entryRepository;

    @RequestMapping(method = RequestMethod.GET)
    public Entry get() {
        Entry entry = entryRepository.findAll().stream()
                .findFirst()
                .orElse(EntryEntity.builder()
                        .date(System.currentTimeMillis())
                        .key("someValue")
                        .value("heheszki")
                        .build());
        return entry;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void add(@RequestBody EntryEntity entry) {
        if (entry.getDate() == null) {
            entry.setDate(System.currentTimeMillis());
        }
        entryRepository.insert(entry);
    }
}
