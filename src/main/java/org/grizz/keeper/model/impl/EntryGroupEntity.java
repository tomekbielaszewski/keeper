package org.grizz.keeper.model.impl;

import lombok.*;
import org.grizz.keeper.model.Entry;
import org.grizz.keeper.model.EntryGroup;

import java.util.List;

/**
 * Created by tomasz.bielaszewski on 2015-09-02.
 */
@Data
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class EntryGroupEntity implements EntryGroup {
    private String name;
    private List<? extends Entry> entries;
}
