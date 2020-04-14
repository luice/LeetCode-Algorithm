class Solution {
    public String convert(String s, int numRows) {
        if (numRows == 1)
            return s;
        StringBuilder[] strings = new StringBuilder[numRows];
        for (int i = 0; i < numRows; i++)
            strings[i] = new StringBuilder();
        int b = 0, sign = 1;
        for (int i = 0; i < s.length(); i++) {
            strings[b].append(s.charAt(i));
            b += sign;
            if (b < 0) {
                b = 1;
                sign = 1;
            } else if (b >= numRows) {
                b = numRows - 2;
                sign = -1;
            }
        }
        StringBuilder result = new StringBuilder();
        for (StringBuilder builder : strings) {
            result.append(builder);
        }
        return result.toString();
    }
}