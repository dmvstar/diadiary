package sf.net.dvstar.diadiary.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;


@Table(name = "ProductMenu")
public class ProductMenu extends Model implements Serializable, CommonItem {

    public static String TAG = "ProductMenu";

    @Column(name = "name")
    String name;

    @Column(name = "locale")
    String locale;

    public ProductMenu() {
        super();
        this.locale="*";
    }

    public ProductMenu(String locale) {
        super();
        this.locale=locale;
    }

    @Override
    public String exportItem() {
        String ret = TAG + "|"

                + name + "|"

                + locale + "|";
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
