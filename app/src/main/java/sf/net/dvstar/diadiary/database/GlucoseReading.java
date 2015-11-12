package sf.net.dvstar.diadiary.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;
import java.util.Date;

import sf.net.dvstar.diadiary.insulins.InsulinUtils;


@Table(name = "GlucoseReading")
public class GlucoseReading extends Model implements Serializable, ActionCommonItem {

    public static String TAG = "GlucoseReading";

    public GlucoseReading() {
        super();
    }

    public GlucoseReading(float value, Date time, int note, String comment) {
        super();
        this.value = value;
        this.time = time;
        this.note = note;
        this.comment = comment;
    }

    @Column(name = "value")
    public float value;

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
        return ACTION_TYPE_GLUCOSE;
    }

    @Override
    public String toString() {
        return "GlucoseReading{" +
                "value=" + value +
                ", time=" + time +
                ", note='" + note + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }

    public String exportItem() {
        String ret = TAG + "|" + value + "|" + time + "|" + note + "|" + comment + "|";
        return ret;
    }

    public void importItem(String item) {
        String[] items = item.split(FIELD_DELIMITER, -1);
        value=Float.parseFloat(items[1]);
        time= InsulinUtils.getDateFromString(items[2]);
        note=Integer.parseInt(items[3]);
        comment=items[4];
    }


}
