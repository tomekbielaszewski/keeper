package org.grizz.keeper.model;

import java.util.List;

/**
 * Created by Grizz on 2015-08-30.
 */
public interface Group {
    String getId();
    String getName();
    List<String> getKeys();
}
