// aabbcdd

class FirstUniqChar {

    public static String firstNonRepeatChar(string myChar) {
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