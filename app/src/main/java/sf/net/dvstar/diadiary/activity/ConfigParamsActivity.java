package sf.net.dvstar.diadiary.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import sf.net.dvstar.diadiary.R;

public class ConfigParamsActivity extends AppCompatActivity implements ActivitySaving{

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_params);
        mContext = this;
    }

    @Override
    public void fillFieldData() {

    }

    @Override
    public void saveFieldData() {

    }

    @Override
    public void cancel(View view) {
        finish();
    }

    @Override
    public void confirm(View view) {
        finish();
    }


}
