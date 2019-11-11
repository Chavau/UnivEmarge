package com.chavau.univ_angers.univemarge.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    // TODO: Demander le bon format
    // https://developer.android.com/reference/java/text/SimpleDateFormat.html
    private static final String pattern = "YYYYMMddHHmm";

    public static String convertDateToString(Date date) {
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

    public static Date convertStringToDate(String str) {
        DateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
