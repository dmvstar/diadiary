package sf.net.dvstar.diadiary.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.buzzingandroid.ui.HSVColorPickerDialog;

import java.util.Arrays;
import java.util.List;

import sf.net.dvstar.diadiary.R;

public class DiaryOtherAddActivity extends AppCompatActivity {

    private int mMode;
    private Context mContext;
    private Button mBtAdd;
    private List mNotesList;
    private Spinner mSpNotes;
    private Spinner mSpActions;


    private  int mStoredColor = 0xFF4488CC;
    private LinearLayout llColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_others_add);

        mContext = this;
        mNotesList = Arrays.asList(getResources().getStringArray(R.array.dialog_notes_list));
        //mMode = getIntent().getExtras().getInt(CommonConstants.KEY_INTENT_EXTRA_EDIT_MODE);

        mBtAdd = (Button) findViewById(R.id.bt_confirm);
        mSpNotes = (Spinner) findViewById(R.id.sp_notes);
        mSpActions = (Spinner) findViewById(R.id.sp_actions_additional);

        String list[] = getResources().getStringArray(R.array.actions_additional_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item,list);
        mSpActions.setAdapter(adapter);
        llColor = (LinearLayout) findViewById(R.id.ll_color);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public void cancel(View view) {
        finish();
    }

    public void confirm(View view) {
        finish();
    }

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

}
