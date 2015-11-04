package sf.net.dvstar.diadiary.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;
import java.util.Date;

import sf.net.dvstar.diadiary.insulins.InsulinUtils;

@Table(name = "InsulinInjection")
public class InsulinInjection extends Model implements Serializable, ActionCommonItem {

    @Column(name = "insulin", indexGroups = {"main"})
    public InsulinItem insulin;

    @Column(name = "dose", indexGroups = {"main"})
    public String dose;

    @Column(name = "time", indexGroups = {"main"})
    public Date time;

    @Column(name = "plan", indexGroups = {"main"})
    public int plan;

    @Column(name = "date")
    public Date date;

    @Column(name = "comment")
    public String comment;

    @Column(name = "color")
    public int color;

    public static int INJECTION_PLAN_REGULAR = 0;
    public static int INJECTION_PLAN_ADDITIONAL = 1;

    public InsulinInjection() {
        super();
    }

    public InsulinInjection(InsulinItem insulin, String dose, Date time, String comment, int plan, int color){
        super();
        this.insulin = insulin;
        this.dose = dose;
        this.time = time;
        this.comment = comment;
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
