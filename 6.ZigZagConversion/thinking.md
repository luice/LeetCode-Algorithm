## ��Ŀ

�ַ��� `"PAYPALISHIRING"` ��ָ�������� Z ���������������ģ���Ϊ�˿���Ч������ʹ�õȿ�����鿴��
```
P   A   H   N
A P L S I I G
Y   I   R
```
һ��һ�еض��������ַ��� `"PAHNAPLSIIGYIR"`

д����ʵ�����������
```
string convert(string s, int numRows);
```
����һ���ַ��������Ը�����������������ת����

��1��

���룺`s = "PAYPALISHIRING", numRows = 3`

�����`"PAHNAPLSIIGYIR"`

��2��

���룺`s = "PAYPALISHIRING", numRows = 4`

�����`"PINALSIGYAHRPI"`

˵����
```
P     I     N
A   L S   I G
Y A   H R
P     I
```

## ����˼·

���⡣������Ϊ�ǿ���䳤�ַ��������á�

`StringBuilder` �ǿɱ��ַ������� `StringBuilder[]` ��ʵ������ͬ����β�����ַ���

�����ַ�����ÿ���ַ����뵽ĳ�� `StringBuilder` �У�Ȼ�������һ���ַ�Ҫ���������һ�У�

��������������һ�� `b` ���� `StringBuilder[]` ���±꣬һ�� `sign` ��ֵΪ `1` �� `-1`��

��ʼʱ `b = 0`, `sign = 1`��

�� `StringBuilder[b]` �м����ַ�֮��`b += sign`��

Ȼ���ж��µ� `b` �Ƿ�Ϸ���������Ϸ�����С�� `0` �� ���ڵ��� `numRows`��˵���Ѿ����˱߽磬�á�ת���ˣ�

�� `b` ��Ϊ���ʵ�ֵ������ `1` �� `numRows - 2`���� `sign` Ҳ����Ӧ�ĵ�����

**ע��** `numRows==1` Ҫ�����жϣ���������

## ���н��
30ms������ 83.58% �� Java �ύ��
