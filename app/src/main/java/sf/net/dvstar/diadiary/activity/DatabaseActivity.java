package sf.net.dvstar.diadiary.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import sf.net.dvstar.diadiary.R;
import sf.net.dvstar.diadiary.database.DatabaseProvider;
import sf.net.dvstar.diadiary.database.ProductGroup;
import sf.net.dvstar.diadiary.database.ProductItem;
import sf.net.dvstar.diadiary.utilitis.OIFileManager;

public class DatabaseActivity extends AppCompatActivity implements OIFileManager{

    private static final String TAG = "DatabaseActivity";
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        setContentView(R.layout.activity_database);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void dbReinit(View v) {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked

                        dbReinitFull();


                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }

    private ProgressDialog mProgressDialog;
    private void dbReinitFull() {


        AsyncTask<Void, String, Void> task = new AsyncTask<Void, String, Void>() {

            @Override
            protected void onPreExecute() {
                mProgressDialog = new ProgressDialog(mContext);
                mProgressDialog.setTitle("Processing...");
                mProgressDialog.setMessage("Please wait.");
                mProgressDialog.setCancelable(false);
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.show();
            }

            @Override
            protected Void doInBackground(Void... arg0) {
                try {
                    //Do something...

                    DatabaseProvider iDatabaseProvider = new DatabaseProvider(mContext);

                    iDatabaseProvider.isCreated();
                    iDatabaseProvider.dropDatabase();

                    publishProgress("Create initial data ...");

                    iDatabaseProvider.initCreate();

                    publishProgress("Load product data ...");

                    iDatabaseProvider.importProductsFromAssets();

                    publishProgress("Add example data ...");

                    iDatabaseProvider.addExamleData();

                    //Thread.sleep(5000);
                }
                catch (IOException e) {
                    mProgressDialog.dismiss();
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(String... progress) {
                mProgressDialog.setMessage("Please wait. " + progress[0]);
            }


            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                Log.v(TAG, "onPostExecute | Ok " + mProgressDialog);
                if (mProgressDialog !=null) {
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.cancel();
                    mProgressDialog.dismiss();
                }
            }

        };
        task.execute((Void[])null);




    }

    private void dbClear() {
        DatabaseProvider iIsulinInitDatabase = new DatabaseProvider(this);
        iIsulinInitDatabase.isCreated();
        iIsulinInitDatabase.dropDatabase();
    }

    private void dbInit() {
        DatabaseProvider iIsulinInitDatabase = new DatabaseProvider(this);
        iIsulinInitDatabase.initCreate();
    }


    public void dbImportProd(final View v) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked

                            // @TODO Move progree dialog from iDatabaseProvider

                            AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {

                                @Override
                                protected void onPreExecute() {
                                    mProgressDialog = new ProgressDialog(mContext);
                                    mProgressDialog.setTitle("Processing...");
                                    mProgressDialog.setMessage("Please wait.");
                                    mProgressDialog.setCancelable(false);
                                    mProgressDialog.setIndeterminate(true);
                                    mProgressDialog.show();
                                }

                                @Override
                                protected Void doInBackground(Void... arg0) {
                                    try {
                                        //Do something...
                                        DatabaseProvider iDatabaseProvider = new DatabaseProvider(mContext);
                                        iDatabaseProvider.importProductsFromAssets();
                                        //Thread.sleep(5000);
                                    }
                                    catch (IOException e) {
                                        mProgressDialog.dismiss();
                                        e.printStackTrace();
                                    }
                                    return null;
                                }

                                @Override
                                protected void onPostExecute(Void result) {
                                    super.onPostExecute(result);
                                    Log.v(TAG, "onPostExecute | Ok " + mProgressDialog);
                                    if (mProgressDialog !=null) {
                                        mProgressDialog.setIndeterminate(false);
                                        mProgressDialog.cancel();
                                        mProgressDialog.dismiss();
                                    }
                                }

                            };
                            task.execute((Void[])null);


                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        dbImportData(v);
                        break;
                    case DialogInterface.BUTTON_NEUTRAL:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setMessage(getResources().getString(R.string.dialog_import_title));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getResources().getString(R.string.dialog_import_from_assets), dialogClickListener);
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getResources().getString(R.string.dialog_import_from_file), dialogClickListener);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getResources().getString(R.string.button_cancel), dialogClickListener);
        alertDialog.show();

    }

    public void dbImportData(View v) {

        Intent intent = new Intent(ACTION_PICK_FILE);

        try {
            startActivityForResult(intent,
                    REQUEST_CODE_PICK_FILE);
        } catch (ActivityNotFoundException e) {
            // No compatible file manager was found.
            Toast.makeText(this, R.string.no_filemanager_installed,
                    Toast.LENGTH_SHORT).show();
        }

    }

    public void dbExport(View v) {

        Intent intent = new Intent(ACTION_PICK_DIRECTORY);

        try {
            startActivityForResult(intent,
                    REQUEST_CODE_PICK_DIRECTORY);
        } catch (ActivityNotFoundException e) {
            // No compatible file manager was found.
            Toast.makeText(this, R.string.no_filemanager_installed,
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_PICK_PROD:
                if (resultCode == RESULT_OK && data != null) {
                    // obtain the filename
                    Uri fileUri = data.getData();
                    if (fileUri != null) {
                        String filePath = fileUri.getPath();

                        Toast.makeText(this, "FILE selected " + filePath,
                                Toast.LENGTH_LONG).show();

                        DatabaseProvider iDatabaseProvider = new DatabaseProvider(this);
                        File f = new File(filePath);
                        String sflist[] = f.list(new FilenameFilter() {
                            @Override
                            public boolean accept(File dir, String filename) {
                                boolean ret = false;
                                if (filename.indexOf("prodgroups")>=0 || filename.indexOf("proditems")>=0) ret = true;
                                return ret;
                            }
                        });

                        if(sflist.length>0){

                            for (String fsi:sflist){
                                String mode = "";
                                String fullPath = filePath+"/"+fsi;
                                Toast.makeText(this, "FILE selected " + fullPath,
                                        Toast.LENGTH_LONG).show();
                                if(fsi.indexOf("prodgroups")>=0) mode = ProductGroup.TAG;
                                if(fsi.indexOf("proditems")>=0) mode = ProductItem.TAG;

                                if(mode.length()>0)
                                    iDatabaseProvider.importProducts(fullPath,mode);

                            }
                        }

                        //iIsulinInitDatabase.importProducts(filePath+"proditems.csv");
                    }
                }
                break;
            case REQUEST_CODE_PICK_FILE:
                if (resultCode == RESULT_OK && data != null) {
                    // obtain the filename
                    Uri fileUri = data.getData();
                    if (fileUri != null) {
                        String filePath = fileUri.getPath();

                        Toast.makeText(this, "FILE selected " + filePath,
                                Toast.LENGTH_LONG).show();

                        DatabaseProvider iIsulinInitDatabase = new DatabaseProvider(this);
                        iIsulinInitDatabase.importData(filePath);
                    }
                }
                break;
            case REQUEST_CODE_PICK_DIRECTORY:
                if (resultCode == RESULT_OK && data != null) {
                    // obtain the filename
                    Uri fileUri = data.getData();
                    if (fileUri != null) {
                        String filePath = fileUri.getPath();

                        Toast.makeText(this, "DIR selected " + filePath,
                                Toast.LENGTH_LONG).show();

                        DatabaseProvider iIsulinInitDatabase = new DatabaseProvider(this);
                        iIsulinInitDatabase.exportData(filePath);
                    }
                }
                break;
            default:
                break;
        }
    }

}
