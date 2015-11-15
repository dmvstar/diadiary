package sf.net.dvstar.diadiary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import sf.net.dvstar.diadiary.R;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    public void showUserProfile(View v){
        Intent intent = new Intent(this, UserProfileActivity.class);
        this.startActivity(intent);
    }

    public void showSystemSetting(View v){

    }
}
