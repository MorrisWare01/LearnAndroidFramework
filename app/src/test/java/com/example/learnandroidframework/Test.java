package com.example.learnandroidframework;

/**
 * @author mmw
 * @date 2020/4/23
 **/
public class Test {

    @org.junit.Test
    public void test() {
        int[] bytes = new int[]{0x10, 0xf5, 0xff, 0xff, 0xff};
        boolean bit37 = (bytes[4] & 0x20) == 0x20;

        int[] result = new int[8];
        result[0] = bytes[0];
        result[1] = bytes[1];
        result[2] = bytes[2];
        result[3] = bytes[3];
        if (bit37) {
            result[4] = bytes[4] & 0x3f | 0xc0;
            result[5] = 0xff;
            result[6] = 0xff;
            result[7] = 0xff;
        } else {

        }

        long data = 0;
        for (int i = 0, len = result.length; i < len; i++) {
            data |= (long) result[i] << (8 * i);
        }
        System.out.println(data);
        System.out.println(Long.toHexString(data));
    }

}
