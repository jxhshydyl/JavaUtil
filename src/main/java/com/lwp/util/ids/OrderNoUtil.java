package com.lwp.util.ids;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class OrderNoUtil {

    public static String getOrderNo() {
        String orderNo = "";
        UUID uuid = UUID.randomUUID();

        String trandNo = String.valueOf((Math.random() * 9 + 1) * 1000000);
        String sdf = new SimpleDateFormat("yyyyMMddHHMMSS").format(new Date());
        orderNo = uuid.toString().substring(0, 8);
        orderNo = orderNo + sdf;
        return orderNo;
    }

    public static void main(String[] args){
        System.out.println(OrderNoUtil.getOrderNo());
        System.out.println();
    }
}
