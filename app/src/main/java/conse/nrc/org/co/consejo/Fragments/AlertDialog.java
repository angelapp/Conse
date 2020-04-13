package conse.nrc.org.co.consejo.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
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
import android.widget.Toast;

import conse.nrc.org.co.consejo.Interfaces.AlertTestInterfaces;
import conse.nrc.org.co.consejo.R;
import conse.nrc.org.co.consejo.Utils.LocalConstants;
import conse.nrc.org.co.consejo.Utils.PermissionClass;
import conse.nrc.org.co.consejo.Utils.UtilsFunctions;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.location.LocationManager.GPS_PROVIDER;
import static android.location.LocationManager.NETWORK_PROVIDER;
import static conse.nrc.org.co.consejo.Utils.LocalConstants.LOCATION_PERMISSION_CODE;

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

    private float alpha_accumulate = LocalConstants.BUTTON_SEND_ALERT_MIN_ALPHA;




    long MILISECONDS_TO_PRESS = 3000;
    long MILISECONDS_FOR_TICK = 200;
    long TIME_TO_VIBRATE = 500;

    private float alpha_delta = (LocalConstants.BUTTON_SEND_ALERT_MAX_ALPHA - LocalConstants.BUTTON_SEND_ALERT_MIN_ALPHA)/(MILISECONDS_TO_PRESS/MILISECONDS_FOR_TICK);

    CountDownTimer timer = new CountDownTimer(MILISECONDS_TO_PRESS, MILISECONDS_FOR_TICK) {
        @Override
        public void onTick(long millisUntilFinished) {
            buttonUpgradeAlpha();

        }

        @Override
        public void onFinish() {

            buttonResetAlpha();
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
                        buttonResetAlpha();
                        return true;
                }
                return false;
            }
        });

        buttonResetAlpha();
        Log.d("Alert", getEmegencyContactsString());

        if (LocalConstants.DEV_VERSION) {
            mBtSendAlert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendAlert();
                }
            });
        }

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
        requestCoordinates();
    }

    private void requestCoordinates() {
//        String recipients = LocalConstants.SMS_CONTACT_PREFIX + getEmegencyContactsString();
//        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse(recipients));
        String coordinates = " ";
        String coordinates2 = " ";



        if (!PermissionClass.isPermissionRequestRequired(getActivity(), new String[]{ACCESS_COARSE_LOCATION},
                LOCATION_PERMISSION_CODE)) {
            // Your code if permission available
            if (ActivityCompat.checkSelfPermission(mCtx,
                    ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                Location location = mLocManager.getLastKnownLocation(GPS_PROVIDER);
                Location wifiLocation = mLocManager.getLastKnownLocation(NETWORK_PROVIDER);
                String coorUrl = getString(R.string.gmaps_sms);
                try {
                    sendSms(String.format(coorUrl, String.valueOf(location.getLatitude()),
                            String.valueOf(location.getLongitude())));

                }catch (Exception ea){
                    ea.printStackTrace();
                    try {
                        sendSms(String.format(coorUrl, String.valueOf(wifiLocation.getLatitude()),
                                String.valueOf(wifiLocation.getLongitude())));
                    }catch (Exception eae){
                        eae.printStackTrace();
                        sendSms("");
                    }
                }
            } else {
                Toast.makeText(mCtx,R.string.needs_location_permission, Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(mCtx,R.string.needs_location_permission, Toast.LENGTH_LONG).show();
        }

//        if (ActivityCompat.checkSelfPermission(mCtx,
//                ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
////            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//            Location location = mLocManager.getLastKnownLocation(GPS_PROVIDER);
//            Location wifiLocation = mLocManager.getLastKnownLocation(NETWORK_PROVIDER);
//            String coorUrl = getString(R.string.gmaps_sms);
//            try {
//                sendSms(String.format(coorUrl, String.valueOf(location.getLatitude()),
//                        String.valueOf(location.getLongitude())));
//
//            }catch (Exception ea){
//                ea.printStackTrace();
//                try {
//                    sendSms(String.format(coorUrl, String.valueOf(wifiLocation.getLatitude()),
//                            String.valueOf(wifiLocation.getLongitude())));
//                }catch (Exception eae){
//                    eae.printStackTrace();
//                    sendSms("");
//                }
//            }
//
//        } else if(PermissionClass.isMarshmallowPlusDevice()){
//            PermissionClass.isPermissionRequestRequired(getActivity(), new String[]{ACCESS_COARSE_LOCATION},LOCATION_PERMISSION_CODE);
//        }
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
        Log.d("SMS", "Result code for " + requestCode + " is: " + resultCode );
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

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//
//        switch (requestCode) {
//            case 1:
//                String coordinates = " ";
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(mCtx,"GPS permission granted", Toast.LENGTH_LONG).show();
//
//                    if (ActivityCompat.checkSelfPermission(mCtx,
//                            ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                        Location location = mLocManager.getLastKnownLocation(GPS_PROVIDER);
//                        try {
//                            coordinates += getString(R.string.latitude) + ": " + String.valueOf(location.getLatitude());
//                            coordinates += ", " + getString(R.string.longitude) + ": " + String.valueOf(location.getLongitude());
//                        } catch (Exception ea) {
//                            ea.printStackTrace();
//                        }
//                    }
//                    //  get Location from your device by some method or code
//
//                } else {
//                    Toast.makeText(mCtx,"GPS permission denied", Toast.LENGTH_LONG).show();
//                    // show user that permission was denied. inactive the location based feature or force user to close the app
//                }
//                sendSms(coordinates);
//                break;
//        }
//    }

    private void sendSms(String coordinates) {
        String recipients = LocalConstants.SMS_CONTACT_PREFIX + getEmegencyContactsString();
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse(recipients));
        if(isTest) {
            smsIntent.putExtra("sms_body", getString(R.string.send_alert_touch_message_example)+ " " + coordinates);
        } else{
            smsIntent.putExtra("sms_body", getString(R.string.send_alert_message) + " " + coordinates);
        }
        startActivityForResult(smsIntent, 1);
        Log.d("SMS", "Started for result" );
    }


    private void buttonUpgradeAlpha(){
        alpha_accumulate += alpha_delta;
        mBtSendAlert.setAlpha(alpha_accumulate);
    }

    private void buttonResetAlpha(){
        alpha_accumulate = LocalConstants.BUTTON_SEND_ALERT_MIN_ALPHA;
        mBtSendAlert.setAlpha(alpha_accumulate);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_PERMISSION_CODE:
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    int grantResult = grantResults[i];
                    switch (permission) {
                        case ACCESS_COARSE_LOCATION:
                            if (PackageManager.PERMISSION_GRANTED == grantResult) {
                                sendAlert();
                            } else{
                                Toast.makeText(mCtx,R.string.needs_location_permission, Toast.LENGTH_LONG).show();
                            }
                            break;
                    }
                }
                break;
        }
    }

}
