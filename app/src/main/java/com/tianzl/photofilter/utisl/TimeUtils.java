package com.tianzl.photofilter.utisl;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tianzl on 2017/8/31.
 */

public class TimeUtils {
    //获取当前yyyyMMddHHmmss格式的时间
    public static String getStringToday() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static final SimpleDateFormat DEFAULT_DATE_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final SimpleDateFormat DATE_FORMAT_DATE =
            new SimpleDateFormat("yyyy-MM-dd");

    private TimeUtils() {

        throw new AssertionError();

    }

    /**
     * long time to string
     *
     * @param timeInMillis
     * @param dateFormat
     * @return
     */

    public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {

        return dateFormat.format(new Date(timeInMillis));

    }

    /**
     * long time to string, format is {@link #DEFAULT_DATE_FORMAT}
     *
     * @param timeInMillis
     * @return
     */

    public static String getTime(long timeInMillis) {

        return getTime(timeInMillis, DEFAULT_DATE_FORMAT);

    }

    /**
     * get current time in milliseconds
     *
     * @return
     */

    public static long getCurrentTimeInLong() {

        return System.currentTimeMillis();

    }

    /**
     * get current time in milliseconds, format is {@link #DEFAULT_DATE_FORMAT}
     *
     * @return
     */

    public static String getCurrentTimeInString() {

        return getTime(getCurrentTimeInLong());

    }

    /**
     * get current time in milliseconds
     *
     * @return
     */

    public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {

        return getTime(getCurrentTimeInLong(), dateFormat);

    }

}
