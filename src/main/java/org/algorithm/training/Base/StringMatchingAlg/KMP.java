package org.algorithm.training.Base.StringMatchingAlg;

import org.algorithm.training.Advanced.DynamicProgramming.DP.IntervalDP;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class KMP {
    int[] next; // 从模式串t的第0个位置开始，next 从0开始

    void getNext(String t) {
        int tLen = t.length();
        next = new int[tLen + 1];
        int j = 0;  // t串第j个位置
        int k = -1; // 从t串的[0,...,j-1]的前缀和后缀匹配串长度为K
        next[0] = -1;   // t串第0个位置之前的前缀与后缀匹配长度不存在为-1
        while (j < tLen) {
            // 当前匹配长度为-1或当前j位置字符与k位置字符相同，则匹配长度+1
            if (k == -1 || t.charAt(j) == t.charAt(k)) {
                next[++j] = ++k;
            } else {
                // 当前串匹配长度不为-1且两个位置字符不同，则匹配长度为前缀[0,...,k-1]的字串匹配长度
                k = next[k];
            }
        }
    }

    void getNextOptimal(String t) {
        int tLen = t.length();
        next = new int[tLen + 1];
        int j = 0;  // T串第j个位置
        int k = -1; // 从T串的[0,...,j-1]的前缀和后缀匹配串长度为K
        next[0] = -1;   // T串第0个位置之前的前缀与后缀匹配长度不存在为-1
        while (j < tLen) {
            // 当前匹配长度为-1或当前j位置字符与k位置字符相同，则匹配长度+1
            if (k == -1 || t.charAt(j) == t.charAt(k)) {
                j++;
                k++;
                // j后移一位，长度同时增加一个，如果相等则匹配长度为前缀[0,...,k-1]的字串匹配长度，不相等则赋值
                if (j < tLen && t.charAt(j) == t.charAt(k)) {
                    next[j] = next[k];
                } else {
                    next[j] = k;
                }
            } else {
                // 当前串匹配长度不为-1且两个位置字符不同，则匹配长度为前缀[0,...,k-1]的字串匹配长度
                k = next[k];
            }
        }
    }

    // s为正文串，t为模式串，pos为起始的匹配位置（从0开始），返回值从
    int getFirstMatch(String s, String t, int pos) {
        int i = pos, j = 0;
        int sLen = s.length();
        int tLen = t.length();
        while (i < sLen && j < tLen) {
            if (j == -1 || s.charAt(i) == t.charAt(j)) {    // 如果相等继续向后比较
                i++;
                j++;
            } else {
                j = next[j];    // 不相等则回退
            }
        }

        if (j >= tLen) {    // 匹配成功
            return i - tLen;
        } else {    // 匹配失败，没有相等串
            return -1;
        }
    }

    // 获取所有重叠的匹配位置
    List<Integer> getAllOverLapPosition(String s, String t) {
        List<Integer> list = new ArrayList<>();
        int result = 0;
        int position = 0;
        while (result != -1 && position < s.length()) {
            result = getFirstMatch(s, t, position);
            if (result != -1) {
                list.add(result);
                position = result + 1;
            }
        }

        return list;
    }

    // 获取所有不重叠的匹配位置
    List<Integer> getAllUnOverLapPosition(String s, String t) {
        List<Integer> list = new ArrayList<>();
        int result = 0;
        int position = 0;
        while (result != -1 && position < s.length()) {
            result = getFirstMatch(s, t, position);
            if (result != -1) {
                list.add(result);
                position = result + t.length();
            }
        }

        return list;
    }

    @Test
    void test1() {
        String S = "abaabaabeca";   // 主串，正文串
        String T = "abaabe";   // 字串，模式串
        getNext(T);
        for (int i = 1; i < next.length; i++) {
            System.out.println("位置" + i + "， 前缀长度 = " + next[i]);
        }
        System.out.println(getFirstMatch(S, T, 0)); // s串第3个位置a处，从0开始
        getNextOptimal(T);
        System.out.println(getFirstMatch(S, T, 0)); // s串第3个位置a处，从0开始

        S = "ABABABABC";
        T = "ABA";
        getNext(T);
        List<Integer> allOverLapPosition = getAllOverLapPosition(S, T);
        for (Integer position :allOverLapPosition){
            System.out.println("位置 = " + position);
        }
        System.out.println();

        getNext(T);
        List<Integer> allUnOverLapPosition = getAllUnOverLapPosition(S, T);
        for (Integer position :allUnOverLapPosition){
            System.out.println("位置 = " + position);
        }
    }

}
