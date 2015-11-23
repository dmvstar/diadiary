package sf.net.dvstar.diadiary.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;
import java.util.List;


@Table(name = "ProductMenuItem")
public class ProductMenuItem extends Model implements Serializable, CommonItem {

    public static String TAG = "ProductMenuItem";

    @Column(name = "menu")
    public ProductMenuDesc menu;

    @Column(name = "prod")
    public ProductItem prod;

    @Column(name = "weight")
    public float weight;

    @Column(name = "prot")
    public float prot;// Белки

    @Column(name = "fats")
    public float fats;// Жиры

    @Column(name = "carb")
    public float carb;// Углеводы

    @Column(name = "gi")
    public int gi;// ГИ гликемический индек

    @Column(name = "xe")
    public int xe;// Количество ХЕ

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

    @Override
    public String toString(){
        return "["+menu.name+"] "+prod.name + "("+weight+")["+prot+"-"+fats+"-"+carb+"-"+gi+"-"+xe+"]";
    }


    public static class ProductMenuItemsCalc {
        public float carb = 0;
        public float fats = 0;
        public float prot = 0;
        public int xe =0;
        public int gi=0;
        public int count=0;
    }

    public static ProductMenuItemsCalc calculteProductMenuItems(List<ProductMenuItem> mListProductMenuItem){
        ProductMenuItemsCalc ret = new ProductMenuItemsCalc();
        ret.count=0;

        for (ProductMenuItem item : mListProductMenuItem) {
            ret.count++;
            ret.carb += item.carb;
            ret.fats += item.fats;
            ret.prot += item.prot;
            ret.xe += item.xe;
            ret.gi+= item.gi;
        }
        if(ret.count>0) {
            ret.gi = ret.gi / ret.count;
        }

        return ret;
    }

}
