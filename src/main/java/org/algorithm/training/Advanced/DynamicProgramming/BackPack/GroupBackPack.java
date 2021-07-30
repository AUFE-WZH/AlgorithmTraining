package org.algorithm.training.Advanced.DynamicProgramming.BackPack;

import org.testng.annotations.Test;

import java.util.Arrays;

public class GroupBackPack {
    int[][] v; // 每组每个物品价值，从0开始
    int[][] w; // 每组每个物品重量，从0开始
    int n; // 物品组数
    int[] dp; // 动态转移数组
    int W; // 背包总容量
    int[] x; // 最优解数组
    int[] c; // 每组物品的个数


    // 初始化，dp[0]为0
    void Init() {
        n = v.length - 1;
        dp = new int[W + 1];
        x = new int[n + 1];

        Arrays.fill(x, 0);
        dp[0] = 0;
    }

    // 分组背包问题算法，从前向后递推（使用num数组对使用数量加以限制）
    void dpAlg() {
        for (int i = 1; i <= n; i++) {
            for (int j = W; j >= 0; j--) {
                for (int k = 1; k <= c[i]; k++) {   // 枚举组内的各个物品
                    // 逆序遍历重量轴，让重量轴中每一个块都对应第i组某一个物品且仅有一个
                   if (j >= w[i][k]) { // 当容量够时放入
                       dp[j] = Math.max(dp[j], dp[j - w[i][k]] + v[i][k]);
                   }
                }
            }
        }
    }

    // 获取最大值
    int getMaxValue() {
        return dp[W];
    }

    @Test
    void test1() {
        // HDU 1712
        GroupBackPack groupBackPack = new GroupBackPack();
        // i门课，v[i][j]为第i门课j天数的收益
        groupBackPack.v = new int[][] {{0, 0, 0}, {0, 1, 2}, {0, 1, 3}};
        groupBackPack.w = new int[][] {{0, 0, 0}, {0, 1, 2}, {0, 1, 2}};
        // 每门课都是两天
        groupBackPack.c = new int[] {0, 2, 2};
        // 总天数
        groupBackPack.W = 2;

        groupBackPack.Init();
        groupBackPack.dpAlg();
        System.out.println("最大价值：" + groupBackPack.getMaxValue());
    }

    @Test
    void test2() {
        // HDU 1712
        GroupBackPack groupBackPack = new GroupBackPack();
        // i门课，v[i][j]为第i门课j天数的收益
        groupBackPack.v = new int[][] {{0, 0, 0}, {0, 2, 1}, {0, 2, 1}};
        groupBackPack.w = new int[][] {{0, 0, 0}, {0, 1, 2}, {0, 1, 2}};
        // 每门课都是两天
        groupBackPack.c = new int[] {0, 2, 2};
        // 总天数
        groupBackPack.W = 2;

        groupBackPack.Init();
        groupBackPack.dpAlg();
        System.out.println("最大价值：" + groupBackPack.getMaxValue());
    }

    @Test
    void test3() {
        // HDU 1712
        /*
        思路：
            1.每门课程分为一组，每组按天数分为一种
            例如：第1门课： 一天价值3，两天价值2，三天价值1 => {0, 3, 2, 1}
            使用分组背包解法即可
         */

        GroupBackPack groupBackPack = new GroupBackPack();
        // i门课，v[i][j]为第i门课j天数的收益
        groupBackPack.v = new int[][] {{0, 0, 0, 0}, {0, 3, 2, 1}, {0, 3, 2, 1}};
        groupBackPack.w = new int[][] {{0, 0, 0, 0}, {0, 1, 2, 3}, {0, 1, 2, 3}};
        // 每门课都是两天
        groupBackPack.c = new int[] {0, 3, 3};
        // 总天数
        groupBackPack.W = 2;

        groupBackPack.Init();
        groupBackPack.dpAlg();
        System.out.println("最大价值：" + groupBackPack.getMaxValue());
    }
}
