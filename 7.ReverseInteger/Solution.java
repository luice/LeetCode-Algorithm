class Solution {
    public int reverse(int x) {
        StringBuilder builder = new StringBuilder();
        if (x < 0) {
            builder.append("-");
            x = -x;
        }
        while (x != 0) {
            builder.append(x % 10);
            x /= 10;
        }
        int result;
        try {
            result = Integer.parseInt(builder.toString());
        } catch (NumberFormatException exception) {
            return 0;
        }
        return result;
    }
}