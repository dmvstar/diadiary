package sf.net.dvstar.diadiary.activity;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.activeandroid.util.Log;
import com.buzzingandroid.ui.HSVColorPickerDialog;

import java.util.List;

import sf.net.dvstar.diadiary.R;
import sf.net.dvstar.diadiary.adapters.InsulinDescAdapter;
import sf.net.dvstar.diadiary.database.InsulinInjection;
import sf.net.dvstar.diadiary.database.InsulinItem;

import sf.net.dvstar.diadiary.utilitis.CommonUtils;
import sf.net.dvstar.diadiary.utilitis.CommonConstants;
import sf.net.dvstar.diadiary.utilitis.SetDateTime;


public class InsulinInjectAddActivity extends AppCompatActivity {

    private static final java.lang.String TAG = "InsulinInjectAddActivity";
    private Context mContext;
    private Button mBtColor;
    private Button mBtAdd;
    private LinearLayout llColor;
    private int mMode;
    private Spinner mSpInsulins;
    private EditText mEtFromTime;
    private EditText mEtFromDate;
    private EditText mEtDose;
    private EditText mEtComment;
    private Spinner mSpInjectType;
    private Spinner mSpNotes;

    private InsulinInjection mInjection;
    private TextView mTvLabelModeAdd;
    private long mInjectionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_inject_add);
        mContext = this;
        mMode = getIntent().getExtras().getInt(CommonConstants.KEY_INTENT_EXTRA_EDIT_MODE);

        mBtAdd = (Button) findViewById(R.id.bt_confirm);

        mBtColor = (Button) findViewById(R.id.bt_color);
        llColor = (LinearLayout) findViewById(R.id.ll_color);
        mEtFromTime = (EditText) findViewById(R.id.et_inject_time);
        mEtFromDate = (EditText) findViewById(R.id.et_inject_date);
        mEtDose = (EditText) findViewById(R.id.et_inject_dose);
        mEtComment = (EditText) findViewById(R.id.et_inject_comment);
        mSpInjectType = (Spinner) findViewById(R.id.sp_inject_type);
        mTvLabelModeAdd = (TextView) findViewById(R.id.tv_label_mode_add);
        mSpNotes = (Spinner) findViewById(R.id.sp_notes);

        SetDateTime.SetTime fromTime = new SetDateTime.SetTime(mEtFromTime, this);
        SetDateTime.SetDate fromDate = new SetDateTime.SetDate(mEtFromDate, this);

        // адаптер
        List<InsulinItem> insulinList = new Select().from(InsulinItem.class).execute();

        InsulinDescAdapter adapter = new InsulinDescAdapter(this, insulinList);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.insulin_desc_item);

        mSpInsulins = (Spinner) findViewById(R.id.sp_inject_insulin);
        mSpInsulins.setAdapter(adapter);
        // заголовок
        mSpInsulins.setPrompt("Title");
        // выделяем элемент
        //mSpInsulins.setSelection(2);
        // устанавливаем обработчик нажатия
        mSpInsulins.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
                //Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
                InsulinItem insulinItem = (InsulinItem) mSpInsulins.getSelectedItem();
                llColor.setBackgroundColor( insulinItem.color );
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        mSpInjectType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position== InsulinInjection.INJECTION_PLAN_REGULAR) {
                    mEtFromDate.setEnabled(false);
                    mEtFromDate.setFocusable(false);
                    mEtFromDate.setText("");
                } else {
                    mEtFromDate.setEnabled(true);
                    mEtFromDate.setFocusableInTouchMode(true);
                    mEtFromDate.setFocusable(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (mMode == CommonConstants.MODE_ACTIONS_EDIT_ITEM) {

            mInjection = (InsulinInjection) getIntent().getExtras().getSerializable(CommonConstants.KEY_INTENT_EXTRA_EDIT_ITEM);
            mInjectionId = getIntent().getExtras().getLong(CommonConstants.KEY_INTENT_EXTRA_ROW_ID);

            mInjection = new Select().from(InsulinInjection.class).where("id = ?", mInjectionId).executeSingle();

            //mEtInsulinName.setEnabled(false);
            /*
            mEtInsulinName.setFocusable(false);
            mEtInsulinName.setBackgroundColor(mInsulinItem.color);
            mEtInsulinName.setText(mInsulinItem.name);
            */

            Log.v(TAG, "!!! " + mInjection.getId() );
            Log.v(TAG, "!!! " + mInjection.toString() );

            llColor.setBackgroundColor(mInjection.color);

            ArrayAdapter aadapter = ((ArrayAdapter)mSpInsulins.getAdapter());

            //ArrayList a = new ArrayList();
            //a.indexOf("2");

            int index = aadapter.getPosition( mInjection.insulin );
            if (index>=0) mSpInsulins.setSelection(index);
            //Toast.makeText(getBaseContext(), "mSPFirmList Position = " + index, Toast.LENGTH_SHORT).show();

            mEtDose.setText(""+mInjection.dose);
            mEtFromTime.setText( CommonUtils.getTimeText(mInjection.time) );
            mEtFromDate.setText( CommonUtils.getDateText(mInjection.date) );
            mEtComment.setText(mInjection.comment);
            if (mInjection.note>=0) mSpNotes.setSelection(mInjection.note);

            mBtAdd.setText( getResources().getString(R.string.label_mode_update) );

            mTvLabelModeAdd.setText( getResources().getString(R.string.label_mode_edit) );

            mSpInjectType.setSelection( mInjection.plan );

        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }


    private  int mStoredColor = 0xFF4488CC;

    public void selectColor(View view){
        HSVColorPickerDialog cpd = new HSVColorPickerDialog( this, mStoredColor, new HSVColorPickerDialog.OnColorSelectedListener() {
            @Override
            public void colorSelected(Integer color) {
                mStoredColor = color;
                llColor.setBackgroundColor( color );
            }
        });
        cpd.setTitle( "Pick a color" );
        cpd.show();
    }

    public void cancel(View view){
        finish();
    }

    public void confirm(View view){

        ColorDrawable viewColor = (ColorDrawable) llColor.getBackground();
        InsulinItem insulinItem = (InsulinItem) mSpInsulins.getSelectedItem();

        if(mMode == CommonConstants.MODE_ACTIONS_EDIT_ADD) {
            mInjection = new InsulinInjection();
        }
        int note = mSpNotes.getSelectedItemPosition();

        mInjection.insulin = insulinItem;
        mInjection.plan = mSpInjectType.getSelectedItemPosition();
        mInjection.color = viewColor.getColor();

        mInjection.dose = Integer.parseInt(mEtDose.getText().toString());
        mInjection.time = CommonUtils.parseTimeText(mEtFromTime.getText().toString());
        mInjection.date = CommonUtils.parseDateText(mEtFromDate.getText().toString());
        mInjection.note = note;

        mInjection.comment = mEtComment.getText().toString();

        Log.v(TAG, "!!! " + mInjection.getId() );
        Log.v(TAG, "!!! " + mInjection.toString() );

        if( mInjection.dose>0 && mInjection.time != null ) {
            mInjection.save();
        } else {
            Toast.makeText(getBaseContext(), "Not Enough data (dose, time ...) for store !", Toast.LENGTH_SHORT).show();
        }

        finish();
    }
}
