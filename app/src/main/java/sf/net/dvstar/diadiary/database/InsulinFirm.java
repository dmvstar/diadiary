package sf.net.dvstar.diadiary.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;
import java.util.Date;


@Table(name = "InsulinFirm")
public class InsulinFirm extends Model implements Serializable, ActionCommonItem {

    /*
    id	code	name
1	NOVO	Novo Nordisk
    */

    public InsulinFirm(){
        super();
    }

    public InsulinFirm(String code, String name){
        super();
        this.code = code;
        this.name = name;
    }

    @Column(name = "code", index = true, unique = true)
    public String code;

    @Column(name = "name", notNull = true)
    public String name;

    @Override
    public String toString() {
        return "InsulinFirm{" +
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

    @Override
    public boolean equals(Object m){
        return this.code.equals(((InsulinFirm) m).code);
    }

    @Override
    public Date getCompareTime() {
        return null;
    }

    @Override
    public int getActionType() {
        return 0;
    }
}
