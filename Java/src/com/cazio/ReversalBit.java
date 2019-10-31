package com.cazio;

/**
 * @program Java
 * @description 将所有的位进行反装
 * @author: ca2io
 * @create: 2019-10-31 22:40
 **/
public class ReversalBit {
    public static void main(String[] args) {
        long x = 31L;
        long result = 0L;
        for(int i=64; i!=0 ;i--){
            result = (result<<1) | (x & 0x1);
            x >>=1;
        }
        System.out.println(result);

    }
}
