package sf.net.dvstar.diadiary.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.activeandroid.query.Select;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import sf.net.dvstar.diadiary.R;

import sf.net.dvstar.diadiary.database.MenuReading;
import sf.net.dvstar.diadiary.database.ProductMenuDesc;
import sf.net.dvstar.diadiary.database.ProductMenuItem;
import sf.net.dvstar.diadiary.utilitis.CommonConstants;
import sf.net.dvstar.diadiary.utilitis.CommonUtils;
import sf.net.dvstar.diadiary.utilitis.SetDateTime;

public class DiaryMenuAddActivity extends AppCompatActivity {

    private EditText mEtDate;
    private EditText mEtTime;
    private Spinner mSpNotes;
    private Spinner mSpMenus;
    private EditText mEtComment;
    private int mMode;
    private Context mContext;

    private Button mBtConfirm;
    private List mNotesList;

    private MenuReading mMenuReading;
    private List<ProductMenuDesc> mProductMenuDescList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_menu_add);

        mContext = this;
        mMode = getIntent().getExtras().getInt(CommonConstants.KEY_INTENT_EXTRA_EDIT_MODE);

        mNotesList = Arrays.asList(getResources().getStringArray(R.array.dialog_notes_list));

        mEtDate = (EditText) findViewById(R.id.et_menu_dinner_date);
        mEtTime = (EditText) findViewById(R.id.et_menu_dinner_time);
        mEtComment = (EditText) findViewById(R.id.et_comment);
        mBtConfirm = (Button) findViewById(R.id.bt_confirm);

        mSpNotes = (Spinner) findViewById(R.id.sp_notes);
        mSpMenus = (Spinner) findViewById(R.id.sp_menu_dinner);

        SetDateTime.SetTime fromTime = new SetDateTime.SetTime(mEtTime, this);
        SetDateTime.SetDate fromDate = new SetDateTime.SetDate(mEtDate, this);

        mProductMenuDescList = new Select().from(ProductMenuDesc.class).execute();

        if (mMode == CommonConstants.MODE_ACTIONS_EDIT_ITEM) {
            mBtConfirm.setText(getResources().getString(R.string.label_mode_update));
            long iId = getIntent().getExtras().getLong(CommonConstants.KEY_INTENT_EXTRA_ROW_ID);
            mMenuReading = new Select().from(MenuReading.class).where("id = ?", iId).executeSingle();

            int indexNotes = mMenuReading.note;
            if(indexNotes>=0) mSpNotes.setSelection(indexNotes);


            mEtTime.setText(CommonUtils.getTimeText(mMenuReading.time));
            mEtDate.setText(CommonUtils.getDateText(mMenuReading.time));
            mEtComment.setText(mMenuReading.comment);



        } else {
            Date today = new Date();
            mEtTime.setText(CommonUtils.getTimeText(today));
            mEtDate.setText(CommonUtils.getDateText(today));
            int indexNotes = CommonUtils.getNotesIndexByTime(today);
            if(indexNotes>=0) mSpNotes.setSelection(indexNotes);

            mSpMenus.setAdapter(new ArrayAdapter(this, R.layout.spinner_item,  mProductMenuDescList));

        }
    }

    public void cancel(View view) {
        finish();
    }

    public void confirm(View view) {

        if (mMode == CommonConstants.MODE_ACTIONS_EDIT_ADD) {
            mMenuReading = new MenuReading();
        }

        String date = mEtDate.getText().toString();
        String stime = mEtTime.getText().toString();
        String comm = mEtComment.getText().toString();

        int note = mSpNotes.getSelectedItemPosition();

        Date time = CommonUtils.parseDateTimeText(stime, date);

        mMenuReading.time = time;

        mMenuReading.note = note;
        mMenuReading.comment = comm;

        if (time != null) {
            mMenuReading.save();
        }

        finish();
    }
}
