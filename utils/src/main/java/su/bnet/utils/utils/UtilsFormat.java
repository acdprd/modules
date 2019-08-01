package su.bnet.utils.utils;

import android.support.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UtilsFormat {
    /**
     * @return formatter(" yyyy - MM - dd ")
     */
    public static SimpleDateFormat getServerFormatter() {
        return new SimpleDateFormat("yyyy-MM-dd", new Locale("ru"));
    }

    /**
     * @return formatter(" dd - MM - yyyy ")
     */
    public static SimpleDateFormat getHumanFormatter() {
        return new SimpleDateFormat("dd-MM-yyyy", new Locale("ru"));
    }

    public static SimpleDateFormat getDebugFormatter(){
        return new SimpleDateFormat("dd-MM-yyyy | HH:mm:ss SSS", new Locale("ru"));
    }

    @Nullable
    public static String reformatServerToHuman(@Nullable String date) {
        if (date == null) return null;
        return getHumanFormatter().format(getDateFromServerTime(date));
    }

    /**
     * @param date - long date
     * @return ex: 29 января
     */
    public static String get_dd_MMMM(long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        SimpleDateFormat monthDate = new SimpleDateFormat("dd MMMM", new Locale("ru"));
        return monthDate.format(calendar.getTime());
    }


    /**
     * @param date - long date
     * @return ПТ, 27 сентября
     */
    public static String get_EE_dd_MMMM(long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        SimpleDateFormat monthDate = new SimpleDateFormat("EE, dd MMMM", new Locale("ru"));
        return monthDate.format(calendar.getTime());
    }

    /**
     * @param date - long date
     * @return ex: 29.01
     */
    public static String get_dd_dot_MM(long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        SimpleDateFormat monthDate = new SimpleDateFormat("dd.MM", new Locale("ru"));
        return monthDate.format(calendar.getTime());
    }

    /**
     * @param date - long date
     * @return ex: 29.01
     */
    public static String get_dd_dot_MM_dot_YY(long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        SimpleDateFormat monthDate = new SimpleDateFormat("dd.MM.yy", new Locale("ru"));
        return monthDate.format(calendar.getTime());
    }

    /**
     * @param date - long date
     * @return ex: январь 18
     */
    public static String get_LLLL_YY(long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        SimpleDateFormat monthDate = new SimpleDateFormat("LLLL yy", new Locale("ru"));
        return monthDate.format(calendar.getTime());
    }

    /**
     * @param date - long date
     * @return ex: январь 18
     */
    public static String get_LLLL_YYYY(long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        SimpleDateFormat monthDate = new SimpleDateFormat("LLLL yyyy", new Locale("ru"));
        return monthDate.format(calendar.getTime());
    }

    /**
     * @param date - date X
     * @return "HH : mm"
     */
    public static String get_HH_mm(long date) {
        return new SimpleDateFormat("HH:mm", new Locale("ru")).format(new Date(date));
    }

    public static String get_HH(long date){
        return new SimpleDateFormat("HH", new Locale("ru")).format(new Date(date));
    }

    @Nullable
    public static Date getDateFromServerTime(@Nullable String t) {
        if (t == null) return null;
        SimpleDateFormat f = getServerFormatter();
        try {
            return f.parse(t);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date(0);
        }
    }

    /**
     * @return formatter(" HH.mm ")
     */
    public static SimpleDateFormat getFormatterForTime() {
        return new SimpleDateFormat("HH.mm", new Locale("ru"));
    }


    /**
     * Преобразовать в другой формат
     *
     * @param inputDate
     * @param inputDateFormat
     * @param outputDateFormat
     * @return
     */
    public static String getDateInNewFormat(String inputDate, String inputDateFormat, String outputDateFormat) {
        return getDateInNewFormat(inputDate, new SimpleDateFormat(inputDateFormat), new SimpleDateFormat(outputDateFormat));
    }

    /**
     * Преобразовать в другой формат
     *
     * @param inputDate
     * @param inputDateFormat
     * @param outputDateFormat
     * @return
     */
    public static String getDateInNewFormat(String inputDate, SimpleDateFormat inputDateFormat, SimpleDateFormat outputDateFormat) {
        try {
            Date date = inputDateFormat.parse(inputDate);
            return outputDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return inputDate;
    }

}
