package sf.net.dvstar.diadiary.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;
import java.util.Date;


@Table(name = "ProductMenuDesc")
public class ProductMenuDesc extends Model implements Serializable, CommonItem {

    /*
    ДПС Доза на снижение сахара крови
    БД Доза на быструю часть углеводов
    МД угл. Доза на медленную часть углеводов
    МД б/ж. Доза на белки и жиры
        Доза на снижение сахара крови и быстрая часть дозы
        Медленная часть дозы
            Доза на углеводы и на снижение сахара крови
            Вся доза
    */

    public static String TAG = "ProductMenuDesc";

    @Column(name = "name")
    public String name;

    @Column(name = "comment")
    public String comment;

    @Column(name = "created")
    public Date created;

    @Column(name = "k1")
    public float k1;

    public ProductMenuDesc() {
        super();
    }

    @Override
    public String exportItem() {
        String ret = TAG + "|"
                + name + "|" + created + "|";
        return ret;
    }

    @Override
    public void importItem(String item) {
        String[] items = item.split(FIELD_DELIMITER, -1);
        int index = 0;
        if(items[index].equals(TAG)) index++;
    }

    @Override
    public String toString() {
        return name + " ["+comment+"]";
    }

    @Override
    public String getListText() {
        return toString();
    }


}
