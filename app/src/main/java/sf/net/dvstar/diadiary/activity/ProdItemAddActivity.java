package sf.net.dvstar.diadiary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import sf.net.dvstar.diadiary.R;
import sf.net.dvstar.diadiary.database.ProductGroup;
import sf.net.dvstar.diadiary.database.ProductItem;
import sf.net.dvstar.diadiary.database.ProductMenuItem;
import sf.net.dvstar.diadiary.utilitis.CommonConstants;


public class ProdItemAddActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    List<ProductGroup> listProductGroup;
    ArrayAdapter<ProductGroup> adapterProductGroup;

    private Spinner mProdGroup;
    private Spinner mProdItems;
    private EditText mProdWeigh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prod_item_add);

        mProdGroup = (Spinner) findViewById(R.id.sp_prod_group);
        mProdItems = (Spinner) findViewById(R.id.sp_prod_item);
        mProdWeigh = (EditText) findViewById(R.id.et_prod_weight);

        listProductGroup = new Select().from(ProductGroup.class).execute();
        adapterProductGroup = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, listProductGroup);
        mProdGroup.setAdapter(adapterProductGroup);
        mProdGroup.setOnItemSelectedListener(this);

    }


    public void cancel(View view) {
        finish();
    }

    public void confirm(View view) {
        int pos;
        if((pos=mProdItems.getSelectedItemPosition())>=0){
            ProductItem product = (ProductItem) mProdItems.getAdapter().getItem(pos);
            Toast.makeText(this, product.getListText(),
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();

            ProductMenuItem productMenuItem = new ProductMenuItem();
            productMenuItem.prod = product;
            if( mProdWeigh.getText().length()>0 )
                productMenuItem.weight = Float.parseFloat(mProdWeigh.getText().toString());
            else
                productMenuItem.weight = product.weight;

            intent.putExtra(CommonConstants.KEY_INTENT_EXTRA_GET_PRODUCT, productMenuItem);
            intent.putExtra(CommonConstants.KEY_INTENT_EXTRA_ROW_ID, product.getId());

            setResult(RESULT_OK, intent);
        }
        finish();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ProductGroup lProductGroup = (ProductGroup) mProdGroup.getAdapter().getItem(position);

        List<ProductItem> list;
        if(lProductGroup.groupId>=0) {
            list = new Select().from(ProductItem.class).where("groupId = ?", lProductGroup.groupId).execute();
            ArrayAdapter adapter = new ArrayAdapter<ProductItem>(this, android.R.layout.simple_list_item_1, list);
            mProdItems.setAdapter(adapter);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
