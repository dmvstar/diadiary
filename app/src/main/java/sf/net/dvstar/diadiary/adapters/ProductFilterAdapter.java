package sf.net.dvstar.diadiary.adapters;


import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

import sf.net.dvstar.diadiary.database.ProductItem;

public class ProductFilterAdapter extends ArrayAdapter<ProductItem> {

    private final Context mContext;
    private List<ProductItem> mDataOriginal;
    private List<ProductItem> mDataFiltered;
    private final int mLayoutResourceId;
    private ProductNameFilter mFilter;

    public ProductFilterAdapter(Context context, int layoutResourceId, List<ProductItem> aData) {
        super(context, layoutResourceId, aData);
        this.mLayoutResourceId = layoutResourceId;
        this.mContext = context;
        this.mDataOriginal = aData;
        this.mDataFiltered = new ArrayList<>(aData);
    }
//
//    public void addFilter(String aFilter){
//        if(!mFilter.equals(aFilter)) {
//            mFilter = aFilter;
//        }
//
//    }

    @Override
    public int getCount() {
        return mDataOriginal.size();
    }

    @Override
    public ProductItem getItem(int position) {
        return mDataOriginal.get(position);
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null)
            mFilter = new ProductNameFilter();
        return mFilter;
    }


    private class ProductNameFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            String prefix = constraint.toString().toLowerCase();

            if (prefix == null || prefix.length() == 0) {
                ArrayList<ProductItem> list = new ArrayList<ProductItem>(mDataOriginal);
                results.values = list;
                results.count  = list.size();
            } else {
                final ArrayList<ProductItem> list = new ArrayList<ProductItem>(mDataOriginal);
                final ArrayList<ProductItem> nlist = new ArrayList<ProductItem>();
                int count = list.size();

                for (int i = 0; i < count; i++) {
                    final ProductItem prod = list.get(i);
                    final String value = prod.name;

                    if (value.toLowerCase().contains(prefix.toLowerCase())) {
                        nlist.add(prod);
                    }
                }
                results.values = nlist;
                results.count = nlist.size();
            }
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mDataOriginal = (ArrayList<ProductItem>) results.values; // has
            notifyDataSetChanged();
        }

    }
}


