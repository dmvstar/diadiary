package sf.net.dvstar.diadiary.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.activeandroid.query.Select;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import sf.net.dvstar.diadiary.R;
import sf.net.dvstar.diadiary.database.GlucoseReading;
import sf.net.dvstar.diadiary.insulins.InsulinConstants;
import sf.net.dvstar.diadiary.insulins.InsulinUtils;
import sf.net.dvstar.diadiary.utilitis.SetDateTime;

public class DiaryGlucoseAddActivity extends AppCompatActivity {

    private EditText mEtGlucoseValue;
    private EditText mEtGlucoseDate;
    private EditText mEtGlucoseTime;
    private EditText mEtGlucoseComment;
    private int mMode;
    private Context mContext;
    private Button mBtAdd;
    private List mNotesList;
    private Spinner mSpNotes;

    private GlucoseReading mGlucoseReading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_glucose_add);

        mContext = this;
        mNotesList = Arrays.asList(getResources().getStringArray(R.array.dialog_notes_list));
        mMode = getIntent().getExtras().getInt(InsulinConstants.KEY_INTENT_EXTRA_EDIT_MODE);

        mEtGlucoseValue = (EditText) findViewById(R.id.et_glucose_value);
        mEtGlucoseDate = (EditText) findViewById(R.id.et_glucose_date);
        mEtGlucoseTime = (EditText) findViewById(R.id.et_glucose_time);
        mEtGlucoseComment = (EditText) findViewById(R.id.et_comment);
        mBtAdd = (Button) findViewById(R.id.bt_confirm);
        mSpNotes = (Spinner) findViewById(R.id.sp_notes);

        mEtGlucoseValue.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    EditText et = (EditText)v;
                    String svalue = et.getText().toString();
                    Double dvalue = Double.parseDouble(svalue);
                    if(dvalue>50.0){
                        svalue = String.format("%.2f",dvalue/18.0).replace(',','.');
                        et.setText(svalue);
                    }
                }
            }
        });

        SetDateTime.SetTime fromTime = new SetDateTime.SetTime(mEtGlucoseTime, this);
        SetDateTime.SetDate fromDate = new SetDateTime.SetDate(mEtGlucoseDate, this);

        if (mMode == InsulinConstants.MODE_ACTIONS_EDIT_ITEM) {
            mBtAdd.setText( getResources().getString(R.string.button_insulin_update) );
            long iId = getIntent().getExtras().getLong(InsulinConstants.KEY_INTENT_EXTRA_ROW_ID);
            mGlucoseReading = new Select().from(GlucoseReading.class).where("id = ?", iId).executeSingle();

//            int indexNotes = mNotesList.indexOf(mGlucoseReading.notes);
            int indexNotes = mGlucoseReading.note;
            if(indexNotes>=0) mSpNotes.setSelection(indexNotes);
            mEtGlucoseValue.setText("" + mGlucoseReading.value);
            mEtGlucoseTime.setText(InsulinUtils.getTimeText(mGlucoseReading.time));
            mEtGlucoseDate.setText(InsulinUtils.getDateText(mGlucoseReading.time));
            mEtGlucoseComment.setText(mGlucoseReading.comment);
        } else {
            Date today = new Date();
            mEtGlucoseTime.setText(InsulinUtils.getTimeText(today));
            mEtGlucoseDate.setText(InsulinUtils.getDateText(today));

        }
    }

    public void cancel(View view) {
        finish();
    }

    public void confirm(View view) {

        if (mMode == InsulinConstants.MODE_ACTIONS_EDIT_ADD) {
            mGlucoseReading = new GlucoseReading();
        }

        String value = mEtGlucoseValue.getText().toString();
        float fvalue = Float.parseFloat(value);
        String date = mEtGlucoseDate.getText().toString();
        String stime = mEtGlucoseTime.getText().toString();
        String comm = mEtGlucoseComment.getText().toString();
//        String note = mSpNotes.getSelectedItem().toString();
        int note = mSpNotes.getSelectedItemPosition();
        Date time = InsulinUtils.parseDateTimeText(stime, date);

        mGlucoseReading.value = fvalue;
        mGlucoseReading.time = time;
        mGlucoseReading.note = note;
        mGlucoseReading.comment = comm;

        if (time != null && fvalue > 0.0) {
            mGlucoseReading.save();
        }

        finish();
    }
}
