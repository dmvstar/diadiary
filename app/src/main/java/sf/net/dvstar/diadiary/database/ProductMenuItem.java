package sf.net.dvstar.diadiary.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;


@Table(name = "ProductMenuItem")
public class ProductMenuItem extends Model implements Serializable, CommonItem {

    public static String TAG = "ProductMenuItem";

    @Column(name = "menu")
    public ProductMenuDesc menu;

    @Column(name = "prod")
    public ProductItem prod;

    @Column(name = "prot")
    float prot;// Белки

    @Column(name = "fats")
    float fats;// Жиры

    @Column(name = "carb")
    float carb;// Углеводы

    @Column(name = "gi")
    int gi;// ГИ гликемический индек

    @Column(name = "xe")
    int xe;// Количество ХЕ

    public ProductMenuItem() {
        super();
    }

    @Override
    public String exportItem() {
        String ret = TAG + "|"
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
        return prod.name + "()";
    }

}
