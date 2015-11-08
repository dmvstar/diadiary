package sf.net.dvstar.diadiary.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.List;
import java.util.ListIterator;

import sf.net.dvstar.diadiary.insulins.InsulinConstants;

public class DatabaseProvider {

    private static final String TAG = "DatabaseProvider";
    private static final java.lang.String FIELD_DELIMITER = "|";
    private Context mContext;

    public DatabaseProvider(Context aContext) {
        mContext = aContext;
    }

    public boolean isCreated() {
        boolean ret;
        try {
            ret = new Select().from(InsulinDuration.class).exists();
        } catch (Exception e) {
            ret = false;
        }
        return ret;
    }

    public void initCreate() {
        if (!isCreated()) {
            InsulinDuration iInsulinDuration;
            iInsulinDuration = new InsulinDuration("USHORT", "The ultra short-acting insulin preparation");
            iInsulinDuration.save();
            iInsulinDuration = new InsulinDuration("SHORT", "The short-acting insulin preparation");
            iInsulinDuration.save();
            iInsulinDuration = new InsulinDuration("MEDIUM", "The intermediate-acting insulin preparation");
            iInsulinDuration.save();
            iInsulinDuration = new InsulinDuration("LONG", "The Long-acting insulin preparations");
            iInsulinDuration.save();

            //--------------------------------------------------------------------------------------
            InsulinType iInsulinType;
            iInsulinDuration = new Select().from(InsulinDuration.class).where("code = 'USHORT'").executeSingle();
            iInsulinType = new InsulinType("UHM", iInsulinDuration, "HM", "Human");
            iInsulinType.save();
            iInsulinType = new InsulinType("UHS", iInsulinDuration, "HS", "Synthetics");
            iInsulinType.save();

            iInsulinDuration = new Select().from(InsulinDuration.class).where("code = 'SHORT'").executeSingle();
            iInsulinType = new InsulinType("SHM", iInsulinDuration, "HM", "Human");
            iInsulinType.save();
            iInsulinType = new InsulinType("SHS", iInsulinDuration, "HS", "Synthetics");
            iInsulinType.save();
            iInsulinType = new InsulinType("SMP", iInsulinDuration, "AP", "Mono-peak");
            iInsulinType.save();

            iInsulinDuration = new Select().from(InsulinDuration.class).where("code = 'MEDIUM'").executeSingle();
            iInsulinType = new InsulinType("MHM", iInsulinDuration, "HM", "Human");
            iInsulinType.save();
            iInsulinType = new InsulinType("MHS", iInsulinDuration, "HS", "Synthetics");
            iInsulinType.save();
            iInsulinType = new InsulinType("MMC", iInsulinDuration, "AC", "Mono-compound");
            iInsulinType.save();
            iInsulinType = new InsulinType("MMP", iInsulinDuration, "AP", "Mono-peak");
            iInsulinType.save();

            iInsulinDuration = new Select().from(InsulinDuration.class).where("code = 'LONG'").executeSingle();
            iInsulinType = new InsulinType("LHM", iInsulinDuration, "HM", "Human");
            iInsulinType.save();
            iInsulinType = new InsulinType("LHS", iInsulinDuration, "HS", "Synthetics");
            iInsulinType.save();
            iInsulinType = new InsulinType("LMC", iInsulinDuration, "AC", "Mono-compound");
            iInsulinType.save();
            iInsulinType = new InsulinType("LMP", iInsulinDuration, "AP", "Mono-peak");
            iInsulinType.save();

            //--------------------------------------------------------------------------------------
            InsulinFirm iInsulinFirm;
            iInsulinFirm = new InsulinFirm("NOVO", "Novo Nordisk");
            iInsulinFirm.save();
            iInsulinFirm = new InsulinFirm("AVENTIS", "Aventis (Hoechst)");
            iInsulinFirm.save();
            iInsulinFirm = new InsulinFirm("BCHEM", "Berlin-Chemie");
            iInsulinFirm.save();
            iInsulinFirm = new InsulinFirm("GALEN", "Galenika");
            iInsulinFirm.save();
            iInsulinFirm = new InsulinFirm("INDAR", "Indar");
            iInsulinFirm.save();
            iInsulinFirm = new InsulinFirm("LILLY", "Lilly");
            iInsulinFirm.save();
            iInsulinFirm = new InsulinFirm("PLIVA", "Pliva");
            iInsulinFirm.save();
            iInsulinFirm = new InsulinFirm("POLFA", "Polfa");
            iInsulinFirm.save();
            iInsulinFirm = new InsulinFirm("NOVOP", "Novo Nordisk Polfa");
            iInsulinFirm.save();
            iInsulinFirm = new InsulinFirm("ICNGAL", "ICN Galenika");
            iInsulinFirm.save();
            iInsulinFirm = new InsulinFirm("USSR", "SNG");
            iInsulinFirm.save();

            InsulinOrigin iInsulinOrigin;
            iInsulinOrigin = new InsulinOrigin("HBIOS", "human biosynthetic");
            iInsulinOrigin.save();
            iInsulinOrigin = new InsulinOrigin("HSEMI", "human semisynthetic");
            iInsulinOrigin.save();
            iInsulinOrigin = new InsulinOrigin("PORK", "pork");
            iInsulinOrigin.save();
            iInsulinOrigin = new InsulinOrigin("BEEF", "beef");
            iInsulinOrigin.save();
            iInsulinOrigin = new InsulinOrigin("BEPO", "beef-pork");
            iInsulinOrigin.save();

            //--------------------------------------------------------------------------------------
            InsulinItem iInsulinItem;
            iInsulinFirm = new Select().from(InsulinFirm.class).where("code = 'NOVO'").executeSingle();
            iInsulinType = new Select().from(InsulinType.class).where("code = 'UHS'").executeSingle();
            iInsulinOrigin = new Select().from(InsulinOrigin.class).where("code = 'HBIOS'").executeSingle();
            iInsulinItem = new InsulinItem("Novorapid", iInsulinType, iInsulinFirm, iInsulinOrigin, 5, "m", 1, "h", 4, "h", InsulinConstants.COLOR_NOVORAPID);
            iInsulinItem.save();

            iInsulinFirm = new Select().from(InsulinFirm.class).where("code = 'AVENTIS'").executeSingle();
            iInsulinItem = new InsulinItem("Apidra", iInsulinType, iInsulinFirm, iInsulinOrigin, 5, "m", 1, "h", 4, "h", InsulinConstants.COLOR_APIDRA);
            iInsulinItem.save();

            iInsulinFirm = new Select().from(InsulinFirm.class).where("code = 'NOVO'").executeSingle();
            iInsulinType = new Select().from(InsulinType.class).where("code = 'SHM'").executeSingle();
            iInsulinItem = new InsulinItem("Actrapid HM", iInsulinType, iInsulinFirm, iInsulinOrigin, 20, "m", 1, "h", 6, "h", InsulinConstants.COLOR_ACTRAPID);
            iInsulinItem.save();

            iInsulinType = new Select().from(InsulinType.class).where("code = 'MHM'").executeSingle();
            iInsulinItem = new InsulinItem("Protaphane HM", iInsulinType, iInsulinFirm, iInsulinOrigin, 1, "h", 4, "h", 20, "h", InsulinConstants.COLOR_PROTAFAN);
            iInsulinItem.save();

            iInsulinType = new Select().from(InsulinType.class).where("code = 'MHM'").executeSingle();
            iInsulinItem = new InsulinItem("Mixtard 30", iInsulinType, iInsulinFirm, iInsulinOrigin, 5, "m", 1, "h", 4, "h", InsulinConstants.COLOR_PROTAFAN);
            iInsulinItem.save();

            iInsulinType = new Select().from(InsulinType.class).where("code = 'MHS'").executeSingle();
            iInsulinItem = new InsulinItem("Levemir", iInsulinType, iInsulinFirm, iInsulinOrigin, 5, "m", 1, "h", 4, "h", InsulinConstants.COLOR_LEVEMIR);
            iInsulinItem.save();

            iInsulinFirm = new Select().from(InsulinFirm.class).where("code = 'LILLY'").executeSingle();
            iInsulinItem = new InsulinItem("Lantus", iInsulinType, iInsulinFirm, iInsulinOrigin, 5, "m", 1, "h", 4, "h", InsulinConstants.COLOR_LANTUS);
            iInsulinItem.save();

        }
    }

    public void dropDatabase() {
        SQLiteDatabase db = ActiveAndroid.getDatabase();
        /*
        List<String> tables = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM sqlite_master WHERE type='table';", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String tableName = cursor.getString(1);
            if (!tableName.equals("android_metadata") &&
                    !tableName.equals("sqlite_sequence")) {
                tables.add(tableName);
            }
            cursor.moveToNext();
        }
        cursor.close();
        for (String tableName : tables) {
            Log.v(TAG, "Drop table " + tableName);
            //db.execSQL("DROP TABLE " + tableName);
        }
        */
        db.close();
        SQLiteDatabase.deleteDatabase(new File(db.getPath()));
    }

    public void importData(String fileImport) {
        try {
            FileInputStream fis = new FileInputStream(fileImport);
            Reader isr = new InputStreamReader(fis);
            BufferedReader bir = new BufferedReader(isr);
            String line;
            while ((line = bir.readLine()) != null) {

                String [] reservoir = line.split(FIELD_DELIMITER);

                Toast.makeText(mContext, "Line selected ["+reservoir.length+"]" + line,
                        Toast.LENGTH_LONG).show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportData(String dirPath) {
        String fileExportName = dirPath + "/diadiary_export.dat";

        File fileExport = new File(fileExportName);
        try {
            FileOutputStream fos = new FileOutputStream(fileExport);
            Writer osw = new OutputStreamWriter(fos);
            BufferedWriter bow = new BufferedWriter(osw);

            List<GlucoseReading> aGlucoseReading;
            aGlucoseReading = new Select()
                    .from(GlucoseReading.class)
                    .orderBy("created")
                    .execute();
            for (ListIterator<GlucoseReading> it = aGlucoseReading.listIterator(); it.hasNext(); ) {
                GlucoseReading item = it.next();
                String export = item.export();
                bow.write(export);
                bow.newLine();
            }
            bow.close();
            osw.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
