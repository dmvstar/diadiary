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
import sf.net.dvstar.diadiary.database.ProductGroup;
import sf.net.dvstar.diadiary.insulins.InsulinConstants;

public class ProdMenuActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView mProdMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prod_menu);


/*
        mProdGroups = (ListView) findViewById(R.id.lv_prod_group);
        List<ProductGroup> list = new Select().from(ProductGroup.class).execute();
        ArrayAdapter adapter = new ArrayAdapter<ProductGroup>(this,android.R.layout.simple_list_item_1, list);
        mProdGroups.setAdapter(adapter);
        mProdGroups.setOnItemClickListener(this);
*/
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
        ProductGroup item = (ProductGroup) mProdMenu.getItemAtPosition(position);

        Toast.makeText(this, item.getListText(),
                Toast.LENGTH_SHORT).show();
/*
        Intent intent = new Intent(this, ProdItemActivity.class);

        if (item != null) {
            long rowId = item.groupId;
            intent.putExtra(InsulinConstants.KEY_INTENT_EXTRA_ROW_ID, rowId);
        }
        this.startActivity(intent);
*/
    }
}
