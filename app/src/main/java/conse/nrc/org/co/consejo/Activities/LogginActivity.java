package conse.nrc.org.co.consejo.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import conse.nrc.org.co.consejo.Fragments.AlertAndCallFragment;
import conse.nrc.org.co.consejo.R;
import conse.nrc.org.co.consejo.Utils.ConseApp;
import conse.nrc.org.co.consejo.Utils.LocalConstants;
import conse.nrc.org.co.consejo.Utils.Models;
import conse.nrc.org.co.consejo.Utils.RequestTask;
import conse.nrc.org.co.consejo.Utils.ServerRequest;
import conse.nrc.org.co.consejo.Utils.UtilsFunctions;

public class LogginActivity extends AppCompatActivity implements RequestTask.OnRequestCompleted{

    EditText mEtEmail, mEtPassword;
    Button mBtSendAlert, mBtLoggin;
    TextView mTvToPswRecovery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loggin);
        mEtEmail =(EditText) findViewById(R.id.et_email);
        mEtPassword =(EditText) findViewById(R.id.et_password);
        mBtSendAlert = (Button)findViewById(R.id.bt_send_alert);
        mTvToPswRecovery = (TextView)findViewById(R.id.tv_psw_recovery);
        mTvToPswRecovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPasswordRecovery();
            }
        });


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

        mBtLoggin = (Button)findViewById(R.id.bt_loggin);

        mBtLoggin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });

    }

    private void goToPasswordRecovery() {
        Intent pswRecovery = new Intent(this, PasswordRecovery.class);
        startActivity(pswRecovery);
    }

    private void validateData() {
        String email = mEtEmail.getText().toString();
        String password = mEtPassword.getText().toString();
        boolean error = false;
        if (email.length()<=0){
            mEtEmail.setError(getString(R.string.must_fill_field));
            error = true;
        }
        if (password.length()<=0){
            mEtPassword.setError(getString(R.string.must_fill_field));
            error = true;
        }
        if (error){
            Toast.makeText(this, getString(R.string.must_complete_information), Toast.LENGTH_SHORT).show();
        }else{
            sendLogginRequest(email, password);
        }

    }

    private void sendLogginRequest(String email, String password) {
        ProgressDialog listener = new ProgressDialog(this);
        listener.setMessage(getString(R.string.loggin));
        Models.RegisterUserProfileModel user = new Models.RegisterUserProfileModel();
        user.email = email;
        user.password = password;
        if (UtilsFunctions.getSharedBoolean(this, LocalConstants.USER_IS_IN_DEVICE) &&
                UtilsFunctions.getSharedString(this, LocalConstants.USER_PSW)!= null){
            if (ConseApp.getActualUser(this)!= null){
                if (ConseApp.getActualUser(this).user.email.equals(email) &&
                        UtilsFunctions.getSharedString(this, LocalConstants.USER_PSW).equals(password)){
                    sendToHome();
                } else {
                    Toast.makeText(this, getString(R.string.credential_not_valid), Toast.LENGTH_SHORT).show();
                }
            }
        } else{
            new ServerRequest.LogginUser(this, this, listener, LocalConstants.POST_USER_LOGGIN_TASK_ID, user).executePost();
        }

    }

    private void sendToHome() {
        Intent tutorial = new Intent(this, MainActivity.class);
        startActivity(tutorial);
        this.finish();
    }

    private void sendAlert() {
        AlertAndCallFragment alertAndCallFragment = new AlertAndCallFragment();
        alertAndCallFragment.show(getSupportFragmentManager(), "tag");
    }

    @Override
    public void onRequestResponse(Object response, int taskId) {
        switch (taskId){
            case LocalConstants.POST_USER_LOGGIN_TASK_ID:
                Models.RegisterUserResponse res = (Models.RegisterUserResponse) response;
                ConseApp.setActualUser(this, res);
                UtilsFunctions.saveSharedString(this, LocalConstants.USER_PSW, mEtPassword.getText().toString());
                UtilsFunctions.saveSharedBoolean(this, LocalConstants.USER_IS_IN_DEVICE, true);
                UtilsFunctions.saveSharedBoolean(this, LocalConstants.IS_USER_LOGGED_IN, true);
//                sendToHome();
                goToNextactivity();
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestError(int errorCode, String errorMsg, int taskId) {

        switch (taskId){
            case LocalConstants.POST_USER_LOGGIN_TASK_ID:
                Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }


    private void goToNextactivity(){
        if (UtilsFunctions.getEmegencyContactsString(this).length() < 2){
            goToContact();
        } else if (UtilsFunctions.getSharedInteger(this, LocalConstants.AVATAR_SELECTED_PART_ + String.valueOf(1)) == 0){
            goToAvatar();
        } else{
            sendToHome();
        }
    }

    private void goToAvatar() {
        Intent avatar = new Intent(this, Avatar.class);
        startActivity(avatar);
        this.finish();
    }

    private void goToContact() {
        Intent contact = new Intent(this, SelectContact.class);
        startActivity(contact);
        this.finish();
    }
}
