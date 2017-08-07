package org.grizz.keeper.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import org.grizz.keeper.model.User;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by tomasz.bielaszewski on 2015-08-25.
 */
public class HashingUtilsTest {

    @Test
    public void hashPassword() {
        String password = "grizz";
        String passwordHash = HashingUtils.hash(password);

        System.out.println("pass: " + password);
        System.out.println("hash: " + passwordHash);

        System.out.println("\nchecks:");
        for (int i = 0; i < 10; i++) {
            System.out.println(HashingUtils.hash(password));
        }

        assertTrue(HashingUtils.check(password, passwordHash));
    }

    @Test
    public void sampleUser() throws Exception {
        User user = User.builder()
                .id("1")
                .login("grizz")
                .password(HashingUtils.hash("grizz"))
                .roles(Sets.newHashSet("ADMIN", "USER"))
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);

        System.out.println(json);
    }
}