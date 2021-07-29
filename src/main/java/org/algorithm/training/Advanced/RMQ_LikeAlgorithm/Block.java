package org.algorithm.training.Advanced.RMQ_LikeAlgorithm;

import java.util.Scanner;

public class Block {
    long[] a; // 原始数组（从1开始）
    int[] L, R; // 左右端点位置数组，从1开始
    int[] pos; // 标记每个元素属于的分块号
    long[] sum; // 计算每个分块的和
    long[] add; // 懒标记累加值

    void buildBlock() {
        int n = a.length - 1; // 数组从1开始，但是输入时从0开始导致长度增加1

        // 计算分块个数
        int t = (int) Math.sqrt(n);
        int num = n / t;
        if (n % t != 0) num++; //获得num个块

        // 初始化
        L = new int[num + 1];
        R = new int[num + 1];
        pos = new int[n + 1];
        sum = new long[num + 1];
        add = new long[num + 1];

        // 计算每个块左右边界
        for (int i = 1; i <= num; i++) {
            L[i] = (i - 1) * t + 1;
            R[i] = i * t;
        }

        R[num] = n;

        // 标记每个元素属于的分块编号，计算每个分块的和，懒标记赋初值
        for (int i = 1; i <= num; i++) {
            add[i] = 0;
            sum[i] = 0;
            for (int j = L[i]; j <= R[i]; j++) {
                pos[j] = i;
                sum[i] += a[j];
            }
        }
    }

    // 区间更新
    void updateBlock(int l, int r, long d) {
        int p = pos[l], q = pos[r];
        // 左右区间属于同一个块
        if (p == q) {
            // 暴力更新同一个块中所有的值
            for (int i = l; i <= r; i++) {
                a[i] += d;
            }

            // 更新和值
            sum[p] += d * (r - l + 1);
        } else { // 多个区间更新
            // 中间块懒标记
            for (int i = p + 1; i <= q - 1; i++) {
                add[i] += d;
            }

            // 左端暴力修改
            for (int i = l; i <= R[p]; i++) {
                a[i] += d;
            }

            // 右端暴力修改
            for (int i = L[q]; i <= r; i++) {
                a[i] += d;
            }

            // 修改和值
            sum[p] += d * (R[p] - l + 1);
            sum[q] += d * (r - L[q] + 1);
        }
    }

    // 区间查询
    long ackBlock(int l, int r) {
        int p = pos[l], q = pos[r];
        long ans = 0;
        // 同一个块内
        if (p == q) {
            for (int i = l; i <= r; i++) {
                ans += a[i];
            }

            // 累加懒标记
            ans += add[p] * (r - l + 1);
        } else { // 多个区间查询
            // 完整区间计算
            for (int i = p + 1; i <= q - 1; i++) {
                ans += sum[i] + add[i] * (R[i] - L[i] + 1);
            }

            // 左端暴力累加
            for (int i = l; i <= R[p]; i++) {
                ans += a[i];
            }

            // 左端懒标记
            ans += add[p] * (R[p] - l + 1);

            // 右端暴力累加
            for (int i = L[q]; i <= r; i++) {
                ans += a[i];
            }

            // 右端懒标记
            ans += add[q] * (r - L[q] + 1);
        }

        return ans;
    }

    // POJ 3468
    public static void main(String[] args) {
        Block block = new Block();
        int m, n;
        Scanner sc = new Scanner(System.in);
        m = sc.nextInt();
        n = sc.nextInt();

        block.a = new long[m + 1];
        for (int i = 1; i <= m; i++) {
            block.a[i] = sc.nextInt();
        }

        block.buildBlock();
        sc.nextLine();
        String[] str;
        for (int i = 0; i < n; i++) {
            String s = sc.nextLine();
            str = s.split("\\s+");
            if (str[0].equals("Q")) {
                int l = Integer.parseInt(str[1]);
                int r = Integer.parseInt(str[2]);
                System.out.println(block.ackBlock(l, r));
            } else if (str[0].equals("C")) {
                int l = Integer.parseInt(str[1]);
                int r = Integer.parseInt(str[2]);
                long d = Long.parseLong(str[3]);
                block.updateBlock(l, r, d);
            }
        }
    }
}
