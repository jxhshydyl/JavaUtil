package com.lwp.util.classloader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestMyClassLoader {

    public static void main(String []args) throws Exception{
        //自定义类加载器的加载路径
        MyClassLoader myClassLoader=new MyClassLoader("D:\\IDEAWoekspace\\atop-java\\ex-public\\target\\classes\\com\\ex\\expublic\\model\\dto\\SysMaintainTimeDto.class");
        //包名+类名
        Class c=myClassLoader.loadClass("com.ex.expublic.model.dto.SysMaintainTimeDto");
        System.out.println(c.getClassLoader().toString());

        //自定义类加载器的加载路径
        MyClassLoader myClassLoader1=new MyClassLoader("D:\\IDEAWoekspace\\JavaUtil\\target/classes/com/lwp/util/classloader/MyClassLoader.class");
        //包名+类名
        Class c1=myClassLoader1.loadClass("com.lwp.util.classloader.MyClassLoader");

        System.out.println(c1.getClassLoader().toString());
    }
}