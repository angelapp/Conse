package conse.nrc.org.co.consejo.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import conse.nrc.org.co.consejo.Interfaces.AlertTestInterfaces;
import conse.nrc.org.co.consejo.R;

/**
 * Created by apple on 12/13/17.
 */

public class AlertAndCallFragment extends DialogFragment{


    Button mBtSendAlert;
    Context mCtx;
    AlertTestInterfaces alertTestInterfaces;


    public AlertAndCallFragment(){

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //actualStep = 0;
        View mView = inflater.inflate(R.layout.send_alert_call_fragment, container, false);

        mBtSendAlert = (Button) mView.findViewById(R.id.bt_send_alert);
        mBtSendAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog();
                alertDialog.isTest = false;
                alertDialog.show(getFragmentManager(), "tag");
                dismiss();
            }
        });

        return mView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCtx = context;

    }
}
