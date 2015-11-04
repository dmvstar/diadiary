package sf.net.dvstar.diadiary.insulins;

import android.graphics.Color;
import android.util.Log;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by dstarzhynskyi on 09.10.2015.
 */
public class InsuGraphProvider {

    private static final String TAG = "InsuGraphProvider";

    public InsuGraphProvider(){
        mInsuGraphContent = new ArrayList<InsuGraphContent>();
    }

    /**
     * List of all insulin graph
     */
    private List<InsuGraphContent> mInsuGraphContent;
    /**
     * Combined data for all insulins
     */
    private InsuGraphContent mInsuGraphCombined;
    /**
     * Summary data for all insulins
     */
    private InsuGraphContent mInsuGraphSummary;

    private double[] mXAxisValues;

    /**
     * normalise the x data for time for all insugraph
      */
    public void normalizeXAxisValues(){

        double[] normalizedXAxisValues = new double[0];
        int counter=0;
        // calculate
        for (ListIterator<InsuGraphContent> it = mInsuGraphContent.listIterator(); it.hasNext(); ) {
            InsuGraphContent insuGraphContent = it.next();
            double[] current  = insuGraphContent.getXAsisValues();
            if(counter==0) {
                normalizedXAxisValues = current;
            } else {
                InsulinUtils.merge(current, normalizedXAxisValues);
            }
            counter++;
        }
        // store to
        for (ListIterator<InsuGraphContent> it = mInsuGraphContent.listIterator(); it.hasNext(); ) {
            InsuGraphContent insuGraphContent = it.next();
            insuGraphContent.setXAsisValues(normalizedXAxisValues);
            insuGraphContent.calculateInsuGraphItems();

            Log.v(TAG, "+++" + insuGraphContent);

        }

    }

    public void addInsulin(InsulinWork aInsulin, int aInsulinDose, double aTimeInjection){
        InsuGraphContent insuGraphContent = new InsuGraphContent(aInsulin, aInsulinDose, aTimeInjection);
        mInsuGraphContent.add(insuGraphContent);
        Log.v(TAG, "---"+insuGraphContent);
    }

    public LineData getLineDataSummaryInsulin(){
        LineData data = null;

        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        ArrayList<String> xValsS = null;
        ArrayList<Double> yValsDSum = null;
        boolean first = true;
        for (ListIterator<InsuGraphContent> it = mInsuGraphContent.listIterator(); it.hasNext(); ) {
            InsuGraphContent insuGraphContent = it.next();
            if (xValsS == null)     xValsS= insuGraphContent.getXValsS();
            if (yValsDSum == null)  yValsDSum = new ArrayList<Double>();
            ArrayList<Double> yValsD = insuGraphContent.getYValsD();
            if(first) {
                for ( int i=0; i<yValsD.size();i++ ){
                    yValsDSum.add(yValsD.get(i));
                }
                first = false;
            } else {
                for ( int i=0; i<yValsD.size();i++ ){
                    Double d = yValsDSum.get(i);
                    d+=yValsD.get(i);
                    yValsDSum.set(i, d);
                }
            }
            //LineDataSet lineDataSet = insuGraphContent.getDataSetInsulin();
            //dataSets.add(lineDataSet); // add the datasets
        }
        ArrayList<Entry> yValsE = new ArrayList<Entry>();
        for (int i=0; i<yValsDSum.size(); i++){
            double val = yValsDSum.get(i);
            yValsE.add(new Entry((float)val, i));
        }
        // create a dataset and give it a type
        LineDataSet dataSetsLineDataSet = new LineDataSet(yValsE, "Insulin Summary");
        // set1.setFillAlpha(110);
        // set1.setFillColor(Color.RED);
        dataSetsLineDataSet.setLineWidth(1.75f);
        dataSetsLineDataSet.setCircleSize(3f);
        dataSetsLineDataSet.setColor(Color.WHITE);
        dataSetsLineDataSet.setCircleColor(Color.WHITE);
        dataSetsLineDataSet.setHighLightColor(Color.WHITE);
        dataSetsLineDataSet.setDrawValues(false);
        dataSetsLineDataSet.setDrawCubic(true);
        dataSetsLineDataSet.setDrawFilled(true);
        // create a data object with the datasets
        data = new LineData(xValsS, dataSetsLineDataSet);
        return data;
    }

    public LineData getLineDataCombineInsulin() {
        LineData data = null;
        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        ArrayList<String> xValsS = null;
        for (ListIterator<InsuGraphContent> it = mInsuGraphContent.listIterator(); it.hasNext(); ) {
            InsuGraphContent insuGraphContent = it.next();
            xValsS = insuGraphContent.getXValsS();
            LineDataSet lineDataSet = insuGraphContent.getDataSetInsulin();
            dataSets.add(lineDataSet); // add the datasets
        }
        // create a data object with the datasets
        data = new LineData(xValsS, dataSets);
        return data;
    }

    public String getLineDataCombineInsulinNames() {
        String data = "";
        for (ListIterator<InsuGraphContent> it = mInsuGraphContent.listIterator(); it.hasNext(); ) {
            data += it.next().getInsulinName()+" ";
        }
        return data;
    }

    public InsuGraphContent getInsulinGraphContent( int index ){
        return mInsuGraphContent.get( index );
    }

    public void createSummaryInsulin() {

    }
}
