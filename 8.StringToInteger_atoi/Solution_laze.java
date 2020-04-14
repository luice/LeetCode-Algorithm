class Solution {
    public int myAtoi(String str) {
        int i, j, n = str.length();
        for (i = 0 ; i < n; i++)
            if (str.charAt(i) != ' ')
                break;
        if (i >= n)
            return 0;
        if (str.charAt(i) == '-' || str.charAt(i) == '+')
            j = i + 1;
        else
            j = i;
        for ( ; j < n; j++)
            if (!Character.isDigit(str.charAt(j)))
                break;
        str = str.substring(i, j);
        if (str.equals("") || str.equals("-") || str.equals("+"))
            return 0;
        int result;
        try {
            result = Integer.parseInt(str);
        } catch (NumberFormatException nfex) {
            if (str.charAt(0) == '-')
                return Integer.MIN_VALUE;
            else
                return Integer.MAX_VALUE;
        }
        return result;
    }
}