package net.myl.business.base.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.management.ManagementFactory;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 时间工具类
 *
 * @author cpmanager
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    public static final String YYYY = "yyyy";

    public static final String DD = "dd";

    public static final String MMDD= "MMdd";

    public static final String YYYY_MM = "yyyy-MM";

    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static final String YYYYMMDD = "yyyyMMdd";

    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static final String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd hh:mm:ss:SSS";

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate() {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format) {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date) {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts) {
        try {
            return new SimpleDateFormat(format).parse(ts);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算两个时间差
     */
    public static long getDatePoor(Date endDate, Date nowDate , DatePoorEnum type) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        switch (type){
            case DAY:
                return day;
            case HOUR:
                return hour;
            case MIN:
                return min;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    /**
     * 字符串转日期类型
     *
     * @param str     日期串
     * @param pattern 日期格式
     * @return
     */
    public static Date stringToDate(String str, String pattern) {
        if (str != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            try {
                return sdf.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * *************************************************************************
     *
     * oldDateType, newDateType
     * @return java.util.Date
     * @Package com.newcapec.util
     * @Description: <格式化时间格式返回时间>
     * @author
     * @date 2017/9/8 11:47
     * @version V1.0
     *          *************************************************************************
     */
    public static Date formateByStyleToDate(String metadata, String oldDateType, String newDateType) {
        if (StringUtils.isBlank(metadata) || StringUtils.isBlank(oldDateType) || StringUtils.isBlank(newDateType)) {
            return null;
        }
        String newDateStr = stringFormat(metadata, oldDateType, newDateType);
        return stringToDate(newDateStr, newDateType);
    }


    /**
     * 日期类型转字符串
     *
     * @param date    日期
     * @param pattern 字符串格式
     * @return
     */
    public static String dateToString(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.format(date);
        }
        return "";
    }

    /**
     * Timestamp转String
     *
     * @param timestamp
     * @param pattern
     * @return
     */
    public static String dateToString(Timestamp timestamp, String pattern) {
        if (timestamp != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            Date date = timestamp;
            return sdf.format(date);
        }
        return "";
    }

    /**
     * 将日期转字符串（格式：yyyy-MM-dd HH:mm:ss）
     *
     * @param date
     * @return
     */
    public static String dateToString(Date date) {
        return dateToString(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获得当天日期
     *
     * @param formate
     * @return
     */
    public static String getToday(String formate) {
        SimpleDateFormat format = new SimpleDateFormat(formate);
        return format.format(new Date());
    }

    /**
     * 计算2个日期相差的天数
     *
     * @param fDate 2014-12-12
     * @param oDate 2014-12-15
     * @return
     */
    public static int getIntervalDays(String fDate, String oDate) throws Exception {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Date bDate = format.parse(fDate);

        Date eDate = format.parse(oDate);

        Calendar d1 = Calendar.getInstance();

        d1.setTime(bDate);

        Calendar d2 = Calendar.getInstance();

        d2.setTime(eDate);

        int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);

        int y2 = d2.get(Calendar.YEAR);

        if (d1.get(Calendar.YEAR) != y2) {
            d1 = (Calendar) d1.clone();
            do {
                days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);//得到当年的实际天数
                d1.add(Calendar.YEAR, 1);
            } while (d1.get(Calendar.YEAR) != y2);
        }
        return days;
    }

    /**
     * 获取当前时间 yyyyMMddHHmmss
     *
     * @return
     */
    public static String getCurrTime() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    /**
     * 根据指定格式获取当前时间
     */
    public static String getCurrTime(String format) {
        if (StringUtils.isBlank(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        return new SimpleDateFormat(format).format(new Date());
    }

    /**
     * 日期字符串转换
     *
     * @param datastr 要格式化的日期字符串
     * @param mat     日期字符串原有格式
     * @param format  日期字符串目标格式
     * @return
     * @throws
     */
    public static String stringFormat(String datastr, String mat, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(mat);
        SimpleDateFormat sdf1 = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sdf.parse(datastr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf1.format(date);
    }

    /**
     * 获取当前系统时间格式为（yyyyMMddhhmmss）
     *
     * @param
     * @param @return
     * @return String
     */
    public static String getDateTimeFormat() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(date);
    }


    /**
     * 根据String型时间，获取long型时间，单位毫秒
     *
     * @param inVal 时间字符串
     * @return long型时间
     */
    public static long fromDateStringToLong(String inVal) {
        Date date = null;
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS");
        try {
            date = inputFormat.parse(inVal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * 获取时间戳
     * 值为当前时间距离19700101的毫秒数
     *
     * @return
     */
    public static String timeStamp() {
        Date date = DateUtils.stringToDate("19700101", DateUtils.YYYYMMDD);
        long stopTime = DateUtils.fromDateStringToLong(new SimpleDateFormat(DateUtils.YYYY_MM_DD_HH_MM_SS_SSS).format(new Date()));
        long startTime = DateUtils.fromDateStringToLong(new SimpleDateFormat(DateUtils.YYYY_MM_DD_HH_MM_SS_SSS).format(date));
        String timestamp = String.valueOf(stopTime - startTime);
        return timestamp;
    }

    /**
     * @param msec 从January 1, 1970, 00:00:00 GMT开始到此刻的毫秒数
     * @return
     */
    public static Date parseDateByMsec(String msec) {
        try {
            long dd = Long.valueOf(msec);
            return new Date(dd);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 当三方返回的支付时间只精确到天时，取一个近似的精确到秒的支付时间
     *
     * @param createTime 订单创建时间
     * @param payDay 清算日期 yyyy-MM-dd 或 yyyyMMdd 或 yyyy-MM-dd HH:mm:ss 或 yyyyMMddHHmmss
     * @param nowTime 当前系统时间
     * @return
     */
    public static Date getCloserPayTime(Date createTime, String payDay, Date nowTime) throws Exception {
        if (StringUtils.isBlank(payDay) || createTime == null || nowTime == null) {
            throw new Exception("输入参数有空值: createTime[" + createTime + "],payDay[" + payDay + "],nowTime[" + nowTime + "]");
        }

        payDay = payDay.trim();
        if (payDay.length() != 8 && payDay.length() != 10) {
            // payDay已经精确到秒:yyyy-MM-dd HH:mm:ss
            if (payDay.matches("^20[0-9]{2}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}$")) {
                return stringToDate(payDay, YYYY_MM_DD_HH_MM_SS);
            }
            // payDay已经精确到秒:yyyyMMddHHmmss
            if (payDay.matches("^20[0-9]{12}$")) {
                return stringToDate(payDay, YYYYMMDDHHMMSS);
            }
            throw new Exception("输入时间格式不合法：" + payDay);
        }
        // 格式化为yyyy-MM-dd
        if (payDay.length() == 8) {
            payDay = payDay.substring(0, 4) + "-" + payDay.substring(4, 6) + "-" + payDay.substring(6);
        }

        String createTimeStr = dateToString(createTime, YYYY_MM_DD_HH_MM_SS);
        String nowTimeStr = dateToString(nowTime, YYYY_MM_DD_HH_MM_SS);
        String payTimeStr = getCloserPayTime(createTimeStr, payDay, nowTimeStr);
        return stringToDate(payTimeStr, YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 当三方返回的支付时间只精确到天时，取一个近似的精确到秒的支付时间
     *
     * @param createTime 订单创建时间 yyyy-MM-dd HH:mm:ss
     * @param payDay 清算日期 yyyy-MM-dd
     * @param nowTime 当前系统时间 yyyy-MM-dd HH:mm:ss
     * @return 近似支付时间 yyyy-MM-dd HH:mm:ss
     */
    private static String getCloserPayTime(String createTime, String payDay, String nowTime) {
        // 清算日期和当前系统日期和一致
        if (nowTime.startsWith(payDay)) {
            return nowTime;
        }
        // 清算日期和订单创建日期一致
        if (createTime.startsWith(payDay)) {
            return createTime;
        }

        return payDay + " 00:00:00";
    }

    /**
     * 判断time和当前输入的时分秒的关系,大于等于为true,小于为false
     *
     * 备注：
     * 1）min,sec可以为null，表示不比较
     * 2）如果min为null则不再判断sec
     *
     * @param hour 小时
     * @param min 分钟
     * @param sec 秒
     * @param time 需要比较的时间
     * @return
     */
    public static boolean compareTime(Integer hour, Integer min, Integer sec, Date time) {
        //获取date对应的时分秒
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        int timeHour = cal.get(Calendar.HOUR_OF_DAY);
        int timeMin = cal.get(Calendar.MINUTE);
        int timeSec = cal.get(Calendar.SECOND);

        boolean res = false;
        //判断
        if(hour != null && hour == timeHour) {
            if (min != null && min == timeMin) {
                if (sec != null) {
                    res = timeSec >= sec;
                } else {
                    res = true;
                }
            } else {
                res = min == null ? true :  timeMin >= min;
            }
        } else {
            res = hour == null ? true :  timeHour >= hour;
        }
        return res;
    }
}