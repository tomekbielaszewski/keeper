package org.grizz.keeper.utils;

import org.junit.Test;

import javax.sound.midi.Soundbank;
import javax.validation.constraints.AssertTrue;

import static org.junit.Assert.*;

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
}