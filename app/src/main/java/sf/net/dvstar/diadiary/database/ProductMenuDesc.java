package sf.net.dvstar.diadiary.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;


@Table(name = "ProductMenuDesc")
public class ProductMenuDesc extends Model implements Serializable, CommonItem {

    public static String TAG = "ProductMenuDesc";

    @Column(name = "name")
    String name;



    public ProductMenuDesc() {
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
