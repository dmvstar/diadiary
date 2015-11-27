package sf.net.dvstar.diadiary.insulins;

import android.graphics.Color;
import android.util.Log;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import sf.net.dvstar.diadiary.utilitis.CommonConstants;
import sf.net.dvstar.diadiary.utilitis.CommonUtils;

/**
 * Created by sdv on 07.10.15.
 */
public class InsuGraphContent {


    private static final String TAG = "InsuGraphContent";

    private InsulinWork mInsulinWork;
    private double mTimeInjection;
    private int mInsulinDose;

    public List<InsuGraphItem> mInsuGraphItemList;

    private GraphCoord mStartGC;
    private GraphCoord mMaximGC;
    private GraphCoord mStopsGC;

    private GraphCoordPair plotLineUp;
    private GraphCoordPair plotLineDn;

    private ArrayList<Double> mXValsD;
    private ArrayList<String> mXValsS;
    private ArrayList<Double> mYValsD;
    private double[] mXAsisValues;


    public InsuGraphContent(InsulinWork aInsulinWork, int aInsulinDose, double aTimeInjection) {
        mInsulinDose    = aInsulinDose;
        mTimeInjection  = aTimeInjection;
        mInsulinWork    = aInsulinWork;
        calculateXAsisValues();
        calculateInsuGraphItems();
    }

    private void calculateXAsisValues() {
        double[] xAxisHours = new double[24];
        for (int i = 0; i < 24; i++) xAxisHours[i]=i;

        double[] bInsulin = mInsulinWork.getDoubleArray();

        mXAsisValues = CommonUtils.merge(xAxisHours, bInsulin);
    }

    public void calculateInsuGraphItems(){
        mInsuGraphItemList = null;

        double[] aInsulin = mInsulinWork.getDoubleArray();

        if (mXAsisValues != null && aInsulin != null && mXAsisValues.length>0){
            mInsuGraphItemList = new ArrayList<InsuGraphItem>();
            int working = 0;
            for (int i = 0; i < mXAsisValues.length; i++) {
                InsuGraphItem item = new InsuGraphItem();
                item.xValue = mXAsisValues[i];
                item.yValue = 0;
                if(working>0) item.wMode = working;

                for (int j = 0; j < aInsulin.length; j++) {
                    double val = aInsulin[j];

                    if(mXAsisValues[i] == val) {
                        if (j == InsuGraphItem.WMODE_STT-1) {
                            item.wMode = InsuGraphItem.WMODE_STT;
                            working = InsuGraphItem.WMODE_STW;

                            mStartGC = new GraphCoord( mXAsisValues[i], 0.0*mInsulinDose);
                        }
                        if (j == InsuGraphItem.WMODE_MAX-1) {
                            item.wMode = InsuGraphItem.WMODE_MAX;
                            item.yValue = 1*mInsulinDose;
                            mMaximGC = new GraphCoord( mXAsisValues[i], 1.0*mInsulinDose);

                            working = InsuGraphItem.WMODE_MAW;
                        }
                        if (j == InsuGraphItem.WMODE_END-1) {
                            item.wMode = InsuGraphItem.WMODE_END;
                            working = InsuGraphItem.WMODE_NON;
                            mStopsGC = new GraphCoord( mXAsisValues[i], 0.0*mInsulinDose);

                        }
                    }
                }
                mInsuGraphItemList.add(item);
            }

            plotLineUp = new GraphCoordPair(CommonConstants.L_DIRECTION_UP, mStartGC, mMaximGC);
            plotLineDn = new GraphCoordPair(CommonConstants.L_DIRECTION_DN, mMaximGC, mStopsGC);

            calculateInsuGraphAdditons(plotLineUp);
            calculateInsuGraphAdditons(plotLineDn);

            mXValsD = new ArrayList<>();
            mXValsS = new ArrayList<>();
            mYValsD = new ArrayList<>();

            for( InsuGraphItem item : mInsuGraphItemList ) {
                mXValsD.add(item.xValue);
                mXValsS.add(""+item.xValue);
                mYValsD.add(item.yValue);
            }
        }
    }


    private void calculateInsuGraphAdditons(GraphCoordPair plotLine) {
        double x,y,x_1,y_1,x_2,y_2;

        x_1 = plotLine.first.mX;
        y_1 = plotLine.first.mY;

        x_2 = plotLine.second.mX;
        y_2 = plotLine.second.mY;

        for ( int i=0; i<mInsuGraphItemList.size();i++) {
            InsuGraphItem item = mInsuGraphItemList.get(i);
            if (item.xValue > x_1 && item.xValue <x_2){
                x=item.xValue;
                y = (y_1*(x_2-x_1) + (x-x_1)*(y_2-y_1))
                        /
                        (x_2-x_1);
                y = addDistortion(item.xValue, y, plotLine);
                item.yValue = y;

                mInsuGraphItemList.set(i,item);
            }
        }
    }


    /**
     * Add distortion to y value
     * @param y
     * @param plotLine
     * @return
     */
    private double addDistortion(double x, double y, GraphCoordPair plotLine) {

        double distortion = (plotLine.second.mY+plotLine.first.mY)/(plotLine.second.mX-plotLine.first.mX);

        return y+distortion;

    }

    public String toString(){
        return "Start=["+ mStartGC.mX +"] Max=["+ mMaximGC.mX +"] End=["+ mStopsGC.mX +"] Values("+mInsuGraphItemList.size()+")="+mInsuGraphItemList;
    }

    public ArrayList<Double> getXValsD() { return mXValsD; }

    public ArrayList<String> getXValsS() {
        return mXValsS;
    }

    public ArrayList<Double> getYValsD() {
        return mYValsD;
    }


    public LineDataSet getDataSetInsulin() {
        ArrayList<Double> xValsD = getXValsD();
        ArrayList<String> xValsS = getXValsS();
        ArrayList<Double> yValsD = getYValsD();
        ArrayList<Entry> yValsE = new ArrayList<Entry>();
        for (int i=0; i<yValsD.size(); i++){
            double val = yValsD.get(i);
            yValsE.add(new Entry((float)val, i));
        }
        Log.v(TAG, "!!!!" + xValsD.size() + "-" + xValsS.size() + "-" + yValsE.size());
        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yValsE, "Insulin "+ mInsulinWork.getName());
        // set1.setFillAlpha(110);
        // set1.setFillColor(Color.RED);
        set1.setLineWidth(1.75f);
        set1.setCircleSize(3f);
        set1.setColor(Color.WHITE);
        set1.setCircleColor(Color.WHITE);
        set1.setHighLightColor(Color.WHITE);
        set1.setDrawValues(false);
        set1.setDrawCubic(true);
        set1.setDrawFilled(true);
        return set1;
    }

    public LineData getLineDataInsulin() {
        LineData data = null;
        ArrayList<String> xValsS = getXValsS();
        LineDataSet set1 = getDataSetInsulin();
        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(set1); // add the datasets
        // create a data object with the datasets
        data = new LineData(xValsS, dataSets);
        return data;
    }

    public String getInsulinName() {
        return mInsulinWork.getName();
    }

    public static class GraphCoord {
        public double mX;
        public double mY;

        public GraphCoord(double aX,double aY){
            mX=aX;
            mY=aY;
        }

        public String toString(){
            return "["+mX+"]["+mY+"]";
        }
    }

    public static class GraphCoordPair {

        public final int direction;
        public final GraphCoord first;
        public final GraphCoord second;

        public GraphCoordPair(int direction, GraphCoord first, GraphCoord second){
            this.direction = direction;
            this.first = first;
            this.second = second;
        }

        public String toString(){
            return "("+first+")("+second+")";
        }
    }

    public static class InsuGraphItem implements CommonConstants {
        public int wMode;
        public double xValue;
        public double yValue;

        public String toString(){
            return "["+wMode+"]["+xValue+"]["+yValue+"]";
        }
    }

    //----------------------------------------------------------------------------------------------

    public double[] getXAsisValues (){
        return mXAsisValues;
    }

    public void setXAsisValues (double[] aXAsisValues){
        mXAsisValues = aXAsisValues;
    }

}
