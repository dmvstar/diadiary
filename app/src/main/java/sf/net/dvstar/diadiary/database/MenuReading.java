package sf.net.dvstar.diadiary.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;
import java.util.Date;

import sf.net.dvstar.diadiary.utilitis.CommonUtils;


@Table(name = "MemuReading")
public class MenuReading extends Model implements Serializable, ActionCommonItem {

    public static String TAG = "MemuReading";

    public MenuReading(){ super(); }

    @Column(name = "menuDesc")
    ProductMenuDesc menuDesc;

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
        return ACTION_TYPE_MENU;
    }



    @Override
    public String exportItem() {
        String ret = TAG;
        return ret;
    }

    @Override
    public void importItem(String item) {
        String[] items = item.split(FIELD_DELIMITER, -1);
        int index = 1;

    }
}
