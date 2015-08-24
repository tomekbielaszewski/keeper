package org.grizz.keeper.controller;

import org.grizz.keeper.model.Entry;
import org.grizz.keeper.model.impl.EntryEntity;
import org.grizz.keeper.service.EntryService;
import org.grizz.keeper.service.exception.MandatoryFieldsMissingException;
import org.grizz.keeper.service.exception.RestrictedKeyException;
import org.grizz.keeper.service.exception.codes.ErrorEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Grizz on 2015-07-13.
 */
@RestController
@RequestMapping("/entries")
public class EntryController {
    @Autowired
    private EntryService entryService;

    @RequestMapping(value = "/{key}", method = RequestMethod.GET)
    public List<? extends Entry> getHistory(@PathVariable String key) {
        return entryService.getHistory(key);
    }

    @RequestMapping(value = "/{key}/{from}", method = RequestMethod.GET)
    public List<? extends Entry> getHistoryFromLast(@PathVariable String key, @PathVariable Long from) {
        return entryService.getHistoryFromLast(key, from);
    }

    @RequestMapping(value = "/last/{key}", method = RequestMethod.GET)
    public EntryEntity getLast(@PathVariable String key) {
        return entryService.getLast(key);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public EntryEntity add(@RequestBody EntryEntity entry) {
        return entryService.add(entry);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/many", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<? extends Entry> addMany(@RequestBody List<EntryEntity> entries) {
        return entryService.addMany(entries);
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/all/{key}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Long deleteAll(@PathVariable String key) {
        return entryService.deleteAll(key);
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/{key}/{date}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Long deleteSingle(@PathVariable String key, @PathVariable Long date) {
        return entryService.deleteSingle(key, date);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/{key}/older/than/{date}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Long deleteOlderThan(@PathVariable String key, @PathVariable Long date) {
        return entryService.deleteOlderThan(key, date);
    }

    @ExceptionHandler(MandatoryFieldsMissingException.class)
    public Entry mandatoryFieldsMissingExceptionHandler(Exception e, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return ErrorEntry.keyAndValueAreMandatory();
    }

    @ExceptionHandler(RestrictedKeyException.class)
    public Entry restrictedKeyExceptionHandler(Exception e, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        RestrictedKeyException exception = (RestrictedKeyException) e;
        return ErrorEntry.restrictedKey(exception.getKey());
    }
}
