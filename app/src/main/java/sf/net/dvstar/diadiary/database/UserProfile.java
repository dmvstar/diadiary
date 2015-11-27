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
    public Float growth;//рост

    @Column(name = "weight")
    public Float weight;//вес

    @Column(name = "gender")
    public int gender;

    @Column(name = "diabetType")
    public int diabetType;

    @Column(name = "glucoseMeasure")
    public String glucoseMeasure;

    @Column(name = "prefRangeMix")
    public Float prefRangeMix;

    @Column(name = "prefRangeMax")
    public Float prefRangeMax;

    @Column(name = "locale")
    public String locale;

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


    @Override
    public String toString() {
        return "UserProfile ["+getId()+"]{" +
                "name='" + name + '\'' +
                ", birth=" + birth +
                ", age='" + age + '\'' +
                '}';
    }
}
