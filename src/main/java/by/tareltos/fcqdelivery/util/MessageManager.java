package by.tareltos.fcqdelivery.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageManager {
    private static final String BUNDLE_NAME = "message";

    private MessageManager() {
    }

    public static String getProperty(String key, String locale) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale(locale));
        return resourceBundle.getString(key);
    }
}


