package com.iche.fxdealswarehouse.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    public static String saveDate(LocalDateTime localDateTime){
        DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("yy-MM-DD");
        return formatter.format(localDateTime);
    }

}
