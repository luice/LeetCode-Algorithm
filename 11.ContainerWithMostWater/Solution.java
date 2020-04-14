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
}

