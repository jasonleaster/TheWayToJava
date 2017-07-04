package com.jasonleaster.example.jvm.oom;

/**
 * Author: jasonleaster
 * Date  : 2017/6/30
 * Email : jasonleaster@gmail.com
 * Description:
 */
public class JavaVMStackOOM {
    private void dontStop(){
        while (true){}
    }

    public void stackLeakByThread(){
        while (true){
            Thread thread = new Thread(this::dontStop);
            thread.start();
        }
    }

    public static void main(String[] args) throws Throwable{
        JavaVMStackOOM oom = new JavaVMStackOOM();
    }
}
