package sf.net.dvstar.diadiary.activity;

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

import com.activeandroid.Model;
import com.activeandroid.query.Select;
import com.buzzingandroid.ui.HSVColorPickerDialog;

import java.util.List;

import sf.net.dvstar.diadiary.R;
import sf.net.dvstar.diadiary.adapters.InsulinCommonAdapter;
import sf.net.dvstar.diadiary.database.InsulinFirm;
import sf.net.dvstar.diadiary.database.InsulinItem;
import sf.net.dvstar.diadiary.database.InsulinOrigin;
import sf.net.dvstar.diadiary.database.InsulinType;
import sf.net.dvstar.diadiary.insulins.InsulinConstants;

public class InsulinDescAddActivity extends AppCompatActivity {

    private Button btColor;
    private LinearLayout llColor;
    private int mMode;
    private InsulinItem mInsulinItem;
    private EditText mEtInsulinName;


    private Spinner mSPFirmList;
    private Spinner mSPInsulinOrigin;
    private Spinner mSPInsulinType;

    private Spinner mSPSttMeasure;
    private Spinner mSPMaxMeasure;
    private Spinner mSPEndMeasure;

    private Button btAdd;
    private EditText mEtSttMin;
    private EditText mEtSttMax;
    private EditText mEtMaxMin;
    private EditText mEtMaxMax;
    private EditText mEtEndMin;
    private EditText mEtEndMax;
    private TextView mTvLabelModeAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insulin_desc_add);

        mMode = getIntent().getExtras().getInt(InsulinConstants.KEY_INTENT_EXTRA_EDIT_MODE);

        btColor = (Button) findViewById(R.id.bt_color);
        btAdd = (Button) findViewById(R.id.bt_insulin_add);

        mTvLabelModeAdd = (TextView) findViewById(R.id.tv_label_mode_add);

        mEtSttMin = (EditText) findViewById(R.id.et_insulin_desc_start_min);
        mEtSttMax = (EditText) findViewById(R.id.et_insulin_desc_start_max);

        mEtMaxMin = (EditText) findViewById(R.id.et_insulin_desc_max_min);
        mEtMaxMax = (EditText) findViewById(R.id.et_insulin_desc_max_max);

        mEtEndMin = (EditText) findViewById(R.id.et_insulin_desc_end_min);
        mEtEndMax = (EditText) findViewById(R.id.et_insulin_desc_end_max);

        mSPSttMeasure = (Spinner) findViewById(R.id.sp_insulin_desc_start_measure);
        mSPMaxMeasure = (Spinner) findViewById(R.id.sp_insulin_desc_max_measure);
        mSPEndMeasure = (Spinner) findViewById(R.id.sp_insulin_desc_end_measure);

        llColor = (LinearLayout) findViewById(R.id.ll_color);
        mEtInsulinName = (EditText) findViewById(R.id.et_insulin_desc_name);

        // адаптер

        List<Model> insulinFirmList = new Select().from(InsulinFirm.class).execute();
        InsulinCommonAdapter adapterInsulinFirmAdapter = new InsulinCommonAdapter(this, insulinFirmList);
        adapterInsulinFirmAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //adapter.setDropDownViewResource(R.layout.insulin_desc_item);


        mSPFirmList = (Spinner) findViewById(R.id.sp_insulin_desc_firm);
        mSPFirmList.setAdapter(adapterInsulinFirmAdapter);
        // заголовок
        //spinner.setPrompt("Title");
        // выделяем элемент
        //spinner.setSelection(2);
        // устанавливаем обработчик нажатия
        mSPFirmList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
                //Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        mSPInsulinType = (Spinner) findViewById(R.id.sp_insulin_desc_type);
        List<Model> insulinTypeList = new Select().from(InsulinType.class).execute();
        InsulinCommonAdapter adapterInsulinTypeListAdapter = new InsulinCommonAdapter(this, insulinTypeList);
        adapterInsulinTypeListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSPInsulinType.setAdapter(adapterInsulinTypeListAdapter);

        mSPInsulinOrigin = (Spinner) findViewById(R.id.sp_insulin_desc_origin);
        List<Model> insulinOriginList = new Select().from(InsulinOrigin.class).execute();
        InsulinCommonAdapter adapterInsulinOriginListAdapter = new InsulinCommonAdapter(this, insulinOriginList);
        adapterInsulinOriginListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSPInsulinOrigin.setAdapter(adapterInsulinOriginListAdapter);

        if (mMode == InsulinConstants.MODE_ACTIONS_EDIT_ITEM) {
            mInsulinItem = (InsulinItem) getIntent().getExtras().getSerializable(InsulinConstants.KEY_INTENT_EXTRA_EDIT_ITEM);
            //mEtInsulinName.setEnabled(false);
            mEtInsulinName.setFocusable(false);
            mEtInsulinName.setBackgroundColor(mInsulinItem.color);
            mEtInsulinName.setText(mInsulinItem.name);
            llColor.setBackgroundColor(mInsulinItem.color);

            ArrayAdapter aadapter = ((ArrayAdapter)mSPFirmList.getAdapter());

            //ArrayList a = new ArrayList();
            //a.indexOf("2");

            int index = aadapter.getPosition( mInsulinItem.firm );

            //Toast.makeText(getBaseContext(), "mSPFirmList Position = " + index, Toast.LENGTH_SHORT).show();

            if (index>=0) mSPFirmList.setSelection(index);

            mEtSttMin.setText(""+mInsulinItem.start_min);
            mEtSttMax.setText(""+mInsulinItem.start_max);

            mEtMaxMin.setText(""+mInsulinItem.work_min);
            mEtMaxMax.setText(""+mInsulinItem.work_max);

            mEtEndMin.setText(""+mInsulinItem.ends_min);
            mEtEndMax.setText(""+mInsulinItem.ends_max);

            btAdd.setText( getResources().getString(R.string.button_insulin_update) );

            mSPSttMeasure.setSelection(getMeasureIndex(mInsulinItem.start_measure));
            mSPMaxMeasure.setSelection(getMeasureIndex(mInsulinItem.work_measure));
            mSPEndMeasure.setSelection(getMeasureIndex(mInsulinItem.ends_measure));

            mTvLabelModeAdd.setText( getResources().getString(R.string.label_mode_edit) );

        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private int getMeasureIndex(String measure) {
        int ret = 0;
        // TODO use the resource for find index
        if (measure.startsWith("h")) ret = 1;
        return ret;
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
        finish();
    }
}
