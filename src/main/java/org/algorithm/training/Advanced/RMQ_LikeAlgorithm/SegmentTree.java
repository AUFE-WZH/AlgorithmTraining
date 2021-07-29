package org.algorithm.training.Advanced.RMQ_LikeAlgorithm;

public class SegmentTree {
    class SegmentTreeImpl {
        public int left;    // 原数组左端点下标
        public int right;   // 原数组右端点下标
        public int maxValue;    // [left, right]区间中最大值
        public int lazyValue;   // [left, right]区间懒标记值
    }

    SegmentTreeImpl[] tree;
    int[] a; // 从1开始

    void Init() {
        int n = a.length - 1;
        tree = new SegmentTreeImpl[3 * n];
        for (int i = 1; i < 3 * n; i++) {
            tree[i] = new SegmentTreeImpl();
            tree[i].lazyValue = -1;
        }
        build(1, 1, n);
    }

    // 创建线段树，index为线段树数组tree[]下标，left和right为tree[index]存储的区间左右边界[left, right]
    void build(int index, int left, int right) {
        tree[index].left = left;
        tree[index].right = right;
        // 叶节点
        if (left == right) {
            tree[index].maxValue = a[left];
            return;
        }

        // 非叶子节点递归构建线段树
        int mid, lc, rc;
        mid = (left + right) / 2; // 划分点
        lc = index * 2;
        rc = index * 2 + 1;
        build(lc, left, mid);
        build(rc, mid + 1, right);
        tree[index].maxValue = Math.max(tree[lc].maxValue, tree[rc].maxValue);
    }

    // 线段树点值更新，将原数组a[i]值更新为value
    void pointUpdate(int index, int i, int value) {
        // 找到叶节点并更新
        if (tree[index].left == tree[index].right && tree[index].left == i) {
            tree[index].maxValue = value;
            return;
        }

        // 非叶节点递归更新
        int mid, lc, rc;
        mid = (tree[index].left + tree[index].right) / 2; // 划分点
        lc = index * 2;
        rc = index * 2 + 1;
        if (i <= mid) {
            pointUpdate(lc, i, value);
        } else {
            pointUpdate(rc, i, value);
        }

        // 递归返回更新父节点最值
        tree[index].maxValue = Math.max(tree[lc].maxValue, tree[rc].maxValue);
    }

    // 线段树区间查询，查询[left, right]区间最值
    int query(int index, int left, int right) {
        if (tree[index].left >= left && tree[index].right <= right) {
            return tree[index].maxValue;
        }

        // 向下传递懒标记
        if (tree[index].lazyValue != -1) {
            pushDown(index);
        }

        int mid, lc, rc;
        mid = (tree[index].left + tree[index].right) / 2; // 划分点
        lc = index * 2;
        rc = index * 2 + 1;
        int max = Integer.MIN_VALUE;
        if (left <= mid) {
            // 到左子树中查询
            max = Math.max(max, query(lc, left, right));
        }
        if (right > mid) {
            // 到右子树中查询
            max = Math.max(max, query(rc, left, right));
        }

        return max;
    }

    // 懒标记：更新最值和懒标记值
    void lazyTag(int index, int value) {
        tree[index].maxValue = value;
        tree[index].lazyValue = value;
    }

    // 向下传递懒标记值
    void pushDown(int index) {
        // 向左右子节点传递懒标记值以及清楚自己的懒标记值
        lazyTag(2 * index, tree[index].lazyValue);
        lazyTag(2 * index + 1, tree[index].lazyValue);
        tree[index].lazyValue = -1;
    }

    // 从线段树index下标开始更新区间[left, right]为value
    void updateInterval(int index, int left, int right, int value) {
        // 找到被完全覆盖区间做懒标记
        if (tree[index].left >= left && tree[index].right <= right) {
            lazyTag(index, value);
            return;
        }

        // 向下传递懒标记
        if (tree[index].lazyValue != -1) {
            pushDown(index);
        }

        // 更新左右子树
        int mid, lc, rc;
        mid = (tree[index].left + tree[index].right) / 2; // 划分点
        lc = index * 2;
        rc = index * 2 + 1;
        if (left <= mid) {
            updateInterval(lc, left, right, value);
        }
        if (right > mid) {
            updateInterval(rc, left, right, value);
        }

        // 返回时更新最大值
        tree[index].maxValue = Math.max(tree[lc].maxValue, tree[rc].maxValue);
    }

    public static void main(String[] args) {
        SegmentTree st = new SegmentTree();
        st.a = new int[]{0, 5, 3, 7, 2, 12, 1, 6, 4, 8, 15}; // 从1开始
        st.Init();
        System.out.println(st.query(1, 1, 10));
        st.pointUpdate(1, 5, 100);
        System.out.println(st.query(1, 1, 10));

        st.updateInterval(1, 6, 8, 20);
        System.out.println(st.query(1, 6, 7));
        System.out.println(st.query(1, 5, 7));
    }
}

