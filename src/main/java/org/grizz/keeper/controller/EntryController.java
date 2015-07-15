package org.grizz.keeper.controller;

import org.grizz.keeper.model.impl.EntryEntity;
import org.grizz.keeper.model.repos.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Grizz on 2015-07-13.
 */
@RestController
@RequestMapping("/entry")
public class EntryController {
    @Autowired
    private EntryRepository entryRepository;

    @RequestMapping(value = "/{key}", method = RequestMethod.GET)
    public List<EntryEntity> getHistory(@PathVariable String key) {
        List<EntryEntity> entries = entryRepository.findByKeyOrderByDateDesc(key);
        return entries;
    }

    @RequestMapping(value = "/{key}/{from}", method = RequestMethod.GET)
    public List<EntryEntity> getHistoryFromLast(@PathVariable String key, @PathVariable Long from) {
        List<EntryEntity> entries = entryRepository.findByKeyAndDateGreaterThanEqualOrderByDateDesc(key, from);
        return entries;
    }

    @RequestMapping(value = "/last/{key}", method = RequestMethod.GET)
    public EntryEntity getLast(@PathVariable String key) {
        EntryEntity entry = entryRepository.findTopByKeyOrderByDateDesc(key);
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
