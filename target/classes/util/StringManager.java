package util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Utility class with methods to externalize strings
 * @author Achille Lambrughi
 * @author Emanuele Drago
 * @author Lorenzo Ottaviani
 * @author Elisabeth Veronika Venturino
 *
 */
public class StringManager {
    private static final String BUNDLE_NAME = "util.string";

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

    /**
     * Default constructor
     */
    private StringManager() {
    }

    /**
     * Returns the string corresponding to the parameter given
     * @param key
     * @return the desired string
     */
    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}