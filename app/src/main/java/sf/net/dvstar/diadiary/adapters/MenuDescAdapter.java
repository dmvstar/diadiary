package sf.net.dvstar.diadiary.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.activeandroid.Model;
import com.activeandroid.query.Select;

import java.util.List;

import sf.net.dvstar.diadiary.R;
import sf.net.dvstar.diadiary.database.InsulinItem;
import sf.net.dvstar.diadiary.database.ProductMenuDesc;
import sf.net.dvstar.diadiary.database.ProductMenuItem;


public class MenuDescAdapter extends ArrayAdapter<ProductMenuDesc> {

    private final Activity mContext;
    private final List<ProductMenuDesc> mProductMenuDesc;
    private List<ProductMenuItem> mListProductMenuItem;

    static class ViewHolder {
        public TextView tv_name;
        public TextView tv_comm;
        public TextView tv_created;

        public TextView tv_carb;
        public TextView tv_fats;
        public TextView tv_prot;
        public TextView tv_xe;
        public TextView tv_gi;

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

            viewHolder.tv_carb = (TextView) rowView.findViewById(R.id.tv_prod_carb);
            viewHolder.tv_fats = (TextView) rowView.findViewById(R.id.tv_prod_fat);
            viewHolder.tv_prot = (TextView) rowView.findViewById(R.id.tv_prod_prot);
            viewHolder.tv_xe = (TextView) rowView.findViewById(R.id.tv_prod_xe);
            viewHolder.tv_gi = (TextView) rowView.findViewById(R.id.tv_prod_gi);

            rowView.setTag(viewHolder);
        }

        // fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();

        ProductMenuDesc item = mProductMenuDesc.get(position);

        holder.tv_name.setText(item.name);
        holder.tv_comm.setText(item.comment);

        calculteProductMenuItems(holder, item.getId());

        return rowView;
    }

    private void calculteProductMenuItems(ViewHolder holder, long id) {

        mListProductMenuItem = new Select().from(ProductMenuItem.class).where("menu = ?", id).execute();

        float carb = 0,fats = 0,prot = 0;
        int xe =0,count=0,gi=0;
        for (ProductMenuItem item : mListProductMenuItem) {
            count++;
            carb += item.carb;
            fats += item.fats;
            prot += item.prot;
            xe += item.xe;
            gi+= item.gi;
        }

        if(count>0) {
            holder.tv_carb.setText("" + carb);
            holder.tv_fats.setText("" + fats);
            holder.tv_prot.setText("" + prot);
            holder.tv_gi.setText("" + gi / count);
            holder.tv_xe.setText("" + xe);
        }
    }

}
