package conse.nrc.org.co.consejo.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.io.IOException;

import conse.nrc.org.co.consejo.Utils.ConseApp;
import conse.nrc.org.co.consejo.Utils.FullscreenVideoLayout;
import conse.nrc.org.co.consejo.Utils.LocalConstants;
import conse.nrc.org.co.consejo.R;
import conse.nrc.org.co.consejo.Utils.UtilsFunctions;

import static android.view.Gravity.CENTER_HORIZONTAL;
import static android.view.Gravity.TOP;

public class VideoTutorial extends Activity implements View.OnClickListener {

    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer.OnInitializedListener onInitializedListener;
    private Button mPlayButton, mBtNetx;
    private VideoView mVideoTutorial;
    private FrameLayout mFlVideoContainer;
    FullscreenVideoLayout fullscreenVideoLayout;
    Configuration actualConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_tutorial);

        mBtNetx = (Button) findViewById(R.id.bt_go_to_register);
        mBtNetx.setOnClickListener(this);
//        mVideoTutorial = (VideoView)findViewById(R.id.vv_tutorial);
        mFlVideoContainer = (FrameLayout)findViewById(R.id.fl_video_content);
        fullscreenVideoLayout = (FullscreenVideoLayout)findViewById(R.id.vfull);
        fullscreenVideoLayout.setActivity(this);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.tutorial);
//        fullscreenVideoLayout.setVideoURI(uri);

        try {
            fullscreenVideoLayout.setVideoURI(uri);
            fullscreenVideoLayout.setShouldAutoplay(true);
            fullscreenVideoLayout.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mBtNetx.setVisibility(View.VISIBLE);
                    fullscreenVideoLayout.setFullscreen(false);
                    fullscreenVideoLayout.hideControls();
                }
            });

            fullscreenVideoLayout.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    //fullscreenVideoLayout.setFullscreen(true);
                    Toast toast = Toast.makeText(VideoTutorial.this, getString(R.string.set_horizontal_screen), Toast.LENGTH_SHORT);
//                    toast.setText(getString(R.string.set_horizontal_screen));
                    toast.setGravity(TOP|CENTER_HORIZONTAL,0, 20 );
//                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.show();
//                    Toast.makeText(VideoTutorial.this, R.string., Toast.LENGTH_SHORT).show();
                }
            });
            mBtNetx.setVisibility(View.GONE);

        } catch (IOException e) {
            e.printStackTrace();
            mBtNetx.setVisibility(View.VISIBLE);
        }
//
//        MediaController mediaController = new MediaController(this);
//        mediaController.setAnchorView(mVideoTutorial);
//        mVideoTutorial.setMediaController(mediaController);



//        LinearLayout lyYoutube = (LinearLayout) findViewById(R.id.ly_youtube_video);
//        mBtNetx.setVisibility(View.GONE);
//        lyYoutube.removeAllViews();
//        youTubePlayerView = new YouTubePlayerView(this);
//        lyYoutube.addView(youTubePlayerView);
//        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
//            @Override
//            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
//                youTubePlayer.loadVideo(LocalConstants.YOUTUBE_VIDEO_ID);
//                youTubePlayer.setPlayerStateChangeListener(VideoTutorial.this);
//            }
//
//            @Override
//            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
//
//            }
//        };
//
//        mPlayButton = (Button) findViewById(R.id.bt_start_video);
//        mPlayButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                youTubePlayerView.initialize(LocalConstants.YOUTUBE_API_KEY, onInitializedListener);
//                startVideo();
//                mPlayButton.setVisibility(View.GONE);
//            }
//        });


    }

//    @Override
//    public void onResume(){
//        super.onResume();
//        this.getRequestedOrientation();
//        //Checks the orientation of the screen
//        Log.d("Video Tutorial", "Orientation changed to: " + this.getWindow().getWindowManager().getDefaultDisplay().getOrientation());
//        int rotation = this.getWindow().getWindowManager().getDefaultDisplay().getOrientation();
//        switch (rotation){
//            case Surface.ROTATION_0:case Surface.ROTATION_180:
//                setVideoOnFullScreen();
//                break;
//            case Surface.ROTATION_90: case Surface.ROTATION_270:
//                setVideoNormal();
//                break;
//        }
//    }
//
//    private void startVideo() {
//        mVideoTutorial.start();
//    }


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
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actualConfiguration = newConfig;
        switch (newConfig.orientation){
            case Configuration.ORIENTATION_LANDSCAPE:
                fullscreenVideoLayout.setFullscreen(true);
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                fullscreenVideoLayout.setFullscreen(false);
                break;
        }
    }


}
