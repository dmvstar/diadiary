package sf.net.dvstar.diadiary.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;
import java.util.Date;


@Table(name = "GlucoseReading")
public class GlucoseReading extends Model implements Serializable, ActionCommonItem {

    public GlucoseReading(){ super(); }

    public GlucoseReading(float value, Date created, String notes, String comment){
        super();
        this.value = value;
        this.created = created;
        this.notes = notes;
        this.comment = comment;
    }

    @Column(name = "value")
    public float value;

    @Column(name = "created")
    public Date created;

    @Column(name = "notes")
    public String notes;

    @Column(name = "comment")
    public String comment;

    @Override
    public String getListText() {
        return "";
    }

    @Override
    public Date getCompareTime() {
        return created;
    }

    @Override
    public int getActionType() {
        return ACTION_TYPE_GLUCOSE;
    }

    @Override
    public String toString() {
        return "GlucoseReading{" +
                "value=" + value +
                ", created=" + created +
                ", notes='" + notes + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
