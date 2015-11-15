package sf.net.dvstar.diadiary.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;
import java.util.Date;


@Table(name = "InsulinType")
public class InsulinType extends Model implements Serializable, ActionCommonItem {

    /*
    id	code	duration	mtype	stype	description
    1	UHN	1	H	H	Human
    1	UHS	1	H	S	Syntetic
     */

    public InsulinType(){
        super();
    }

    public InsulinType(String code, InsulinDuration iInsulinDuration, String itype, String desc) {
        super();
        this.code = code;
        this.durations = iInsulinDuration;
        this.itype = itype;
        this.description = desc;
    }

    @Column(name = "code", index = true, unique = true)
    public String code;

    @Column(name = "itype", notNull = true )
    public String itype;

    @Column(name = "description", notNull = true)
    public String description;

    @Column(name = "durations", notNull = true)
    public InsulinDuration durations;

    @Override
    public String toString() {
        return "InsulinType{" +
                "code='" + code + '\'' +
                ", itype='" + itype + '\'' +
                ", descr='" + description + '\'' +
                ", durations=" + durations +
                '}';
    }

    @Override
    public String getListText() {
        return "["+code+"] "+description +" ("+durations.getListText()+")";
    }

    @Override
    public String exportItem() {
        return null;
    }

    @Override
    public void importItem(String item) {

    }

    @Override
    public Date getCompareTime() {
        return null;
    }

    @Override
    public int getActionType() {
        return 0;
    }

    /*
    @Override
    public int getActionType() {
        return ACTION_TYPE_INSULIN;
    }
    */
}
