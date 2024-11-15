package api;

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
        int start = str.indexOf(first) + first.length();
        int end = str.lastIndexOf(last);
        return str.substring(start, end);
    }
}
