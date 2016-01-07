package sf.net.dvstar.diadiary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import sf.net.dvstar.diadiary.R;

public class ConfigContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configs);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    public void showUserProfile(View v){
        Intent intent = new Intent(this, ConfigProfileActivity.class);
        this.startActivity(intent);
    }

    public void showSystemSetting(View v){
        Intent intent = new Intent(this, ConfigParamsActivity.class);
        this.startActivity(intent);
    }
}
