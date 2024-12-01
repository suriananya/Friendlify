package utilities;

import org.json.JSONArray;

/**
 * A utility class where public, static methods potentially usable by everyone can be placed.
 */
public class Utility {
    /**
     * Splice a string from the first occurrence of first to the last occurrence of last. Not inclusive.
     * If first is an empty string, then it will splice up to last, and vice versa.
     * If both are empty, nothing happens.
     * @param str is the string you intend to splice.
     * @param first is the word to begin the splice at.
     * @param last is the word to end the splice at.
     * @return the spliced string.
     */
    public static String spliceString(String str, String first, String last) {
        final int start = str.indexOf(first) + first.length();
        final int end = str.lastIndexOf(last);
        return str.substring(start, end);
    }

    /**
     * Removes all the null values from a JSONArray.
     * @param unsanitized an arbitrary JSONArray, possibly with null values.
     * @return a version of the above JSONArray without any null values.
     */
    public static JSONArray sanitizeJSONArray(JSONArray unsanitized) {
        final JSONArray sanitized = new JSONArray();
        for (int i = 0; i < unsanitized.length(); i++) {
            if (!unsanitized.isNull(i)) {
                sanitized.put(unsanitized.getJSONObject(i));
            }
        }
        return sanitized;
    }
}
