package sf.net.dvstar.diadiary.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.activeandroid.Model;

import java.util.List;

import sf.net.dvstar.diadiary.database.ActionCommonItem;

/**
 * Created by sdv on 22.10.15.
 */

//InsulinCommonAdapter
public class InsulinCommonAdapter extends ArrayAdapter<Model> {
    private final Activity mContext;
    private final List<Model> mItems;
    private final LayoutInflater mInflater;
    private final int mResource;
    private TextView mText;

    public InsulinCommonAdapter(Activity context, List<Model> items) {
        super(context, android.R.layout.simple_list_item_1, items);
        this.mContext   = context;
        this.mItems = items;
        this.mInflater = LayoutInflater.from(context);
        this.mResource = android.R.layout.simple_list_item_1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createViewFromResource(mInflater, position, convertView, parent, mResource);

    }

    private View createViewFromResource(LayoutInflater mInflater, int position, View convertView, ViewGroup parent, int mResource) {

        View rowView = convertView;

        if (convertView == null) {
            rowView = mInflater.inflate(mResource, parent, false);
        } else {
            rowView = convertView;
        }


        mText = (TextView) rowView.findViewById(android.R.id.text1);

        ActionCommonItem item = (ActionCommonItem) mItems.get(position);

        mText.setText(item.getListText());

        return rowView;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        final LayoutInflater inflater = mInflater;
        return createViewFromResource(inflater, position, convertView, parent, mResource);
    }


}
