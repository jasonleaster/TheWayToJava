package com.jasonleaster.example.jvm.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: jasonleaster
 * Date  : 2017/6/30
 * Email : jasonleaster@gmail.com
 * Description:
 *      务必在运行HeapOOM类的main方法时, 使用 -Xms与-Xmx限制堆的大小
 *      否则程序可能吃掉机器所有的内存. -Xms与-Xmx数值一致能保证堆的大小不会动态扩展。
 *
 *      通过参数-XX:+HeapDumpOnOutOfMemoryError 可以让虚拟机在出现内存溢出异常的时候
 *      Dump出当前的内存堆转储快照以便时候进行分析。
 *
 * Vm Args:  -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 */
public class HeapOOM {
    static class OOMObject{
    }

    public static void main(String[] args){
        List<OOMObject> list = new ArrayList<OOMObject>();
        while (true){
            list.add(new OOMObject());
        }
    }
}
