package com.lwp.util.classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MyClassLoader extends ClassLoader{

    private String classPath;

    public MyClassLoader(String classPath) {
        this.classPath=classPath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            byte [] classDate=getDate(classPath);
            
            if(classDate==null){}
            
            else{
                //defineClass方法将字节码转化为类
                return defineClass(name,classDate,0,classDate.length);
            }
            
        } catch (IOException e) {
            
            e.printStackTrace();
        }
        
        return super.findClass(name);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        // app类加载器  加载classpath下的class  ext加载器 加载 jre/lib/jext   bootstarp加载器加载 jre/lib
        //获取app类加载器的父加载器  防止app加载器 加载classpath下的class
        ClassLoader systemClassLoader = getSystemClassLoader().getParent();
        Class<?> aClass = null;
        try {
            // 如果 name 不在jre/lib/jext 或 jre/lib下 会 抛ClassNotFound异常
            aClass = systemClassLoader.loadClass(name);
        }catch (Exception e){

        }
        if(aClass == null){
            return findClass(name);
        }
        return aClass;
    }

    //返回类的字节码
    private byte[] getDate(String className) throws IOException{
        InputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            in=new FileInputStream(className);
            out=new ByteArrayOutputStream();
            byte[] buffer=new byte[2048];
            int len=0;
            while((len=in.read(buffer))!=-1){
                out.write(buffer,0,len);
            }
            return out.toByteArray();
        } 
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally{
            in.close();
            out.close();
        }
        return null;
    }
}