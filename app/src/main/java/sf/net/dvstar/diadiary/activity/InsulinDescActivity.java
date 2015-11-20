package sf.net.dvstar.diadiary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.buzzingandroid.ui.HSVColorPickerDialog;

import java.util.List;

import sf.net.dvstar.diadiary.R;
import sf.net.dvstar.diadiary.adapters.InsulinDescAdapter;
import sf.net.dvstar.diadiary.database.InsulinItem;
import sf.net.dvstar.diadiary.utilitis.CommonConstants;


public class InsulinDescActivity extends AppCompatActivity {


    Button btColor;
    LinearLayout llColor;
    ListView insulinListView;
    private List<InsulinItem> mInsulins;
    private ListView mLvInsulins;
    private List<InsulinItem> mInsulinsItems;

    @Override
    protected void onResume() {
        super.onResume();
        setListViewContent();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insulin_desc);

        btColor = (Button) findViewById(R.id.bt_color);
        llColor = (LinearLayout) findViewById(R.id.ll_color);

        setListViewContent();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                */
                showAddInsulinsDesc(CommonConstants.MODE_ACTIONS_EDIT_ADD, view, null);
            }
        });
    }

    private void setListViewContent() {
        mLvInsulins = (ListView) findViewById(R.id.insulin_desc_list);
        mInsulins = getInsulins();
        InsulinDescAdapter adapter = new InsulinDescAdapter(this, mInsulins);
        mLvInsulins.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
//                Toast.makeText(getBaseContext(),"itemSelect: position = " + position + ", id = "
//                        + id+", "+parent.getAdapter().getItem(position), Toast.LENGTH_SHORT).show();
                showAddInsulinsDesc(CommonConstants.MODE_ACTIONS_EDIT_ITEM, view, (InsulinItem) parent.getAdapter().getItem(position));
            }
        });
        mLvInsulins.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
//                Toast.makeText(getBaseContext(),"itemSelect: position = " + position + ", id = "
//                        + id+" "+parent.getSelectedItem(), Toast.LENGTH_SHORT).show();

            }

            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getBaseContext(), "itemSelect: nothing", Toast.LENGTH_SHORT).show();
            }
        });
        mLvInsulins.setAdapter(adapter);
    }



    private  int mStoredColor = 0xFF4488AA;

    public void selectColor(View view){
        HSVColorPickerDialog cpd = new HSVColorPickerDialog( this, mStoredColor, new HSVColorPickerDialog.OnColorSelectedListener() {
            @Override
            public void colorSelected(Integer color) {
                mStoredColor = color;
                llColor.setBackgroundColor( color );
            }
        });
        cpd.setTitle("Pick a color");
        cpd.show();
    }

    public void cancel(View view){
        finish();
    }

    public List<InsulinItem> getInsulins() {

        List<InsulinItem> insulinList = new Select().from(InsulinItem.class).execute();

        return insulinList;
    }

    private void showAddInsulinsDesc(int mode, View view, InsulinItem item) {

        Intent intent = new Intent(this, InsulinDescAddActivity.class);

        intent.putExtra(CommonConstants.KEY_INTENT_EXTRA_EDIT_MODE,mode);
        if(item != null)
            intent.putExtra(CommonConstants.KEY_INTENT_EXTRA_EDIT_ITEM, item);

        this.startActivity(intent);

    }


}
