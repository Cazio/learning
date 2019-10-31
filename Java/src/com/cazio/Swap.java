package com.cazio;

/**
 * @program Java
 * @description 基于异或的交换
 * @author: ca2io
 * @create: 2019-10-31 22:32
 **/
public class Swap {
    public static void main(String[] args) {
        int a = 1;
        int b = 2;
        swap(a,b);
    }

    private static void swap(int a, int b) {
        a = a ^ b;
        b = a ^ b;
        a = a ^ b;
    }

}
