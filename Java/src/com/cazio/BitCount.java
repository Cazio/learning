package com.cazio;

/**
 * @program Java
 * @description 统计位为1的奇偶性
 * @author: ca2io
 * @create: 2019-10-31 22:34
 **/
public class BitCount {
    public static void main(String[] args) {
        long x = 3L;
        long result = 0;
        while(x != 0){
                result = result ^ x;
                x = x >> 1;
        }
        result = result & 0x1;
        System.out.println(result);
    }

}
