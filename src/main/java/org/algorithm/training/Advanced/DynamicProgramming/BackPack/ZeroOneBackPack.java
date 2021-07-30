package org.algorithm.training.Advanced.DynamicProgramming.BackPack;

import org.testng.annotations.Test;

import java.util.Arrays;

public class ZeroOneBackPack {
    int[] v; // 每个物品价值，从0开始
    int[] w; // 每个物品重量，从0开始
    int n; // 物品个数
    int[][] c; // 动态转移数组
    int W; // 背包总容量
    int[] x; // 最优解数组

    // 初始化，第0行和第0列为0
    void Init() {
        n = v.length - 1;
        c = new int[n + 1][W + 1];
        x = new int[n + 1];

        Arrays.fill(x, 0);

        for (int i = 0; i <= n; i++) {
            c[i][0] = 0;
        }

        for (int j = 0; j <= W; j++) {
            c[0][j] = 0;
        }
    }

    // 朴素dp算法，二维空间
    void commonDpAlg() {
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= W; j++) {
                if (j < w[i]) { // 物品容量i大于背包容量，不放物品i
                    c[i][j] = c[i - 1][j];
                } else {
                    // 放和不放取最大值
                    c[i][j] = Math.max(c[i - 1][j], c[i - 1][j - w[i]] + v[i]);
                }
            }
        }
    }

    // 获取最大值
    int getMaxValue() {
        return c[n][W];
    }

    // 逆向构造最优解
    void getSelectArray() {
        int tmp = W;
        for (int i = n; i > 0; i--) {
            if (c[i][tmp] > c[i - 1][tmp]) {
                x[i] = 1;
                tmp -= w[i];
            } else {
                x[i] = 0;
            }
        }
    }

    @Test
    void test1() {
        ZeroOneBackPack zeroOneBackPack = new ZeroOneBackPack();
        zeroOneBackPack.v = new int[]{0, 6, 3, 5, 4, 6};
        zeroOneBackPack.w = new int[]{0, 2, 5, 4, 2, 3};
        zeroOneBackPack.W = 10;

        zeroOneBackPack.Init();
        zeroOneBackPack.commonDpAlg();
        System.out.println("最大价值：" + zeroOneBackPack.getMaxValue());
        zeroOneBackPack.getSelectArray();
        for (int i = 0; i < zeroOneBackPack.x.length; i++) {
            System.out.println("物品" + i + ": " + (zeroOneBackPack.x[i] == 1 ? "选择" : "未选择"));
        }
    }

    int[] dp; // 优化的一维dp数组

    // 初始化，dp[0]为0
    void InitOptimal() {
        n = v.length - 1;
        dp = new int[W + 1];
        x = new int[n + 1];

        Arrays.fill(x, 0);

        dp[0] = 0;
    }

    // 优化后01背包问题算法，从后向前递推
    void optimalDpAlg() {
        for (int i = 1; i <= n; i++) {
            for (int j = W; j >= w[i]; j--) {
                dp[j] = Math.max(dp[j], dp[j - w[i]] + v[i]);
            }
        }
    }

    // 获取最大值
    int getMaxValueOptimal() {
        return dp[W];
    }

    @Test
    void test2() {
        ZeroOneBackPack zeroOneBackPack = new ZeroOneBackPack();
        zeroOneBackPack.v = new int[]{0, 6, 3, 5, 4, 6};
        zeroOneBackPack.w = new int[]{0, 2, 5, 4, 2, 3};
        zeroOneBackPack.W = 10;

        zeroOneBackPack.InitOptimal();
        zeroOneBackPack.optimalDpAlg();
        System.out.println("最大价值：" + zeroOneBackPack.getMaxValueOptimal());
    }
}
