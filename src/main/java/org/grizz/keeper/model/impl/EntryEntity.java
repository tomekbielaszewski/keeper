package org.grizz.keeper.model.impl;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.grizz.keeper.model.Entry;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Grizz on 2015-07-13.
 */
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Document(collection = "entries")
public class EntryEntity implements Entry {
    private String key;
    private String value;
    private Long date;

    public EntryEntity() {
    }
}
