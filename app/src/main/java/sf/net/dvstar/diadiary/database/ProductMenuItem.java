package sf.net.dvstar.diadiary.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;


@Table(name = "ProductMenuItem")
public class ProductMenuItem extends Model implements Serializable, CommonItem {

    public static String TAG = "ProductMenuItem";

    @Column(name = "name")
    String name;

    @Column(name = "menu")
    ProductMenuDesc menu;

    @Column(name = "prod")
    ProductItem prod;

    @Column(name = "weight")
    Float weight;//вес

    @Column(name = "fats")
    float fats;//жиры

    @Column(name = "carb")
    float carb;//углеводы

    @Column(name = "prot")
    float prot;// белок

    @Column(name = "gi")
    int gi;// ГИ гликемический индек

    public ProductMenuItem() {
        super();
    }

    @Override
    public String exportItem() {
        String ret = TAG + "|"
                + name + "|"
                +"<"+ menu + ">|";
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
