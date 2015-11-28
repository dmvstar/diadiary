package sf.net.dvstar.diadiary.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.Model;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sf.net.dvstar.diadiary.R;
import sf.net.dvstar.diadiary.database.ProductItem;
import sf.net.dvstar.diadiary.database.ProductMenuDesc;
import sf.net.dvstar.diadiary.database.ProductMenuItem;
import sf.net.dvstar.diadiary.database.UserProfileCoeff;
import sf.net.dvstar.diadiary.utilitis.CommonConstants;


public class ProdMenuAddActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, ActivitySaving {

    private static final String TAG = "ProdMenuAddActivity";
    private ProductMenuDesc mProductMenuDesc;

    ListView mProdMenu;

    List<ProductMenuItem> mListProductMenuItem = new ArrayList<>();

    ArrayAdapter<ProductMenuItem> adapter;

    private int mMode;
    private EditText mEtName;
    private EditText mEtComment;

    private TextView mTvCarb;
    private TextView mTvFat;
    private TextView mTvProt;
    private TextView mTvGI;
    private TextView mTvXE;
    private long mId;
    private List<UserProfileCoeff> mLsttUserProfileCoeff;
    private Spinner mSpUserProfileK1;
    private Button mBtConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prod_menu_add);
        mMode = CommonConstants.MODE_ACTIONS_EDIT_ADD;

        if (getIntent().getExtras() != null) {
            mMode = getIntent().getExtras().getInt(CommonConstants.KEY_INTENT_EXTRA_EDIT_MODE, CommonConstants.MODE_ACTIONS_EDIT_ADD);
            mId = getIntent().getExtras().getLong(CommonConstants.KEY_INTENT_EXTRA_ROW_ID, -1);
            if (mId > 0) {
                mProductMenuDesc = new Select().from(ProductMenuDesc.class).where("id = ?", mId).executeSingle();
            }
//            else {
//                mProductMenuDesc = new ProductMenuDesc();
//            }
        }

        mEtName = (EditText) findViewById(R.id.et_menu_name);
        mEtComment = (EditText) findViewById(R.id.et_comment);
        mBtConfirm = (Button) findViewById(R.id.bt_confirm);

        mTvCarb = (TextView) findViewById(R.id.tv_prod_carb);
        mTvFat = (TextView) findViewById(R.id.tv_prod_fat);
        mTvProt = (TextView) findViewById(R.id.tv_prod_prot);
        mTvGI = (TextView) findViewById(R.id.tv_prod_gi);
        mTvXE = (TextView) findViewById(R.id.tv_prod_xe);

        mProdMenu = (ListView) findViewById(R.id.lv_menu_product_list);

        fillFieldData();

        adapter = new ArrayAdapter<ProductMenuItem>(this, android.R.layout.simple_list_item_1, mListProductMenuItem);
        mProdMenu.setAdapter(adapter);
        mProdMenu.setOnItemClickListener(this);
        mSpUserProfileK1   = (Spinner) findViewById(R.id.sp_user_profile_k1);

        ProductMenuItem.ProductMenuItemsCalc calc = ProductMenuItem.calculteProductMenuItems(mListProductMenuItem);

        fillProductMenuItems(calc);
        fillUserProfileCoeff();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }

    private void fillUserProfileCoeff() {
        mLsttUserProfileCoeff = new Select().from(UserProfileCoeff.class).execute();
        ArrayAdapter<UserProfileCoeff> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,mLsttUserProfileCoeff);
        mSpUserProfileK1.setAdapter(adapter);
    }



    private void showProducItemActivity() {
        Intent intent = new Intent(this, ProdItemActivity.class);
        this.startActivity(intent);
    }

    private void showProductGroupActivity() {
        Intent intent = new Intent(this, ProdGroupActivity.class);
        intent.putExtra(CommonConstants.KEY_INTENT_EXTRA_GET_PRODUCT, CommonConstants.MODE_ACTIONS_GET_PRODUCT);
        this.startActivityForResult(intent, CommonConstants.MODE_ACTIONS_GET_PRODUCT);
    }

    private void showProdItemAddActivity() {
        Intent intent = new Intent(this, ProdItemAddActivity.class);
        intent.putExtra(CommonConstants.KEY_INTENT_EXTRA_GET_PRODUCT, CommonConstants.MODE_ACTIONS_GET_PRODUCT);
        this.startActivityForResult(intent, CommonConstants.MODE_ACTIONS_GET_PRODUCT);
    }

    public void cancel(View view) {
        finish();
    }

    public void confirm(View view) {
        saveFieldData();
        finish();
    }

    public void addProduct(View view) {
        saveFieldData();
        showProdItemAddActivity();
        //showProductGroupActivity();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            ProductMenuItem productItem = (ProductMenuItem) data.getExtras().getSerializable(CommonConstants.KEY_INTENT_EXTRA_GET_PRODUCT);
            Long productId = data.getExtras().getLong(CommonConstants.KEY_INTENT_EXTRA_ROW_ID);
            ProductItem product = new Select().from(ProductItem.class).where("id = ?", productId).executeSingle();

            productItem.menu = mProductMenuDesc;
            productItem.prod = product;

            calculteProductMenuItem(productItem);
            mListProductMenuItem.add(productItem);

            ProductMenuItem.ProductMenuItemsCalc calc = ProductMenuItem.calculteProductMenuItems(mListProductMenuItem);

            fillProductMenuItems(calc);

            adapter.notifyDataSetChanged();
        }
    }

    private void calculteProductMenuItem(ProductMenuItem productItem) {

        float koeff = productItem.weight / productItem.prod.weight;
        productItem.prot = productItem.prod.prot * koeff;
        productItem.carb = productItem.prod.carb * koeff;
        productItem.fats = productItem.prod.fats * koeff;
        productItem.gi = productItem.prod.gi;
        productItem.xe = (int) (productItem.carb / 12.0);

    }

    private void fillProductMenuItems(ProductMenuItem.ProductMenuItemsCalc calc) {
            mTvCarb.setText("" + calc.carb);
            mTvFat.setText("" + calc.fats);
            mTvProt.setText("" + calc.prot);
            mTvGI.setText("" + calc.gi);
            mTvXE.setText("" + calc.xe);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void fillFieldData() {

        if (mMode == CommonConstants.MODE_ACTIONS_EDIT_ITEM) {
            mBtConfirm.setText(getResources().getString(R.string.button_insulin_update));
        }

        if (mId > 0) {
            //mProductMenuDesc = new Select().from(ProductMenuDesc.class).where("id = ?", mId).executeSingle();
            mEtName.setText(mProductMenuDesc.name);
            mEtComment.setText(mProductMenuDesc.comment);

            mListProductMenuItem = new Select().from(ProductMenuItem.class).where("menu = ?", mProductMenuDesc.getId()).execute();

            Select select = new Select();
            int count = select.from(ProductMenuItem.class).where("menu = ?", mProductMenuDesc.getId()).execute().size();

            Log.v(TAG, "!!! fillFieldData = " + count);
        }
    }

    @Override
    public void saveFieldData() {
        if (mMode == CommonConstants.MODE_ACTIONS_EDIT_ADD && mProductMenuDesc == null) {
            mProductMenuDesc = new ProductMenuDesc();
            mProductMenuDesc.created = new Date();
        }

        String name = mEtName.getText().toString();
        String comm = mEtComment.getText().toString();

        mProductMenuDesc.name = name;
        mProductMenuDesc.comment = comm;

        if (name.length() > 0) {
            mProductMenuDesc.save();

            for (ProductMenuItem itemProductItem : mListProductMenuItem) {
                itemProductItem.save();
            }

        }

        int count = new Select().from(ProductMenuDesc.class).count();
        Log.v(TAG, "!!! Select ProductMenuDesc.class=" + count);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
