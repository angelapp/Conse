package conse.nrc.org.co.consejo.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import conse.nrc.org.co.consejo.Utils.ConseApp;
import conse.nrc.org.co.consejo.Utils.LocalConstants;
import conse.nrc.org.co.consejo.Utils.Models;
import conse.nrc.org.co.consejo.Utils.RequestTask;
import conse.nrc.org.co.consejo.Utils.ServerRequest;
import conse.nrc.org.co.consejo.R;

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

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);

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
        Intent tutorial = new Intent(this, VideoTutorial.class);
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
