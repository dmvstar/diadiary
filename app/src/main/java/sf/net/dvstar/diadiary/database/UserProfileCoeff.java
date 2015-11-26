package sf.net.dvstar.diadiary.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;
import java.util.Date;


@Table(name = "UserProfileCoeff")
public class UserProfileCoeff extends Model implements Serializable, CommonItem {

    public static String TAG = "UserProfileCoeff";

    @Column(name = "user")
    public UserProfile user;

    @Column(name = "create")
    public Date create;

    @Column(name = "k1")
    public float k1;

    @Column(name = "k2")
    public float k2;

    @Column(name = "k3")
    public float k3;

    @Override
    public String exportItem() {
        String ret = TAG + "|";

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
        return "k1="+k1+",k2="+k2+",k3="+k3;
    }


}
