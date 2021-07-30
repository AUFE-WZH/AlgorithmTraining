package org.algorithm.training.Advanced.DynamicProgramming.BackPack;

import org.testng.annotations.Test;

import java.util.Arrays;

public class MultiBackPack {
    int[] v; // 每个物品价值，从0开始
    int[] w; // 每个物品重量，从0开始
    int n; // 物品个数
    int[] dp; // 动态转移数组
    int W; // 背包总容量
    int[] x; // 最优解数组
    int[] c; // 每种物品个数

    int[] num; // 记录当前重量，该种物品使用个数

    // 初始化，dp[0]为0
    void Init() {
        n = v.length - 1;
        dp = new int[W + 1];
        x = new int[n + 1];
        num = new int[W + 1];

        Arrays.fill(x, 0);
        dp[0] = 0;
    }

    // 多重背包问题算法，从前向后递推（使用num数组对使用数量加以限制）
    void dpAlg() {
        for (int i = 1; i <= n; i++) {
            Arrays.fill(num, 0);
            for (int j = w[i]; j <= W; j++) {
                if (dp[j] < dp[j - w[i]] + v[i] && num[j - w[i]] < c[i]) {
                    dp[j] = dp[j - w[i]] + v[i];
                    num[j] = num[j - w[i]] + 1;
                }
            }
        }
    }

    // 获取最大值
    int getMaxValue() {
        return dp[W];
    }

    @Test
    void test() {
        MultiBackPack multiBackPack = new MultiBackPack();
        multiBackPack.v = new int[]{0, 6, 3, 5, 4, 6};
        multiBackPack.w = new int[]{0, 2, 5, 4, 2, 3};
        multiBackPack.c = new int[]{0, 2, 5, 5, 5, 5}; // 第一个物品取2个，第4个物品取3个或第5个物品取2个，最大价值24
        multiBackPack.W = 10;

        multiBackPack.Init();
        multiBackPack.dpAlg();
        System.out.println("最大价值：" + multiBackPack.getMaxValue());
    }
}
