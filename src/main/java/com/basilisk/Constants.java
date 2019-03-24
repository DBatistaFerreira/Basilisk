package com.basilisk;

public abstract class Constants {
    public static final String CURRENT_USER = "currentUser";
    public static final String USER_PROFILE = "userProfile";
    public static final String PROFILE_ROUTE = "profile/";
    public static final String HOME_ROUTE = "home/";

    /**
     * Sanitizes strings by trimming whitespace off the ends and removing unnecessary newlines
     *
     * @return Sanitized string
     */
    public static String cleanString(String input) {
        input = input.trim();
        input = input.replaceAll("\\n{3,}", "\n\n");
        input = input.replaceAll("\\r{3,}", "\r\r");
        input = input.replaceAll("(\\r\\n){3,}", "\r\n\r\n");
        return input;
    }
}
