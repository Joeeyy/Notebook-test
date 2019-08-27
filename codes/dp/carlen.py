#coding=utf8

#cartlen

import sys
import time

def solve2(n):
    answer = 0

    if n == 2:
        return 1
    if n == 4:
        return 2
    
    for i in range(1):
        for j in range(i+1,n):
            a1 = a2 = 0
            if (j - i) % 2 == 0 and j != 1 and j != n-1:
                continue
            len1 = j - i - 1
            len2 = n - len1 - 2
            if len1 > 0:
                a1 = solve(len1)
            else:
                a1 = 1
            if len2 > 0:
                a2 = solve(len2)
            else:
                a2 = 1
            answer += a1 * a2

    return answer

def solve(n):
    answer = 0

    if n == 2:
        return 1
    if n == 4:
        return 2

    dp = [0 for i in range(n+1)]

    dp[0] = 1
    dp[2] = 1
    dp[4] = 2

    for j in range(6,n+1):
        if j % 2 != 0:
            continue
        for i in range(j):
            if i % 2 != 0:
                continue
            leftLen = i
            rightLen = j-leftLen-2
            #print(leftLen,rightLen,j)
            dp[j] += dp[leftLen] * dp[rightLen]

    #print(dp)

    return dp[-1]

def main():
    n = 8
    n = int(sys.stdin.readline().strip())
    t1 = time.clock()
    print(solve(n))
    t2 = time.clock()
    print("Time used: ",t2-t1)
    print(solve2(n))
    t3 = time.clock()
    print("Time used: ",t3-t2)

if __name__ == '__main__':
    main()