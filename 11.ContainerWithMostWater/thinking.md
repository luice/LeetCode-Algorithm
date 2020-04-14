## 题目

给你 `n` 个非负整数 `a1, a2, ..., an`，每个数代表坐标中的一个点 `(i, ai)` 。在坐标内画 `n` 条垂直线，垂直线 `i` 的两个端点分别为 `(i, ai)` 和 `(i, 0)`。找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。

#### 说明

你不能倾斜容器，且 `n` 的值至少为 `2`。

![](https://aliyun-lc-upload.oss-cn-hangzhou.aliyuncs.com/aliyun-lc-upload/uploads/2018/07/25/question_11.jpg)

图中垂直线代表输入数组 `[1,8,6,2,5,4,8,3,7]`。在此情况下，容器能够容纳水（表示为蓝色部分）的最大值为 `49`。

#### 示例

> **输入**：`[1,8,6,2,5,4,8,3,7]`\
> **输出**：`49`

## 思路

暴力方法需要遍历所有可能的组合，![](http://latex.codecogs.com/gif.latex?O\left(n^2\right)) 时间复杂度，不太能接受。

仔细分析一下这个题，其实有 ![](http://latex.codecogs.com/gif.latex?O\left(n\right)) 时间复杂度的做法。

先说方法，再分析。
方法就是，用两个指针，`i` 从 `0` 开始，`j` 从 `n-1` 开始，判断 `A[i]` 与 `A[j]` 围成的面积，更新最大值。
然后，`A[i]` 与 `A[j]` 谁小，就把哪个指针往中间移一位，然后再比较，以此类推，直到两个指针相遇。

这个方法乍一年可能是错的，但是仔细分析一下，这个方法其实很巧妙。如图：

![](/1.TwoSum/image.png)

假设阴影部分就是最终结果，则所有可能的情况都在图中列出来了。下文用 `(a3, a4)` 表示阴影部分的面积，其他表示含义类似。

1. 如果 `a1 > a4`，那么 `(a1, a4)` 一定比 `(a3, a4)` 大，一来与假设不符，二来，算法如果能够找到 `(a1, a4)` 这个组合，那么下一步肯定是右指针向左移，不会到 `(a3, a4)` 的。

2. 如果 `a2 < a4`，那么 `(a2, a4)` 与 `(a3, a4)` 谁大谁小就不知道，如果先到 `(a2, a4)`，则按照算法，下一步肯定是左指针右移，则 `(a3, a4)` 可能参与比较。

3. 如果 `a5 > a4`，那么 `(a3, a5)` 一定大于 `(a3, a4)`，与假设不符。

4. 如果 `a6 < a4`，那么与情况 2 一样，需要参与比较，如果先前比较过 `(a3, a6)`，则下一步一定是右指针左移，`(a3, a4)` 就有可能参与比较。

下面需要分析，以上四种情况，用此本算法一定能够找到真正的最大值。事实上，情况 1 和 情况 3 是一种类型，就是最大值在外面。本算法没有参与比较的组合，其实都符合这种情况，比它大的值已经在外面比较过了。

假设输入就是上图，则参与判断的容器依次是：`(a1, a6), (a1, a5), (a2, a5), (a3, a5), (a3, a4)`，6 条边一共有 15 种组合，我们可以逐个讨论没有参与比较的容器：

> `(a1, a2)`，它小于 `(a1, a5)`\
> `(a1, a3)`，它小于 `(a1, a5)`\
> `(a1, a4)`，它小于 `(a1, a5)`\
> `(a2, a3)`，它小于 `(a2, a5)`\
> `(a2, a4)`，它小于 `(a2, a5)`\
> `(a2, a6)`，它小于 `(a1, a6)`\
> `(a3, a6)`，它小于 `(a1, a6)`\
> `(a4, a5)`，它小于 `(a3, a5)`\
> `(a4, a6)`，它小于 `(a1, a6)`\
> `(a5, a6)`，它小于 `(a1, a6)`

通过这些比较，很显然，对于没有参与最大值比较的组合来说，一定存在一个更大的组合包含它，且其中一个端点是重合的。

用形式化语言来说：

![](http://latex.codecogs.com/gif.latex?%5Cinline%20%5Cforall%28i%3Cj%29%5Cexists%28k%29%5Crightarrow%28k%5Cleqslant%20i%5Cwedge%20A%5Bk%5D%5Cgeqslant%20a%5Bi%5D%5Cvee%20k%5Cgeqslant%20j%5Cwedge%20A%5Bk%5D%5Cgeqslant%20A%5Bj%5D%29)

只需要证明这一点，算法就可以严格证明。

首先，算法的左右指针从数组的两个端点向中间移动，直到相遇为止，那么可以确定，数组中的每个元素都有至少一次作为端点参与比较。

对于没有参与比较的组合 `(A[i], A[j])`，不妨设 `i < j && A[i] < A[j]`，则在参与比较的组合中，必然存在至少一个组合，满足以下两个条件之一：

> + 以 `i` 为左端点；
> + 以 `j` 为右端点；

这个结论很容易证明。如果所有以 `i` 为端点的组合都是以 `i` 为右端点，那么在算法执行过程中，右指针从数组右端向左移动到 `i` 的过程中，一定会经过 `j`，此时 `j` 是某个组合的右端点。同理，如果以 `j` 为端点的组合都是以 `j` 为左端点，那么左指针从数组左端向右移动过程中，一定会比较一个以 `i` 为左端点的组合。

然后，在所有参与比较的组合中，一定存在至少一个组合，满足以下两个条件之一：

> + 以 `i` 为左端点，记其右端点为 `r`，有 `r > j && A[i] <= A[r]`；
> + 以 `j` 为右端点，记其左端点为 `l`，有 `l < i && A[l] >= A[i]`；

证明如下：

考虑以 `i` 为左端点的参与比较的组合，记其右端点为 `r`。如果其中存在 `r > j`，则算法执行过程中，左指针指向 `i` 时，右指针在不停地左移，要么，右指针与 `j` 相遇（与大前提“`(i, j)`不参与比较”矛盾），要么，遇到一个 `A[i] <= A[r]`，然后左指针右移，而此时，该组合满足上述条件一。

考虑以 `j` 为右端点的参与比较的组合，记其左端点为 `l`。如果其中存在 `l < i`，则算法执行过程中，右指针指向 `j` 时，左指针在不停地右移，要么，左指针与 `i` 相遇（与大前提“`(i, j)`不参与比较”矛盾），要么，遇到一个 `A[l] >= A[j]`，然后右指针左移，而此时，该组合满足上述条件二。

最后还有一个命题需要证明，所有参与比较的组合中，一定存在至少一个组合，满足以下两个条件之一：

> + 以 `i` 为左端点，以 `r` 为右端点，且 `r > j`；
> + 以 `j` 为右端点，以 `l` 为左端点，且 `l < i`；

证明很简单。如果所有参与比较的以 `i` 为左端点的组合中，右端点 `r` 都满足 `r < j`，则在算法执行时，左指针移动到 `i` 之前，右指针要经过 `j`，即存在一个以 `j` 为右端点的、左端点 `l` 小于 `i` 的组合参与比较。同理，如果所有参与比较的以 `j` 为右端点的组合中，左端点 `l` 都满足 `l > i`，则必然存在一个以 `i` 为左端点的、右端点 `r` 大于 `j` 的组合参与比较。

这样，我们就证明了这个算法。对于所能的组合，它要么在算法中参与比较，要么，会被一个与它有一个端点重合的更大的组合所包含。

算法的时间复杂度是 ![](http://latex.codecogs.com/gif.latex?O\left(n\right))。

## 代码

[Solution.java](/11.ContainerWithMostWater/Solution.java)

## 执行结果

用时：2 ms，超过 99.33% 的 Java 提交。