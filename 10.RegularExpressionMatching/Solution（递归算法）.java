class Solution {
    private boolean match(String s, String p, int sIndex, int pIndex) {
        int sLength = s.length();
        int pLength = p.length();
        int sPartLength = sLength - sIndex;
        int pPartLength = pLength - pIndex;
        if (pPartLength == 0)
            return sPartLength == 0;
        if (pPartLength == 1)
            return sPartLength == 1 &&
                    (s.charAt(sIndex) == p.charAt(pIndex) || p.charAt(pIndex) == '.');
        if (p.charAt(pIndex + 1) != '*')
            return (sPartLength != 0 &&
                    (s.charAt(sIndex) == p.charAt(pIndex) || p.charAt(pIndex) == '.') &&
                    match(s, p, sIndex + 1, pIndex + 1));
        // now p[pindex+1] == '*'
        if (sPartLength != 0 &&
                (s.charAt(sIndex) == p.charAt(pIndex) || p.charAt(pIndex) == '.'))
            return match(s, p, sIndex + 1, pIndex) || match(s, p, sIndex, pIndex + 2);
        // s is empty or s[sIndex] not match p[pIndex]
        return match(s, p, sIndex, pIndex + 2);
    }

    public boolean isMatch(String s, String p) {
        return match(s, p, 0, 0);
    }
}