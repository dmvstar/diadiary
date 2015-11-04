package sf.net.dvstar.diadiary.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.activeandroid.query.Select;

import java.util.Date;

import sf.net.dvstar.diadiary.R;
import sf.net.dvstar.diadiary.database.GlucoseReading;
import sf.net.dvstar.diadiary.insulins.InsulinConstants;
import sf.net.dvstar.diadiary.insulins.InsulinUtils;
import sf.net.dvstar.diadiary.insulins.SetDateTime;

public class DiaryGlucoseAddActivity extends AppCompatActivity {

    private EditText mEtGlucoseValue;
    private EditText mEtGlucoseDate;
    private EditText mEtGlucoseTime;
    private Spinner mSpGlucoseWhen;
    private EditText mEtGlucoseComment;
    private int mMode;
    private Context mContext;
    private GlucoseReading mGlucoseReading;
    private Button mBtAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_glucose_add);

        mContext = this;
        mMode = getIntent().getExtras().getInt(InsulinConstants.KEY_INTENT_EXTRA_INJECT_EDIT_MODE);

        mEtGlucoseValue = (EditText) findViewById(R.id.et_glucose_value);
        mEtGlucoseDate = (EditText) findViewById(R.id.et_glucose_date);
        mEtGlucoseTime = (EditText) findViewById(R.id.et_glucose_time);
        mSpGlucoseWhen = (Spinner) findViewById(R.id.sp_glucose_when);
        mEtGlucoseComment = (EditText) findViewById(R.id.et_comment);
        mBtAdd = (Button) findViewById(R.id.bt_add_update);

        SetDateTime.SetTime fromTime = new SetDateTime.SetTime(mEtGlucoseTime, this);
        SetDateTime.SetDate fromDate = new SetDateTime.SetDate(mEtGlucoseDate, this);

        if (mMode == InsulinConstants.MODE_ACTIONS_EDIT_ITEM) {
            mBtAdd.setText( getResources().getString(R.string.button_insulin_update) );
            long iId = getIntent().getExtras().getLong(InsulinConstants.KEY_INTENT_EXTRA_ROW_ID);
            mGlucoseReading = new Select().from(GlucoseReading.class).where("id = ?", iId).executeSingle();

            mEtGlucoseValue.setText("" + mGlucoseReading.value);
            mEtGlucoseTime.setText(InsulinUtils.getTimeText(mGlucoseReading.created));
            mEtGlucoseDate.setText(InsulinUtils.getDateText(mGlucoseReading.created));
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

    public void add_update(View view) {

        if (mMode == InsulinConstants.MODE_ACTIONS_EDIT_ADD) {
            mGlucoseReading = new GlucoseReading();
        }

        String value = mEtGlucoseValue.getText().toString();
        float fvalue = Float.parseFloat(value);
        String date = mEtGlucoseDate.getText().toString();
        String time = mEtGlucoseTime.getText().toString();
        String comm = mEtGlucoseComment.getText().toString();
        String note = mSpGlucoseWhen.getSelectedItem().toString();
        Date created = InsulinUtils.parseDateTimeText(time, date);

        mGlucoseReading.value = fvalue;
        mGlucoseReading.created = created;
        mGlucoseReading.notes = note;
        mGlucoseReading.comment = comm;

        if (created != null && fvalue > 0.0) {
            mGlucoseReading.save();
        }

        finish();
    }
}
