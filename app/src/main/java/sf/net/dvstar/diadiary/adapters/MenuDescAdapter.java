package sf.net.dvstar.diadiary.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import sf.net.dvstar.diadiary.R;
import sf.net.dvstar.diadiary.database.InsulinItem;
import sf.net.dvstar.diadiary.database.ProductMenuDesc;


public class MenuDescAdapter extends ArrayAdapter<ProductMenuDesc> {

    private final Activity mContext;
    private final List<ProductMenuDesc> mProductMenuDesc;

    static class ViewHolder {
        public TextView tv_name;
        public TextView tv_comm;
        public TextView tv_created;
    }

    public MenuDescAdapter(Activity context, List<ProductMenuDesc> menus) {
        super(context, R.layout.item_menu_detail, menus);
        this.mContext   = context;
        this.mProductMenuDesc = menus;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        // reuse views
        if (rowView == null) {
            LayoutInflater inflater = mContext.getLayoutInflater();
            rowView = inflater.inflate(R.layout.item_menu_detail, null);
            // configure view holder
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.tv_name = (TextView) rowView.findViewById(R.id.tv_name);
            viewHolder.tv_comm = (TextView) rowView.findViewById(R.id.tv_comment);
            rowView.setTag(viewHolder);
        }

        // fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();

        ProductMenuDesc item = mProductMenuDesc.get(position);

        holder.tv_name.setText(item.name);
        holder.tv_comm.setText(item.comment);

        return rowView;
    }


}
