package org.grizz.keeper.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Document(collection = "entries")
public class Entry {
    @Id
    private String id;
    private String key;
    private String value;
    private String owner;
    private Long date;

    public Entry() {
    }
}
