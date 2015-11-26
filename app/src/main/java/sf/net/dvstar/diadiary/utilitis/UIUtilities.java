package sf.net.dvstar.diadiary.utilitis;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;


public class UIUtilities {

    public static void showYesNoDialog(final int aFrom, View view, String message, final UIInterfaceYesNo aYesNo) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        aYesNo.dialogActionYes(aFrom);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        aYesNo.dialogActionNo(aFrom);
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage(message)
                .setPositiveButton(view.getResources().getString(android.R.string.yes), dialogClickListener) // "Yes"
                .setNegativeButton(view.getResources().getString(android.R.string.no), dialogClickListener)  // "No"
                .show();

    }

}
