package sf.net.dvstar.diadiary.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_common_add);

        mContext = this;
        mNotesList = Arrays.asList(getResources().getStringArray(R.array.dialog_notes_list));
        //mMode = getIntent().getExtras().getInt(CommonConstants.KEY_INTENT_EXTRA_EDIT_MODE);

        mBtAdd = (Button) findViewById(R.id.bt_confirm);
        mSpNotes = (Spinner) findViewById(R.id.sp_notes);
        mSpActions = (Spinner) findViewById(R.id.sp_actions_additional);

        String list[] = getResources().getStringArray(R.array.actions_additional_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item,list);
        mSpActions.setAdapter(adapter);

    }

    public void cancel(View view) {
        finish();
    }

    public void confirm(View view) {
        finish();
    }
}
