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

import java.util.List;

import conse.nrc.org.co.consejo.Activities.MainActivity;
import conse.nrc.org.co.consejo.Fragments.VBG_Course_1.ClueDialog;
import conse.nrc.org.co.consejo.Fragments.VBG_Course_1.NotAcertedCrosswordDialog;
import conse.nrc.org.co.consejo.Interfaces.MainInterface;
import conse.nrc.org.co.consejo.R;
import conse.nrc.org.co.consejo.Utils.ConseApp;
import conse.nrc.org.co.consejo.Utils.DataBase;
import conse.nrc.org.co.consejo.Utils.LocalConstants;

import static conse.nrc.org.co.consejo.Utils.LocalConstants.LEADERS_PROTECION_PATH_LAYOUT_LIST;
import static conse.nrc.org.co.consejo.Utils.LocalConstants.LEADERS_PROTECION_PATH_LAYOUT_LIST_2;
import static conse.nrc.org.co.consejo.Utils.LocalConstants.VBG_PROTECION_PATH_LAYOUT_LIST;

/**
 * Created by apple on 11/27/17.
 */

public class ProtectionPathsVBG extends Fragment implements View.OnClickListener{



    public int COURSE_ID;
    List<Integer> layout_list;
    private View view;
    private Context mCtx;
    private LayoutInflater inflater;
    MainInterface mainInterface;
    private FrameLayout courseContainer;
    private int index;
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
        courseContainer = (FrameLayout) view.findViewById(R.id.course_container);
        dataBase = MainActivity.dataBase;

        setLayoutList();

        setInitialPageToShow();

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        setInitialPageToShow();
    }

    private void setInitialPageToShow() {
        try{
            index = dataBase.getLastPageReadForProtectionPath(COURSE_ID);
            if (index > layout_list.size()) {
                index = layout_list.size();
            }
        } catch (Exception ea){
            index = 0;
            updateReadPage();
            ea.printStackTrace();
        }
        Log.d("Protection Path", "Page to show: " + index);
        inflateLayout();
    }

    private void updateReadPage(){
        try {
            if (index <= layout_list.size()) {
                dataBase.insertUpdateLastPageProtectionPath(COURSE_ID, index);
            } else if (index < 0) {
                dataBase.insertUpdateLastPageProtectionPath(COURSE_ID, 0);
            } else {
                dataBase.insertUpdateLastPageProtectionPath(COURSE_ID, layout_list.size());
            }
        } catch (Exception ea){
            ea.printStackTrace();
            setLayoutList();
            setInitialPageToShow();
        }
    }

    public void setLayoutList(){
        if (COURSE_ID == 1){
            layout_list = VBG_PROTECION_PATH_LAYOUT_LIST;
        } else if(COURSE_ID ==2){
            layout_list = LEADERS_PROTECION_PATH_LAYOUT_LIST;
        } else if(COURSE_ID ==3){
            layout_list = LEADERS_PROTECION_PATH_LAYOUT_LIST_2;
        }
    }
    public void setNextPage(){
            index++;
        if (index < layout_list.size()){
            inflateLayout();
            updateReadPage();
        } else if(index >= layout_list.size()) {
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
        if (tag < layout_list.size() && tag >= 0){
            index = tag;
            inflateLayout();
        }
    }

    private void finishCourse() {
        mainInterface.setCourseSelectionFragment();
    }

    private void inflateLayout() {
        courseContainer.removeAllViews();
        final View view = inflater.inflate(layout_list.get(index), null, false);
        courseContainer.addView(view);
        prepareFragmentForView(view);
    }

    private void prepareFragmentForView(View view) {

        try{
            ((Button)view.findViewById(R.id.bt_previous)).setOnClickListener(this);
        } catch (Exception ea){
            ea.printStackTrace();
        }
        try{
            ((Button)view.findViewById(R.id.bt_next)).setOnClickListener(this);
        } catch (Exception ea){
            ea.printStackTrace();
        }

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
        Log.d("Protection Path", "Click listened: id:" + v.getId());
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
