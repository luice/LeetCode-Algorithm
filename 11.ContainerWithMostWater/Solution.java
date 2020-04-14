class Solution {
    public int maxArea(int[] height) {
        int max = 0;
        for (int i = 0, j = height.length - 1; i < j; ) {
            int t = (j - i) * (height[i] < height[j] ? height[i] : height[j]);
            if (max < t)
                max = t;
            if (height[i] < height[j])
                ++i;
            else
                --j;
        }
        return max;
    }

    public static void main(String[] argv) {
        int[] h = {1,8,6,2,5,4,8,3,7};
        Solution s = new Solution();
        System.out.println(s.maxArea(h));
    }
}

