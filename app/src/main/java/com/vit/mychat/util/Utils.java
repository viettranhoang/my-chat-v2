package com.vit.mychat.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Utils {
    public static String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("E, HH:mm");
        return mdformat.format(calendar.getTime());
    }
}
