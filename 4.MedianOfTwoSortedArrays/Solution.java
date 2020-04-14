class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if (nums1.length == 0)
            return findMedianSortedArray(nums2);
        if (nums2.length == 0)
            return findMedianSortedArray(nums1);
        int[] longer, shorter;              // 定义较长、较短数组引用
        if (nums1.length > nums2.length) {
            longer = nums1;
            shorter = nums2;
        } else {
            longer = nums2;
            shorter = nums1;
        }
        final int k = longer.length + shorter.length+1;
        int lo = 0, hi = 2 * shorter.length + 1, mid;       // 用于二分查找 [lo,hi)
        int cShort, cLong, lShort, lLong, rShort, rLong;
        while (true) {
            mid = (lo + hi) / 2;
            cShort = mid;                   // 割点
            cLong = k - cShort;             // 割点
            // 防越界处理
            lShort = cShort == 0 ? Integer.MIN_VALUE : shorter[(cShort-1)/2];
            rShort = cShort == 2 * shorter.length ? Integer.MAX_VALUE : shorter[cShort/2];
            lLong = cLong == 0 ? Integer.MIN_VALUE : longer[(cLong-1)/2];
            rLong = cLong == 2 * longer.length ? Integer.MAX_VALUE : longer[cLong/2];
            if (lShort > rLong)
                hi = mid;                   // cShort 太大了，要往小调
            else if (lLong > rShort)
                lo = mid + 1;               // cShort 太小了，要往大调
            else
                break;
        }
        return (max(lShort, lLong) + min(rShort, rLong)) / 2.0;
    }

    private double findMedianSortedArray(int[] nums) {
        return (nums[nums.length/2] + nums[(nums.length-1)/2]) / 2.0;
    }

    private int max(int a, int b) {
        return a > b ? a : b;
    }

    private int min(int a, int b) {
        return a < b ? a : b;
    }
}