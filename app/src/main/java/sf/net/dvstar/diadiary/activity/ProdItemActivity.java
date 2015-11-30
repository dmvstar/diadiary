package sf.net.dvstar.diadiary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.activeandroid.query.Select;

import java.util.List;

import sf.net.dvstar.diadiary.R;
import sf.net.dvstar.diadiary.database.ProductItem;

import sf.net.dvstar.diadiary.utilitis.CommonConstants;

public class ProdItemActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView mProductItems;

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
        setContentView(R.layout.activity_prod_item);

        long iId = -1;
        if(getIntent().getExtras()!=null)
            iId = getIntent().getExtras().getLong(CommonConstants.KEY_INTENT_EXTRA_ROW_ID);

        mProductItems = (ListView) findViewById(R.id.lv_prod_items);
        List<ProductItem> list;
        if(iId>=0)
            list = new Select().from(ProductItem.class).where("groupId = ?",iId).execute();
        else
            list = new Select().from(ProductItem.class).execute();

        ArrayAdapter adapter = new ArrayAdapter<ProductItem>(this,android.R.layout.simple_list_item_1, list);
        mProductItems.setAdapter(adapter);
        mProductItems.setOnItemClickListener(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String product = mProductItems.getAdapter().getItem(position).toString();
        Intent intent = new Intent();
        intent.putExtra(CommonConstants.KEY_INTENT_EXTRA_GET_PRODUCT, product);
        setResult(RESULT_OK, intent);
        finish();
    }
}
