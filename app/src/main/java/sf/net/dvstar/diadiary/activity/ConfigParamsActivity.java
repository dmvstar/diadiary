package sf.net.dvstar.diadiary.activity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import sf.net.dvstar.diadiary.R;

public class ConfigParamsActivity extends AppCompatActivity implements ActivitySaving, AdapterView.OnItemSelectedListener {

    private Context mContext;
    private Spinner mSpSelectTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_params);
        mContext = this;

        mSpSelectTheme = (Spinner) findViewById(R.id.sp_config_global_theme);
        mSpSelectTheme.setOnItemSelectedListener(this);

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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Toast.makeText(getBaseContext(), "Position = " + position+" "+getApplication().getTheme().toString(), Toast.LENGTH_SHORT).show();
        switch(position){
            case 0: // dark
                getApplication().setTheme(R.style.AppTheme_Dark_NoActionBar);
                getApplicationContext().setTheme(R.style.AppTheme_Dark_NoActionBar);
                setTheme(R.style.AppTheme_Dark_NoActionBar);
                break;
            case 1: // light
                getApplication().setTheme(R.style.AppTheme_Light_NoActionBar);
                getApplicationContext().setTheme(R.style.AppTheme_Light_NoActionBar);
                setTheme(R.style.AppTheme_Light_NoActionBar);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
