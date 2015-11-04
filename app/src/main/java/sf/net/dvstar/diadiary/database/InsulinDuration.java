package sf.net.dvstar.diadiary.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;

/**
 * Created by sdv on 04.10.15.
 */
@Table(name = "InsulinDuration")
public class InsulinDuration extends Model implements Serializable, CommonItem {

    /*
    id	code	descriptions
    1	USHORT	The ultra short-acting insulin preparation
    */

    public InsulinDuration(){
        super();
    }

    public InsulinDuration(String code, String description){
        super();
        this.code = code;
        this.description = description;
    }

    @Column(name = "code", index = true, unique = true)
    public String code;

    @Column(name = "description", notNull = true)
    public String description;

    @Override
    public String toString() {
        return "InsulinDuration{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public String getListText() {
        return "["+code+"] "+description ;
    }

}
