package sf.net.dvstar.diadiary.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import sf.net.dvstar.diadiary.R;
import sf.net.dvstar.diadiary.database.ActionCommonItem;
import sf.net.dvstar.diadiary.database.GlucoseReading;
import sf.net.dvstar.diadiary.database.InsulinInjection;
import sf.net.dvstar.diadiary.insulins.InsulinUtils;


public class DiaryActionstAdapter extends ArrayAdapter<ActionCommonItem> {

    private final Activity mContext;
    private final List<ActionCommonItem> mActionss;
    private final LayoutInflater mLayoutInflater;

    static class ViewHolderInject {
        public TextView tv_insulin;
        public TextView tv_dose;
        public TextView tv_time;
        public TextView tv_comment;
        public TextView tv_planned;
    }

    static class ViewHolderGlucose {
        public TextView tv_value;
        public TextView tv_time;
        public TextView tv_note;
        public TextView tv_comment;
    }


    public DiaryActionstAdapter(Activity context, List<ActionCommonItem> actions) {
        //super(context, android.R.layout.simple_list_item_1, insulins);
        super(context, R.layout.insulin_inject_item, actions);
        this.mContext   = context;
        this.mActionss  = actions;
        this.mLayoutInflater = mContext.getLayoutInflater();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        //return super.getDropDownView(position, convertView, parent);
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(int position, View recycledView, ViewGroup parent) {
        View rowView = recycledView;
        ActionCommonItem action = mActionss.get(position);
        int key = action.getActionType();
        // reuse views
        if (key == ActionCommonItem.ACTION_TYPE_INJECT && (rowView == null || rowView.getTag(R.id.ACTION_TYPE_INJECT)==null)) {
            rowView = mLayoutInflater.inflate(R.layout.insulin_inject_item, null);
            // configure view holder
            ViewHolderInject viewHolder = new ViewHolderInject();
            viewHolder.tv_insulin = (TextView) rowView.findViewById(R.id.tv_insulin);
            viewHolder.tv_dose = (TextView) rowView.findViewById(R.id.tv_dose);
            viewHolder.tv_time = (TextView) rowView.findViewById(R.id.tv_time);
            viewHolder.tv_comment = (TextView) rowView.findViewById(R.id.tv_comment);
            viewHolder.tv_planned = (TextView) rowView.findViewById(R.id.tv_planned);
            rowView.setTag(R.id.ACTION_TYPE_INJECT, viewHolder);
        }
        if (key == ActionCommonItem.ACTION_TYPE_GLUCOSE && (rowView == null || rowView.getTag(R.id.ACTION_TYPE_GLUCOSE)==null)) {
            rowView = mLayoutInflater.inflate(R.layout.insulin_glucose_item, null);
            ViewHolderGlucose viewHolder = new ViewHolderGlucose();
            viewHolder.tv_value = (TextView) rowView.findViewById(R.id.tv_value);
            viewHolder.tv_time = (TextView) rowView.findViewById(R.id.tv_time);
            viewHolder.tv_comment = (TextView) rowView.findViewById(R.id.tv_comment);
            rowView.setTag(R.id.ACTION_TYPE_GLUCOSE, viewHolder);
        }

        // fill data **
        if(key == ActionCommonItem.ACTION_TYPE_INJECT) {
            ViewHolderInject holder = (ViewHolderInject) rowView.getTag(R.id.ACTION_TYPE_INJECT);
            InsulinInjection item = (InsulinInjection) action;
            //if(holder != null)
            {
                holder.tv_insulin.setText(item.insulin.name);
                holder.tv_dose.setText(item.dose);
                holder.tv_time.setText(InsulinUtils.getTimeText(item.time));
                holder.tv_comment.setText(item.comment);//+" "+InsulinUtils.getDateTimeFrom(item.time, null).toString()
                holder.tv_planned.setText(getPlannedDescription(item.plan));
                rowView.setBackgroundColor(item.color);
            }
        }
        if(key == ActionCommonItem.ACTION_TYPE_GLUCOSE) {
            ViewHolderGlucose holder = (ViewHolderGlucose) rowView.getTag(R.id.ACTION_TYPE_GLUCOSE);
            GlucoseReading item = (GlucoseReading) action;
            //if(holder != null)
            {
                holder.tv_value.setText("" + item.value);
                holder.tv_time.setText(InsulinUtils.getDateTimeText(item.created));
                holder.tv_comment.setText(item.comment);
            }
        }
        return rowView;
    }

    // @TODO replace to resource
    private String getPlannedDescription(int planned) {
        String ret = mContext.getResources().getString(R.string.planned_none);
        if (planned == InsulinInjection.INJECTION_PLAN_REGULAR) ret = mContext.getResources().getString(R.string.planned_regular);
        if (planned == InsulinInjection.INJECTION_PLAN_ADDITIONAL) ret = mContext.getResources().getString(R.string.planned_additionals);
        return ret;
    }

}
