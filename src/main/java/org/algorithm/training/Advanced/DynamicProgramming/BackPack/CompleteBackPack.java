package org.algorithm.training.Advanced.DynamicProgramming.BackPack;

import org.testng.annotations.Test;

import java.util.Arrays;

public class CompleteBackPack {
    int[] v; // 每个物品价值，从0开始
    int[] w; // 每个物品重量，从0开始
    int n; // 物品个数
    int[] dp; // 动态转移数组
    int W; // 背包总容量
    int[] x; // 最优解数组

    // 初始化，dp[0]为0
    void Init() {
        n = v.length - 1;
        dp = new int[W + 1];
        x = new int[n + 1];

        Arrays.fill(x, 0);

        dp[0] = 0;
    }

    // 完全背包问题算法，从前向后递推（可以放入同一物品不限次数）
    void dpAlg() {
        for (int i = 1; i <= n; i++) {
            for (int j = w[i]; j <= W; j++) {
                dp[j] = Math.max(dp[j], dp[j - w[i]] + v[i]);
            }
        }
    }

    // 获取最大值
    int getMaxValue() {
        return dp[W];
    }

    @Test
    void test() {
        CompleteBackPack completeBackPack = new CompleteBackPack();
        completeBackPack.v = new int[]{0, 6, 3, 5, 4, 6};
        completeBackPack.w = new int[]{0, 2, 5, 4, 2, 3};
        completeBackPack.W = 10;

        completeBackPack.Init();
        completeBackPack.dpAlg();
        System.out.println("最大价值：" + completeBackPack.getMaxValue());
    }
}
