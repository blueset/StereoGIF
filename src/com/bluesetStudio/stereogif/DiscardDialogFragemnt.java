package com.bluesetStudio.stereogif;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class DiscardDialogFragemnt extends DialogFragment {
 
    private StereoGIF stereoGIF;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stereoGIF = (StereoGIF) getActivity().getApplication();
    }
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.popup_discard_title)
               .setMessage(R.string.popup_discard_message)
               .setPositiveButton(R.string.button_discard, new DialogInterface.OnClickListener() {
                
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    stereoGIF.clearPhotoPath();
                    getActivity().finish();
                }
            })
               .setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
                
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    //Do nothing.       
                }
            });
        return builder.show();
    }
}