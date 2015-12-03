package sf.net.dvstar.diadiary.adapters;


import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

import sf.net.dvstar.diadiary.database.ProductItem;

public class ProductFilterAdapter extends ArrayAdapter<ProductItem> {
    public ProductFilterAdapter(Context context, int resource, List<ProductItem> objects) {
        super(context, resource, objects);
    }


}
