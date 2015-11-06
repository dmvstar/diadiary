/* Copyright 2015 Heitor Colangelo
* 
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package sf.net.dvstar.diadiary.utilitis;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup.LayoutParams;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.LinearLayout;

import java.util.Calendar;
import java.util.Date;

import sf.net.dvstar.diadiary.R;

public class CalendarDialogBuilder {

	public interface OnDateSetListener {

        void onDateSet(int Year, int Month, int Day);
    }

    private OnDateSetListener dateSetlistener;
    private Context context;
    private long initialDate;
    private CalendarView mCv;
    private AlertDialog.Builder alertBuilder;

    // To hold the values of year, month and day.
    public int YEAR, MONTH, DAY;

    // Constructor.
    public CalendarDialogBuilder(Context ctx, OnDateSetListener listener) {
        this.context = ctx;
        this.dateSetlistener = listener;
        this.alertBuilder = returnDialog();
    }

    public CalendarDialogBuilder(Context ctx, OnDateSetListener listener, long initialDate) {
        this.context = ctx;
        this.dateSetlistener = listener;
        this.initialDate = initialDate;
        this.alertBuilder = returnDialog();
    }

    public static void showCalendarViewDialog(Context context){
        LayoutInflater inflater = (LayoutInflater)context.getApplicationContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout ll= (LinearLayout)inflater.inflate(R.layout.calendar_dialog, null, false);
        CalendarView cv = (CalendarView) ll.getChildAt(0);
        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {

            }
        });
        new AlertDialog.Builder(context)
                .setTitle("Event Calendar")
                .setMessage("Click to schedule or view events.")
                .setView(ll)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //do nothing...yet
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Do nothing.
                    }
                }
        ).show();

    }


    public AlertDialog.Builder returnDialog1(){
        LayoutInflater inflater = (LayoutInflater)context.getApplicationContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout ll = (LinearLayout)inflater.inflate(R.layout.calendar_dialog, null, false);
        /*
        mCv = (CalendarView) ll.getChildAt(0);
        mCv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {

            }
        });
        */
        this.alertBuilder = new AlertDialog.Builder(context)
                .setTitle("Event Calendar")
                .setMessage("Click to schedule or view events.")
                .setView(ll)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //do nothing...yet
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Do nothing.
                    }
                }
        );

        return alertBuilder;
    }

    public AlertDialog.Builder returnDialog() {
        // Inflating the layour.
        LayoutInflater inflater = (LayoutInflater) context.getApplicationContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.calendar_dialog, null, false);

        // Getting the CalendarView.
        mCv = (CalendarView) ll.getChildAt(0);

        // Setting the listener.
        mCv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                YEAR = year;
                MONTH = month;
                DAY = dayOfMonth;
            }
        });

        // Configuring the calendar view.
        configCalendarView();

        // Creating the alert dialog that will display our LinearLayout with the calendar view.
        AlertDialog.Builder aDBuilder = new AlertDialog.Builder(context)
                .setView(ll)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dateSetlistener.onDateSet(YEAR, MONTH, DAY);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dateSetlistener.onDateSet(0, 0, 0);
                    }
                });

        return aDBuilder;

    }

    // This method will be called from the activity.
    public void showCalendar() {
        alertBuilder.show();
    }

    public void setStartDate(long startDate) {
        mCv.setMinDate(startDate);
    }

    public void setEndDate(long endDate) {
        mCv.setMaxDate(endDate);
    }

    // Here you can configure your CalendarView.
    @SuppressLint("NewApi")
    private void configCalendarView() {

        // sets whether to show the week number.
        mCv.setShowWeekNumber(false);

        // sets the first day of week according to Calendar.
        // here I set Sunday as the first day of the Calendar
        mCv.setFirstDayOfWeek(1);

        // sets whether to show the week number.
        mCv.setShowWeekNumber(false);

        // if there's a initial date, set it.
        if (initialDate != 0) {
            mCv.setDate(initialDate, true, false);

            Date dateToSet = new Date(initialDate);
            //Calendar calendar.
            YEAR = dateToSet.getYear();
            MONTH = dateToSet.getMonth();
            DAY = dateToSet.getDay();

        }

        // sets the background color.
        mCv.setBackgroundColor(context.getResources().getColor(R.color.a200_orange));

        // theses methods are API 16+ only.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            //The background color for the selected week.
            mCv.setSelectedWeekBackgroundColor(context.getResources().getColor(R.color.a100_orange));

            //sets the color for the vertical bar shown at the beginning and at the end of the selected date.
            mCv.setSelectedDateVerticalBar(new ColorDrawable(context.getResources().getColor(R.color.green)));

            //sets the color for the dates of an unfocused month.
            mCv.setUnfocusedMonthDateColor(context.getResources().getColor(R.color.grey));

            //sets the color for the separator line between weeks.
            mCv.setWeekSeparatorLineColor(context.getResources().getColor(R.color.darkgreen));
        }
    }
	
}
