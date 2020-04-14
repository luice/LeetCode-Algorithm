class Solution {
    public boolean isPalindrome(int x) {
        if (x < 0)
            return false;
        else if (x == 0)
            return true;
        int[] digits = new int[12];
        int n;
        for (n = 0; x != 0; n++, x /= 10)
            digits[n] = x % 10;
        for (int i = 0, j = n-1; i < j; i++, --j)
            if (digits[i] != digits[j])
                return false;
        return true;
    }
}