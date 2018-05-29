package conse.nrc.org.co.consejo.Fragments;

import android.content.Context;
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
import conse.nrc.org.co.consejo.Interfaces.MainInterface;
import conse.nrc.org.co.consejo.R;
import conse.nrc.org.co.consejo.Utils.ConseApp;
import conse.nrc.org.co.consejo.Utils.LocalConstants;

import static conse.nrc.org.co.consejo.Utils.ConseApp.getMainActivity;

/**
 * Created by apple on 5/29/18.
 */

public class ConseYouTubeFragment extends YouTubePlayerSupportFragment implements YouTubePlayer.PlayerStateChangeListener {

    Context mCtx;
    YouTubePlayerView youTubePlayerView;
    static String mVideoId;

    public static ConseYouTubeFragment newInstance(String url) {
        ConseYouTubeFragment playerYouTubeFrag = new ConseYouTubeFragment();
        mVideoId = url;
        return playerYouTubeFrag;
    }

//    public void setVideoId(String id){
//        mVideoId = id;
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.conse_youtube_player_fragment, container, false);
//        RelativeLayout rlYoutube = (RelativeLayout)view.findViewById(R.id.you_tube_video);
////        youTubePlayerView = (YouTubePlayerView)view.findViewById(R.id.view2);
////        this.youTubePlayerView
////        youTubePlayerView = new YouTubePlayerView(mCtx);
////        rlYoutube.addView(youTubePlayerView);
//        initYouTube();
//        return view;
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        mCtx = context;
//    }

    public void init(){
        initYouTube();
    }

    private void initYouTube() {

//        if (youTubePlayerView == null && mVideoId != null) {
//
//            youTubePlayerView = (YouTubePlayerFragment)(R.id.youtube_frag);
            this.initialize(LocalConstants.YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
                    Log.i("Detail","YouTube Player onInitializationSuccess");
                    player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                    player.loadVideo(mVideoId);
                    player.play();
//                    player.setPlayerStateChangeListener(ConseYouTubeFragment.this);
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

    }

    @Override
    public void onError(YouTubePlayer.ErrorReason errorReason) {

    }
}
