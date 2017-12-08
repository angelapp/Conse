package conse.nrc.org.co.consejo.Fragments.VBG_Course_1;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import conse.nrc.org.co.consejo.R;

/**
 * Created by Daniel Trujillo on 01/12/2017.
 */

public class ClueDialog extends android.app.DialogFragment {


    private int clueNumber;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.clue_layout, container, false);


        TextView clue = (TextView) view.findViewById(R.id.text_clue);
        TextView numerClue = (TextView) view.findViewById(R.id.number_clue);

        clue.setText(getResources().getStringArray(R.array.clueTexts)[getClueNumber()]);
        numerClue.setText(String.valueOf(getClueNumber()+1));

        return view;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.onBackPressed();
        dialog.cancel();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        return dialog;
    }


    public int getClueNumber() {
        return clueNumber;
    }

    public void setClueNumber(int clueNumber) {
        this.clueNumber = clueNumber;
    }
}