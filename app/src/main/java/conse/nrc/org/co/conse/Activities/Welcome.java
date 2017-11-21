package conse.nrc.org.co.conse.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import Utils.ConseApp;
import Utils.LocalConstants;
import Utils.Models;
import Utils.RequestTask;
import Utils.ServerRequest;
import conse.nrc.org.co.conse.R;

public class Welcome extends AppCompatActivity implements View.OnClickListener, RequestTask.OnRequestCompleted{

    private Button mBtGoToVideo;
    ProgressDialog listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mBtGoToVideo = (Button)findViewById(R.id.bt_go_to_video);
        mBtGoToVideo.setOnClickListener(this);
        listener = new ProgressDialog(this);
        listener.setMessage(getString(R.string.getting_app_conf));
        new ServerRequest.GetAppConfiguration(this,listener, LocalConstants.GET_APP_CONF_TASK_ID).executeGet();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_go_to_video:
                goToVideo();
                break;
            default:
                break;
        }

    }

    private void goToVideo() {
        Intent tutorial = new Intent(this, SelectContact.class);
        startActivity(tutorial);
        this.finish();
    }

    @Override
    public void onRequestResponse(Object response, int taskId) {

        switch (taskId){
            case LocalConstants.GET_APP_CONF_TASK_ID:
                Models.ApplicationConfiguration appConf = (Models.ApplicationConfiguration) response;
                ConseApp.appConfiguration = appConf;
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestError(int errorCode, String errorMsg, int taskId) {

        switch (taskId){
            case LocalConstants.GET_APP_CONF_TASK_ID:
                Toast.makeText(this, errorMsg,Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

    }
}
