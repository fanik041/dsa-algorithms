import java.util.HashMap;
import java.util.Map;

class FirstUniqChar {

    // Private constructor to prevent instantiation
    private FirstUniqChar() {}

    public static String firstNonRepeatChar(String myChar) {
        Map<Character, Integer> charCountMap = new HashMap<>();

        for (char c : myChar.toCharArray()) {
            charCountMap.put(c, charCountMap.getOrDefault(c, 0) + 1);
        }

        for (char c : myChar.toCharArray()) {
            if (charCountMap.get(c) == 1) {
                return String.valueOf(c);
            }
        }

        return "-1";
    }
}