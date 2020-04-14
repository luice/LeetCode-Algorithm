## 题目

字符串 `"PAYPALISHIRING"` 以指定行数按 Z 字形排列是这样的：（为了看清效果，请使用等宽字体查看）
```
P   A   H   N
A P L S I I G
Y   I   R
```
一行一行地读就有了字符串 `"PAHNAPLSIIGYIR"`

写代码实现这个函数：
```
string convert(string s, int numRows);
```
接受一个字符串，并以给定的行数进行这种转化。

例1：

输入：`s = "PAYPALISHIRING", numRows = 3`

输出：`"PAHNAPLSIIGYIR"`

例2：

输入：`s = "PAYPALISHIRING", numRows = 4`

输出：`"PINALSIGYAHRPI"`

说明：
```
P     I     N
A   L S   I G
Y A   H R
P     I
```

## 解题思路

简单题。个人认为是考查变长字符串的运用。

`StringBuilder` 是可变字符串，用 `StringBuilder[]` 可实现往不同的行尾加入字符。

遍历字符串，每个字符加入到某个 `StringBuilder` 中，然后决定下一个字符要插入的是哪一行？

可设两个变量，一个 `b` 代表 `StringBuilder[]` 的下标，一个 `sign` 其值为 `1` 或 `-1`。

起始时 `b = 0`, `sign = 1`。

往 `StringBuilder[b]` 中加入字符之后，`b += sign`。

然后判断新的 `b` 是否合法，如果不合法，即小于 `0` 或 大于等于 `numRows`，说明已经到了边界，该“转向”了，

把 `b` 改为合适的值，比如 `1` 或 `numRows - 2`。把 `sign` 也做相应的调整。

**注意** `numRows==1` 要单独判断，单独处理。

## 运行结果
30ms，超过 83.58% 的 Java 提交。
