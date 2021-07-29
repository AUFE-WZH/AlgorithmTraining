package org.algorithm.training.Advanced.RMQ_LikeAlgorithm;

// 树状数组
public class BinaryIndexedTrees {
    static class OneDimensionalBIT {
        int[] a; // 原始数组（从1开始）
        int[] c; //树状数组（从1开始，不可以从0开始）
        int n; // 数组长度（从1开始）

        // 区间长度（最低位1与其后的0构成的数字）（不可以计算lowbit(0)会死循环）
        int lowbit(int i) {
            return (-i) & i;
        }

        // 前缀和 O(logn)
        int sum(int index) {
            int s = 0;
            for (; index > 0; index -= lowbit(index)) {
                s += c[index];
            }
            return s;
        }

        // 点更新 O(logn)
        void add(int index, int value) {
            for (; index <= n ; index += lowbit(index)) {
                c[index] += value;
            }
        }

        // 区间和（a[left] ... a[right] O(logn)
        int sum(int left, int right) {
            return sum(right) - sum(left);
        }

        // 初始化树状数组 O(nlogn)
        void init() {
            n = a.length - 1;
            c = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                add(i, a[i]);
            }
        }
    }

    static class twoDimensionalBIT {
        int[][] a; // 原始数组（从1开始）
        int[][] c; //树状数组（从1开始，不可以从0开始）
        int row, col; // 数组长度（从1开始）

        // 区间长度（最低位1与其后的0构成的数字）（不可以计算lowbit(0)会死循环）
        int lowbit(int i) {
            return (-i) & i;
        }

        // 前缀和 O((logn) ^ m) 从左上角（1，1）到右下角（xIndex, yIndex）
        int sum(int xIndex, int yIndex) {
            int s = 0;
            for (int i = xIndex; i > 0; i -= lowbit(i)) {
                for (int j = yIndex; j > 0; j -= lowbit(j)) {
                    s += c[i][j];
                }
            }
            return s;
        }

        // 点更新 O((logn) ^ m)
        void add(int xIndex, int yIndex, int value) {
            for (int i = xIndex; i <= row; i += lowbit(i)) {
                for (int j = yIndex; j <= col; j++) {
                    c[i][j] += value;
                }
            }
        }

        // 区间和（从左上角a[left1][right1] 到右下角 a[left2][right2] O((logn) ^ m)
        int sum(int left1, int right1, int left2, int right2) {
            return sum(left1, right1) - sum(left2, right2);
        }

        // 初始化树状数组 O(nlogn)
        void init() {
            row = a.length - 1;
            col = a[0].length - 1;
            c = new int[row + 1][col + 1];
            for (int i = 1; i <= row; i++) {
                for (int j = 1; j <= col; j++) {
                    add(i, j, a[i][j]);
                }
            }
        }
    }

    static void test1() {
        OneDimensionalBIT odbt = new OneDimensionalBIT();
        odbt.a = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}; // 从第1开始，忽略a[0]
        odbt.init();

        for (int i = 1; i <= odbt.n; i++) {
            System.out.print(odbt.c[i] + " ");
        }
        System.out.println();
    }

    static void test2() {
        twoDimensionalBIT tdbt = new twoDimensionalBIT();
        tdbt.a = new int[][] {{0, 0, 0, 0}, {0, 1, 2, 3}, {0, 1, 2, 3}, {0, 1, 2, 3}}; // 从(1,1)开始，忽略0列和0行
        tdbt.init();

        for (int i = 1; i <= tdbt.row; i++) {
            for (int j = 1; j <= tdbt.col; j++) {
                System.out.print(tdbt.c[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
//        test1();
        test2();
    }
}

