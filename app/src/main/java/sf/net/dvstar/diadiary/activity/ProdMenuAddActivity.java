package sf.net.dvstar.diadiary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import sf.net.dvstar.diadiary.R;
import sf.net.dvstar.diadiary.insulins.InsulinConstants;

public class ProdMenuAddActivity extends AppCompatActivity {

    ListView mProdMenu;

    ArrayList<String> listItems = new ArrayList<String>();

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prod_menu_add);
        mProdMenu = (ListView) findViewById(R.id.lv_menu_product_list);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        mProdMenu.setAdapter(adapter);
/*
        mProdGroups = (ListView) findViewById(R.id.lv_prod_group);
        List<ProductGroup> list = new Select().from(ProductGroup.class).execute();
        ArrayAdapter adapter = new ArrayAdapter<ProductGroup>(this,android.R.layout.simple_list_item_1, list);
        mProdGroups.setAdapter(adapter);
        mProdGroups.setOnItemClickListener(this);
*/
    }

    private void showProducItemActivity() {
        Intent intent = new Intent(this, ProdItemActivity.class);
        this.startActivity(intent);
    }

    private void showProductGroupActivity() {
        Intent intent = new Intent(this, ProdGroupActivity.class);
        intent.putExtra(InsulinConstants.KEY_INTENT_EXTRA_GET_PRODUCT, InsulinConstants.MODE_ACTIONS_GET_PRODUCT);
        this.startActivityForResult(intent, InsulinConstants.MODE_ACTIONS_GET_PRODUCT);
    }

    private void showProdItemAddActivity() {
        Intent intent = new Intent(this, ProdItemAddActivity.class);
        intent.putExtra(InsulinConstants.KEY_INTENT_EXTRA_GET_PRODUCT, InsulinConstants.MODE_ACTIONS_GET_PRODUCT);
        this.startActivityForResult(intent, InsulinConstants.MODE_ACTIONS_GET_PRODUCT);
    }

    public void cancel(View view) {
        finish();
    }

    public void confirm(View view) {
        finish();
    }

    public void addProduct(View view) {
        showProdItemAddActivity();
        //showProductGroupActivity();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK) {
            String product = data.getExtras().getSerializable(InsulinConstants.KEY_INTENT_EXTRA_GET_PRODUCT).toString();
            listItems.add(product);
            adapter.notifyDataSetChanged();
        }
    }

}
