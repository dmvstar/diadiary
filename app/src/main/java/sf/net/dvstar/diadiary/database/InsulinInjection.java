package sf.net.dvstar.diadiary.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;
import java.util.Date;

import sf.net.dvstar.diadiary.insulins.InsulinUtils;


@Table(name = "InsulinInjection")
public class InsulinInjection extends Model implements Serializable, ActionCommonItem {

    public static String TAG = "InsulinInjection";

    @Column(name = "insulin", indexGroups = {"main"})
    public InsulinItem insulin;

    @Column(name = "dose", indexGroups = {"main"})
    public int dose;

    @Column(name = "time", indexGroups = {"main"})
    public Date time;

    /**
     * Dropdown from @arrays/insulin_inject_type
     */
    @Column(name = "plan", indexGroups = {"main"})
    public int plan;

    @Column(name = "date")
    public Date date;

    /**
     * Dropdown from @arrays/dialog_notes_list
     */
    //@Column(name = "notes")
    //public String notes;

    @Column(name = "note")
    public int note;

    @Column(name = "comment")
    public String comment;

    @Column(name = "color")
    public int color;

    public static int INJECTION_PLAN_REGULAR = 0;
    public static int INJECTION_PLAN_ADDITIONAL = 1;

    public InsulinInjection() {
        super();
    }

    public InsulinInjection(InsulinItem insulin, int dose, Date time, int note, String comment, int plan, int color){
        super();
        this.insulin = insulin;
        this.dose = dose;
        this.time = time;
        this.comment = comment;
        this.note = note;
        this.plan = plan;
        this.color = color;
    }

    @Override
    public String getListText() {
        return "dose="+dose;
    }

    @Override
    public String toString() {
        return "InsulinInjection ["+getId()+"]{" +
                "insulin=" + insulin +
                ", dose='" + dose + '\'' +
                ", time='" + time + '\'' +
                ", plan=" + plan +
                ", date='" + date + '\'' +
                ", comment='" + comment + '\'' +
                ", color=" + color +
                '}';
    }

    @Override
    public Date getCompareTime() {
        Date ret;
        if(plan==INJECTION_PLAN_REGULAR){
            ret = InsulinUtils.getDateTimeFrom(time, null);
        } else {
            ret = InsulinUtils.getDateTimeFrom(time, date);
        }
        return ret;
    }

    @Override
    public int getActionType() {
        return ACTION_TYPE_INJECT;
    }

    @Override
    public String exportItem() {
        String ret = TAG+"|"+insulin.name+"|"+dose+"|"+time+"|"+comment+"|"+plan+"|"+color+ "|";
        return ret;
    }

    @Override
    public void importItem(String item) {
        String[] items = item.split(FIELD_DELIMITER, -1);
        int index = 0;
        if(items[index].equals(TAG)) index++;
        String insulin_name = items[index++];
        dose=Integer.parseInt(items[index++]);
        time= InsulinUtils.getDateFromString(items[index++]);
        comment=items[index++];
        plan=Integer.parseInt(items[index++]);
        color=Integer.parseInt(items[index++]);
    }


/*
    public InsulinInjection(InsulinItem insulin, String dose, String time, String comment){
        this.insulin = insulin;
        this.dose = dose;
        this.time = time;
        this.comment = comment;
        this.color = Color.GRAY;
    }
*/

}
