## 题目

输入一个字符串 `s` 和一个模式字符串 `p`，判断 `s` 是否匹配 `p`。其中，`s` 由小写字母组成，`p` 由小写字母及 `.` 和 `*` 组成。

> `.` 匹配任何单一字符\
> `*` 代表前面的字符出现 0 次或多次

例 1：

输入：`s = "aa", p = "a"`

输出：`false`

例 2：

输入：`s = "aa", p = "a*"`

输出：`true`

例 3：

输入：`s = "ab", p = ".*"`

输出：`true`

说明：`.` 代表任意字符，`*` 出现 0 次或多次，所以 `.*` 实际上可以匹配任意字符串。

例 4：

输入：`s = "aab", p = "c*a*b"`

输出：`true`

说明：这样匹配，`c` 出现 0 次，`a` 出现 2 次，再加一个 `b`。

例 5：

输入：`s = "mississippi", p = "mis*is*p*."`

输出：`false`

说明：`p*` 前面少了个 `i`，否则就可以匹配了。

## 思路

用 Java 的话，如果只是为了过测试点，可以直接调系统提供的正则表达式匹配函数，一行代码就可以：

```
class Solution {
    public boolean isMatch(String s, String p) {
        return s.matches(p);
    }
}
```

但是这显然不是我们练习算法的目的。而且，系统自带的正则表达式引擎过于庞大，测试的执行时间高达 100 多毫秒。

### 方法一：递归求解

从第一个字符开始匹配。如果第一个字符匹配成功，递归判断子串。如果匹配不成功，但是 `p` 的第二个字符是 `*`，则 `p` 往后跳两位，再判断。

需要考虑的细节问题有很多。

先说说最复杂的部分，考虑 `s` 非空且 `p` 的长度大于等于 2 且 `p[1] == '*'` ，则需要处理以下三种情况：

1. `p[0]*` 恰好匹配 `s[0]`。
2. `p[0]*` 匹配了以 `s[0]` 开头的多个字母。
3. `p[0]*` 匹配空串，但是 `p[2]` 匹配 `s[0]`。但是这种情况必须要递归，因为还有可能是 `p[0]*p[2]*p[4]*...p[x]` 匹配了一个 `s[0]`，即 `p` 中前面连续的几个字符都有 `*` 修饰，都匹配空串，然后才开始真正匹配 `s[0]`。

递归时，以上三种情况分别这样处理：

1. 对以 `s[1], p[2]` 开头的子串进行递归。
2. 对以 `s[1], p[0]` 开头的子串进行递归。
3. 对以 `s[0], p[2]` 开关的子串进行递归。

同时，要想清楚，情况 3 比较特殊，因为此时，`s[0]` 可能匹配也可能不匹配 `p[0]`，但都要进行递归。而事实上，有了情况 3，则情况 1 和情况 2 可以合并。试想，`s[0] == p[0] && p[1] == '*'` 时，就是情况 1 或情况 2，此时，只要用 `s+1, p` 直接递归即可，如果是情况 1，则递归后会进入情况 3，即以 `s+1, p+2` 递归；如果是情况 2，则递归后还会是情况 2，即以 `s+2, p` 递归。

程序中，对空串、长度为 1 的串单独判断，其他情况就递归就好了。
若 `p[1] == '*'`，看首位。
如果 `s[0] != p[0]`，则递归一种情况，`s, p+2`。
如果 `s[0] == p[0]`，则递归两种情况，`s+1, p` 和 `s, p+2`，只要其中一个为 `true` 结果就是 `true` 。
若 `p[1] != '*'`，则递归一种情况即可，即 `s+1, p+1`

### 方法二：动态规划

先考虑一般情况，即先不考虑空串的问题。

定义 `dp[i][j]` 表示 `s` 的前 `i` 个字符与 `p` 的前 `j` 个字符是否匹配，布尔类型。

> + 若 `p[j] != '*'`，则须判断 `s[i]` 与 `p[j]` 是否匹配，即 `s[i] == p[j] || p[j] == '.'`，
如果匹配，则 `dp[i][j] = dp[i-1][j-1]`；否则 `dp[i][j] = false`。
这种情况，实际上直接用 `s[i] == p[j] || p[j] == '.'` 就可以表示，因为 `s` 中不会有 `'*'`。
> + 若 `p[j] == '*'`，这种情况要仔细讨论一下。

当 `p[j] == '*'` 时，得看前一字符的匹配情况，有如下可能：

> + `p[j-1]` 匹配 `s[i]` 这一个字符，则 `dp[i][j] = dp[i][j-1]`。
> + `p[j-1]*` 匹配 `s[i]` 及其之前的多个字符，则 `dp[i][j] = dp[i-1][j]`。这个想一下就能明白，相当于到 `p[0..j]` 能匹配 `s[0..i-1]`，类似一种递归。
> + `p[j-1]` 不匹配任何字符，这时 `dp[i][j] = dp[i][j-2]`。相当于压根就没有 `p[j-1]` 和 `'*'`。

具体到编程中，怎么判断是否匹配以及匹配几个呢。当 `p[j] == '*'` 时：

> + `s[i-1] != p[j-1] && p[j-1] != '.'`，即前一个字符不匹配，此时只可能是 `dp[i][j] = dp[i][j-2]`。
> + `s[i-1] == p[j-1] || p[j-1] == '.'`，即前一个字符匹配，此时有前面分析的三种可能，是“**或**”的关系，只要满足其中之一就是 `true`。

再说空串的问题，这个有点头疼。可以采用一个懒办法：往 `s` 和 `p` 之前都加一个相同的字符，这样能保证不用再判断空串，而且结果不会受到影响。这是个很常用的技巧。
并且，为了比较容易地处理初始化问题，可以给 `dp` 多开一行及一列，简单的分析可知，只需要令 `dp[0][0] = true`，第 `[0]` 行及第 `[0]` 列其他元素设为 `false` 即可。
此时，`dp[i][j]` 对应的是 `s[i-1]` 和 `p[j-1]`。

### 方法三：回溯

这个不写了，编程比较复杂，而且不比递归省时间。

## 代码

**递归算法**：[Solution\_rc.java](/10.RegularExpressionMatching/Solution_rc.java)

**动态规划**：[Solution\_dp.java](/10.RegularExpressionMatching/Solution_dp.java)

## 运行结果

递归算法：33ms，超过 44.18% 的 Java 提交。

动态规划：3ms，超过 91.54% 的 Java 提交。
