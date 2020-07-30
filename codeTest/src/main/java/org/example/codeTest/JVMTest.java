package org.example.codeTest;

import lombok.extern.log4j.Log4j2;


/**
 * See https://www.jianshu.com/p/1ba925157f7d
 */
@Log4j2
public class JVMTest {
    public native void sayHelloWorld();

    static {
        System.loadLibrary("HelloWorldImpl");
    }

    public static void main(String[] args) {
        JVMTest helloWorld = new JVMTest();
        helloWorld.sayHelloWorld();
    }
}
