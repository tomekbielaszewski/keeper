package org.grizz.keeper.controller;

import org.grizz.keeper.model.impl.EntryEntity;
import org.grizz.keeper.service.EntryService;
import org.grizz.keeper.service.exception.MandatoryFieldsMissingException;
import org.grizz.keeper.service.exception.RestrictedKeyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Grizz on 2015-07-13.
 */
@RestController
@RequestMapping("/entry")
public class EntryController {
    @Autowired
    private EntryService entryService;

    @RequestMapping(value = "/{key}", method = RequestMethod.GET)
    public List<EntryEntity> getHistory(@PathVariable String key) {
        return entryService.getHistory(key);
    }

    @RequestMapping(value = "/{key}/{from}", method = RequestMethod.GET)
    public List<EntryEntity> getHistoryFromLast(@PathVariable String key, @PathVariable Long from) {
        return entryService.getHistoryFromLast(key, from);
    }

    @RequestMapping(value = "/last/{key}", method = RequestMethod.GET)
    public EntryEntity getLast(@PathVariable String key) {
        return entryService.getLast(key);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public EntryEntity add(@RequestBody EntryEntity entry, HttpServletResponse response) {
        return entryService.add(entry);
    }

    @RequestMapping(value = "/add/many", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<EntryEntity> addMany(@RequestBody List<EntryEntity> entries, HttpServletResponse response) {
        return entryService.addMany(entries);
    }

    @RequestMapping(value = "/delete/all/{key}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Long deleteAll(@PathVariable String key, HttpServletResponse response) {
        return entryService.deleteAll(key);
    }

    @RequestMapping(value = "/delete/{key}/{date}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Long deleteSingle(@PathVariable String key, @PathVariable Long date, HttpServletResponse response) {
        return entryService.deleteSingle(key, date);
    }

    @RequestMapping(value = "/delete/{key}/older/than/{date}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Long deleteOlderThan(@PathVariable String key, @PathVariable Long date, HttpServletResponse response) {
        return entryService.deleteOlderThan(key, date);
    }

    @ExceptionHandler(MandatoryFieldsMissingException.class)
    public EntryEntity mandatoryFieldsMissingExceptionHandler(Exception e) {
        return EntryEntity.builder()
                .key("ERROR")
                .value("KEY and VALUE are mandatory!")
                .date(System.currentTimeMillis())
                .build();
    }

    @ExceptionHandler(RestrictedKeyException.class)
    public EntryEntity restrictedKeyExceptionHandler(Exception e) {
        RestrictedKeyException exception = (RestrictedKeyException) e;
        return EntryEntity.builder()
                .key("ERROR")
                .value("Provided key [" + exception.getKey() + "] is restricted!")
                .date(System.currentTimeMillis())
                .build();
    }
}
