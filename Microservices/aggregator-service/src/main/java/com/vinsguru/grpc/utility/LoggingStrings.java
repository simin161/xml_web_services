package com.vinsguru.grpc.utility;

import java.time.LocalDateTime;

public class LoggingStrings {

    public static String getAuthenticationFailed(String component, String toString) {
        return LocalDateTime.now().toString() + "|" + component + "|" + toString;
    }

    public static String getUserWithEmailDoesntExists(String component, String email, String toString) {
        return LocalDateTime.now().toString() + "|" + component + "| searched user: "+ email + "|" + toString;
    }

    public static String getLoggedMessage(String component, String email, String toString) {
        return LocalDateTime.now().toString() + "|" + component + "| user: "+ email + "|" + toString;
    }

    public static String VerifyUser(String component, String toString) {
        return LocalDateTime.now().toString() + "|" + component + "|" + toString;
    }

    public static String getLoggedMessageAPIToken(String component, String userAPItoken, String toString) {
        return LocalDateTime.now().toString() + "|" + component + "| userAPIToken: "+ userAPItoken + "|" + toString;
    }

    public static String getLoggedMessageResendVerificationMail(String component, String email, String toString) {
        return LocalDateTime.now().toString() + "|" + component + "| resend destination: "+ email + "|" + toString;
    }
}
