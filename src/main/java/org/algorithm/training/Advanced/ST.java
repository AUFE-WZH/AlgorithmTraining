package org.algorithm.training.Advanced;

public class ST {
    int[] a; // 区间值数组（从1开始）
    int n; // 区间大小
    int[][] F; // ST表
    int[] lb; // 计算log(i)值数组

    // 创建ST, O(nlogn)
    void ST_create() {
        n = a.length - 1; // 获取区间大小（由于从1开始）
        int k = (int) Math.log(n); // 计算ST表最大区间为2^k不会超过区间大小
        F = new int[n + 1][k + 1]; // 建立ST表数组
        // 遍历区间（从1 到 n）计算初始f[i][0]
        for (int i = 1; i <= n; i++) {
            F[i][0] = a[i];
        }
        // 计算其他ST表中的数值（先列后行）
        for (int j = 1; j <= k; j++) {
            for (int i = 1; i <= (n - (1 << j) + 1); i++) {
                F[i][j] = Math.max(F[i][j - 1], F[i + (1 << (j - 1))][j - 1]);
            }
        }

        InitLog();
    }

    void InitLog() {
        lb = new int[n];
        lb[0] = -1;
        for (int i = 1; i < n; i++) {
            // 如果当前数与前一个数&计算不为0则log不变，否则加一
            lb[i] = ((i & (i - 1)) != 0) ? lb[i - 1] : (lb[i - 1] + 1);
        }
    }

    // 查询, O(1)
    int ST_query(int l, int r) {
        if (l > r) return 0;
//        int k = (int) Math.log(r - l + 1);
        int k = lb[r - l + 1];
        return Math.max(F[l][k], F[r - (1 << k) + 1][k]);
    }



}
