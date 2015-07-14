package org.grizz.keeper.model.impl;

import lombok.Builder;
import lombok.Value;
import org.grizz.keeper.model.Entry;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by Grizz on 2015-07-13.
 */
@Value
@Builder
@Document(collection = "values")
public class EntryEntity implements Entry {
    private String key;
    private String value;
    private Date date;
}
