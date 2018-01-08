package conse.nrc.org.co.consejo.Fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import conse.nrc.org.co.consejo.Activities.MainActivity;
import conse.nrc.org.co.consejo.Fragments.VBG_Course_1.ClueDialog;
import conse.nrc.org.co.consejo.Fragments.VBG_Course_1.NotAcertedCrosswordDialog;
import conse.nrc.org.co.consejo.Interfaces.MainInterface;
import conse.nrc.org.co.consejo.R;
import conse.nrc.org.co.consejo.Utils.ConseApp;
import conse.nrc.org.co.consejo.Utils.DataBase;
import conse.nrc.org.co.consejo.Utils.LocalConstants;

/**
 * Created by apple on 11/27/17.
 */

public class ProtectionPathsVBG extends Fragment implements View.OnClickListener{



    public final static int COURSE_ID = 1;
    private View view;
    private Context mCtx;
    private LayoutInflater inflater;
    MainInterface mainInterface;

    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer.OnInitializedListener onInitializedListener;

    MediaPlayer player1;
    int actualPlaying;
    Button preLastClickedButton;

    GridLayout mCrossword;

    LinearLayout mActualQuestionaryPage;

    DisplayImageOptions optionsPreview;
    private com.nostra13.universalimageloader.core.ImageLoader imageLoader;

    private int mAvatarGender;

    private LinearLayout courseContainer;
    private int index;
    private int crosswordActualOrientation;
    private boolean erasing = false;

    DataBase dataBase;

    @Override
    public void onStart() {
        super.onStart();
        index = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inflater =  inflater;
        view = inflater.inflate(R.layout.protecion_paths_course_basic_layout, container, false);
        courseContainer = (LinearLayout) view.findViewById(R.id.course_container);
        dataBase = MainActivity.dataBase;
        setInitialPageToShow();
        mAvatarGender = ConseApp.getAvatarGender(mCtx);

        if (LocalConstants.DEV_VERSION){
            ((Button)view.findViewById(R.id.bt_reset)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dataBase.clearPagesRead();
                    dataBase.clearTopicActivity();
                    setInitialPageToShow();
                }
            });
        } else{
            ((Button)view.findViewById(R.id.bt_reset)).setVisibility(View.GONE);
        }
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        setInitialPageToShow();
    }

    private void setInitialPageToShow() {
        try{
            index = dataBase.getLastPageReadForCurse(COURSE_ID);
            if (index > LocalConstants.VBG_LAYOUT_LIST.size()) {
                index = LocalConstants.VBG_LAYOUT_LIST.size();
            }
        } catch (Exception ea){
            index = 0;
            updateReadPage();
            ea.printStackTrace();
        }
        Log.d("VBG Mod 1", "Page to show: " + index);
        inflateLayout();
    }

    private void updateReadPage(){
        if (index <= LocalConstants.VBG_LAYOUT_LIST.size()){
            dataBase.insertUpdateLastPage(COURSE_ID, index);
        } else if(index < 0){
            dataBase.insertUpdateLastPage(COURSE_ID, 0);
        } else{
            dataBase.insertUpdateLastPage(COURSE_ID, LocalConstants.VBG_LAYOUT_LIST.size());
        }
    }

    public void setNextPage(){
            index++;
        if (index < LocalConstants.VBG_LAYOUT_LIST.size()){
            inflateLayout();
            updateReadPage();
        } else if(index >= LocalConstants.VBG_LAYOUT_LIST.size()) {
            finishCourse();
        }
    }

    public void setPreviousPage(){
        if (index > 0){
            index--;
            inflateLayout();
        }
    }

    private void return_to_layout(int tag) {
        if (tag < LocalConstants.VBG_LAYOUT_LIST.size() && tag >= 0){
            index = tag;
            inflateLayout();
        }
    }

    private void finishCourse() {
        mainInterface.setCourseSelectionFragment();
    }

    private void inflateLayout() {
        courseContainer.removeAllViews();
        final View view = inflater.inflate(LocalConstants.VBG_LAYOUT_LIST.get(index), null, false);
//        view.setOnClickListener(this);
        courseContainer.addView(view);
        if(player1 != null) {
            try {
                player1.stop();
            } catch (Exception ea) {
                ea.printStackTrace();
            }
        }
        prepareFragmentForView(view);
    }

    private void prepareFragmentForView(View view) {

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCtx = context;
        mainInterface = (MainInterface) mCtx;
    }

    private void markActivityAsComplete(int activity_id){
        mainInterface.saveActivityCompleted(activity_id);
    }

    @Override
    public void onClick(View v) {
        Log.d("VBG MOD", "Click listened: id:" + v.getId());
        switch (v.getId()){
            case R.id.bt_previous:
                setPreviousPage();
                break;
            case R.id.bt_next:
                setNextPage();
                break;
            default:
                break;
        }
    }

}
