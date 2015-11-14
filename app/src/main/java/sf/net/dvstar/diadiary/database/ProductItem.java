package sf.net.dvstar.diadiary.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Table;

import java.io.Serializable;


@Table(name = "ProductItem")
public class ProductItem extends Model implements Serializable, CommonItem {

    public static String TAG = "ProductItem";

    /*
    proditems
    idProd|name|prot|fats|carb|gi|weight|compl|idGroup|usage
    1|Вода|0.0|0.0|0.0|50|100.0|0|1|0
            2|Глюкозы таблетки австрийские|0.0|0.899999976158142|96.4000015258789|96|100.0|0|1|0
    */
    //5|Сахар|0.0|0.0|99.8000030517578|65|100.0|0|1|0
    int prodId;
    String name;
    float fats;
    float carb;
    float prot;
    int gi;
    float weight;
    int compl;
    int groupId;
    int usage;
    String locale;

    @Override
    public String exportItem() {
        String ret = TAG + "|"
                + prodId + "|"
                + name + "|"
                + fats + "|"
                + carb + "|"
                + prot + "|"
                + gi + "|"
                + weight + "|"
                + compl + "|"
                + groupId + "|"
                + usage + "|"
                + locale + "|";
        return ret;
    }

    @Override
    public void importItem(String item) {
        String[] items = item.split(FIELD_DELIMITER, -1);
        int index = 0;
        if(items[index].equals(TAG)) index++;
        prodId=Integer.parseInt(items[index++]);
        name=items[index++];
        fats=Float.parseFloat(items[index++]);
        carb=Float.parseFloat(items[index++]);
        prot=Float.parseFloat(items[index++]);
        gi=Integer.parseInt(items[index++]);
        weight=Float.parseFloat(items[index++]);
        compl=Integer.parseInt(items[index++]);
        groupId=Integer.parseInt(items[index++]);
        usage=Integer.parseInt(items[index++]);
    }

    @Override
    public String getListText() {
        return name;
    }
}
