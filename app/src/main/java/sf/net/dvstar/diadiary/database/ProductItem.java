package sf.net.dvstar.diadiary.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
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
    @Column(name = "prodId", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    int prodId;

    @Column(name = "name")
    public String name;

    @Column(name = "weight_netto")
    public float weight_netto;

    @Column(name = "weight_brutto")
    public float weight_brutto;

    @Column(name = "fats")
    float fats;

    @Column(name = "carb")
    float carb;

    @Column(name = "prot")
    float prot;

    @Column(name = "gi")
    int gi;

    @Column(name = "compl")
    int compl;

    @Column(name = "groupId")
    int groupId;

    @Column(name = "usage")
    int usage;

    @Column(name = "locale")
    String locale;

    public ProductItem() {
        super();
        this.locale="*";
    }

    public ProductItem(String locale) {
        super();
        this.locale=locale;
    }

    @Override
    public String exportItem() {
        String ret = TAG + "|"
                + prodId + "|"
                + name + "|"
                + fats + "|"
                + carb + "|"
                + prot + "|"
                + gi + "|"
                + weight_netto + "|"
                + weight_brutto + "|"
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
        weight_netto=Float.parseFloat(items[index++]);
        weight_brutto=Float.parseFloat(items[index++]);
        compl=Integer.parseInt(items[index++]);
        groupId=Integer.parseInt(items[index++]);
        usage=Integer.parseInt(items[index++]);
    }

    @Override
    public String getListText() {
        return name;
    }

    @Override
    public String toString(){
        return "["+groupId+"] "+name;
    }
}
