package com.jasonleaster.example.jvm.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: jasonleaster
 * Date  : 2017/8/22
 * Email : jasonleaster@gmail.com
 * VM Args :
 * TODO java 8 中 PermSize 和 MaxPermSize都不起作用了
 * 本测试无效
 * Description:
 */
public class RuntimeConstantPoolOOM {
    public static void main(String[] args){
        // 使用List保持着常量池的引用，避免Full GC回收常量池
        List<String> list = new ArrayList<>();
        int i = 0;
        // 10M的PermSize在integer范围之内足够产生OOM
        while (true){
            list.add(String.valueOf(i++).intern());
        }
    }
}
