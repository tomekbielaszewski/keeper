package org.grizz.keeper.model;

import lombok.*;

import java.util.List;

@Data
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class EntryGroup {
    private String name;
    private List<Entry> entries;
}
