package sf.net.dvstar.diadiary.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;
import java.util.Date;


@Table(name = "UserProfile")
public class UserProfile extends Model implements Serializable, CommonItem {

    public static String TAG = "UserProfile";

    @Column(name = "name")
    String name;

    @Column(name = "age")
    String age;

    @Column(name = "birth")
    Date birth;

    @Column(name = "growth")
    Float growth;

    @Column(name = "weight")
    Float weight;

    @Column(name = "locale")
    String locale;

    @Column(name = "gender")
    int gender;

    @Column(name = "diabetType")
    int diabetType;

    @Column(name = "glucoseMeasure")
    String glucoseMeasure;

    @Column(name = "prefRangeMix")
    Float prefRangeMix;

    @Column(name = "prefRangeMax")
    Float prefRangeMax;

    public UserProfile() {
        super();
    }

    @Override
    public String exportItem() {
        String ret = TAG + "|"
                + name + "|";
        return ret;
    }

    @Override
    public void importItem(String item) {
        String[] items = item.split(FIELD_DELIMITER, -1);
        int index = 0;
        if(items[index].equals(TAG)) index++;
    }

    @Override
    public String getListText() {
        return name;
    }


}
