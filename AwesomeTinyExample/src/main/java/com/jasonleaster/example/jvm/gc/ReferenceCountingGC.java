package com.jasonleaster.example.jvm.gc;

/**
 * Author: jasonleaster
 * Date  : 2017/7/13
 * Email : jasonleaster@gmail.com
 * Description:
 */
public class ReferenceCountingGC {

    public Object instance = null;

    private static final int _1MB = 1024 * 1024;

    private byte[] bigSize = new byte[2 * _1MB];

    public static void main(String[] arg){
        ReferenceCountingGC objA = new ReferenceCountingGC();
        ReferenceCountingGC objB = new ReferenceCountingGC();

        objA.instance = objB;
        objB.instance = objA;

        objA = null;
        objB = null;

        // 假设在这里运行发生GC，那么objA和objB是否能够被收回？
        System.gc();
    }
}
