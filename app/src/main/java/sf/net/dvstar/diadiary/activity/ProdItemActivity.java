package sf.net.dvstar.diadiary.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.activeandroid.query.Select;

import java.util.List;

import sf.net.dvstar.diadiary.R;
import sf.net.dvstar.diadiary.database.ProductGroup;
import sf.net.dvstar.diadiary.database.ProductItem;
import sf.net.dvstar.diadiary.insulins.InsulinConstants;

public class ProdItemActivity extends AppCompatActivity {

    ListView mProdGroups;


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

        long iId = getIntent().getExtras().getLong(InsulinConstants.KEY_INTENT_EXTRA_ROW_ID);

        mProdGroups = (ListView) findViewById(R.id.lv_prod_items);
        List<ProductItem> list = new Select().from(ProductItem.class).where("groupId = ?",iId).execute();
        ArrayAdapter adapter = new ArrayAdapter<ProductItem>(this,android.R.layout.simple_list_item_1, list);
        mProdGroups.setAdapter(adapter);

    }
}
