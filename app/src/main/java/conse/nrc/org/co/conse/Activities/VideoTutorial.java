package conse.nrc.org.co.conse.Activities;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.zip.Inflater;

import Utils.LocalConstants;
import conse.nrc.org.co.conse.R;

public class VideoTutorial extends YouTubeBaseActivity implements View.OnClickListener {

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
//        lyYoutube.removeAllViews();
        youTubePlayerView = new YouTubePlayerView(this);
        lyYoutube.addView(youTubePlayerView);
        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(LocalConstants.YOUTUBE_VIDEO_ID);
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
                goToRegister();
                break;
            default:
                break;
        }
    }

    private void goToRegister() {
        Intent register = new Intent(this, Register.class);
        startActivity(register);
    }
}
