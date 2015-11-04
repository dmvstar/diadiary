package sf.net.dvstar.diadiary.insulins;


/**
 * Created by sdv on 11.10.15.
 */

public class InsulinWork implements InsulinConstants{
    private InsulinTime[] mInsulinTimes;
    private String mName;
    private String mFirm;
    private int    mWorkKind;
    private double[] mDubleArray;

    public InsulinWork(String aName, InsulinTime[] aInsulinTimes){
        this.setName(aName);
        this.setInsulinTime(aInsulinTimes);
        this.mDubleArray = new double[aInsulinTimes.length];
        for (int i=0;i<aInsulinTimes.length;i++) {mDubleArray[i]=aInsulinTimes[i].mWorkTime;}
        this.mWorkKind = I_WORK_MODE_HOURS;
        recalculateTo(mWorkKind);
    }

    private void recalculateTo(int mWorkKind) {
        int pos = 0;
        for( InsulinTime i : mInsulinTimes ) {

            if(i.mWorkKind!=mWorkKind) {

                if(i.mWorkKind == I_WORK_MODE_MINUTES) {
                    i.mWorkTime = i.mWorkTime/60;
                    i.mWorkKind = mWorkKind;
                } else {
                    i.mWorkTime = i.mWorkTime*60;
                    i.mWorkKind = mWorkKind;
                }

                mInsulinTimes[pos] = i;

            }
            pos++;
        }
        for (int i=0;i<mInsulinTimes.length;i++) {mDubleArray[i]=mInsulinTimes[i].mWorkTime;}

    }

    public double[] getDoubleArray(){
        return mDubleArray;
    }

    public InsulinTime[] getInsulinTime() {
        return mInsulinTimes;
    }

    public void setInsulinTime(InsulinTime[] mInsulinTimes) {
        this.mInsulinTimes = mInsulinTimes;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getFirm() {
        return mFirm;
    }

    public void setFirm(String mFirm) {
        this.mFirm = mFirm;
    }


    public static class InsulinTime {

        double mWorkTime;
        int mWorkKind;

        public InsulinTime(double aWorkTime, String aWorkKind){
            this.mWorkTime=aWorkTime;
            this.mWorkKind = I_WORK_MODE_HOURS;
            if(aWorkKind.equals("m")) this.mWorkKind = I_WORK_MODE_MINUTES;
        }

    }
}
