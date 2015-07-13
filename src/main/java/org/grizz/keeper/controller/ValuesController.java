package org.grizz.keeper.controller;

import org.grizz.keeper.model.Entry;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Grizz on 2015-07-13.
 */
@RestController
@RequestMapping("/val")
public class ValuesController {

    @RequestMapping(method = RequestMethod.GET)
    public Entry get() {
        return new Entry();
    }
}
