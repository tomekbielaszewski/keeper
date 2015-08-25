package org.grizz.keeper.utils;

/**
 * Created by Grizz on 2015-08-25.
 */
public class HashingUtils {
    public static String hash(String password) {
        return password; //TODO hashuj, sol, hashuj!
    }

    public static boolean check(String password, String hashed) {
        return password.equals(hashed); //TODO hashuj, sol, hashuj!
    }
}
