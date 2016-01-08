package sf.net.dvstar.diadiary.activity;

import android.view.View;

public interface ActivitySaving {

    public void fillFieldData();
    public void saveFieldData();
    public void cancel(View view);
    public void confirm(View view);

}
