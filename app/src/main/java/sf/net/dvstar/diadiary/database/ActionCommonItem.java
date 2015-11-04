package sf.net.dvstar.diadiary.database;

import java.util.Date;

public interface ActionCommonItem extends CommonItem {

    int ACTION_TYPE_INJECT = 1;
    int ACTION_TYPE_GLUCOSE = 2;
    int ACTION_TYPE_INSULIN = 3;

    Date   getCompareTime();
    int getActionType();
}
