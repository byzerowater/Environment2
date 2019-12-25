package com.zerowater.environment.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import timber.log.Timber;

/**
 * Created by YoungSoo Kim on 2016-08-10.
 * company Ltd
 * byzerowater@gmail.com
 * 날짜 포맷 유틸
 */
public class DateFormatUtil {

    /**
     * 날짜를 형식에 맞게 변경
     *
     * @param date   날짜
     * @param format 변경 형식
     * @return 형식에 맞게 변경 된 날짜
     */
    public static String getDateFormat(Date date, String format) {

        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());

        return sdf.format(date).toLowerCase();
    }

    /**
     * 날짜를 형식에 맞게 변경
     *
     * @param date        날짜
     * @param curFormat   지금 형식
     * @param parseFormat 변경할 형식
     * @return 형식에 맞게 변경 된 날짜
     */
    public static String convertDateFormat(String date, String curFormat, String parseFormat) {
        String parseDate = null;
        if (!StringUtil.isEmpty(date)) {
            try {
                SimpleDateFormat curSdf = new SimpleDateFormat(curFormat, Locale.getDefault());
                SimpleDateFormat parSdf = new SimpleDateFormat(parseFormat, Locale.getDefault());
                parseDate = parSdf.format(curSdf.parse(date));
            } catch (ParseException e) {
                Timber.e(e);
            }
        }
        return StringUtil.isEmpty(parseDate) ? parseDate : parseDate.toLowerCase();
    }

    /**
     * 날짜 문자열을 날짜로 변경
     *
     * @param date      날짜 문자열
     * @param curFormat 지금 형식
     * @return 변경한 날짜
     */
    public static Date parseDateFormat(String date, String curFormat) {
        Date parse = null;
        if (!StringUtil.isEmpty(date)) {
            try {
                SimpleDateFormat curSdf = new SimpleDateFormat(curFormat, Locale.getDefault());
                parse = curSdf.parse(date);
            } catch (ParseException e) {
                Timber.e(e);
            }
        }

        return parse;
    }


}
