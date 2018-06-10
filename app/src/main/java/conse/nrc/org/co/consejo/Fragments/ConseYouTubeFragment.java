package conse.nrc.org.co.consejo.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import conse.nrc.org.co.consejo.Activities.MainActivity;
import conse.nrc.org.co.consejo.Activities.YouTubeActivity;
import conse.nrc.org.co.consejo.Interfaces.MainInterface;
import conse.nrc.org.co.consejo.R;
import conse.nrc.org.co.consejo.Utils.ConseApp;
import conse.nrc.org.co.consejo.Utils.LocalConstants;

import static com.google.android.youtube.player.YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE;
import static com.google.android.youtube.player.YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION;
import static com.google.android.youtube.player.YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI;
import static conse.nrc.org.co.consejo.Utils.ConseApp.getMainActivity;

/**
 * Created by apple on 5/29/18.
 */

public class ConseYouTubeFragment extends YouTubePlayerSupportFragment {

    Context mCtx;
    YouTubePlayerView youTubePlayerView;
    YouTubePlayer youTubePlayer;
    public String mVideoId;
    MainActivity mainActivity;

    public static ConseYouTubeFragment newInstance(String url) {
        ConseYouTubeFragment playerYouTubeFrag = new ConseYouTubeFragment();
        return playerYouTubeFrag;
    }


    public void init(){
        initYouTube();
    }

    private void initYouTube() {

        mainActivity = (MainActivity) getActivity();
        mainActivity.conseYouTubeFragment = this;

            this.initialize(LocalConstants.YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer player, boolean wasRestored) {
                    Log.i("Detail","YouTube Player onInitializationSuccess");
                    youTubePlayer = player;
                    player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                    player.loadVideo(mVideoId);
//                    player.play();
                    mainActivity.youTubePlayer = player;
                    player.setFullscreenControlFlags(
                            FULLSCREEN_FLAG_CONTROL_SYSTEM_UI
                            );
//                    player.setOnFullscreenListener(mainActivity);
                    player.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
                        @Override
                        public void onFullscreen(boolean b) {
                            if (b) {
                                player.setFullscreen(false);
                                Intent intent = new Intent(mainActivity, YouTubeActivity.class);
                                intent.putExtra(LocalConstants.VIDEO_ID, mVideoId);
                                Log.i("ConseYoutubeFragment","Currenttimeinmillis: " + player.getCurrentTimeMillis());
                                intent.putExtra(LocalConstants.ACTUAL_VIDEO_TIME, player.getCurrentTimeMillis());
                                mainActivity.startActivityForResult(intent,0);
//                                player.pause();
//                                player.release();
                            }
                        }
                    });
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
//        }
    }


    @Override
    public void onResume(){
        super.onResume();
//        if (this.isVisible()){
//            init();
//        }
//        if (youTubePlayer == null) {
//            init();
//        }
    }

}
