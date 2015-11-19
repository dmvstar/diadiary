package sf.net.dvstar.diadiary.insulins;

import android.graphics.Color;

/**
 * Created by dstarzhynskyi on 09.10.2015.
 */
public interface InsulinConstants {

    // 1-start 2-max 3-end
    int WMODE_NON = 0;
    int WMODE_STT = 1;
    int WMODE_STW = 10;
    int WMODE_MAX = 2;
    int WMODE_MAW = 20;
    int WMODE_END = 3;

    int I_WORK_MODE_MINUTES = 1;
    int I_WORK_MODE_HOURS = 2;

    String S_WORK_MODE_MINUTES = "m";
    String S_WORK_MODE_HOURS = "h";

    int L_DIRECTION_UP = 1;
    int L_DIRECTION_DN = 2;

    String KEY_INTENT_EXTRA_EDIT_MODE = "edit_item";
    String KEY_INTENT_EXTRA_EDIT_ITEM = "item_item";
    String KEY_INTENT_EXTRA_ROW_ID = "row_id";
    String KEY_INTENT_EXTRA_GET_PRODUCT = "get_product";

    int MODE_ACTIONS_EDIT_ITEM = 1;
    int MODE_ACTIONS_EDIT_ADD = 2;
    int MODE_ACTIONS_GET_PRODUCT = 3;

    int COLOR_NOVORAPID = Color.parseColor("#FF9900");
    int COLOR_LEVEMIR = Color.parseColor("#00AF8E");
    int COLOR_ACTRAPID = Color.parseColor("#E2CD36");
    int COLOR_PROTAFAN = Color.parseColor("#67B943");
    int COLOR_LANTUS = Color.parseColor("#9C80CC");
    int COLOR_APIDRA = Color.parseColor("#15479C");

}
