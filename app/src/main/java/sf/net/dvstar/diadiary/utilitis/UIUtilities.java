package sf.net.dvstar.diadiary.utilitis;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;


public class UIUtilities {

    /**
     * Show Yes No dialog
     * @param aFrom mode
     * @param aView parent view
     * @param aMessage message text
     * @param aYesNo callback
     */
    public static void showYesNoDialog(final int aFrom, View aView, String aMessage, final UIInterfaceYesNo aYesNo) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        aYesNo.dialogActionYes(aFrom, "");
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        aYesNo.dialogActionNo(aFrom);
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(aView.getContext());
        builder.setMessage(aMessage)
                .setPositiveButton(aView.getResources().getString(android.R.string.yes), dialogClickListener) // "Yes"
                .setNegativeButton(aView.getResources().getString(android.R.string.no), dialogClickListener)  // "No"
                .show();

    }

    /**
     * Show input text dialog
     * @param aFrom mode
     * @param aContext context
     * @param aTitle title
     * @param aMessage message text
     * @param aYesNo callback
     */
    public static void showInputDialog(final int aFrom, Context aContext, String aTitle, String aMessage, final UIInterfaceYesNo aYesNo) {
        final EditText input = new EditText(aContext);

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        String value = input.getText().toString();
                        if(value.length()>0){
                            aYesNo.dialogActionYes(aFrom, value);
                        }
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        aYesNo.dialogActionNo(aFrom);
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(aContext);
        builder.setTitle(aTitle);
        builder.setMessage(aMessage);

        input.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
        builder.setView(input);


        builder.setPositiveButton("Ok", dialogClickListener);

        builder.setNegativeButton("Cancel", dialogClickListener);
        builder.show();


    }
}
