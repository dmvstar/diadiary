package sf.net.dvstar.diadiary.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;
import java.util.Date;

import sf.net.dvstar.diadiary.insulins.InsulinUtils;


@Table(name = "ProductGroup")
public class ProductGroup extends Model implements Serializable, CommonItem {

    public static String TAG = "ProductGroup";


    @Column(name = "groupId", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    int groupId;

    @Column(name = "name")
    String name;

    @Column(name = "sortOrder")
    int sortOrder;

    @Column(name = "locale")
    String locale;

    public ProductGroup() {
        super();
        this.locale="*";
    }

    public ProductGroup(String locale) {
        super();
        this.locale=locale;
    }

    public String exportItem() {
        String ret = TAG + "|"
                + groupId + "|"
                + name + "|"
                + sortOrder + "|"
                + locale + "|";
        return ret;
    }

    /*
    prodgroups
    idGroup|Name|sortInd
    1|Без группы|1
    3|Хлебобулочные изделия|3
    */

    public void importItem(String item) {
        String[] items = item.split(FIELD_DELIMITER, -1);
        int index = 0;
        if(items[index].equals(TAG)) index++;
        groupId=Integer.parseInt(items[index++]);
        name= items[index++];
        sortOrder=Integer.parseInt(items[index++]);
    }

    @Override
    public String getListText() {
        return name;
    }
}
