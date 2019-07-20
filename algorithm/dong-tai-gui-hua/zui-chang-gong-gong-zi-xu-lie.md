# 最长公共子序列

## 问题描述

1. 子序列：某个序列去除任意个元素后产生的序列为该序列的子序列。
2. 公共子序列：假设存在序列A和序列B，那么序列C若想称为A和B的公共子序列，当且仅当C同时是A和B的子序列。
3. 最长公共子序列：字面意思吧。

## 解题思路

### 1. 暴力搜

1. 枚举序列A的所有子序列。
2. 检查每个A的子序列是否为B的子序列。
3. 找出符合条件最长的子序列。

### 2. 动态规划

假设存在序列X，Y如下：

$$X = <x_1,x_2,...,x_m>, Y = <y_1,y_2,...,y_n>$$

序列$$Z = <z_1,z_2,...,z_k>$$为X、Y的任一公共子序列。那么有：

1. 若$$x_m = y_n$$，则$$z_k = x_m = y_n$$，且$$Z_{k-1}$$是$$X_{m-1}$$和$$Y_{n-1}$$的一个公共子序列。
2. 若$$x_m ≠ y_n$$，且$$z_k ≠ x_m$$，则$$Z$$是$$X_{m-1}$$和$$Y$$的一个公共子序列。
3. 若$$x_m ≠ y_n$$，且$$z_k ≠ y_n$$，则$$Z$$是$$X$$和$$Y_{n-1}$$的一个公共子序列。

综上可知，X、Y两个序列的最长公共子序列问题包含了该两个序列的前缀序列的最长公共子序列问题。

假设$$C[i,j]$$表示$$X_i$$和$$Y_j$$的最长公共子序列长度，若$$i=0或j=0$$，即其中一个序列长度为0时，ga公共子序列长度为0。根据上面总结的三条公共子序列性质可得如下公式：


$$
C[i,j]=
\begin{cases}
0, & & 当 i = 0 或 j = 0 \\

C[i-1,j-1]+1,& & 当 i,j>0且x_i = y_j \\

max(C[i-1,j],C[i,j-1]),& & 当i,j>0且xi \ne yj
\end{cases}
$$


## 解题代码

**伪代码1，求最长公共子序列长度，递归**

```py
LCS(x,y,i,j)
if x[i] = y[j]:
    then c[i,j] = LCS(x,y,i-1,j-1) + 1
    else c[i,j] = max(LCS(x,y,i-1,j),
                      LCS(x,y,i,j-1))
```

**伪代码2，求最长公共子序列长度，非递归**

```py
LCS(x,y)
m = length(x)
n = length(y)
for i in range(1,m)    # 对矩阵C的置零初始化
    do c[i,0] = 0
for j in range(1,n)
    do c[j,0] = 0
for i in range(1,m)
    do for j in range(1,n)
        do if x[i] = y[j]
            then c[i,j] = c[i-1,j-1] + 1
                 b[i,j] = "↖"    # 这里的b可以用在后面重组最长公共子序列
            else if c[i-1,j] >= c[i,j-1]
                then c[i,j] = c[i-1,j]
                     b[i,j] = "↑"
                else c[i,j] = c[i,j-1]
                     b[i,j] = "←"
 return c and b
```

**伪代码3，重组最长公共子序列**

```py
PRINT-LCS(b,X,i,j)
if i = 0 or j = 0
    then return
if b[i,j] = "↖"
    then PRINT-LCS(b,X,i-1,j-1)
         print x[i]
else if b[i,j] = "↑"
    then PRINT-LCS(b,X,i-1,j)
else PRINT-LCS(b,X,i,j-1)
```

**Python 3实现**

```py
#coding=utf8

def printLCS(b,x,x_len,y_len):
    if x_len == 0 or y_len == 0:
        return ""

    result = ""
    if b[x_len][y_len] == "↖":
        result += printLCS(b,x,x_len-1,y_len-1)
        result += x[x_len-1]
    elif b[x_len][y_len] == "←":
        result += printLCS(b,x,x_len,y_len-1)
    else:
        result += printLCS(b,x,x_len-1,y_len)

    return result


def lcs(x,y):
    x_len = len(x)
    y_len = len(y)
    c = [[0 for j in range(y_len+1)] for i in range(x_len+1)]
    b = [["" for j in range(y_len+1)] for i in range(x_len+1)]

    for i in range(1,x_len+1):
        for j in range(1,y_len+1):
            if x[i-1] == y[j-1]:
                c[i][j] = c[i-1][j-1] + 1
                b[i][j] = "↖"
            elif c[i-1][j] >= c[i][j-1]:
                c[i][j] = c[i-1][j]
                b[i][j] = "↑"
            else:
                c[i][j] = c[i][j-1]
                b[i][j] = "←"

    for i in range(x_len+1):
        line = ""
        for j in range(y_len+1):
            line = line + "%d "%c[i][j]
        print(line)

    print()

    for i in range(x_len+1):
        line = ""
        for j in range(y_len+1):
            line = line + "%s "%b[i][j]
        print(line)

    answer = printLCS(b,x,x_len,y_len)
    return answer


def main():
    print("hh")
    x1 = "ABCBDAB"
    y1 = "BDCABA"
    answer1 = "BCBA"
    x2 = "ACCGGTCGAGTGCGCGGAAGCCGGCCGAA"
    y2 = "GTCGTTCGGAATGCCGTTGCTCTGTAAA"
    answer2 = "GTCGTCGGAAGCCGGCCGAA"
    a1 = lcs(x1,y1)
    a2 = lcs(x2,y2)
    assert(answer1==a1)
    assert(answer2==a2)
    print("answer1: %s"%a1)
    print("answer2: %s"%a2)
if __name__ == '__main__':
    main()
```



