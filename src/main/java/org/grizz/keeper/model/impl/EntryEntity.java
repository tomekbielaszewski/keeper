package org.grizz.keeper.model.impl;

import lombok.*;
import org.grizz.keeper.model.Entry;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Grizz on 2015-07-13.
 */
@Data
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Document(collection = "entries")
public class EntryEntity implements Entry {
    @Id
    private String id;
    private String key;
    private String value;
    private String owner;
    private Long date;

    public EntryEntity() {
    }
}
