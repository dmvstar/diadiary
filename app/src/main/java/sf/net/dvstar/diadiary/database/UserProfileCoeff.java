package sf.net.dvstar.diadiary.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;
import java.util.Date;

import sf.net.dvstar.diadiary.utilitis.CommonUtils;


@Table(name = "UserProfileCoeff")
public class UserProfileCoeff extends Model implements Serializable, CommonItem {

    public static String TAG = "UserProfileCoeff";

    public UserProfileCoeff(){
        super();
    }

    @Column(name = "user")
    public UserProfile user;

    @Column(name = "created")
    public Date created;

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

    @Override
    public String toString() {
        return "k1="+k1 +"("+ CommonUtils.getDateText(created) +")";
    }

}
