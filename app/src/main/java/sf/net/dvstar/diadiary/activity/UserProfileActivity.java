package sf.net.dvstar.diadiary.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.activeandroid.query.Select;

import sf.net.dvstar.diadiary.R;
import sf.net.dvstar.diadiary.database.UserProfile;
import sf.net.dvstar.diadiary.utilitis.CommonConstants;
import sf.net.dvstar.diadiary.utilitis.UIInterfaceYesNo;
import sf.net.dvstar.diadiary.utilitis.UIUtilities;

public class UserProfileActivity extends AppCompatActivity implements ActivitySaving, UIInterfaceYesNo {

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

        fillFieldData();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }

    public void addK1(View view) {
        UIUtilities.showYesNoDialog(1, view
                , getResources().getString(R.string.dialog_add_k1_title)
                , this);
    }

    public void delK1(View view) {
        UIUtilities.showYesNoDialog(2, view
                , getResources().getString(R.string.dialog_del_k1_title)
                , this);
    }

    @Override
    public void dialogActionYes(int aFrom){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Text");
        alert.setMessage("Enter Text :");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
        alert.setView(input);


        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                if(value.length()>0){
                    addK1Value(value);
                }
                return;
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        alert.show();

        Toast.makeText(this, "dialogActionYes "+aFrom +" user = "+mUserProfile.toString(),
                Toast.LENGTH_SHORT).show();
    }

    private void addK1Value(String value) {
        Toast.makeText(this, "addK1Value "+value+" user = "+mUserProfile.toString(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void dialogActionNo(int aFrom){
        Toast.makeText(this, "dialogActionNo "+aFrom,
                Toast.LENGTH_SHORT).show();
    }

    public void cancel(View view) {
        finish();
    }

    public void confirm(View view) {
        saveFieldData();
        finish();
    }


    @Override
    public void fillFieldData() {
        mUserProfile = new Select().from(UserProfile.class).where("id = 1").executeSingle();
        if(mUserProfile!=null) mMode = CommonConstants.MODE_ACTIONS_EDIT_ITEM;
        else mMode = CommonConstants.MODE_ACTIONS_EDIT_ADD;

        if (mMode == CommonConstants.MODE_ACTIONS_EDIT_ITEM) {
            mBtAdd.setText( getResources().getString(R.string.button_insulin_update) );
            mEtUserName.setText(mUserProfile.name);
            mEtUserAge.setText(mUserProfile.age);
        }
    }

    @Override
    public void saveFieldData() {
        if (mMode == CommonConstants.MODE_ACTIONS_EDIT_ADD) {
            mUserProfile = new UserProfile();
        }

        String name = mEtUserName.getText().toString();
        String age = mEtUserAge.getText().toString();

        mUserProfile.name = name;
        mUserProfile.age = age;

        if (name.length()>0) {
            mUserProfile.save();
        }
    }

}
