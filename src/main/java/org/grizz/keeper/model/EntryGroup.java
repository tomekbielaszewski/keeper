package org.grizz.keeper.model;

import java.security.PrivateKey;
import java.util.List;

/**
 * Created by tomasz.bielaszewski on 2015-09-02.
 */
public interface EntryGroup {
    String getName();
    List<? extends Entry> getEntries();
}
