package com.lwp.util.ids;

import java.util.UUID;

/**
 * @Auther: liuweipeng
 * @Date: 2018/7/24 14:29
 * @Description:
 */
public class UUIDUtil {
    public static String getUUID(){
       String uuid = UUID.randomUUID().toString();
        return uuid;
    }
    public static void main(String[] args){
        System.out.println(UUIDUtil.getUUID());
    }
}
