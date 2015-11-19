package sf.net.dvstar.diadiary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.Date;

import sf.net.dvstar.diadiary.R;
import sf.net.dvstar.diadiary.database.ProductItem;
import sf.net.dvstar.diadiary.database.ProductMenuDesc;
import sf.net.dvstar.diadiary.database.ProductMenuItem;
import sf.net.dvstar.diadiary.insulins.InsulinConstants;

public class ProdMenuAddActivity extends AppCompatActivity {

    private static final String TAG = "ProdMenuAddActivity";
    ListView mProdMenu;

    ArrayList<ProductItem> listItems = new ArrayList<>();
    ArrayAdapter<ProductItem> adapter;

    private int mMode;
    private ProductMenuDesc mProductMenuDesc;
    private EditText mEtName;
    private EditText mEtComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prod_menu_add);
        mMode = InsulinConstants.MODE_ACTIONS_EDIT_ADD;

        if(getIntent().getExtras()!=null)
            mMode = getIntent().getExtras().getInt(InsulinConstants.KEY_INTENT_EXTRA_EDIT_MODE,InsulinConstants.MODE_ACTIONS_EDIT_ADD);

        mEtName = (EditText)findViewById(R.id.et_menu_name);
        mEtComment = (EditText) findViewById(R.id.et_comment);

        mProdMenu = (ListView) findViewById(R.id.lv_menu_product_list);

        adapter = new ArrayAdapter<ProductItem>(this, android.R.layout.simple_list_item_1, listItems);
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

        if (mMode == InsulinConstants.MODE_ACTIONS_EDIT_ADD) {
            mProductMenuDesc = new ProductMenuDesc();
            mProductMenuDesc.created = new Date();
        }

        String name = mEtName.getText().toString();
        String comm = mEtComment.getText().toString();

        mProductMenuDesc.name = name;
        mProductMenuDesc.comment = comm;

        if (name.length()>0) {
            mProductMenuDesc.save();
        }

        int count = new Select().from(ProductMenuDesc.class).count();
        Log.v(TAG, "Select ProductMenuDesc.class=" +count);
        finish();
    }

    public void addProduct(View view) {
        showProdItemAddActivity();
        //showProductGroupActivity();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK) {
            ProductItem product = (ProductItem) data.getExtras().getSerializable(InsulinConstants.KEY_INTENT_EXTRA_GET_PRODUCT);
            listItems.add(product);
            adapter.notifyDataSetChanged();
        }
    }

}
