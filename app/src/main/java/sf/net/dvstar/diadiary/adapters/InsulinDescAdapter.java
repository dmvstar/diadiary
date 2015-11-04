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


public class InsulinDescAdapter  extends ArrayAdapter<InsulinItem> {

    private final Activity mContext;
    private final List<InsulinItem> mInsulins;

    static class ViewHolder {
        public TextView tv_insulin;
        public TextView tv_firm;
        public TextView tv_type;
        public TextView tv_work;
    }

    public InsulinDescAdapter(Activity context, List<InsulinItem> insulins) {
        super(context, R.layout.insulin_desc_item, insulins);
        this.mContext   = context;
        this.mInsulins = insulins;
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
            rowView = inflater.inflate(R.layout.insulin_desc_item, null);
            // configure view holder
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.tv_insulin = (TextView) rowView.findViewById(R.id.tv_insulin);
            viewHolder.tv_firm = (TextView) rowView.findViewById(R.id.tv_firm);
            viewHolder.tv_type = (TextView) rowView.findViewById(R.id.tv_type);
            viewHolder.tv_work = (TextView) rowView.findViewById(R.id.tv_work);
            rowView.setTag(viewHolder);
        }

        // fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();

        InsulinItem item = mInsulins.get(position);

        holder.tv_insulin.setText(item.name);
        holder.tv_firm.setText(item.firm.name);
        holder.tv_type.setText(item.type.description);
        holder.tv_work.setText(item.getWork());
        rowView.setBackgroundColor(item.color);

        return rowView;
    }


}
