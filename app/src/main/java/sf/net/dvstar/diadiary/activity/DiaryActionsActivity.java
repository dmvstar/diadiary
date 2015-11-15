package sf.net.dvstar.diadiary.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import sf.net.dvstar.diadiary.R;
import sf.net.dvstar.diadiary.database.ActionCommonItem;
import sf.net.dvstar.diadiary.database.CommonItem;
import sf.net.dvstar.diadiary.database.GlucoseReading;
import sf.net.dvstar.diadiary.database.InsulinInjection;
import sf.net.dvstar.diadiary.database.InsulinItem;
import sf.net.dvstar.diadiary.adapters.DiaryActionstAdapter;
import sf.net.dvstar.diadiary.database.PressureReading;
import sf.net.dvstar.diadiary.insulins.InsulinConstants;
import sf.net.dvstar.diadiary.insulins.InsulinUtils;
import sf.net.dvstar.diadiary.utilitis.CalendarDialogBuilder;
import sf.net.dvstar.diadiary.utilitis.OIFileManager;


public class DiaryActionsActivity extends AppCompatActivity implements
        CalendarDialogBuilder.OnDateSetListener, OIFileManager {


    private static final String TAG = "DiaryActionsActivity";

    private ArrayList<ActionCommonItem> mDiaryActions;


    private TextView mTvDiaryActionsDateInto;
    private ListView mLvDiaryActions;
    private Context mContext;
    private TextView mTotalInsulunDose;
    private TextView mAverageGlucose;

    private FloatingActionMenu menu;
    private FloatingActionButton fab12;
    private FloatingActionButton fab22;
    private FloatingActionButton fab32;
    private FloatingActionButton fab33;
    private FloatingActionMenu mFloatingActionMenu;
    private DiaryActionsComparator mDiaryActionsComparator;
    private Date mDiaryActionsDateFrom;
    private Date mDiaryActionsDateInto;
    private Calendar mCalendarActionsDateFrom;
    private Calendar mCalendarActionsDateInto;
    private TextView mTvDiaryActionsDateFrom;
    private Spinner mSpPreriod;

    @Override
    protected void onResume() {
        super.onResume();
        setListViewContent();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_action);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mContext = this;

        mCalendarActionsDateFrom = Calendar.getInstance();
        mCalendarActionsDateInto = Calendar.getInstance();

        mTvDiaryActionsDateFrom = (TextView) findViewById(R.id.tv_diary_actions_date_from);
        mTvDiaryActionsDateInto = (TextView) findViewById(R.id.tv_diary_actions_date_into);
        mTotalInsulunDose = (TextView) findViewById(R.id.tv_injection_total_value);
        mAverageGlucose = (TextView) findViewById(R.id.tv_average_glucose_value);

        mSpPreriod = (Spinner) findViewById(R.id.sp_actin_period);

        Date today = Calendar.getInstance().getTime();
        mTvDiaryActionsDateFrom.setText(InsulinUtils.getDateText(today));
        mTvDiaryActionsDateInto.setText(InsulinUtils.getDateText(today));
        mDiaryActionsDateFrom = InsulinUtils.getDateTimeFrom(null, today);
        mDiaryActionsDateInto = InsulinUtils.getDateTimeFrom(today, Calendar.DAY_OF_MONTH, 1);
        mTvDiaryActionsDateInto.setOnClickListener(new ActionsOnDateSetListener());

        /*
        FloatingActionButton fab_menu_action = (FloatingActionButton) findViewById(R.id.fab_menu_action);
        fab_menu_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddInsulinsInjection(InsulinConstants.MODE_ACTIONS_EDIT_ADD, view, null);
            }
        });
        */

        mFloatingActionMenu = (FloatingActionMenu) findViewById(R.id.menu_actions_add);

        fab12 = (FloatingActionButton) findViewById(R.id.fab_inject);
        fab22 = (FloatingActionButton) findViewById(R.id.fab_eating);
        fab32 = (FloatingActionButton) findViewById(R.id.fab_glucose);
        fab33 = (FloatingActionButton) findViewById(R.id.fab_pressure);

        fab12.setOnClickListener(clickListener);
        fab22.setOnClickListener(clickListener);
        fab32.setOnClickListener(clickListener);
        fab33.setOnClickListener(clickListener);

        setListViewContent();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.action_settings) {
            showSettingActivity();
        }

/*
        if (id == R.id.action_injections) {
            showActionsActivity();
        }
*/
        if (id == R.id.action_insulins) {
            showInsulinDescActivity();
        }

        if (id == R.id.action_graph) {
            showDiagramActivity();
        }

        if (id == R.id.action_database) {
            showDatabaseActivity();
        }

        if (id == R.id.action_about) {
            showAboutActivity();
        }

        if(id == R.id.action_products) {
            showProdMenuActivity();
        }

        return super.onOptionsItemSelected(item);
    }

    private void showDatabaseActivity() {
        Intent intent = new Intent(this, DatabaseActivity.class);
        //intent.putExtra("key", value); //Optional parameters
        this.startActivity(intent);
    }

    private void showDiagramActivity() {
        Intent intent = new Intent(this, DiagramActivity.class);
        //intent.putExtra("key", value); //Optional parameters
        this.startActivity(intent);
    }


    private void showInsulinDescActivity() {
        Intent intent = new Intent(this, InsulinDescActivity.class);
        //intent.putExtra("key", value); //Optional parameters
        this.startActivity(intent);
    }

    @Override
    public void onDateSet(int Year, int Month, int Day, int mode) {
        setDateTextField(Year, Month+1, Day, mode);
    }

    public void setDateTextField(int Year, int Month, int Day, int mode) {
        String test = InsulinUtils.getDateText(Year, Month, Day);
        boolean changeContent = false;
        if (Year > 0) {
            // && !mTvDiaryActionsDateInto.getText().toString().equals(test)
            switch (mode) {

                case CalendarDialogBuilder.DATE_SET_MODE_ONCE: {
                    if (!mTvDiaryActionsDateFrom.getText().toString().equals(test)) {
                        changeContent = true;
                        mTvDiaryActionsDateFrom.setText(test);
                        mDiaryActionsDateFrom = InsulinUtils.getDateTimeFrom(Year, Month, Day);
                        mCalendarActionsDateFrom.set(Year, Month-1, Day);

                        mTvDiaryActionsDateInto.setText(test);
                        mDiaryActionsDateInto = InsulinUtils.getDateTimeFrom(Year, Month, Day+1);
                        mCalendarActionsDateInto.set(Year, Month-1, Day+1);
                    }

                }
                break;
                case CalendarDialogBuilder.DATE_SET_MODE_FROM: {
                    if (!mTvDiaryActionsDateFrom.getText().toString().equals(test)) {
                        changeContent = true;
                        mTvDiaryActionsDateFrom.setText(test);
                        mDiaryActionsDateFrom = InsulinUtils.getDateTimeFrom(Year, Month, Day);
                        mCalendarActionsDateFrom.set(Year, Month, Day);
                    }
                }
                break;
                case CalendarDialogBuilder.DATE_SET_MODE_INTO: {
                    if (!mTvDiaryActionsDateInto.getText().toString().equals(test)) {
                        changeContent = true;
                        mTvDiaryActionsDateInto.setText(test);
                        mDiaryActionsDateInto = InsulinUtils.getDateTimeFrom(Year, Month, Day);
                        mCalendarActionsDateInto.set(Year, Month, Day);
                    }
                }
                break;

            }


            if (changeContent)
                setListViewContent();
        }
    }

    class ActionsOnDateSetListener implements DatePickerDialog.OnDateSetListener, View.OnClickListener, CalendarDialogBuilder.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String test = InsulinUtils.getDateText(year, monthOfYear + 1, dayOfMonth + 1);


            if (!mTvDiaryActionsDateInto.getText().toString().equals(test)) {
                mTvDiaryActionsDateInto.setText(test);
                mDiaryActionsDateFrom = InsulinUtils.getDateTimeFrom(year, monthOfYear, dayOfMonth);
                mCalendarActionsDateFrom.set(year, monthOfYear, dayOfMonth);
                setListViewContent();
            }
        }

        @Override
        public void onClick(View v) {

            showDateSelect(v);

        }

        @Override
        public void onDateSet(int Year, int Month, int Day, int mode) {
            setDateTextField(Year, Month, Day, mode);
        }
    }

    /**
     * Set content for actions
     */
    private void setListViewContent() {
        mLvDiaryActions = (ListView) findViewById(R.id.lv_diary_action_list);
        mLvDiaryActions.setItemsCanFocus(false);

        ArrayList<InsulinInjection> aInsulinsInjections;
        ArrayList<GlucoseReading> aGlucoseReading;
        ArrayList<PressureReading> aPressureReading;

        aInsulinsInjections = getInsulinsInjections();
        aGlucoseReading = getGlucodeReading();
        aPressureReading = getPressureReading();

        mDiaryActions = new ArrayList<>();
        mDiaryActionsComparator = new DiaryActionsComparator();

        for (InsulinInjection item : aInsulinsInjections) {
            mDiaryActions.add(item);
            //Log.v(TAG, "--------1 " + item);
        }
        for (GlucoseReading item : aGlucoseReading) {
            mDiaryActions.add(item);
            //Log.v(TAG, "--------2 " + item);
        }
        for (PressureReading item : aPressureReading) {
            mDiaryActions.add(item);
            //Log.v(TAG, "--------3 " + item);
        }

        Collections.sort(mDiaryActions, mDiaryActionsComparator);
        //Log.v(TAG, "--------2 "+mDiaryActions);

        for (ActionCommonItem item : mDiaryActions) {
            Log.v(TAG, "--------4 " + item);
        }

        //ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,mInsulinsInjections);
        DiaryActionstAdapter adapter = new DiaryActionstAdapter(this, mDiaryActions);

        mLvDiaryActions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
//                Toast.makeText(getBaseContext(), "itemSelect: position = " + position + ", id = "
//                        + id + ", " + parent.getAdapter().getItem(position), Toast.LENGTH_SHORT).show();
                if (mDiaryActions.get(position).getActionType() == ActionCommonItem.ACTION_TYPE_INJECT)
                    showAddInsulinsInjection(InsulinConstants.MODE_ACTIONS_EDIT_ITEM, view, (InsulinInjection) mDiaryActions.get(position));
                if (mDiaryActions.get(position).getActionType() == ActionCommonItem.ACTION_TYPE_GLUCOSE)
                    showAddGlucoseReading(InsulinConstants.MODE_ACTIONS_EDIT_ITEM, view, (GlucoseReading) mDiaryActions.get(position));
                if (mDiaryActions.get(position).getActionType() == ActionCommonItem.ACTION_TYPE_PRESSURE)
                    showAddPressureReading(InsulinConstants.MODE_ACTIONS_EDIT_ITEM, view, (PressureReading) mDiaryActions.get(position));
            }
        });

        mLvDiaryActions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
//                Toast.makeText(getBaseContext(),"itemSelect: position = " + position + ", id = "
//                        + id+" "+parent.getSelectedItem(), Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> parent) {
//                Toast.makeText(getBaseContext(), "itemSelect: nothing", Toast.LENGTH_SHORT).show();
            }
        });

        mLvDiaryActions.setOnItemLongClickListener(

                new AdapterView.OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int position, long id) {

                        CharSequence items[] = new CharSequence[]{getResources().getString(R.string.dialog_edit),
                                getResources().getString(R.string.dialog_delete)};
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                        builder.setItems(items, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (which == 0) {
                                    // EDIT
                                    showAddAction(InsulinConstants.MODE_ACTIONS_EDIT_ITEM, view, (CommonItem) parent.getAdapter().getItem(position));
                                } else {
                                    // DELETE
                                    showDelAction(InsulinConstants.MODE_ACTIONS_EDIT_ITEM, view, (CommonItem) parent.getAdapter().getItem(position));
                                }
                            }
                        });
                        builder.show();

                        return true;
                    }
                }
        );

        mLvDiaryActions.setAdapter(adapter);

        calculateTotalInsulinDose();
        calculateAverageGlucode();
    }

    public void showDateSelect(View view) {

/*
            LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout ll= (LinearLayout)inflater.inflate(R.layout.calendar_dialog, null, false);
            CalendarView cv = (CalendarView) ll.getChildAt(0);
            cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

                @Override
                public void onSelectedDayChange(CalendarView view, int year, int month,
                                                int dayOfMonth) {

                }
            });
            new AlertDialog.Builder(DiaryActionsActivity.this)
                    .setTitle("Event Calendar")
                    .setMessage("Click to schedule or view events.")
                    .setView(ll)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            //do nothing...yet
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // Do nothing.
                        }
                    }
            ).show();
*/

        if (mSpPreriod.getSelectedItemPosition() > 2) {
            CalendarDialogBuilder calendar;
            calendar = new CalendarDialogBuilder(this, this, "To " + mSpPreriod.getSelectedItem().toString(), CalendarDialogBuilder.DATE_SET_MODE_INTO);
            //calendar.setStartDate( mCalendarActionsDate.getTime().getTime()  );
            //calendar.setEndDate( mCalendarActionsDate.getTime().getTime() );
            calendar.showCalendar();

            CalendarDialogBuilder calendarFrom;
            calendarFrom = new CalendarDialogBuilder(this, this, "From " + mSpPreriod.getSelectedItem().toString(), CalendarDialogBuilder.DATE_SET_MODE_FROM);
            //calendar.setStartDate( mCalendarActionsDate.getTime().getTime()  );
            //calendar.setEndDate( mCalendarActionsDate.getTime().getTime() );
            calendarFrom.showCalendar();
        } else {
            CalendarDialogBuilder calendar;
            calendar = new CalendarDialogBuilder(this, this, "Date " + mSpPreriod.getSelectedItem().toString(), CalendarDialogBuilder.DATE_SET_MODE_ONCE);
            //calendar.setStartDate( mCalendarActionsDate.getTime().getTime()  );
            //calendar.setEndDate( mCalendarActionsDate.getTime().getTime() );
            calendar.setDate(mCalendarActionsDateFrom.getTimeInMillis());
            calendar.showCalendar();
        }

//            CalendarDialogBuilder.showCalendarViewDialog(mContext);
/*
            new DatePickerDialog(mContext, this, mCalendarActionsDate
                    .get(Calendar.YEAR), mCalendarActionsDate.get(Calendar.MONTH),
                    mCalendarActionsDate.get(Calendar.DAY_OF_MONTH)).show();
*/

    }

    private void calculateTotalInsulinDose() {
        int totalDose = 0;
        for (ActionCommonItem item : mDiaryActions) {
            if (item instanceof InsulinInjection) {
                totalDose += Integer.parseInt(((InsulinInjection) item).dose);
            }
        }
        mTotalInsulunDose.setText("" + totalDose + " " + getResources().getString(R.string.insulin_inject_unit));
    }

    private void calculateAverageGlucode() {
        float totalDose = 0;
        int count = 0;
        for (ActionCommonItem item : mDiaryActions) {
            if (item instanceof GlucoseReading) {
                totalDose += ((GlucoseReading) item).value;
                count += 1;
            }
        }
        if(count>0) totalDose = totalDose/count;
        mAverageGlucose.setText("" + totalDose + " " + getResources().getString(R.string.glucoce_label_unit));
    }


    public ArrayList<GlucoseReading> getGlucodeReading() {
        List<GlucoseReading> ret;
        ret = new Select()
                .from(GlucoseReading.class)
                .where("time >= ?", mDiaryActionsDateFrom.getTime())
                .and("time <= ?", mDiaryActionsDateInto.getTime())
                .orderBy("time")
                .execute();
        Log.v(TAG, "++++++++[" + mDiaryActionsDateFrom + "][" + ret.size() + "]" + ret.toString());
        return (ArrayList<GlucoseReading>) ret;
    }

    private ArrayList<InsulinInjection> getInsulinsInjections() {
        List<InsulinInjection> ret;// = new ArrayList<>();
        ret = new Select()
                .from(InsulinInjection.class)
                .where("plan = ?", InsulinInjection.INJECTION_PLAN_REGULAR)
                .or("date >= ? and date < ?", mDiaryActionsDateFrom.getTime(), mDiaryActionsDateInto.getTime())
                .orderBy("time")
                .execute();

        Log.v(TAG, "!!!!!!!![" + mDiaryActionsDateFrom + "][" + ret.size() + "]" + ret.toString());
        return (ArrayList<InsulinInjection>) ret;
    }

    private ArrayList<PressureReading> getPressureReading() {
        List<PressureReading> ret;
        ret = new Select()
                .from(PressureReading.class)
                .where("time >= ?", mDiaryActionsDateFrom.getTime())
                .and("time <= ?", mDiaryActionsDateInto.getTime())
                .orderBy("time")
                .execute();
        Log.v(TAG, "!!!!!!!![" + mDiaryActionsDateFrom + "][" + ret.size() + "]" + ret.toString());
        return (ArrayList<PressureReading>) ret;
    }


    private ArrayList<InsulinItem> getInsulinItems() {
        List<InsulinItem> insulins = new Select()
                .from(InsulinItem.class)
                .execute();

        Log.v(TAG, "!!!!!!!!" + insulins.toString());
        return (ArrayList<InsulinItem>) insulins;
    }

    private void showAddAction(int mode, View view, CommonItem item) {
        if (item instanceof InsulinInjection) {
            showAddInsulinsInjection(mode, view, (InsulinInjection) item);
        }
        if (item instanceof GlucoseReading) {
            showAddGlucoseReading(mode, view, (GlucoseReading) item);
        }
    }

    private void showProdMenuActivity() {
        Intent intent = new Intent(this, ProdMenuActivity.class);
        this.startActivity(intent);
    }

    /**
     * Show add or edit activity
     *
     * @param mode work mode add or edit
     * @param view parent View
     * @param item data value
     */
    private void showAddInsulinsInjection(int mode, View view, InsulinInjection item) {

        Intent intent = new Intent(this, InsulinInjectAddActivity.class);
        intent.putExtra(InsulinConstants.KEY_INTENT_EXTRA_EDIT_MODE, mode);

        if (item != null) {
            long rowId = item.getId();
            intent.putExtra(InsulinConstants.KEY_INTENT_EXTRA_ROW_ID, rowId);
            intent.putExtra(InsulinConstants.KEY_INTENT_EXTRA_EDIT_ITEM, item);
        }

        this.startActivity(intent);

    }

    private void showSettingActivity() {
        Intent intent = new Intent(this, SettingActivity.class);
        this.startActivity(intent);
    }

    private void showMenuActivity() {
        Intent intent = new Intent(this, ProdMenuActivity.class);
        this.startActivity(intent);
    }


    private void showAddGlucoseReading(int mode, View view, GlucoseReading item) {

        Intent intent = new Intent(this, DiaryGlucoseAddActivity.class);
        intent.putExtra(InsulinConstants.KEY_INTENT_EXTRA_EDIT_MODE, mode);
        if (item != null) {
            long rowId = item.getId();
            intent.putExtra(InsulinConstants.KEY_INTENT_EXTRA_ROW_ID, rowId);
        }

        this.startActivity(intent);
    }

    private void showAddPressureReading(int mode, View view, PressureReading item) {
        Intent intent = new Intent(this, DiaryPressureAddActivity.class);
        intent.putExtra(InsulinConstants.KEY_INTENT_EXTRA_EDIT_MODE, mode);
        if (item != null) {
            long rowId = item.getId();
            intent.putExtra(InsulinConstants.KEY_INTENT_EXTRA_ROW_ID, rowId);
        }
        this.startActivity(intent);
    }

    private void showAboutActivity() {

        Intent intent = new Intent(this, AboutActivity.class);
        this.startActivity(intent);
    }

    private void showDelAction(int mode, View view, CommonItem item) {
        if (item instanceof InsulinInjection) {
            showDelInsulinsInjection(mode, view, (InsulinInjection) item);
        }
        if (item instanceof GlucoseReading) {
            showDelGlucoseReading(mode, view, (GlucoseReading) item);
        }
        setListViewContent();
    }

    private void showDelGlucoseReading(int mode, View view, GlucoseReading item) {
        item.delete();
    }

    private void showDelInsulinsInjection(int mode, View view, InsulinInjection item) {
        item.delete();
    }


    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String text = "";

            switch (v.getId()) {
                case R.id.fab_inject:
                    text = fab12.getLabelText();
                    mFloatingActionMenu.close(false);
                    showAddInsulinsInjection(InsulinConstants.MODE_ACTIONS_EDIT_ADD, v, null);
                    break;
                case R.id.fab_eating:
                    text = fab22.getLabelText();
                    mFloatingActionMenu.close(false);
                    break;
                case R.id.fab_glucose:
                    text = fab32.getLabelText();
                    mFloatingActionMenu.close(false);
                    showAddGlucoseReading(InsulinConstants.MODE_ACTIONS_EDIT_ADD, v, null);
                    break;
                case R.id.fab_pressure:
                    text = fab33.getLabelText();
                    mFloatingActionMenu.close(false);
                    showAddPressureReading(InsulinConstants.MODE_ACTIONS_EDIT_ADD, v, null);
                    break;

            }

            Toast.makeText(DiaryActionsActivity.this, text, Toast.LENGTH_SHORT).show();
        }
    };


    private class DiaryActionsComparator implements Comparator<ActionCommonItem> {
        @Override
        public int compare(ActionCommonItem lhs, ActionCommonItem rhs) {
            int ret;
            // < 0 lhs < rhs
            // > 0 lhs > rhs
            Date lds = lhs.getCompareTime();
            Date rds = rhs.getCompareTime();
            if (lds.getTime() < rds.getTime()) ret = -1;
            else ret = 1;
            return ret;
        }
    }
}
