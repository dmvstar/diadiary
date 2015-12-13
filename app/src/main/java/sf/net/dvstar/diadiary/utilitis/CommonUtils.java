package sf.net.dvstar.diadiary.utilitis;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;


public class CommonUtils {


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

    public static Date getDateTimeFrom(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.DAY_OF_MONTH, 0 );
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.YEAR, 0);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Date ret = calendar.getTime();
        return ret;
    }

    public static Date getDateTimeFrom(int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth );
        calendar.set(Calendar.MONTH, monthOfYear-1);
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

    public static Date getDateTimeFrom(Date iDate, int mode, int adddate) {

        Calendar calendar = Calendar.getInstance();
        Calendar calendarDate = Calendar.getInstance();
        calendarDate.setTime(iDate);

        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        switch(mode){

            case Calendar.DAY_OF_MONTH: {
                calendar.set(Calendar.DAY_OF_MONTH, calendarDate.get(Calendar.DAY_OF_MONTH)+adddate);
            } break;

        }
        Date ret = calendar.getTime();
        Log.v("!!!!!!!!!! calendar=", "" + ret);

        return ret;
    }

    public static Date getDateFromString(String date) {
        SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy");
        sf.setLenient(true);
        Date ret = new Date();
        try {
            ret = sf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static String getFloatString2Decimal(float aFloatValue){
        String ret = String.format("%.2f", aFloatValue);
        return ret.replace(',','.');
    }


    public static Float getSafeFloatFromString(String value) {
        Float ret = 0.0f;
        if(value.length()>0)
            ret = Float.parseFloat(value);
        return ret;
    }

    public static int getSafeIntFromString(String value) {
        int ret = 0;
        if(value.length()>0)
            ret = Integer.parseInt(value);
        return ret;
    }

    public static int getNotesIndexByTime(Date aDate){
        int ret = 9;
        SimpleDateFormat localDateFormat = new SimpleDateFormat("HH");
        String sTime = localDateFormat.format(aDate);
        int iTime = Integer.parseInt(sTime);

        if(iTime>=0 && iTime<7) ret=8;

        if(iTime>=7 && iTime<8) ret=0;
        if(iTime>=8 && iTime<12) ret=1;

        if(iTime>=12 && iTime<13) ret=2;
        if(iTime>=13 && iTime<16) ret=3;

        if(iTime>=16 && iTime<19) ret=4;
        if(iTime>=19 && iTime<24) ret=5;

        return ret;
    }


}




