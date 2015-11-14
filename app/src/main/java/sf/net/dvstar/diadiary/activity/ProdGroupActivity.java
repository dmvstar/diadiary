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
import sf.net.dvstar.diadiary.insulins.InsulinConstants;

public class ProdGroupActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

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
        setContentView(R.layout.activity_prod_group);

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
            intent.putExtra(InsulinConstants.KEY_INTENT_EXTRA_ROW_ID, rowId);
        }
        this.startActivity(intent);

    }
}
