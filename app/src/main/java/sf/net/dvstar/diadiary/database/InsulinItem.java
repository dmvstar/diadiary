package sf.net.dvstar.diadiary.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;

/**
 * Created by sdv on 18.10.15.
 */
@Table(name = "InsulinItem")
public class InsulinItem extends Model implements Serializable, CommonItem {

    /*
    name	    type	firm	origin	start_min	start_max	start_measure	work_min	work_max	work_measure	ends_min	ends_max	ends_measure
    Humalog	    UHS	    LILLY	HBIOS	5	15	m	1-2	1	2	h	4-5	4	5	h	Хумалог (лизпро)
    Novorapid	UHS	    NOVO	HBIOS	5	15	m	1-2	1	2	h	4-5	4	5	h	Новорапид (аспарт)
    Apidra	    UHS	    AVENTIS	HBIOS	5	15	m	1-2	1	2	h	4-5	4	5	h	Авентис (Хехст)Апидра (глюлизин)
     */

    @Column(name = "name", notNull = true, unique = true)
    public String name;

    @Column(name = "type", notNull = true)
    public InsulinType type;

    @Column(name = "firm", notNull = true)
    public InsulinFirm firm;

    @Column(name = "origin", notNull = true)
    public InsulinOrigin origin;

    @Column(name = "start_min", notNull = true)
    public int start_min;

    @Column(name = "start_max", notNull = true)
    public int start_max;

    @Column(name = "start_measure", notNull = true)
    public String start_measure;

    @Column(name = "work_min", notNull = true)
    public int work_min;

    @Column(name = "work_max", notNull = true)
    public int work_max;

    @Column(name = "work_measure", notNull = true)
    public String work_measure;

    @Column(name = "ends_min", notNull = true)
    public int ends_min;

    @Column(name = "ends_max", notNull = true)
    public int ends_max;

    @Column(name = "ends_measure", notNull = true)
    public String ends_measure;

    @Column(name = "color", notNull = true)
    public int color;

    public InsulinItem() {
        super();
    }

    public InsulinItem(String name, int color){
        super();
        this.name = name;
        this.color = color;
    }

    public InsulinItem(String name, InsulinType type, InsulinFirm firm, InsulinOrigin origin,
                       int start_min, int start_max, String start_measure,
                       int work_min, int work_max, String work_measure,
                       int ends_min, int ends_max, String ends_measure,
                       int color) {
        super();
        this.name = name;
        this.type = type;
        this.firm = firm;
        this.origin = origin;

        this.start_min = start_min;
        this.start_max = start_max;
        this.start_measure = start_measure;

        this.work_min = work_min;
        this.work_max = work_max;
        this.work_measure = work_measure;

        this.ends_min = ends_min;
        this.ends_max = ends_max;
        this.ends_measure = ends_measure;

        this.color = color;
    }

    public InsulinItem(String name, InsulinType type, InsulinFirm firm, InsulinOrigin origin,
                       int start_min, String start_measure,
                       int work_min, String work_measure,
                       int ends_min, String ends_measure,
                       int color) {
        this(name, type, firm, origin,
                start_min, start_min, start_measure,
                work_min, work_min, work_measure,
                ends_min, ends_min, ends_measure,
                color);
    }

    @Override
    public String toString() {
        return "InsulinItem{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", firm=" + firm +
                ", origin=" + origin +
                ", start_min=" + start_min +
                ", start_max=" + start_max +
                ", start_measure='" + start_measure + '\'' +
                ", work_min=" + work_min +
                ", work_max=" + work_max +
                ", work_measure='" + work_measure + '\'' +
                ", ends_min=" + ends_min +
                ", ends_max=" + ends_max +
                ", ends_measure='" + ends_measure + '\'' +
                ", color=" + color +
                '}';
    }

    public String getWork() {
        return ""+start_min+"-"+work_min+"-"+ends_min;
    }

    @Override
    public String getListText() {
        return "["+name+"] "+name;
    }

    @Override
    public boolean equals(Object m){
        if(this.name.equals( ((InsulinItem)m).name  ))
            return true;
        return false;
    }

    /*
    @Override
    public int getActionType() {
        return ACTION_TYPE_INSULIN;
    }
    */

}
