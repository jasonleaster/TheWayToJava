package com.jasonleaster.example.jvm.oom;

import java.lang.reflect.Field;
import sun.misc.Unsafe;

/**
 * Author: jasonleaster
 * Date  : 2017/7/12
 * Email : jasonleaster@gmail.com
 * Description:
 */
public class DirectMemoryOOM {

    public static final int _1MB = 1024 * 1024;

    public static void main(String[] args) throws Exception {

        Field unsafeFiled = Unsafe.class.getDeclaredFields()[0];
        unsafeFiled.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeFiled.get(null);

        while (true) {
            unsafe.allocateMemory(_1MB);
        }
    }
}
