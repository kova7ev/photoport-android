package com.despair.photoport.client.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.despair.photoport.client.listeners.SimpleDialogListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by root on 6/12/14.
 */
public class UncoupleDialog extends DialogFragment {
    public static String message;
    private List mListeners = new ArrayList();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        firePositiveButtonClick();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        fireNegativeButtonClick();
                    }
                });

        return builder.create();
    }

    public synchronized void addDialogListener(SimpleDialogListener listener) {
        mListeners.add(listener);
    }

    public synchronized void removeDialogListener(SimpleDialogListener listener) {
        mListeners.remove(listener);
    }

    private synchronized void firePositiveButtonClick() {
        Iterator i = mListeners.iterator();
        while (i.hasNext()) {
            ((SimpleDialogListener) i.next()).OnPositiveButtonClick();
        }
    }

    private synchronized void fireNegativeButtonClick() {
        Iterator i = mListeners.iterator();
        while (i.hasNext()) {
            ((SimpleDialogListener) i.next()).OnNegativeButtonClick();
        }
    }
}