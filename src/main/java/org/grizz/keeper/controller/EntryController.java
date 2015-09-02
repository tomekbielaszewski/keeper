package org.grizz.keeper.controller;

import lombok.extern.slf4j.Slf4j;
import org.grizz.keeper.model.Entry;
import org.grizz.keeper.model.impl.EntryEntity;
import org.grizz.keeper.service.EntryService;
import org.grizz.keeper.service.UserService;
import org.grizz.keeper.service.exception.entry.KeyAlreadyExistsException;
import org.grizz.keeper.service.exception.MandatoryFieldsMissingException;
import org.grizz.keeper.service.exception.entry.RestrictedKeyException;
import org.grizz.keeper.service.exception.codes.ErrorEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * Created by Grizz on 2015-07-13.
 */
@Slf4j
@RestController
@RequestMapping("/entries")
public class EntryController {
    @Autowired
    private EntryService entryService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/{key}", method = RequestMethod.GET)
    public List<? extends Entry> getHistory(@PathVariable String key) {
        List<? extends Entry> history = entryService.getHistory(key);
        log.info("{} got history of [{}] entries. Amount: {}", userService.getCurrentUserLogin(), key, history.size());
        return history;
    }

    @RequestMapping(value = "/{key}/{since}", method = RequestMethod.GET)
    public List<? extends Entry> getHistorySince(@PathVariable String key, @PathVariable Long since) {
        List<? extends Entry> history = entryService.getHistorySince(key, since);
        log.info("{} got history of [{}] entries since [{}]. Amount: {}", userService.getCurrentUserLogin(), key, new Date(since), history.size());
        return history;
    }

    @RequestMapping(value = "/last/{key}", method = RequestMethod.GET)
    public Entry getLast(@PathVariable String key) {
        log.info("{} got last entry of [{}]", userService.getCurrentUserLogin(), key);
        return entryService.getLast(key);
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Entry add(@RequestBody EntryEntity entry) {
        Entry added = entryService.add(entry);
        log.info("{} added new entry of [{}]", userService.getCurrentUserLogin(), entry.getKey());
        return added;
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/many", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<? extends Entry> addMany(@RequestBody List<EntryEntity> entries) {
        List<? extends Entry> addedEntries = entryService.addMany(entries);
        log.info("{} added many entries. Amount: {}", userService.getCurrentUserLogin(), entries.size());
        return addedEntries;
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/all/{key}", method = RequestMethod.DELETE)
    public Long deleteAll(@PathVariable String key) {
        Long amountOfDeleted = entryService.deleteAll(key);
        log.info("{} deleted all {} entries of {}", userService.getCurrentUserLogin(), amountOfDeleted, key);
        return amountOfDeleted;
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/{key}/{date}", method = RequestMethod.DELETE)
    public Long deleteSingle(@PathVariable String key, @PathVariable Long date) {
        Long amountOfDeleted = entryService.deleteSingle(key, date);
        log.info("{} deleted {} entry of {} with date [{}]", userService.getCurrentUserLogin(), amountOfDeleted, key, new Date(date));
        return amountOfDeleted;
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/{key}/older/than/{date}", method = RequestMethod.DELETE)
    public Long deleteOlderThan(@PathVariable String key, @PathVariable Long date) {
        Long amountOfDeleted = entryService.deleteOlderThan(key, date);
        log.info("{} deleted {} entries of {} older than {}", userService.getCurrentUserLogin(), amountOfDeleted, key, new Date(date));
        return amountOfDeleted;
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

    @ExceptionHandler(KeyAlreadyExistsException.class)
    public Entry keyAlreadyExistsExceptionHandler(Exception e, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        KeyAlreadyExistsException exception = (KeyAlreadyExistsException) e;
        return ErrorEntry.keyAlreadyExists(exception.getKey());
    }
}
