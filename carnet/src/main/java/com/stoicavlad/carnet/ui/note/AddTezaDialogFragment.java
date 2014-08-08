package com.stoicavlad.carnet.ui.note;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.provider.CarnetContract;
import com.stoicavlad.carnet.ui.utils.NotaSelector;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Vlad on 1/26/14.
 */
public class AddTezaDialogFragment extends NotaSelectorDialogFragment {

    private static String TAG_MATERIE_NAME = "materie";
    private String mMaterieName;

    public static AddTezaDialogFragment newInstance(int materieId, String materieName){
        AddTezaDialogFragment dialogFragment = new AddTezaDialogFragment();
        Bundle arguments = new Bundle();

        arguments.putInt(TAG_MATERIE_ID, materieId);
        arguments.putString(TAG_MATERIE_NAME, materieName);

        dialogFragment.setArguments(arguments);
        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        materieId = getArguments().getInt(TAG_MATERIE_ID);
        layoutXml = R.layout.dialog_addteza;
        mMaterieName = getArguments().getString(TAG_MATERIE_NAME);
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle(getString(R.string.add_teza)+" "+mMaterieName);
        return dialog;
    }

    @Override
    protected void okButtonSelected() {
        super.okButtonSelected();
        ContentValues materieValues = new ContentValues();
        materieValues.put(CarnetContract.MaterieEntry.COLUMN_TEZA, super.selectedValue);
        getActivity().getContentResolver()
                .update(CarnetContract.MaterieEntry.buildMaterieUri(materieId),
                    materieValues, null, null);
    }


}
