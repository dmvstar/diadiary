package sf.net.dvstar.diadiary.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.activeandroid.query.Select;

import java.util.Date;
import java.util.List;

import sf.net.dvstar.diadiary.R;
import sf.net.dvstar.diadiary.database.UserProfile;
import sf.net.dvstar.diadiary.database.UserProfileCoeff;
import sf.net.dvstar.diadiary.utilitis.CommonConstants;
import sf.net.dvstar.diadiary.utilitis.UIInterfaceYesNo;
import sf.net.dvstar.diadiary.utilitis.UIUtilities;

public class UserProfileActivity extends AppCompatActivity implements ActivitySaving, UIInterfaceYesNo {

    private static final int DIALOG_SHOW_INPUT_K1 = 1;
    private static final int DIALOG_SHOW_DEL_K1 = 2;

    private Context mContext;
    private int mMode;
    private Button mBtConfirm;
    private UserProfile mUserProfile;
    private EditText mEtUserName;
    private EditText mEtUserAge;
    private EditText mEtUserDateOfBirth;
    private EditText mEtUserGlucoseRangeMin;
    private EditText mEtUserGlucoseRangeMax;

    private Spinner mSpUserProfileGender;
    private Spinner mSpUserProfileDiabType;
    private Spinner mSpUserProfileK1;
    private Spinner mSpUserGlucoseMeasure;
    private List<UserProfileCoeff> mLsttUserProfileCoeff;
    private EditText mEtUserGrowth;
    private EditText mEtUserWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        mContext = this;
        mBtConfirm = (Button) findViewById(R.id.bt_confirm);

        mEtUserName = (EditText)findViewById(R.id.et_user_profile_name);
        mEtUserAge    = (EditText) findViewById(R.id.et_user_profile_age);
        mEtUserGrowth = (EditText) findViewById(R.id.et_user_profile_growth);
        mEtUserWeight = (EditText) findViewById(R.id.et_user_profile_weight);

        mEtUserDateOfBirth      = (EditText) findViewById(R.id.et_user_profile_date_of_birth);
        mEtUserGlucoseRangeMin  = (EditText) findViewById(R.id.et_user_glucose_range_min);
        mEtUserGlucoseRangeMax  = (EditText) findViewById(R.id.et_user_glucose_range_max);

        mSpUserGlucoseMeasure = (Spinner) findViewById(R.id.sp_user_glucose_measure);
        mSpUserProfileGender   = (Spinner) findViewById(R.id.sp_user_profile_gender);
        mSpUserProfileDiabType   = (Spinner) findViewById(R.id.sp_user_profile_diab_type);
        mSpUserProfileK1   = (Spinner) findViewById(R.id.sp_user_profile_k1);

        fillFieldData();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }

    public void addK1(View view) {
        UIUtilities.showInputDialog(DIALOG_SHOW_INPUT_K1, this
                , getResources().getString(R.string.dialog_add_k1_title)
                , getResources().getString(R.string.dialog_add_k1_message)
                , this
        );

//        UIUtilities.showYesNoDialog(1, view
//                , getResources().getString(R.string.dialog_add_k1_title)
//                , this);
    }

    public void delK1(View view) {
        UIUtilities.showYesNoDialog(DIALOG_SHOW_DEL_K1, view
                , getResources().getString(R.string.dialog_del_k1_title)  + " ("+mSpUserProfileK1.getSelectedItem()+")"
                , this);
    }

    @Override
    public void dialogActionYes(int aFrom, String value){

        if(aFrom== DIALOG_SHOW_INPUT_K1){
            addK1Value(value);
        }

        if(aFrom== DIALOG_SHOW_DEL_K1){
            delK1Value();
        }

//        UIUtilities.showInputDialog(1, this
//                , getResources().getString(R.string.dialog_add_k1_title)
//                , this
//        );

//        AlertDialog.Builder alert = new AlertDialog.Builder(this);
//        alert.setTitle("Text");
//        alert.setMessage("Enter Text :");
//
//        final EditText input = new EditText(this);
//        input.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
//        alert.setView(input);
//
//
//        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                String value = input.getText().toString();
//                if(value.length()>0){
//                    addK1Value(value);
//                }
//                return;
//            }
//        });
//
//        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//
//            public void onClick(DialogInterface dialog, int which) {
//                return;
//            }
//        });
//        alert.show();

        Toast.makeText(this, "dialogActionYes "+aFrom +" user = "+mUserProfile.toString(),
                Toast.LENGTH_SHORT).show();
    }

    private void delK1Value() {
        UserProfileCoeff coeff = (UserProfileCoeff) mSpUserProfileK1.getSelectedItem();
        coeff.delete();
        fillUserProfileCoeff();
    }

    private void addK1Value(String value) {
        Toast.makeText(this, "addK1Value "+value+" user = "+mUserProfile.toString(),
                Toast.LENGTH_SHORT).show();

        UserProfileCoeff coeff = new UserProfileCoeff();
        coeff.k1 = Float.parseFloat(value);
        coeff.created = new Date();
        coeff.save();

        fillUserProfileCoeff();
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
            mBtConfirm.setText(getResources().getString(R.string.button_insulin_update));
            mEtUserName.setText(mUserProfile.name);
            mEtUserAge.setText(mUserProfile.age);
            //mEtUserDateOfBirth.setText(""+mUserProfile.birth);
            mEtUserGlucoseRangeMin.setText(""+mUserProfile.prefRangeMix);
            mEtUserGlucoseRangeMax.setText("" + mUserProfile.prefRangeMax);

            mEtUserGrowth.setText("" + mUserProfile.growth);
            mEtUserWeight.setText("" + mUserProfile.weight);

            fillUserProfileCoeff();
        }
    }

    private void fillUserProfileCoeff() {
        mLsttUserProfileCoeff = new Select().from(UserProfileCoeff.class).execute();
        ArrayAdapter<UserProfileCoeff> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,mLsttUserProfileCoeff);
        mSpUserProfileK1.setAdapter(adapter);
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

        mUserProfile.prefRangeMix = Float.parseFloat(mEtUserGlucoseRangeMin.getText().toString());
        mUserProfile.prefRangeMax = Float.parseFloat(mEtUserGlucoseRangeMax.getText().toString());

        mUserProfile.growth = Integer.parseInt(mEtUserGrowth.getText().toString());
        mUserProfile.weight = Float.parseFloat(mEtUserWeight.getText().toString());


        if (name.length()>0) {
            mUserProfile.save();
        }
    }

}
