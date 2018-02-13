package conse.nrc.org.co.consejo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import conse.nrc.org.co.consejo.Utils.ConseApp;
import conse.nrc.org.co.consejo.Utils.LocalConstants;
import conse.nrc.org.co.consejo.R;
import conse.nrc.org.co.consejo.Utils.UtilsFunctions;

public class VideoTutorial extends YouTubeBaseActivity implements View.OnClickListener, YouTubePlayer.PlayerStateChangeListener  {

    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer.OnInitializedListener onInitializedListener;
    private Button mPlayButton, mBtNetx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_tutorial);
        LinearLayout lyYoutube = (LinearLayout) findViewById(R.id.ly_youtube_video);
        mBtNetx = (Button) findViewById(R.id.bt_go_to_register);
        mBtNetx.setOnClickListener(this);
//        mBtNetx.setVisibility(View.GONE);
//        lyYoutube.removeAllViews();
        youTubePlayerView = new YouTubePlayerView(this);
        lyYoutube.addView(youTubePlayerView);
        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(LocalConstants.YOUTUBE_VIDEO_ID);
                youTubePlayer.setPlayerStateChangeListener(VideoTutorial.this);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        mPlayButton = (Button) findViewById(R.id.bt_start_video);
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                youTubePlayerView.initialize(LocalConstants.YOUTUBE_API_KEY, onInitializedListener);
                mPlayButton.setVisibility(View.GONE);
            }
        });


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_go_to_register:
                goToNextactivity();
                break;
            default:
                break;
        }
    }

    private void goToRegister() {
        if (ConseApp.validateGpsStatus(true, this)) {
            if (ConseApp.validateNetworkStatus(true, this)) {
                Intent register = new Intent(this, Register.class);
                startActivity(register);
            }
            else {
                Toast.makeText(this,getString(R.string.conse_need_gps), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this,getString(R.string.not_network_conection), Toast.LENGTH_SHORT).show();
        }
    }

    private void goToNextactivity(){
        if(UtilsFunctions.getSharedBoolean(this, LocalConstants.IS_USER_LOGGED_IN)){
            super.onBackPressed();
        } else {
            goToRegister();
        }
    }


    @Override
    public void onLoading() {

    }

    @Override
    public void onLoaded(String s) {

    }

    @Override
    public void onAdStarted() {

    }

    @Override
    public void onVideoStarted() {

    }

    @Override
    public void onVideoEnded() {

        mBtNetx.setVisibility(View.VISIBLE);
    }

    @Override
    public void onError(YouTubePlayer.ErrorReason errorReason) {

    }
}
