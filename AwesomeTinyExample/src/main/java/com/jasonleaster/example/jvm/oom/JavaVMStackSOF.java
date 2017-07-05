package com.jasonleaster.example.jvm.oom;

/**
 * Author: jasonleaster
 * Date  : 2017/6/30
 * Email : jasonleaster@gmail.com
 * Description:
 *
 */
public class JavaVMStackSOF {
    private int stackLength = 1;

    /**
     * 尝试通过递归爆栈的方式触发 StackOverflowError
     */
    public void stackLeak() {
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) throws Throwable{
        JavaVMStackSOF oom = new JavaVMStackSOF();
        try {
            oom.stackLeak();
        }catch (Throwable e){
            System.out.println("stack length :" + oom.stackLength);
            throw e;
        }
    }
}
