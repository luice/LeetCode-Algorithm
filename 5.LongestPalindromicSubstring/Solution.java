class Solution {
    public String longestPalindrome(String s) {
        char[] s_new = new char[2 * s.length() + 1];    // 处理过的字符串（字符间加 '\0'）
        int i = 0;
        s_new[i++] = '\0';
        for (int j = 0; j < s.length(); j++) {
            s_new[i++] = s.charAt(j);
            s_new[i++] = '\0';
        }
        int[] len = new int[s_new.length];
        len[0] = 1;
        int sub_midd = 0;   // 在 i 之前所得到的 len[] 中最大值所在位置
        int sub_side = 0;   // 以 sub_midd 为中心的最长回文子串的最右端所在位置
        for (i = 1; i < s_new.length; i++) {
            int j = 2 * sub_midd - i;   // i 关于 sub_midd 的对称点
            if (i < sub_side && j - len[j] >= 0) {
                // 以 j 为中心的最长回文子串在以 sub_midd 为中心的最长回文子串中，
                // 由于 i 和 j 关于 sub_midd 对称，
                // 以 i 为中心的最长回文子串与以 j 为中心的最长回文子串相同，
                // 故而 len[i] = len[j]
                if (len[j] <= sub_side - i)
                    len[i] = len[j];
                // 以 j 为中心的最长回文子串有一部分超出了以 sub_midd 为中心的最长回文子串，
                // 经过分析可知，最时以 i 为中心的最长回文子串的最右端就是 sub_side
                else
                    len[i] = sub_side - i + 1;
                // 特殊情况：len[j] == sub_side - i + 1，
                // 此时若以 i 为中心的最长回文子串比以 j 为中心的最长回文子串更长，也符合逻辑
                if (len[j] == sub_side - i + 1) {
                    // len[i] = sub_side - i + 1;
                    // 不必从 len[i] = 1 开始
                    while (i - len[i] >= 0 && i + len[i] < s_new.length &&
                            s_new[i-len[i]] == s_new[i+len[i]])
                        len[i]++;
                }
            } else {
                // 要从重新开始匹配
                len[i] = 1;
                while (i - len[i] >= 0 && i + len[i] < s_new.length &&
                       s_new[i-len[i]] == s_new[i+len[i]])
                    len[i]++;
            }
            // 更新 sub_midd 和 sub_side
            if (len[i] > len[sub_midd]) {
                sub_midd = i;
                sub_side = i + len[i] - 1;
            }
        }
        return s.substring((2 * sub_midd - sub_side) >> 1, sub_side >> 1);
    }
}