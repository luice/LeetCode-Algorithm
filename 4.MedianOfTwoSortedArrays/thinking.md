## 题目

有两个有序的数组 nums1 和 nums2，大小分别为 m 和 n.

找出这两个数组合在一起后的中位数。总的时间复杂度应为 O(log (m+n)).

可以假设 nums1 和 nums2 不同时为空。

例1：
```
nums1 = [1, 3]
nums2 = [2]
```
中位数是 `2.0`

例2：
```
nums1 = [1, 2]
nums2 = [3, 4]
```
中位数是 `(2 + 3)/2 = 2.5`

## 解题思路

由于对时间复杂度作了要求，所以不能直接把两个数组归并然后求中位数，因为归并的时间复杂度是 O(m+n).

以下思路参考了[这个博客](https://hk029.gitbooks.io/leetbook/%E5%88%86%E6%B2%BB/004.%20Median%20of%20Two%20Sorted%20Arrays[H]/004.%20Median%20of%20Two%20Sorted%20Arrays[H].html)。非我原创，感谢作者。

此法可以解决，寻找两个有序数组中的 Top K 元素，这个问题。

先明确一个概念：“割”

定义一个狭义的割，只能落在一个数组中某两数之间。

比如：`a1 a2 a3 | a4 a5 a6`，在 `a3` 和 `a4` 之间割了一刀，左边的属于左边，右边的属于右边。

对于两个数组，K = 5，则可能有这样的割：
```
a1 a2 | a3 a4 a5 a6 a7 a8 a9
0  1     2  3  4  5  6  7  8
   ^  ^  ^
   L1 C1 R2

b1 b2 b3 | b4 b5 b6 b7 b8 b9
0  1  2     3  4  5  6  7  8
      ^  ^  ^
      L2 C2 R2
```
割的位置，叫作 `C1 C2`，割左边的数叫 `L1 L2`，割右边的数叫 `R1 R2`.

考虑：要保证 `C1 + C2 == K`，在此前提下，
如果 `L1 > R2`，则 `C1` 要往左挪，`C2` 要往右挪；即，让 `L1 R1` 变小点，`L2 R2` 变大点。
如果 `L2 > R1`，则 `C1` 要往右挪，`C2` 要往右挪；即，让 `L1 R1` 变大点，`L2 R2` 变小点。
最后，满足的是：`L1 <= R2, L2 <= R1`，而 `C1 + C2 == K`，所以第 K 大的数就是 `max{L1, L2}`。

那么怎么寻找中位数呢？
如果总数 N 是偶数，则 `K = N/2`，中位数是割两边的数的平均数。
如果 N 是奇数，则只是中间的一个数。
这时可以假设，这个数后面有一个虚拟的数，与之相等，则找到一个割，中位数是割左右两边的数的平均数。

一种作法是，扩展割的定义，割即可以落在两数中间，意义同上文；
割也可以落在一个数上，这时，割所在的数既属于左边也属于右边。
这种设计编码起来有些复杂，不容易表示。

在此之上，还有一种设计：
在数组的开头结尾以及每两个数之间虚拟地插入一个符号 \*；同时，要求割只能割在 \* 或者数字上。
例如：
```
A = [1 4 7 9]  ==>  [* 1 * 4 * 7 * 9 *]
B = [2 3 5]    ==>  [* 2 * 3 * 5 *]
```
这样操作带来很多好处：

1. 处理过的数组，长度都变为奇数了；

2. 进一步，两个数组长度之和为偶数，总的中位数一定是两个数的平均数；

3. 用 C 表示割的位置（加上虚拟符号之后的下标），L、R 表示原始数组中割左右两边的数的下标，则

```
L = (C-1) / 2
R = C / 2
```
这样表示之后，不管割是落在 \* 还是数字上，都符合我们的需求：
比如，A 割在 `4 * 7` 间的 * 上，则 `C1 = 4, L1 = (4-1)/2 = 1, R1 = 4/2 = 2`，恰为原数组中 4,7 的位置；
B 割在 3 上，则 `C2 = 3， L2 = (3-1)/2 = 1, R2 = 3/2 = 1`，都恰为原数组中 3 的位置，
直接自动实现了，割在两数之间，则划分左边和右边；割在数字上，即属于左边又属于右边，这一要求。

4. 这样实现之后，若以 L 表示割左边的数，R 表示割右边的数，则最后所求的中位数就是：
在 `K = m + n` 时，`max(L1, L2)` 与 `min(R1, R2)` 的平均数。

**注意**这里的 K 一定是 m+n 而不是 m+n+1，我想不到一个很好的解释。参考的文章中对这一块根本没有解释。

我能想到的一个比较牵强的解释：
考虑单一扩展后的数组，长度 N，割点的位置就是 N，此时其左边是 N 个元素，即 K 值为 N。
考虑两个扩展后的数组，由于割点位置既不计入左边又不计入右边，
所以，两个左边总个数应该是 m+n，两个右边总个数也是 `m+n`，这才是中位数。所以 `C1+C2` 应该等于 `m+n`。

此时，还要想的一个问题，是越界的问题。如果割在扩展后的数组的开始或结束位置的 \* 处，要怎么办？
比较懒的做法是，
割在最左边时，直接给 L 值赋为无穷小，即 `Integer.MIN_VALUE`，对 L 取最大值时可以保证正确；
割在最右边时，直接给 R 值赋为无穷大，即 `Integer.MAX_VALUE`，对 R 取最小值时可以保证正确。

上文说了，（下标）当 `L1 > R2` 时，要把 C1 往小了调；当 `L2 > R1` 时，要把 C1 往大了调。
那么，是调整 C1 还是 C2 呢？由于 C1 与 C2 之和是常数 K，所以只需要调整其中一个，另一个就可以算出来。
每次调整多少呢？用二分查找再好不过。所以结论是：哪个数组短，就用二分调整这个数组中的 C 值。

需要注意，其中一个数组可能为空，这时要单独处理。

## 代码

[Solution.java](/4.MedianOfTwoSortedArrays/Solution.java)

## 运行结果
39ms，超过 56.32% 的 Java 提交。
