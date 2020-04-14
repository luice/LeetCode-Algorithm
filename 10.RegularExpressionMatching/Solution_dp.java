class Solution {
    public boolean isMatch(String s, String p) {
        s = "^" + s;
        p = "^" + p;
        int lenS = s.length();
        int lenP = p.length();
        boolean[][] dp = new boolean[lenS + 1][lenP + 1];
        dp[0][0] = true;
        for (int i = 1; i <= lenS; i++)
            dp[i][0] = false;
        for (int i = 1; i <= lenP; i++)
            dp[0][i] = false;
        for (int i = 1; i <= lenS; i++) {   // traverse S
            for (int j = 1; j <= lenP; j++) {   // traverse P
                if (s.charAt(i-1) == p.charAt(j-1) || p.charAt(j-1) == '.')
                    dp[i][j] = dp[i-1][j-1];    /* Æ¥Åä */
                else if (p.charAt(j-1) == '*') {
                    if (s.charAt(i-1) != p.charAt(j-2) && p.charAt(j-2) != '.')
                        dp[i][j] = dp[i][j-2];  /* '*' Æ¥Åä 0 ´Î */
                    else
                        dp[i][j] = dp[i][j-2]   /* '*' Æ¥Åä 0 ´Î */
                                || dp[i][j-1]   /* '*' Æ¥Åä 1 ´Î */
                                || dp[i-1][j];  /* '*' Æ¥Åä ¶à ´Î */
                }
            }
        }
        return dp[lenS][lenP];
    }
}
