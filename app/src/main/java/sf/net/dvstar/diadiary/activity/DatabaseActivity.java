package sf.net.dvstar.diadiary.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import sf.net.dvstar.diadiary.R;
import sf.net.dvstar.diadiary.database.DatabaseProvider;
import sf.net.dvstar.diadiary.utilitis.OIFileManager;

public class DatabaseActivity extends AppCompatActivity implements OIFileManager{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void dbReinit(View v) {
        dbClear();
        dbInit();
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

    public void dbImport(View v) {

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
