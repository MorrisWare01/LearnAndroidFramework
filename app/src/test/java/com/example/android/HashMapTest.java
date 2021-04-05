package com.example.android;

import org.junit.Test;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

/**
 * @author mmw
 * @date 2020/5/8
 **/
public class HashMapTest {

    static final int MAXIMUM_CAPACITY = 1 << 30;

    @Test
    public void test() {
        System.out.println(tableSizeFor(1));
        System.out.println(tableSizeFor((int) Math.pow(2, 10) - 1));
        System.out.println(tableSizeFor((int) Math.pow(2, 10)));
        System.out.println(tableSizeFor((int) Math.pow(2, 10) + 1));
        System.out.println(tableSizeFor(11231));
        System.out.println(tableSizeFor(1123213));
        System.out.println(tableSizeFor(112313131));
    }

    @Test
    public void testHash() {
        System.out.println(hash(1) & 3);
        System.out.println(hash(5) & 3);
        System.out.println(hash(65) & 3);
    }

    @Test
    public void test1() {
        final HashMap<Integer, String> map = new HashMap<>((int) Math.pow(2, 2));

        new Thread() {
            @Override
            public void run() {
                super.run();
                map.put(1, "1");
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                super.run();
                map.put(5, "5");
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                super.run();
                map.put(6, "6");
            }
        }.start();

        if (Thread.activeCount() > 0) {
            Thread.yield();
        }
        System.out.println(map.get(1) + "---" + map.get(5) + "---" + map.get(6));
    }

    @Test
    public void test2() {
        for (int i = 0; i < 1000; i++) {
            final CountDownLatch latch = new CountDownLatch(4);

            final HashMap<String, String> map = new HashMap<>();
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    map.put("1", "0");
                    latch.countDown();
                }
            }.start();
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    map.put("1", "2");
                    latch.countDown();
                }
            }.start();
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    map.put("1", "3");
                    latch.countDown();
                }
            }.start();
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    map.put("1", "4");
                    latch.countDown();
                }
            }.start();

            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(map.get("1"));
        }
    }

    void printNull(String value) {
        if (value == null) {
            System.out.println("null");
        }
    }

    final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
}
