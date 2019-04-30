package com.vit.mychat.util;

import java.text.SimpleDateFormat;

public class Utils {

    public static String getTime(long minisecondTime) {
        String currentDay = new SimpleDateFormat("dd").format(System.currentTimeMillis());
        String day = new SimpleDateFormat("dd").format(minisecondTime);

        if (currentDay.equals(day)) {
            return new SimpleDateFormat("HH:mm").format(minisecondTime);
        } else
            return new SimpleDateFormat("E").format(minisecondTime);
    }
}
