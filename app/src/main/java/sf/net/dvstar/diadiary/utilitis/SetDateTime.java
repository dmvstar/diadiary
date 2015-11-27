package sf.net.dvstar.diadiary.utilitis;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import sf.net.dvstar.diadiary.utilitis.CommonUtils;

public class SetDateTime {


    public static class SetDate implements View.OnFocusChangeListener, DatePickerDialog.OnDateSetListener {
        private final Context mContext;
        private EditText editText;
        private Calendar myCalendar;

        public SetDate(EditText editText, Context ctx) {
            this.mContext = ctx;
            this.editText = editText;
            this.editText.setOnFocusChangeListener(this);
            this.myCalendar = Calendar.getInstance();
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            //@TODO get localised format
            this.editText.setText(String.format("%02d.%02d.%02d", dayOfMonth, monthOfYear+1, year));
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                int year = myCalendar.get(Calendar.YEAR);
                int month = myCalendar.get(Calendar.MONTH);
                int day = myCalendar.get(Calendar.DAY_OF_MONTH);

                String time = editText.getText().toString();
                if (time.length() > 0) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

                    Date dt = CommonUtils.parseDateText(time);
                    myCalendar.setTime(dt);
                    year = myCalendar.get(Calendar.YEAR);
                    month = myCalendar.get(Calendar.MONTH);
                    day = myCalendar.get(Calendar.DAY_OF_MONTH);

                }
                new DatePickerDialog(mContext, this, year, month, day).show();
            }
        }
    }

    public static class SetTime implements View.OnFocusChangeListener, TimePickerDialog.OnTimeSetListener {
        private final Context mContext;
        private EditText editText;
        private Calendar myCalendar;

        public SetTime(EditText editText, Context ctx) {
            this.mContext = ctx;
            this.editText = editText;
            this.editText.setOnFocusChangeListener(this);
            this.myCalendar = Calendar.getInstance();
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
                int minute = myCalendar.get(Calendar.MINUTE);
                String time = editText.getText().toString();
                if (time.length() > 0) {
                    Date dt = CommonUtils.parseTimeText(time);
                    myCalendar.setTime(dt);
                    hour = myCalendar.get(Calendar.HOUR_OF_DAY);
                    minute = myCalendar.get(Calendar.MINUTE);
                }
                new TimePickerDialog(mContext, this, hour, minute, true).show();
            }
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            //@TODO get localised format
            this.editText.setText(String.format("%02d:%02d", hourOfDay, minute));
        }
    }


}
