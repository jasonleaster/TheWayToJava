package org.jasonleaster.utils.encrypt;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

/**
 * Author: jasonleaster
 * Date  : 2017/8/27
 * Email : jasonleaster@gmail.com
 * Description:
 */
public final class PasswordUtils {

    private static final String SECRET_KEY = "secretKey";
    private static final PasswordEncoder encoder = new StandardPasswordEncoder(SECRET_KEY);

    public static String encrypt(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    public static boolean match(String rawPassword, String password) {
        return encoder.matches(rawPassword, password);
    }
}
