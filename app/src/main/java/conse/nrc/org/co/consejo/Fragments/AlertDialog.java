package conse.nrc.org.co.consejo.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
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

import conse.nrc.org.co.consejo.Interfaces.AlertTestInterfaces;
import conse.nrc.org.co.consejo.R;
import conse.nrc.org.co.consejo.Utils.LocalConstants;
import conse.nrc.org.co.consejo.Utils.UtilsFunctions;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.CALL_PHONE;
import static android.location.LocationManager.GPS_PROVIDER;

/**
 * Created by apple on 11/20/17.
 */

public class AlertDialog extends DialogFragment {


    Button mBtSendAlert;
    long down,up;
    Context mCtx;
    AlertTestInterfaces alertTestInterfaces;
    public static boolean isTest = true;
    LocationManager mLocManager;


    long MILISECONDS_TO_PRESS = 3000;
    long TIME_TO_VIBRATE = 500;

    CountDownTimer timer = new CountDownTimer(MILISECONDS_TO_PRESS, MILISECONDS_TO_PRESS) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {

            sendAlert();

        }
    };



    public AlertDialog(){

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //actualStep = 0;
        View mView = inflater.inflate(R.layout.send_alert_test_confirmation_dialog, container, false);

        mBtSendAlert = (Button) mView.findViewById(R.id.bt_yes_sure);

        mBtSendAlert.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN :
                        timer.start();
                        break;
                    case MotionEvent.ACTION_UP :
                        timer.cancel();
                        return true;
                }
                return false;
            }
        });
        Log.d("Alert", getEmegencyContactsString());

        if(!isTest){
            ((TextView)mView.findViewById(R.id.tv_message)).setText(R.string.send_alert_message);
            (mView.findViewById(R.id.tv_cancel)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.this.dismiss();
                }
            });
        } else{
            (mView.findViewById(R.id.tv_cancel)).setVisibility(View.GONE);
        }

        mLocManager = (LocationManager)
                mCtx.getSystemService(Context.LOCATION_SERVICE);

        return mView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCtx = context;
        if(isTest) {
            alertTestInterfaces = (AlertTestInterfaces) mCtx;
        }

    }

    private void sendAlert() {
        Vibrator v = (Vibrator) mCtx.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(TIME_TO_VIBRATE);
        sendSms();
    }

    private void sendSms() {
        String recipients = LocalConstants.SMS_CONTACT_PREFIX + getEmegencyContactsString();
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse(recipients));
        String coordinates = " ";
        if (ActivityCompat.checkSelfPermission(mCtx,
                ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location location = mLocManager.getLastKnownLocation(GPS_PROVIDER);
            try {
                coordinates += getString(R.string.latitude) + ": " + String.valueOf(location.getLatitude());
                coordinates += ", " + getString(R.string.longitude) + ": " + String.valueOf(location.getLongitude());
            }catch (Exception ea){
                ea.printStackTrace();
            }
        }
        if(isTest) {
            smsIntent.putExtra("sms_body", getString(R.string.send_alert_touch_message_example) + coordinates);
        } else{
            smsIntent.putExtra("sms_body", getString(R.string.send_alert_message) + coordinates);
        }
        startActivityForResult(smsIntent, 1);
    }

    public String getEmegencyContactsString() {

//        int contacts_size = UtilsFunctions.getSharedInteger(mCtx, LocalConstants.CONTACT_SIZE);
//        int i = 1;
//        String emergencyContactsString = "";
//
//        for(i = 1; i <= contacts_size; i++){
//            emergencyContactsString = emergencyContactsString
//                    + ";" + UtilsFunctions.getSharedString(mCtx, LocalConstants.CONTACT_NUMBER_ + String.valueOf(i));
//            Log.d("Alert", UtilsFunctions.getSharedString(mCtx, LocalConstants.CONTACT_NUMBER_ + String.valueOf(i)));
//        }
        return UtilsFunctions.getEmegencyContactsString(mCtx);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if (isTest) {
                    alertTestInterfaces.alertTestSuccess();
                }
                this.dismiss();
                break;
            default:
                break;
        }

    }
}
