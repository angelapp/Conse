package conse.nrc.org.co.consejo.Activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import conse.nrc.org.co.consejo.Fragments.AlertAndCallFragment;
import conse.nrc.org.co.consejo.R;
import conse.nrc.org.co.consejo.Utils.ConseApp;
import conse.nrc.org.co.consejo.Utils.LocalConstants;
import conse.nrc.org.co.consejo.Utils.Models;
import conse.nrc.org.co.consejo.Utils.RequestTask;
import conse.nrc.org.co.consejo.Utils.ServerRequest;
import conse.nrc.org.co.consejo.Utils.UtilsFunctions;

public class PasswordRecovery extends AppCompatActivity implements RequestTask.OnRequestCompleted {

    EditText mEtEmail;
    Button mBtSendAlert, mBtPswRecovery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recovery);

        mEtEmail =(EditText) findViewById(R.id.et_email);
        mBtSendAlert = (Button)findViewById(R.id.bt_send_alert);


        if (UtilsFunctions.getEmegencyContactsString(this).length() > 3) {
            mBtSendAlert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendAlert();
                }
            });
        } else {
            mBtSendAlert.setVisibility(View.GONE);
        }

        mBtPswRecovery = (Button)findViewById(R.id.bt_psw_recovery);

        mBtPswRecovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });

    }

    private void validateData() {
        String email = mEtEmail.getText().toString();
        boolean error = false;
        if (email.length()<=0){
            mEtEmail.setError(getString(R.string.must_fill_field));
            error = true;
        }
        if (error){
            Toast.makeText(this, getString(R.string.must_complete_information), Toast.LENGTH_SHORT).show();
        }else{
            sendPswRequest(email);
        }

    }

    private void sendPswRequest(String email) {
        ProgressDialog listener = new ProgressDialog(this);
        listener.setMessage(getString(R.string.recovering_password));
        Models.RegisterUserProfileModel user = new Models.RegisterUserProfileModel();
        user.email = email;
        new ServerRequest.PasswordRecovery(this,this,listener, LocalConstants.POST_PASSWORD_RECERY_TASK_ID, user).executePost();

    }



    private void sendAlert() {
        AlertAndCallFragment alertAndCallFragment = new AlertAndCallFragment();
        alertAndCallFragment.show(getSupportFragmentManager(), "tag");
    }

    @Override
    public void onRequestResponse(Object response, int taskId) {
        switch (taskId){
            case LocalConstants.POST_PASSWORD_RECERY_TASK_ID:
                Models.SimpleResponseModel res = (Models.SimpleResponseModel) response;
                Toast.makeText(this, res.detail, Toast.LENGTH_SHORT).show();
                UtilsFunctions.saveSharedBoolean(this, LocalConstants.IS_USER_LOGGED_IN, false);
                UtilsFunctions.deleteKeySharedPreferences(this, LocalConstants.USER_PSW);
                super.onBackPressed();
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestError(int errorCode, String errorMsg, int taskId) {

        switch (taskId){
            case LocalConstants.POST_PASSWORD_RECERY_TASK_ID:
                Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
