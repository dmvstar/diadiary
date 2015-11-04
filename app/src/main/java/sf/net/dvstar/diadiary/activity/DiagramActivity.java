package sf.net.dvstar.diadiary.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.LineData;

import sf.net.dvstar.diadiary.R;
import sf.net.dvstar.diadiary.insulins.InsuGraphContent;
import sf.net.dvstar.diadiary.insulins.InsuGraphProvider;
import sf.net.dvstar.diadiary.insulins.InsulinWork;

public class DiagramActivity extends AppCompatActivity {

    private static final String TAG = "DiagramActivity";
    InsuGraphProvider vInsuGraphProvider = new InsuGraphProvider();
    private LineChart[] mCharts = new LineChart[4];
    private Typeface mTf;
    private int[] mColors = new int[]{
            Color.rgb(137, 230, 81),
            Color.rgb(240, 240, 30),
            Color.rgb(89, 199, 250),
            Color.rgb(250, 104, 104
            )
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
*/
        //setContentView(R.layout.activity_colored_lines);
        setContentView(R.layout.activity_graph);

        mCharts[0] = (LineChart) findViewById(R.id.chart1);
        mCharts[1] = (LineChart) findViewById(R.id.chart2);
        mCharts[2] = (LineChart) findViewById(R.id.chart3);
        mCharts[3] = (LineChart) findViewById(R.id.chart4);

        mTf = Typeface.createFromAsset(getAssets(), "OpenSans-Bold.ttf");

        LineData data;

        prepareDataInsulin();

        for (int i = 0; i < mCharts.length; i++) {
            // add some transparency to the color with "& 0x90FFFFFF"

            switch (i) {

                case 0:
                case 1: {
                    InsuGraphContent insulinGraphContent = vInsuGraphProvider.getInsulinGraphContent(i);
                    data = insulinGraphContent.getLineDataInsulin();
                    //data = getLineDataInsulin(insulins[i]);
                    data.setValueTypeface(mTf);
                    setupChart(insulinGraphContent.getInsulinName(), mCharts[i], data, mColors[i % mColors.length]);
                }
                break;

                case 2: {
                    data = vInsuGraphProvider.getLineDataCombineInsulin();
                    data.setValueTypeface(mTf);
                    setupChart(vInsuGraphProvider.getLineDataCombineInsulinNames(), mCharts[i], data, mColors[i % mColors.length]);
                }
                break;

                case 3: {
                    /*
                    data = getData(36, 100);
                    data.setValueTypeface(mTf);
                    setupChart("Number " + i, mCharts[i], data, mColors[i % mColors.length]);
                    */
                    data = vInsuGraphProvider.getLineDataSummaryInsulin();
                    data.setValueTypeface(mTf);
                    setupChart("Summary" + i, mCharts[i], data, mColors[i % mColors.length]);

                }
                break;

                default:
                    break;

            }


        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //FloatingActionButton fab_menu_action = (FloatingActionButton) findViewById(R.id.fab_menu_action);

        // https://github.com/Clans/FloatingActionButton
        com.github.clans.fab.FloatingActionButton fab = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showActionsActivity();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_graph, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    private void setupChart(String title, LineChart chart, LineData data, int color) {

        // no description text
        chart.setDescription(title);
        chart.setDescriptionColor(Color.RED);
        chart.setDescriptionTextSize(24f);

        chart.setNoDataTextDescription("You need to provide data for the chart.");

        // mChart.setDrawHorizontalGrid(false);
        //
        // enable / disable grid background
        chart.setDrawGridBackground(false);
//        chart.getRenderer().getGridPaint().setGridColor(Color.WHITE & 0x70FFFFFF);

        // enable touch gestures
        chart.setTouchEnabled(true);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(false);

        chart.setBackgroundColor(color);

        // set custom chart offsets (automatic offset calculation is hereby disabled)
        chart.setViewPortOffsets(10, 0, 10, 0);

        // add data
        chart.setData(data);

        // get the legend (only possible after setting data)
        Legend l = chart.getLegend();
        l.setEnabled(false);

        chart.getAxisLeft().setEnabled(false);
        chart.getAxisRight().setEnabled(false);

        chart.getXAxis().setEnabled(false);

        // animate calls invalidate()...
        chart.animateX(2500);
    }

    private void prepareDataInsulin() {

        InsulinWork insulinWork;

        insulinWork = new InsulinWork(
                "actrapid",
                new InsulinWork.InsulinTime[]{
                        new InsulinWork.InsulinTime(20, "m"),
                        new InsulinWork.InsulinTime(1, "h"),
                        new InsulinWork.InsulinTime(6, "h")
                }
        );
        vInsuGraphProvider.addInsulin(insulinWork, 8, 0);

        insulinWork = new InsulinWork(
                "protafan",
                new InsulinWork.InsulinTime[]{
                        new InsulinWork.InsulinTime(1, "h"),
                        new InsulinWork.InsulinTime(4, "h"),
                        new InsulinWork.InsulinTime(18, "h")
                }
        );
        vInsuGraphProvider.addInsulin(insulinWork, 16, 0);

        vInsuGraphProvider.normalizeXAxisValues();

        vInsuGraphProvider.createSummaryInsulin();

    }

    //----------------------------------------------------------------------------------------------

}
