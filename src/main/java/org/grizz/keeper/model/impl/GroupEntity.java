package org.grizz.keeper.model.impl;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by Grizz on 2015-08-30.
 */
@Data
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Document(collection = "groups")
public class GroupEntity implements org.grizz.keeper.model.Group {
    @Id
    private String id;
    private String name;
    private List<String> keys;
}
