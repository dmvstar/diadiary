package sf.net.dvstar.diadiary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.query.Select;

import java.util.List;

import sf.net.dvstar.diadiary.R;
import sf.net.dvstar.diadiary.adapters.MenuDescAdapter;
import sf.net.dvstar.diadiary.database.ProductGroup;
import sf.net.dvstar.diadiary.database.ProductMenuDesc;
import sf.net.dvstar.diadiary.database.ProductMenuItem;

import sf.net.dvstar.diadiary.utilitis.CommonConstants;

public class ProdMenuActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView mMenuItems;

    @Override
    protected void onResume(){
        super.onResume();
        fillMenuList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prod_menu);
        mMenuItems = (ListView) findViewById(R.id.lv_prod_menu);
        fillMenuList();
        // https://github.com/Clans/FloatingActionButton
        com.github.clans.fab.FloatingActionButton fab = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProducMenuAddActivity();
            }
        });

    }

    private void fillMenuList() {
        List<ProductMenuDesc> list = new Select().from(ProductMenuDesc.class).execute();
        //ArrayAdapter adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, list);
        //ArrayAdapter adapter = new ArrayAdapter<>(this,R.layout.item_menu_detail, list);
        MenuDescAdapter adapter = new MenuDescAdapter(this, list);
        mMenuItems.setAdapter(adapter);
        mMenuItems.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_prod_memu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.action_product_group) {
            showProductGroupActivity();
        }

        if (id == R.id.action_product_item) {
            showProducItemActivity();
        }

        if (id == R.id.action_product_menu_add) {
            showProducMenuAddActivity();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Show add product dialog
     */
    private void showProducMenuAddActivity() {
        Intent intent = new Intent(this, ProdMenuAddActivity.class);
        this.startActivity(intent);
    }

    private void showProducItemActivity() {
        Intent intent = new Intent(this, ProdItemActivity.class);
        this.startActivity(intent);
    }

    private void showProductGroupActivity() {
        Intent intent = new Intent(this, ProdGroupActivity.class);
        this.startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ProductMenuDesc item = (ProductMenuDesc) mMenuItems.getItemAtPosition(position);

        Toast.makeText(this, item.toString(),
                Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, ProdMenuAddActivity.class);

        if (item != null) {
            long rowId = item.getId();
            intent.putExtra(CommonConstants.KEY_INTENT_EXTRA_ROW_ID, rowId);
            intent.putExtra(CommonConstants.KEY_INTENT_EXTRA_EDIT_MODE, CommonConstants.MODE_ACTIONS_EDIT_ITEM);
        }

        this.startActivity(intent);

    }
}
