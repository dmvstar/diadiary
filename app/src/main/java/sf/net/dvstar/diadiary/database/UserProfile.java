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
    public String name;

    @Column(name = "birth")
    public Date birth;// дата рождения

    @Column(name = "age")
    public String age;//возраст

    @Column(name = "growth")
    Float growth;//рост

    @Column(name = "weight")
    Float weight;//вес

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

    @Column(name = "locale")
    String locale;

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
