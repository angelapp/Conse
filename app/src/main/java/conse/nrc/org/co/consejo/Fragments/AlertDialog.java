package conse.nrc.org.co.consejo.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import conse.nrc.org.co.consejo.Interfaces.AlertTestInterfaces;
import conse.nrc.org.co.consejo.R;
import conse.nrc.org.co.consejo.Utils.LocalConstants;
import conse.nrc.org.co.consejo.Utils.UtilsFunctions;

/**
 * Created by apple on 11/20/17.
 */

public class AlertDialog extends DialogFragment {

    Button mBtSendAlert;
    long down,up;
    Context mCtx;
    AlertTestInterfaces alertTestInterfaces;


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
        View mView = inflater.inflate(R.layout.send_alert_confirmation_dialog, container, false);

        mBtSendAlert = (Button) mView.findViewById(R.id.bt_yes_sure);

        mBtSendAlert.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN :
                        //Toast.makeText(MainActivity.this, "Down", Toast.LENGTH_SHORT).show();
                        //down=System.currentTimeMillis();
                        timer.start();
                        break;
                    case MotionEvent.ACTION_UP :
                        //Toast.makeText(MainActivity.this, "Up", Toast.LENGTH_SHORT).show();
                        //up=System.currentTimeMillis();
                        //if(up-down>3000)
                            //Toast.makeText(MainActivity.this, "More than 3", Toast.LENGTH_SHORT).show();
                        timer.cancel();
                        return true;
                }
                return false;
            }
        });
        Log.d("Alert", getEmegencyContactsString());


        return mView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCtx = context;
        alertTestInterfaces = (AlertTestInterfaces)mCtx;

    }

    private void sendAlert() {
        Vibrator v = (Vibrator) mCtx.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(TIME_TO_VIBRATE);
        sendSms();
    }

    private void sendSms() {
        String recipients = LocalConstants.SMS_CONTACT_PREFIX + getEmegencyContactsString();
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse(recipients));
        smsIntent.putExtra("sms_body", getString(R.string.send_alert_touch_message_example));
        startActivityForResult(smsIntent, 1);
    }

    public String getEmegencyContactsString() {

        int contacts_size = UtilsFunctions.getSharedInteger(mCtx, LocalConstants.CONTACT_SIZE);
        int i = 1;
        String emergencyContactsString = "";

        for(i = 1; i <= contacts_size; i++){
            emergencyContactsString = emergencyContactsString
                    + ";" + UtilsFunctions.getSharedString(mCtx, LocalConstants.CONTACT_NUMBER_ + String.valueOf(i));
            Log.d("Alert", UtilsFunctions.getSharedString(mCtx, LocalConstants.CONTACT_NUMBER_ + String.valueOf(i)));
        }

        return emergencyContactsString;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                alertTestInterfaces.alertTestSuccess();
                this.dismiss();
                break;
            default:
                break;
        }

    }
}
