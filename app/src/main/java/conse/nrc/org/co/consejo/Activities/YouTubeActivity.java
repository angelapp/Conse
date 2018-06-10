package conse.nrc.org.co.consejo.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import conse.nrc.org.co.consejo.R;
import conse.nrc.org.co.consejo.Utils.LocalConstants;

import static com.google.android.youtube.player.YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE;
import static com.google.android.youtube.player.YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION;
import static com.google.android.youtube.player.YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class YouTubeActivity extends YouTubeBaseActivity {

    public YouTubePlayerView playerView;
    public YouTubePlayer youTubePlayer;
    String mVideoId;
    int mInitialVideoTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        playerView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        mVideoId = getIntent().getStringExtra(LocalConstants.VIDEO_ID);
        mInitialVideoTime = getIntent().getIntExtra(LocalConstants.ACTUAL_VIDEO_TIME,0);
        Log.i("YouTubeActivity","Currenttimeinmillis: " + mInitialVideoTime);
    }

    @Override
    protected void onStart(){
        super.onStart();
        playerView.initialize(LocalConstants.YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer player, boolean wasRestored) {
                Log.i("Detail","YouTube Player onInitializationSuccess");
                youTubePlayer = player;
                youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                youTubePlayer.setShowFullscreenButton(false);
                youTubePlayer.loadVideo(mVideoId, mInitialVideoTime);
//                youTubePlayer.pause();
//                youTubePlayer.seekToMillis(mInitialVideoTime);
//                player.play();
//                player.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
//                    @Override
//                    public void onFullscreen(boolean b) {
//                        if (!b){
////                            onBackPressed();
////                            player.release();
//                            YouTubeActivity.this.finish();
//                        }
//                    }
//                });

                if (!wasRestored) {
//                    player.loadVideo(mVideoId);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.i("Detail", "Failed: "+youTubeInitializationResult);
                if (youTubeInitializationResult.isUserRecoverableError()) {
//                        youTubeInitializationResult.getErrorDialog(getMainActivity(), RECOVERY_DIALOG_REQUEST).show();
                } else {
//                        callToast(youTubeInitializationResult.toString(), true);
                }
            }
        });
    }

    @Override
    public void onBackPressed(){
        try {
            youTubePlayer.release();
        } catch (Exception ea){
            ea.printStackTrace();
        }
        this.finish();
        super.onBackPressed();
    }
}
