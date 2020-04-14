class Solution {
    // 判断 a+b 会不会越界
    private boolean addOK(int a, int b) {
        return a + b >= 0;
    }
    // 判断 a*b 会不会越界
    private boolean multiOK(int a, int b) {
        return a == 0 || (a * b) / a == b;
    }
    public int myAtoi(String str) {
        int n = str.length();
        int result = 0;
        int i;
        for (i = 0; i < n; i++) {
            if (str.charAt(i) != ' ')
                break;
        }
        if (i >= n)
            return 0;
        boolean negative = false;
        if (str.charAt(i) == '-') {
            negative = true;
            i++;
        } else if (str.charAt(i) == '+') {
            i++;
        }
        for ( ; i < n && Character.isDigit(str.charAt(i)); i++) {
            if (!multiOK(result, 10))
                return negative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            result *= 10;
            int num = str.charAt(i) - '0';
            if (!addOK(result, num))
                return negative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            result += num;
        }
        return negative ? -result : result;
    }
}