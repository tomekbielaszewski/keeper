package org.grizz.keeper.controller;

import lombok.extern.slf4j.Slf4j;
import org.grizz.keeper.model.Entry;
import org.grizz.keeper.service.EntryService;
import org.grizz.keeper.service.SystemEntry;
import org.grizz.keeper.service.UserService;
import org.grizz.keeper.service.exception.MandatoryFieldsMissingException;
import org.grizz.keeper.service.exception.codes.ErrorEntry;
import org.grizz.keeper.service.exception.entry.EntryDoesNotExistException;
import org.grizz.keeper.service.exception.entry.InvalidKeyOwnerException;
import org.grizz.keeper.service.exception.entry.KeyDoesNotExistException;
import org.grizz.keeper.service.exception.entry.RestrictedKeyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/entries")
public class EntryController {
    @Autowired
    private EntryService entryService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/{key}", method = RequestMethod.GET)
    public List<Entry> getHistory(@PathVariable String key) {
        List<Entry> history = entryService.getHistory(key);
        log.info("{} got history of [{}] entries. Amount: {}", userService.getCurrentUserLogin(), key, history.size());
        return history;
    }

    @RequestMapping(value = "/{key}/{since}", method = RequestMethod.GET)
    public List<Entry> getHistorySince(@PathVariable String key, @PathVariable Long since) {
        List<Entry> history = entryService.getHistorySince(key, since);
        log.info("{} got history of [{}] entries since [{}]. Amount: {}", userService.getCurrentUserLogin(), key, new Date(since), history.size());
        return history;
    }

    @RequestMapping(value = "/last/{key}", method = RequestMethod.GET)
    public Entry getLast(@PathVariable String key) {
        log.info("{} got last entry of [{}]", userService.getCurrentUserLogin(), key);
        return entryService.getLast(key);
    }

    @PreAuthorize("hasAuthority('USER')")
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Entry add(@RequestBody Entry entry) {
        Entry added = entryService.add(entry);
        log.info("{} added new entry of [{}]", userService.getCurrentUserLogin(), entry.getKey());
        return added;
    }

    @PreAuthorize("hasAuthority('USER')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Entry delete(@PathVariable String id) {
        entryService.delete(id);
        log.info("{} deleted single entry with id {}", userService.getCurrentUserLogin(), id);
        return SystemEntry.removed();
    }

    @PreAuthorize("hasAuthority('USER')")
    @RequestMapping(value = "/all/{key}", method = RequestMethod.DELETE)
    public Entry deleteAll(@PathVariable String key) {
        Long amountOfDeleted = entryService.deleteAll(key);
        log.info("{} deleted all {} entries of {}", userService.getCurrentUserLogin(), amountOfDeleted, key);
        return SystemEntry.removedAmount(amountOfDeleted);
    }

    @PreAuthorize("hasAuthority('USER')")
    @RequestMapping(value = "/all/{key}/exact/{date}", method = RequestMethod.DELETE)
    public Entry deleteSingle(@PathVariable String key, @PathVariable Long date) {
        Long amountOfDeleted = entryService.deleteSingle(key, date);
        log.info("{} deleted {} entry of {} with date [{}]", userService.getCurrentUserLogin(), amountOfDeleted, key, new Date(date));
        return SystemEntry.removedAmount(amountOfDeleted);
    }

    @PreAuthorize("hasAuthority('USER')")
    @RequestMapping(value = "/all/{key}/older/than/{date}", method = RequestMethod.DELETE)
    public Entry deleteOlderThan(@PathVariable String key, @PathVariable Long date) {
        Long amountOfDeleted = entryService.deleteOlderThan(key, date);
        log.info("{} deleted {} entries of {} older than {}", userService.getCurrentUserLogin(), amountOfDeleted, key, new Date(date));
        return SystemEntry.removedAmount(amountOfDeleted);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MandatoryFieldsMissingException.class)
    public Entry mandatoryFieldsMissingExceptionHandler() {
        return ErrorEntry.keyAndValueAreMandatory();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RestrictedKeyException.class)
    public Entry restrictedKeyExceptionHandler(RestrictedKeyException e) {
        return ErrorEntry.restrictedKey(e.getKey());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidKeyOwnerException.class)
    public Entry invalidKeyOwnerExceptionHandler(InvalidKeyOwnerException e) {
        return ErrorEntry.invalidKeyOwner(e.getKey());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(KeyDoesNotExistException.class)
    public Entry keyDoesNotExistExceptionHandler(KeyDoesNotExistException e) {
        return ErrorEntry.keyDoesNotExist(e.getKey());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntryDoesNotExistException.class)
    public Entry entryDoesNotExistExceptionHandler(EntryDoesNotExistException e) {
        return ErrorEntry.entryDoesNotExist(e.getId());
    }
}
