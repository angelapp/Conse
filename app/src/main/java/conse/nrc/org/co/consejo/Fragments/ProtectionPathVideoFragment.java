package conse.nrc.org.co.consejo.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import conse.nrc.org.co.consejo.R;
import conse.nrc.org.co.consejo.Utils.DownloadTask;
import conse.nrc.org.co.consejo.Utils.LocalConstants;

import static conse.nrc.org.co.consejo.Utils.LocalConstants.VBG_VIDEO_DOWNLOAD_URL;

/**
 * Created by apple on 12/9/17.
 */

public class ProtectionPathVideoFragment extends Fragment {

    Context mCtx;
    public int video_id;
    FrameLayout flContent;
    public String mVideoId;
    public String mTittle;
    public String mVideoDownloadURL;
    ConseYouTubeFragment conseYouTubeFragment;


    public ProtectionPathVideoFragment() {
        // Required empty public constructor
    }

    public static ProtectionPathVideoFragment newInstance(String video, String tittle){
        ProtectionPathVideoFragment protectionPathVideoFragment = new ProtectionPathVideoFragment();
        return protectionPathVideoFragment;
    }

    @Override
    public void onStart() {
        super.onStart();
//        homeInterface.changeTopToolbar(ConfigurationFile.BAR_WITHOUT_BUTTONS);
//        homeInterface.changeBottomToolbar(1);
//        FirebaseClass.setFragmentScreen(getActivity(),ConfigurationFile.RULES_FRAGMENT);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCtx = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.protection_paths_video_fragment, container, false);
        ((TextView)view.findViewById(R.id.tv_video_tittle)).setText(mTittle);
//        final Button mPlayButton = (Button) view.findViewById(R.id.bt_start_video);
//        mPlayButton.setVisibility(View.GONE);
        setYoutubeVideo(view);
        return view;
    }


    private void setYoutubeVideo(View mView) {
        try {
            FragmentManager fragmentManager = getChildFragmentManager();
            LinearLayout lyYoutube = (LinearLayout) mView.findViewWithTag(LocalConstants.HERE_VIDEO_TAG);
            Log.d("VBG", "Container id: " + R.id.ly_video);
            conseYouTubeFragment = new ConseYouTubeFragment();
            conseYouTubeFragment.mVideoId = mVideoId;
            fragmentManager.beginTransaction().replace(R.id.ly_video, conseYouTubeFragment).addToBackStack(null).commit();
            final Button mPlayButton = (Button) mView.findViewById(R.id.bt_start_video);
            mPlayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    conseYouTubeFragment.init();
                    mPlayButton.setVisibility(View.GONE);
                }
            });

            final Button mDownloadButton = (Button) mView.findViewById(R.id.bt_download_video);
            mDownloadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mVideoDownloadURL)));

//                    new DownloadTask(mCtx, mDownloadButton, mVideoDownloadURL);
                }
            });
        } catch (Exception ea){
            ea.printStackTrace();
        }
    }

}
