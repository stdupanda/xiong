package cn.mbg;

import java.text.SimpleDateFormat;
import java.util.Date;

public class aaasdf {
    public static void main(String[] args) {
        Date date = new Date();
        System.out.println(date);
        String s = date.toString();
        Date x = new Date("Dec 31 23:59:59 GMT+08 2018");
        System.out.println(x);
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(x));

    }
}
