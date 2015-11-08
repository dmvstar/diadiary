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

public class DiaryPressureAddActivity extends AppCompatActivity {

    private EditText mEtPressureSystoleValue;
    private EditText mEtPressureDiastolicValue;
    private EditText mEtPressureDate;
    private EditText mEtPressureTime;
    private Spinner mSpNotes;
    private EditText mEtComment;
    private int mMode;
    private Context mContext;

    private Button mBtAdd;
    private List mNotesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_pressure_add);

        mContext = this;
        mNotesList = Arrays.asList(getResources().getStringArray(R.array.dialog_when_list));
        mMode = getIntent().getExtras().getInt(InsulinConstants.KEY_INTENT_EXTRA_INJECT_EDIT_MODE);

        mEtPressureSystoleValue = (EditText) findViewById(R.id.et_pressure_systole_value);
        mEtPressureDiastolicValue = (EditText) findViewById(R.id.et_pressure_diastolic_value);

        mEtPressureDate = (EditText) findViewById(R.id.et_date);
        mEtPressureTime = (EditText) findViewById(R.id.et_time);
        mSpNotes = (Spinner) findViewById(R.id.sp_when);
        mEtComment = (EditText) findViewById(R.id.et_comment);
        mBtAdd = (Button) findViewById(R.id.bt_add_update);

        SetDateTime.SetTime fromTime = new SetDateTime.SetTime(mEtPressureTime, this);
        SetDateTime.SetDate fromDate = new SetDateTime.SetDate(mEtPressureDate, this);
/*
        if (mMode == InsulinConstants.MODE_ACTIONS_EDIT_ITEM) {
            mBtAdd.setText( getResources().getString(R.string.button_insulin_update) );
            long iId = getIntent().getExtras().getLong(InsulinConstants.KEY_INTENT_EXTRA_ROW_ID);
            mGlucoseReading = new Select().from(GlucoseReading.class).where("id = ?", iId).executeSingle();

            int indexNotes = mNotesList.indexOf(mGlucoseReading.notes);
            if(indexNotes>=0) mSpNotes.setSelection(indexNotes);
            mEtGlucoseValue.setText("" + mGlucoseReading.value);
            mEtPressureTime.setText(InsulinUtils.getTimeText(mGlucoseReading.created));
            mEtPressureDate.setText(InsulinUtils.getDateText(mGlucoseReading.created));
            mEtComment.setText(mGlucoseReading.comment);
        } else {
            Date today = new Date();
            mEtPressureTime.setText(InsulinUtils.getTimeText(today));
            mEtPressureDate.setText(InsulinUtils.getDateText(today));

        }
        */
    }

    public void cancel(View view) {
        finish();
    }

    public void add_update(View view) {

        if (mMode == InsulinConstants.MODE_ACTIONS_EDIT_ADD) {
           // mGlucoseReading = new GlucoseReading();
        }

        String date = mEtPressureDate.getText().toString();
        String time = mEtPressureTime.getText().toString();
        String comm = mEtComment.getText().toString();
        String note = mSpNotes.getSelectedItem().toString();

        Date created = InsulinUtils.parseDateTimeText(time, date);
/*
        mGlucoseReading.value = fvalue;
        mGlucoseReading.created = created;
        mGlucoseReading.notes = note;
        mGlucoseReading.comment = comm;

        if (created != null && fvalue > 0.0) {
            mGlucoseReading.save();
        }
*/
        finish();
    }
}
