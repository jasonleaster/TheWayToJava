package com.jasonleaster.example.jvm.classloading.classloader;

import java.io.IOException;
import java.io.InputStream;

/**
 * Author: jasonleaster
 * Date  : 2017/8/20
 * Email : jasonleaster@gmail.com
 * Description:
 */
public class ClassLoaderTest {

    public static void main(String[] args) throws Exception {
        ClassLoader myLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                    InputStream is = getClass().getResourceAsStream(fileName);
                    if (is == null) {
                        return super.loadClass(name);
                    }
                    byte[] b = new byte[is.available()];
                    is.read(b);
                    return defineClass(name, b, 0, b.length);

                } catch (IOException e) {
                    throw new ClassNotFoundException(name);
                }
            }
        };

        Object obj = myLoader
            .loadClass("com.jasonleaster.example.jvm.classloading.classloader.ClassLoaderTest")
            .newInstance();

        System.out.println(obj.getClass());
        System.out.println(
            obj instanceof com.jasonleaster.example.jvm.classloading.classloader.ClassLoaderTest);
    }

}
