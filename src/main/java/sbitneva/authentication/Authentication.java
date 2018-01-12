package sbitneva.authentication;

import javax.servlet.http.HttpSession;
import java.util.Objects;

public class Authentication {

    private Authentication() {
    }

    public static boolean isUserLogIn(HttpSession session) {
        return Objects.nonNull(session.getAttribute("login"));
    }
}
