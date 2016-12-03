package org.grizz.keeper.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String login;
    private String passwordHash;
    private Set<String> roles;

    public User() {
    }
}
