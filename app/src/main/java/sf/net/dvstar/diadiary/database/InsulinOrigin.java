package sf.net.dvstar.diadiary.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;

/**
 * Created by sdv on 18.10.15.
 */
@Table(name = "InsulinOrigin")
public class InsulinOrigin extends Model implements Serializable, CommonItem {
    /*
    id	code	name	name_ru
    1	HBIOS	human biosynthetic	человеческий биосинтетический
    2	HSEMI	human semisynthetic	человеческий полусинтетический
    3	PORK	pork	свиной
    4	BEEF	beef	говяжий
    5	BEPO	beef-pork	говяже-свиной
     */

    public InsulinOrigin(){
        super();
    }

    public InsulinOrigin(String code, String name){
        super();
        this.code = code;
        this.name = name;
    }


    @Column(name = "code", index = true, unique = true)
    public String code;

    @Column(name = "name", notNull = true )
    public String name;

    @Override
    public String toString() {
        return "InsulinOrigin{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public String getListText() {
        return "["+code+"] "+name;
    }

    @Override
    public String exportItem() {
        return null;
    }

    @Override
    public void importItem(String item) {

    }

    /*
    @Override
    public int getActionType() {
        return ACTION_TYPE_INSULIN;
    }
    */
}
