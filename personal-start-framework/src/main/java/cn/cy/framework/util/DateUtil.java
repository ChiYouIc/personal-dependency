package cn.cy.framework.util;


import cn.hutool.core.util.ObjectUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;

/**
 * @Author: 友叔
 * @Date: 2020/9/13 16:43
 * @Description: LocalDate转换工具
 */
public class DateUtil extends cn.hutool.core.date.DateUtil {

    private static final String LOCAL_DATE_REGEX_1 = "\\d{1,4}-\\d{1,2}-\\d{1,2}";
    private static final String LOCAL_DATE_REGEX_2 = "\\d{1,4}/\\d{1,2}/\\d{1,2}";
    private static final String LOCAL_TIME_REGEX_1 = "\\d{1,2}:[0-6]\\d:[0-6]\\d";
    private static final String LOCAL_TIME_REGEX_2 = "\\d{1,2}:[0-6]\\d";
    private static final String LOCAL_TIME_REGEX_3 = "\\d{1,2}(:[0-6]\\d){1,2}";


    /**
     * 字符串转LocalDate<br>
     * <ul>
     *     <li>支持格式: yyyy-MM-dd、yyyy-M-d、yyyy-MM-d、yyyy-M-dd</li>
     *     <li>支持格式: yyyy/MM/dd、yyyy/M/d、yyyy/MM/d、yyyy/M/dd</li>
     * </ul>
     *
     * @param localDate 字符串
     * @return LocalDate 日期
     * @throws DataFormatException 格式不正确或内容错误.
     */
    public static LocalDate stringToLocalDate(String localDate) throws DataFormatException {
        boolean isMatches = Pattern.matches(LOCAL_DATE_REGEX_1, localDate) || Pattern.matches(LOCAL_DATE_REGEX_2, localDate);
        if (isMatches) {
            String[] split = localDate.split("-").length == 3 ? localDate.split("-") : localDate.split("/");
            return LocalDate.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
        }
        throw new DataFormatException(String.format("%s 格式不正确.", localDate));
    }

    /**
     * 字符串转 LocalTime<br>
     * <ul>
     *     <li>支持格式: HH:mm</li>
     *     <li>支持格式: H:mm</li>
     *     <li>支持格式: HH:mm:ss</li>
     *     <li>支持格式: H:mm:ss</li>
     * </ul>
     *
     * @param localTime 字符串
     * @return LocalTime 时间
     * @throws DataFormatException 格式不正确或内容错误.
     */
    public static LocalTime stringToLocalTime(String localTime) throws DataFormatException {
        boolean isMatches = Pattern.matches(LOCAL_TIME_REGEX_3, localTime);
        if (isMatches) {
            String[] split = localTime.split(":");
            return split.length == 2
                    ? LocalTime.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]))
                    : LocalTime.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
        }
        throw new DataFormatException(String.format("%s 格式不正确.", localTime));
    }

    /**
     * 字符串转 LocalDateTime<br>
     * <p>内部使用字符串拆分，拆分为 LocalDate 和 LocalTime 字符串格式，然后通过
     * {@link #stringToLocalDate(String)} 和 {@link #stringToLocalTime(String)} 处理.</p>
     *
     * @param localDateTime 字符串
     * @return LocalDateTime 日期时间
     * @throws DataFormatException DataFormatException 格式不正确或内容错误.
     */
    public static LocalDateTime stringToLocalDateTime(String localDateTime) throws DataFormatException {
        String[] split = localDateTime.split(" ");
        if (split.length == 2) {
            return LocalDateTime.of(stringToLocalDate(split[0]), stringToLocalTime(split[1]));
        }
        throw new DataFormatException(String.format("%s 格式不正确.", localDateTime));
    }

    /**
     * 日期格式化
     *
     * @param localDate 日期
     * @param format    格式化样式
     * @return 格式化后的日期字符串
     */
    public static String formatLocalDateToString(LocalDate localDate, String format) {
        try {
            if (ObjectUtil.isNull(localDate)) {
                return null;
            } else {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                return localDate.format(formatter);
            }
        } catch (DateTimeParseException var3) {
            return null;
        }
    }

    public static String formatLocalDateTimeToString(LocalDateTime localDateTime) {
        return formatLocalDateTimeToString(localDateTime, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 日期时间格式化
     *
     * @param localDateTime datetime
     * @param format        格式化样式
     * @return 格式化后的datetime
     */
    public static String formatLocalDateTimeToString(LocalDateTime localDateTime, String format) {
        try {
            if (ObjectUtil.isNull(localDateTime)) {
                return null;
            } else {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                return localDateTime.format(formatter);
            }
        } catch (DateTimeParseException var3) {
            return null;
        }
    }
}
