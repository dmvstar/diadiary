package sf.net.dvstar.diadiary.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.query.Select;

import java.util.Date;

import sf.net.dvstar.diadiary.R;
import sf.net.dvstar.diadiary.database.PressureReading;
import sf.net.dvstar.diadiary.database.ProductGroup;
import sf.net.dvstar.diadiary.database.UserProfile;
import sf.net.dvstar.diadiary.insulins.InsulinConstants;
import sf.net.dvstar.diadiary.insulins.InsulinUtils;

public class UserProfileActivity extends AppCompatActivity {

    private Context mContext;
    private int mMode;
    private Button mBtAdd;
    private UserProfile mUserProfile;
    private EditText mEtUserName;
    private EditText mEtUserAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        mContext = this;
        mBtAdd = (Button) findViewById(R.id.bt_confirm);

        mEtUserName = (EditText)findViewById(R.id.et_user_profile_name);
        mEtUserAge = (EditText) findViewById(R.id.et_user_profile_age);

        mUserProfile = new Select().from(UserProfile.class).where("id = 1").executeSingle();
        if(mUserProfile!=null) mMode = InsulinConstants.MODE_ACTIONS_EDIT_ITEM;
        else mMode = InsulinConstants.MODE_ACTIONS_EDIT_ADD;

        if (mMode == InsulinConstants.MODE_ACTIONS_EDIT_ITEM) {
            mBtAdd.setText( getResources().getString(R.string.button_insulin_update) );
            mEtUserName.setText(mUserProfile.name);
            mEtUserAge.setText(mUserProfile.age);
        }

    }

    public void cancel(View view) {
        finish();
    }

    public void confirm(View view) {

        if (mMode == InsulinConstants.MODE_ACTIONS_EDIT_ADD) {
            mUserProfile = new UserProfile();
        }

        String name = mEtUserName.getText().toString();
        String age = mEtUserAge.getText().toString();

        mUserProfile.name = name;
        mUserProfile.age = age;

        if (name.length()>0) {
            mUserProfile.save();
        }

        finish();
    }


}
