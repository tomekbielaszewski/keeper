package org.grizz.keeper.utils;

import static org.grizz.keeper.utils.BCrypt.*;

public class HashingUtils {
    public static String hash(String password) {
        return hashpw(password, gensalt());
    }

    public static boolean check(String password, String hashed) {
        return checkpw(password, hashed);
    }
}
