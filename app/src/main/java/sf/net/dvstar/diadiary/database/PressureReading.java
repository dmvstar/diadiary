package sf.net.dvstar.diadiary.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;
import java.util.Date;


@Table(name = "PressureReading")
public class PressureReading extends Model implements Serializable, ActionCommonItem {

    public static String TAG = "PressureReading";

    public PressureReading(){ super(); }

    public PressureReading(int systole_value, int diastolic_value, Date time, int note, String comment){
        super();
        this.systole_value = systole_value;
        this.diastolic_value = diastolic_value;
        this.time = time;
        this.note = note;
        this.comment = comment;
    }

    @Column(name = "systole_value")
    public int systole_value;

    @Column(name = "diastolic_value")
    public int diastolic_value;

    @Column(name = "time")
    public Date time;

    /**
     * Dropdown from @arrays/dialog_notes_list
     */
    //@Column(name = "notes")
    //public String notes;

    @Column(name = "note")
    public int note;

    @Column(name = "comment")
    public String comment;

    @Override
    public String getListText() {
        return "";
    }

    @Override
    public Date getCompareTime() {
        return time;
    }

    @Override
    public int getActionType() {
        return ACTION_TYPE_PRESSURE;
    }

    @Override
    public String toString() {
        return "PressureReading{" +
                "systole_value=" + systole_value +
                ", diastolic_value=" + diastolic_value +
                ", time=" + time +
                ", note='" + note + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }

    @Override
    public String exportItem() {
        String ret = TAG+"|"+systole_value+"|"+diastolic_value+"|"+time+"|"+note+"|"+comment;
        return ret;
    }

    @Override
    public void importItem(String item) {

    }
}
