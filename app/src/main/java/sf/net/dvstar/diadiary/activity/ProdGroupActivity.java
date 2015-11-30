package sf.net.dvstar.diadiary.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.query.Select;

import java.util.List;

import sf.net.dvstar.diadiary.R;
import sf.net.dvstar.diadiary.database.ProductGroup;
import sf.net.dvstar.diadiary.utilitis.CommonConstants;


public class ProdGroupActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView mProdGroups;
    private int mMode=0;


    /*
    String query = Client.all().orderBy("name").toSql();
    Cursor cursor = ActiveAndroid.getDatabase().rawQuery(query, null);
    ClientListCursorAdapter adapter = new ClientListCursorAdapter(this, R.layout.clients_listview_row, cursor, 0 );
    this.setListAdapter(adapter);

    new Select().from(table).execute().size()

    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prod_group);

        if(getIntent().getExtras()!=null) {
            mMode = getIntent().getExtras().getInt(CommonConstants.KEY_INTENT_EXTRA_GET_PRODUCT);
        }

        mProdGroups = (ListView) findViewById(R.id.lv_prod_group);
        List<ProductGroup> list = new Select().from(ProductGroup.class).execute();
        ArrayAdapter adapter = new ArrayAdapter<ProductGroup>(this,android.R.layout.simple_list_item_1, list);
        mProdGroups.setAdapter(adapter);
        mProdGroups.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ProductGroup item = (ProductGroup) mProdGroups.getItemAtPosition(position);

        Toast.makeText(this, item.getListText(),
                Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, ProdItemActivity.class);

        if (item != null) {
            long rowId = item.groupId;
            intent.putExtra(CommonConstants.KEY_INTENT_EXTRA_ROW_ID, rowId);
        }

        if(mMode==CommonConstants.MODE_ACTIONS_GET_PRODUCT_ITEM)
            this.startActivityForResult(intent, CommonConstants.MODE_ACTIONS_GET_PRODUCT_ITEM);
        else
           this.startActivity(intent);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK) {
            String product = data.getExtras().getString(CommonConstants.KEY_INTENT_EXTRA_GET_PRODUCT);
            Intent intent = new Intent();
            intent.putExtra(CommonConstants.KEY_INTENT_EXTRA_GET_PRODUCT, product);
            setResult(RESULT_OK, intent);
        }
        finish();
    }
}
