package org.grizz.keeper.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Document(collection = "entries")
public class Entry {
    @Id
    private String id;
    private String key;
    private Map<String, Object> value;
    private String owner;
    private Long date;

    public Entry() {
    }
}
