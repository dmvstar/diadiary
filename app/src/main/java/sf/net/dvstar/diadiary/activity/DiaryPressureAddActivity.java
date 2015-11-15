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
import sf.net.dvstar.diadiary.database.PressureReading;
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

    private PressureReading mPressureReading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_pressure_add);

        mContext = this;
        mNotesList = Arrays.asList(getResources().getStringArray(R.array.dialog_notes_list));
        mMode = getIntent().getExtras().getInt(InsulinConstants.KEY_INTENT_EXTRA_EDIT_MODE);

        mEtPressureSystoleValue = (EditText) findViewById(R.id.et_pressure_systole_value);
        mEtPressureDiastolicValue = (EditText) findViewById(R.id.et_pressure_diastolic_value);

        mEtPressureDate = (EditText) findViewById(R.id.et_date);
        mEtPressureTime = (EditText) findViewById(R.id.et_time);
        mEtComment = (EditText) findViewById(R.id.et_comment);
        mBtAdd = (Button) findViewById(R.id.bt_confirm);
        mSpNotes = (Spinner) findViewById(R.id.sp_notes);

        SetDateTime.SetTime fromTime = new SetDateTime.SetTime(mEtPressureTime, this);
        SetDateTime.SetDate fromDate = new SetDateTime.SetDate(mEtPressureDate, this);

        if (mMode == InsulinConstants.MODE_ACTIONS_EDIT_ITEM) {
            mBtAdd.setText( getResources().getString(R.string.button_insulin_update) );
            long iId = getIntent().getExtras().getLong(InsulinConstants.KEY_INTENT_EXTRA_ROW_ID);
            mPressureReading = new Select().from(PressureReading.class).where("id = ?", iId).executeSingle();

            int indexNotes = mPressureReading.note;
            if(indexNotes>=0) mSpNotes.setSelection(indexNotes);
            mEtPressureSystoleValue.setText("" + mPressureReading.systole_value);
            mEtPressureDiastolicValue.setText("" + mPressureReading.diastolic_value);
            mEtPressureTime.setText(InsulinUtils.getTimeText(mPressureReading.time));
            mEtPressureDate.setText(InsulinUtils.getDateText(mPressureReading.time));
            mEtComment.setText(mPressureReading.comment);
        } else {
            Date today = new Date();
            mEtPressureTime.setText(InsulinUtils.getTimeText(today));
            mEtPressureDate.setText(InsulinUtils.getDateText(today));
        }
    }

    public void cancel(View view) {
        finish();
    }

    public void confirm(View view) {

        if (mMode == InsulinConstants.MODE_ACTIONS_EDIT_ADD) {
            mPressureReading = new PressureReading();
        }

        int syst = Integer.parseInt(mEtPressureSystoleValue.getText().toString());
        int dias = Integer.parseInt(mEtPressureDiastolicValue.getText().toString());

        String date = mEtPressureDate.getText().toString();
        String stime = mEtPressureTime.getText().toString();
        String comm = mEtComment.getText().toString();

        int note = mSpNotes.getSelectedItemPosition();

        Date time = InsulinUtils.parseDateTimeText(stime, date);

        mPressureReading.time = time;
        mPressureReading.systole_value = syst;
        mPressureReading.diastolic_value = dias;
        mPressureReading.note = note;
        mPressureReading.comment = comm;

        if (time != null && syst > 0 && dias > 0) {
            mPressureReading.save();
        }

        finish();
    }
}
