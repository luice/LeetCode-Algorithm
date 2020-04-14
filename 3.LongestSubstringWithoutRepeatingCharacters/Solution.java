import java.util.HashMap;

class Solution {
    public int lengthOfLongestSubstring(String s) {
        HashMap<Character, Integer> appeared = new HashMap<>();
        int length = 0, start = 0, end = 0;
        int l = 0;
        while (end < s.length()) {
            char c = s.charAt(end);
            if (appeared.containsKey(c)) {
                if (l > length)
                    length = l;
                int index = appeared.get(c);
                l -= index - start;
                for ( ; start <= index; start++) {
                    appeared.remove(s.charAt(start));
                }
                appeared.put(c, end);
                end++;
            } else {
                appeared.put(c, end);
                end++;
                l++;
            }
        }
        if (l > length)
            length = l;
        return length;
    }
}