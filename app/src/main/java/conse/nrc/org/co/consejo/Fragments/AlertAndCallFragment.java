package conse.nrc.org.co.consejo.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import conse.nrc.org.co.consejo.Interfaces.AlertTestInterfaces;
import conse.nrc.org.co.consejo.R;
import conse.nrc.org.co.consejo.Utils.PermissionClass;

import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.READ_CONTACTS;
import static conse.nrc.org.co.consejo.Utils.LocalConstants.CONTACTS_PERMISSION_CODE;
import static conse.nrc.org.co.consejo.Utils.LocalConstants.PHONE_PERMISSION_CODE;

/**
 * Created by apple on 12/13/17.
 */

public class AlertAndCallFragment extends DialogFragment implements View.OnClickListener{


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

        ((Button)mView.findViewById(R.id.bt_call_123)).setOnClickListener(this);
        ((Button)mView.findViewById(R.id.bt_call_155)).setOnClickListener(this);
        ((Button)mView.findViewById(R.id.bt_call_141)).setOnClickListener(this);
        ((Button)mView.findViewById(R.id.bt_call_0314)).setOnClickListener(this);
        ((Button)mView.findViewById(R.id.bt_call_01800)).setOnClickListener(this);

        return mView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCtx = context;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_call_123:case R.id.bt_call_141:
            case R.id.bt_call_155:case R.id.bt_call_01800:
            case R.id.bt_call_0314:
                makeCall(v);
                break;
        }
    }

    private void makeCall(View v) {
        try {
            if (!PermissionClass.isPermissionRequestRequired(getActivity(), new String[]{CALL_PHONE},
                    PHONE_PERMISSION_CODE)) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + (String) v.getTag()));
                startActivity(intent);
            } else {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:" + (String) v.getTag()));
                startActivity(dialIntent);
            }
        } catch (android.content.ActivityNotFoundException e){
            Toast.makeText(mCtx.getApplicationContext(),R.string.app_not_found,Toast.LENGTH_LONG).show();
        }
    }
}
