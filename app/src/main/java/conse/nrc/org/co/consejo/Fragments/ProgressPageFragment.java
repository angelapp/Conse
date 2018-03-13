package conse.nrc.org.co.consejo.Fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;

import conse.nrc.org.co.consejo.Activities.MainActivity;
import conse.nrc.org.co.consejo.Adapters.PagerAdapterFragment;
import conse.nrc.org.co.consejo.Interfaces.MainInterface;
import conse.nrc.org.co.consejo.R;
import conse.nrc.org.co.consejo.Utils.ConseApp;
import conse.nrc.org.co.consejo.Utils.LocalConstants;
import conse.nrc.org.co.consejo.Utils.Models;
import conse.nrc.org.co.consejo.Utils.UtilsFunctions;

/**
 * Created by apple on 12/9/17.
 */

public class ProgressPageFragment extends Fragment {

    Context mCtx;
    View mView;
    LinearLayout mLyContent;
    public int mCourseId;
    private com.nostra13.universalimageloader.core.ImageLoader imageLoader;
    DisplayImageOptions options;
    MainInterface mainInterface;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView =  inflater.inflate(R.layout.progress_page_fragment, container, false);
        mLyContent = (LinearLayout) mView.findViewById(R.id.ly_module_content);

        fillProgressBar();

//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mCtx).build();
//        ImageLoader.getInstance().init(config);

        imageLoader = imageLoader.getInstance();

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.conse)
                .showImageForEmptyUri(R.drawable.conse)
                .showImageOnFail(R.drawable.conse)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();


        return mView;
    }

    private void fillProgressBar() {

        mLyContent.removeAllViews();


        if (ConseApp.getAppConfiguration(mCtx).getCourseById(mCourseId).course_topics != null) {
            int i = 1;
            for (final Models.Topic topic : ConseApp.getAppConfiguration(mCtx).getCourseById(mCourseId).course_topics) {
                final LayoutInflater inflater = (LayoutInflater) this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View progressItem = inflater.inflate(R.layout.progres_bar_item, null, false);

                ((TextView) progressItem.findViewById(R.id.tv_module_tittle)).setText(UtilsFunctions.getConseGenderTittle(mCtx, i));
                ((ProgressBar) progressItem.findViewById(R.id.pb_progress)).setProgress(topic.getUserProgressByTopic());
                imageLoader = imageLoader.getInstance();
                ImageView im = (ImageView) progressItem.findViewById(R.id.iv_module_icon);
                imageLoader.displayImage(topic.icon, im, options);

                im.setClickable(true);
                im.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mCourseId == LocalConstants.VBG_COURSE_ID) {
                            mainInterface.startVbgCourseInAskedPage(LocalConstants.MODULES_INDEX.get(topic.id));
                        } else if (mCourseId == LocalConstants.LEADERS_COURSE_ID){
                            mainInterface.startLeadersCourseInAskedPage(LocalConstants.MODULES_INDEX.get(topic.id));
                        }
                        getActivity().onBackPressed();
                        Log.d("Progress Page", "Clicked in: " + topic.id + " - " + topic.description);
                    }
                });

                Log.d("Progress Page", "Filling progress bar with topic: " + topic.description);

                if (topic.getUserProgressByTopic() < 100) {
                    im.setAlpha(0.5f);
                } else {
                    im.setAlpha(1f);
                }
                i++;
                mLyContent.addView(progressItem);
            }
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCtx = (MainActivity)ConseApp.getMainActivity();
        mainInterface = (MainInterface)mCtx;
    }
}
