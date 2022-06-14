package com.agent.app.utility;

import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;

public class LoggingStrings {

    public static String getAuthenticationFailed(String component, String toString) {
        return LocalDateTime.now().toString() + "|" + component + "|" + toString;
    }

    public String getUserWithEmailDoesntExists(String email) {
        return LocalDateTime.now().toString() + "|com.agent.app.service.UserService|user " + email + " does not exists";
    }
}
