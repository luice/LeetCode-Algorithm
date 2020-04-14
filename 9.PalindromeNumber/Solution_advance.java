class Solution {
    public boolean isPalindrome(int x) {
        if (x < 0)
            return false;
        else if (x == 0)
            return true;
        int mask;               // 获得最高位所需除数，比如 x = 123，mask = 100
        if (x >= 1000000000) {
            mask = 1000000000;
        } else {
            mask = 10;
            while (x / mask != 0)
                mask *= 10;
            mask /= 10;
        }
        while (x != 0) {
            int hi = x / mask;  // 最高位
            int lo = x % 10;    // 最低位
            if (hi != lo)
                return false;
            x = (x - hi*mask) / 10;
            mask /= 100;
        }
        return true;
    }
}