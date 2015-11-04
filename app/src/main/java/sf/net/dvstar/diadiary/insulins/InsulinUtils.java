package sf.net.dvstar.diadiary.insulins;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;


public class InsulinUtils {


    // hours
    public static double[] merge(double[] a, double[] b) {
        int N = a.length;
        int M = b.length;
        int size = M+N;
        for (double elementB : b){
            if (Arrays.binarySearch(a, elementB)>=0){
                size--;
            }
        }
        double[] c = new double[size];
        for (int i=0; i<a.length; i++){
            c[i]=a[i];
        }
        for (int i=a.length, j=0; j<b.length; j++){
            if (Arrays.binarySearch(c,b[j])<0){
                c[i]=b[j];
                i++;
            }
        }
        Arrays.sort(c);
        return c;
    }


    public static String mDateFormatString = "%02d.%02d.%02d";
    public static String mDateFormat = "dd.MM.yyyy";
    public static String mTimeFormat = "HH:mm";
    public static String mDateTimeFormat = "HH:mm dd.MM.yyyy";
    public static String mDateTimeFormatRev = "dd.MM.yyyy HH:mm";

    public static SimpleDateFormat mSimpleDateFormatDate = new SimpleDateFormat(mDateFormat);
    public static SimpleDateFormat mSimpleTimeFormatDate = new SimpleDateFormat(mTimeFormat);
    public static SimpleDateFormat mSimpleDateTimeFormatDate = new SimpleDateFormat(mDateTimeFormat);
    public static SimpleDateFormat mSimpleDateTimeFormatRev = new SimpleDateFormat(mDateTimeFormatRev);

    public static String getDateText(Date date) {
        String ret = "";
        if(date !=null)
            ret = mSimpleDateFormatDate.format(date);
        return ret;
    }

    public static String getTimeText(Date date) {
        String ret = "";
        if(date !=null)
            ret = mSimpleTimeFormatDate.format(date);
        return ret;
    }

    public static String getDateTimeText(Date date) {
        String ret = "";
        if(date !=null)
            ret = mSimpleDateTimeFormatDate.format(date);
        return ret;
    }

    public static String getDateTimeTextRev(Date date) {
        String ret = "";
        if(date !=null)
            ret = mSimpleDateTimeFormatRev.format(date);
        return ret;
    }



    public static String getDateText(int year, int monthOfYear, int dayOfMonth) {
        return String.format(mDateFormatString, dayOfMonth , monthOfYear, year);
    }


    public static Date parseTimeText(String time) {
        Date ret = null;//Calendar.getInstance().getTime();

        try {
            ret = mSimpleTimeFormatDate.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public static Date parseDateText(String date) {
        Date ret = null;//Calendar.getInstance().getTime();

        try {
            ret = mSimpleDateFormatDate.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public static Date parseDateTimeText(String time, String date) {
        Date ret = null;

        if(time.length()!=0 && date.length() !=0 ) {

            Calendar calendar = Calendar.getInstance();
            try {
                Date tempDate = mSimpleDateFormatDate.parse(date);
                Calendar calendarDate = Calendar.getInstance();
                calendarDate.setTime(tempDate);

                Date tempTime = mSimpleTimeFormatDate.parse(time);
                Calendar calendarTime = Calendar.getInstance();
                calendarTime.setTime(tempTime);

                calendar.set(Calendar.MINUTE, calendarTime.get(Calendar.MINUTE));
                calendar.set(Calendar.HOUR_OF_DAY, calendarTime.get(Calendar.HOUR_OF_DAY));

                calendar.set(Calendar.DAY_OF_MONTH, calendarDate.get(Calendar.DAY_OF_MONTH));
                calendar.set(Calendar.MONTH, calendarDate.get(Calendar.MONTH));
                calendar.set(Calendar.YEAR, calendarDate.get(Calendar.YEAR));

                ret = calendar.getTime();

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return ret;
    }

    public static Date getDateTimeFrom(int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth );
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Date ret = calendar.getTime();
        return ret;
    }

    public static Date getDateTimeFrom(Date time, Date date) {
        Date iTime, iDate;
        Calendar calendar = Calendar.getInstance();

        if(date==null){
            iTime = time;
            iDate = new Date();
        } else {
            iTime = time;
            iDate = date;
        }

        Calendar calendarDate = Calendar.getInstance();
        calendarDate.setTime(iDate);

        Calendar calendarTime = Calendar.getInstance();
        if(iTime!=null) calendarTime.setTime(iTime);

        if(iTime!=null) {
            calendar.set(Calendar.MINUTE, calendarTime.get(Calendar.MINUTE));
            calendar.set(Calendar.HOUR_OF_DAY, calendarTime.get(Calendar.HOUR_OF_DAY));
        } else {
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
        }

        //calendar.set(Calendar.AM_PM, calendarTime.get(Calendar.AM_PM) );

        calendar.set(Calendar.DAY_OF_MONTH, calendarDate.get(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.MONTH, calendarDate.get(Calendar.MONTH));
        calendar.set(Calendar.YEAR, calendarDate.get(Calendar.YEAR));

        Date ret = calendar.getTime();
        Log.v("!!!!!!!!!! calendar=", "" + ret);

        return ret;
    }
}




