package com.damon.util;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;


public class DateUtil {
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String formantDate(Timestamp date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        return date == null ? null : simpleDateFormat.format(date);
    }

    public static String formantDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        return date == null ? null : simpleDateFormat.format(date);
    }


    public static String formantDateWithZone(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
        return date == null ? null : simpleDateFormat.format(date);
    }

    public static String formantDateWithZone(Timestamp date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
        return date == null ? null : simpleDateFormat.format(date);
    }

    // 自定义时间
    public static Timestamp getTime(String time, String format) throws ParseException {
        SimpleDateFormat formatter_begin = new SimpleDateFormat(format);
        Date date_begin = formatter_begin.parse(time);
        return new Timestamp(date_begin.getTime());
    }


    //根据时区获取时间
    public static String getFormatedDateString(float timeZoneOffset, String format) {
        if (timeZoneOffset > 13 || timeZoneOffset < -12) {
            timeZoneOffset = 0;
        }
        int newTime = (int) (timeZoneOffset * 60 * 60 * 1000);
        TimeZone timeZone;
        String[] ids = TimeZone.getAvailableIDs(newTime);
        if (ids.length == 0) {
            timeZone = TimeZone.getDefault();
        } else {
            timeZone = new SimpleTimeZone(newTime, ids[0]);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        if (StrUtil.isNotBlank(format))
            sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(timeZone);
        return sdf.format(new Date());
    }

    /**
     * 时间戳转时间
     *
     * @param time
     * @return
     */
    public static Date getDate(Long time) {
        SimpleDateFormat sf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        String d = sf.format(time);
        Date date = null;
        try {
            date = sf.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getGMT8Date(Long time) {
        SimpleDateFormat sf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        sf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return sf.format(time);
    }

    /**
     * 获取订单有效期(最多申请两年,不满一年按一年算)
     *
     * @param expireTime
     * @return
     */
    public static int getValidityPeriod(Timestamp expireTime) {
        Double d_expireTime = Double.valueOf(expireTime.getTime());
        Double now_Time = Double.valueOf(System.currentTimeMillis());
        Double d_year = Double.valueOf(((d_expireTime / (1000 * 60 * 60 * 24)) - (now_Time / (1000 * 60 * 60 * 24))) / 365);
        int year = d_year >= 1.01 ? 2 : 1;
        return year;

    }

    /**
     * 获取两时间相差天数
     *
     * @param from
     * @param end
     * @return
     */
    public static int getDiffDay(Date from, Date end) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String[] arr_from = sf.format(from).split("-");
        String[] arr_end = sf.format(end).split("-");
        LocalDate localDate_f = LocalDate.of(Integer.valueOf(arr_from[0]), Integer.valueOf(arr_from[1]), Integer.valueOf(arr_from[2]));
        LocalDate localDate_e = LocalDate.of(Integer.valueOf(arr_end[0]), Integer.valueOf(arr_end[1]), Integer.valueOf(arr_end[2]));
        return (int) ((localDate_f.until(localDate_e, ChronoUnit.DAYS)));
    }

    public static int getExtraMonths(Date certEndTime) {
        int extraMonths = 0;
        Date nowDate = new Date();
        if (certEndTime.after(nowDate)) {
            int diffDays = getDiffDay(nowDate, certEndTime);
            if (diffDays > 0) {
                extraMonths = (int) Math.ceil((float) diffDays / 31);
                if (extraMonths > 12) {
                    extraMonths = 12;
                }
            }
        }
        return extraMonths;
    }

    /**
     * 当前时间减一个月
     *
     * @return
     */
    public static String getLastMonthDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        Date m = c.getTime();
        String mon = format.format(m);
        return mon;
    }

    public static Date getOneYearLaterDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, 1);
        return c.getTime();
    }

    public static Date getOneMonthLaterDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, 1);
        return c.getTime();
    }

    public static String formatDateStr(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return date == null ? null : simpleDateFormat.format(date);
    }

}
