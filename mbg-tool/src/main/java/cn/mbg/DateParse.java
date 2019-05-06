package cn.mbg;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParse {
    public static void main(String[] args) throws ParseException {
        Date date = new Date();
        System.out.println(date);
        //                EEE MMM dd HH:mm:ss zzz yyyy
        String dateStr = "Dec 31 23:59:59 GMT+08 2018";
        Date x = new Date(dateStr);
        System.out.println(x);
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(x));
    }
}
