package org.grizz.keeper.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Document(collection = "groups")
public class Group {
    @Id
    private String id;
    private String name;
    private String owner;
    private List<String> keys;

    public Group() {
    }
}
