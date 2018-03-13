package conse.nrc.org.co.consejo.Fragments.VBG_Course_1;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import conse.nrc.org.co.consejo.R;

import static conse.nrc.org.co.consejo.Utils.LocalConstants.DIALOG_DIM_ALPHA;

/**
 * Created by Daniel Trujillo on 01/12/2017.
 */

public class NotAcertedCrosswordDialog extends android.app.DialogFragment {


    public static String mMesaggeText;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vbg_course_1_10, container, false);

        Button btBack = (Button) view.findViewById(R.id.bt_previous);


        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        if(mMesaggeText != null){
            ((TextView)view.findViewById(R.id.tv_message)).setText(mMesaggeText);
        }
        return view;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.onBackPressed();
        dialog.cancel();
        dialog.getWindow().setDimAmount(DIALOG_DIM_ALPHA);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.transparent)));
        //dialog.getWindow().getAttributes().alpha = 0.7f;
        return dialog;
    }

}