package org.grizz.keeper.model.impl;

import lombok.*;
import org.grizz.keeper.model.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by tomasz.bielaszewski on 2015-08-24.
 */
@Data
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Document(collection = "users")
public class UserEntity implements User {
    @Id
    private String id;
    private String login;
    private String passwordHash;

    public UserEntity() {
    }
}
