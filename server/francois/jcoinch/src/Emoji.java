package im.delight.java.emoji;

import java.util.Map;
import java.util.regex.Pattern;
import java.util.HashMap;

/**
 * Lets you replace all emoticons in a text with their corresponding Unicode Emoji
 * <p>
 * Usage: String myEmojiString = Emoji.replaceInText(myString);
 */

public class Emoji {

    /** Characters that may not occur immediately before or after an emoticon */
    private static final String REGEX_SURROUNDING_CHARS_DISALLOWED = "[-_a-zA-Z)(;:*<>=/]";
    /** A negative look-behind ensuring that the match is not preceded by one of the characters above */
    private static final String REGEX_NEGATIVE_LOOKBEHIND = "(?<!"+REGEX_SURROUNDING_CHARS_DISALLOWED+")";
    /** A negative look-ahead ensuring that the match is not followed by one of the characters above */
    private static final String REGEX_NEGATIVE_LOOKAHEAD = "(?!"+REGEX_SURROUNDING_CHARS_DISALLOWED+")";

    private static class ReplacementsMap extends HashMap<String,Integer> {

        private static final long serialVersionUID = 4948071414363715959L;
        private static ReplacementsMap mInstance;

        private ReplacementsMap() {
            super();
            put("A", 0x2663);
            put("B", 0x2665);
            put("C", 0x2660);
            put("D", 0x2666);
        }

        public static ReplacementsMap getInstance() {
            if (mInstance == null) {
                mInstance = new ReplacementsMap();
            }
            return mInstance;
        }

    }

    private static String getUnicodeChar(int codepoint) {
        return new String(Character.toChars(codepoint));
    }

    /** Constructs a regular expression which ensures that the emoticon is not part of a longer string of special chars (e.g. <:)))> or <http://> which both include basic emoticons) */
    private static String getEmoticonSearchRegex(String emoticon) {
        return REGEX_NEGATIVE_LOOKBEHIND+"("+Pattern.quote(emoticon)+")"+REGEX_NEGATIVE_LOOKAHEAD;
    }

    /**
     * Replaces all emoticons in the given text with their corresponding Unicode Emoji
     *
     * @param text the String to search and replace in
     * @return the new String containing the Unicode Emoji
     */
    public static String replaceInText(String text) {
        return replaceInText(text, false);
    }

    /**
     * Converts between emoticons and their corresponding Unicode Emoji in the given text
     *
     * @param text the String to search and replace in
     * @param reverse whether to replace emoticons with emoji as usual (false) or revert emoji to emoticons (true)
     * @return the new String containing the the converted entities
     */
    public static String replaceInText(String text, boolean reverse) {
        final ReplacementsMap replacements = ReplacementsMap.getInstance();
        String emoticon;
        Integer codepoint;

        for (Map.Entry<String, Integer> entry : replacements.entrySet()) {
            if (entry != null) {
                emoticon = entry.getKey();
                codepoint = entry.getValue();
                if (emoticon != null && codepoint != null) {
                    String unicodeChar = getUnicodeChar(codepoint.intValue());
                    if (reverse) {
                        text = text.replace(unicodeChar, emoticon);
                    }
                    else {
                        text = text.replaceAll(getEmoticonSearchRegex(emoticon), unicodeChar);
                    }
                }
            }
        }

        return text;
    }

}