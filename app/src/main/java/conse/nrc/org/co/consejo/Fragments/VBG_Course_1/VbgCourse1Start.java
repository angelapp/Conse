package conse.nrc.org.co.consejo.Fragments.VBG_Course_1;

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
import conse.nrc.org.co.consejo.Interfaces.MainInterface;
import conse.nrc.org.co.consejo.R;
import conse.nrc.org.co.consejo.Utils.ConseApp;
import conse.nrc.org.co.consejo.Utils.DataBase;
import conse.nrc.org.co.consejo.Utils.LocalConstants;

/**
 * Created by apple on 11/27/17.
 */

public class VbgCourse1Start extends Fragment implements View.OnClickListener{



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

    private int [] layouts;

    @Override
    public void onStart() {
        super.onStart();
        index = 0;


        optionsPreview = new DisplayImageOptions.Builder()
                .showImageOnLoading(null)
                .showImageForEmptyUri(null)
                .showImageOnFail(null)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        imageLoader = imageLoader.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inflater =  inflater;
        view = inflater.inflate(R.layout.vbg_course_1_start, container, false);
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

        if (view.getTag() !=  null) {
            Log.d("VBg Mod", "The view tag is: " + view.getTag().toString());

            switch ((String) view.getTag()) {
                case LocalConstants.NEED_AVATAR_TAG:
                    try {
                        FrameLayout avatar = (FrameLayout) view.findViewWithTag(LocalConstants.HERE_AVATAR_TAG);
                        avatar.removeAllViews();
                        avatar.addView(ConseApp.getAvatarFrame(mCtx, imageLoader, optionsPreview));
                    } catch (Exception ea) {
                        ea.printStackTrace();
                    }

                    break;
                case LocalConstants.MOD_1_CW1_SCREEN:
                    setCrossWordMod1();
                    break;
                case LocalConstants.HAS_QUESTIONARY:
                    mActualQuestionaryPage = (LinearLayout)view.findViewWithTag(LocalConstants.QUESTIONARY_CONTAINER);
                    Log.d("VBg Mod", "Find questionary container" + mActualQuestionaryPage.getTag().toString());
                    break;
                case LocalConstants.NEED_YOUTUBE_VIDEO_TAG:
                    setYoutubeVideo(view);
                    break;
                default:
                    break;
            }
        } else {
            Log.d("VBg Mod", "The view tag is: null");
        }

    }

    private void setYoutubeVideo(View mView) {

//        youTubePlayerView = new YouTubePlayerView(mCtx);
//        LinearLayout lyYoutube = (LinearLayout)mView.findViewWithTag(LocalConstants.HERE_VIDEO_TAG);
//        lyYoutube.addView(youTubePlayerView);
//        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
//            @Override
//            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
//                youTubePlayer.loadVideo(LocalConstants.YOUTUBE_VIDEO_ID);
//                //youTubePlayer.setPlayerStateChangeListener();
//            }
//
//            @Override
//            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
//
//            }
//        };

//        final Button mPlayButton = (Button) mView.findViewById(R.id.bt_start_video);
//        mPlayButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                youTubePlayerView.initialize(LocalConstants.YOUTUBE_API_KEY, onInitializedListener);
//                mPlayButton.setVisibility(View.GONE);
//            }
//        });
    }

    private void setCrossWordMod1() {
        mCrossword = (GridLayout) courseContainer.findViewById(R.id.gl_crossword);
        for (int i = 0; i < mCrossword.getChildCount(); i++){
            Log.d("VBg Mod", "Index is: " + i);
            final EditText et = (EditText)mCrossword.getChildAt(i);
            if (et.getTag() != null) {
                if (((String) et.getTag()).equals(getString(R.string.clue))) {
                    et.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final ClueDialog cluedialog = new ClueDialog();
                            cluedialog.setClueNumber(Integer.valueOf(et.getText().toString()) - 1);
                            cluedialog.show(getActivity().getFragmentManager(), "");
                        }
                    });
                }
            }

            if (et.isFocusable()){
                Log.d("VBg Mod", "Index is: " + i + " The tag is: " + et.getTag().toString());

                et.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (et.getNextFocusDownId() != View.NO_ID){
                            crosswordActualOrientation = LocalConstants.crowd_vertical;
                        } else {
                            crosswordActualOrientation = LocalConstants.crowd_horizontal;
                        }
                        return false;
                    }
                });


                et.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        Log.d("Crosword", "On TextChanged");
                        if (s.length() == 0){
                            erasing = true;
                        } else {
                            erasing = false;
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (!erasing) {
                            switch (crosswordActualOrientation) {
                                case LocalConstants.crowd_horizontal:
                                    if (et.getNextFocusRightId() != View.NO_ID) {
                                        mCrossword.findViewById(et.getNextFocusRightId()).requestFocus();
                                        if (((EditText)mCrossword.findViewById(et.getNextFocusRightId())).getText().length()>0) {
                                            ((EditText) mCrossword.findViewById(et.getNextFocusRightId())).setSelection(0, 1);
                                        }
                                    }
                                    break;
                                case LocalConstants.crowd_vertical:
                                    if (et.getNextFocusDownId() != View.NO_ID) {
                                        mCrossword.findViewById(et.getNextFocusDownId()).requestFocus();
                                        if (((EditText)mCrossword.findViewById(et.getNextFocusDownId())).getText().length()>0) {
                                            ((EditText) mCrossword.findViewById(et.getNextFocusDownId())).setSelection(0, 1);
                                        }
                                    }
                                    break;
                                default:
                                    break;
                            }
                        }
//                        if (et.getNextFocusDownId() != View.NO_ID) {
//                            mCrossword.findViewById(et.getNextFocusDownId()).requestFocus();
//                        } else if(et.getNextFocusRightId() != View.NO_ID) {
//                                mCrossword.findViewById(et.getNextFocusRightId()).requestFocus();
//                        }
                    }
                });
            }

//            final ClueDialog cluedialog = new ClueDialog();
//
//            if(et.getHint() != null && et.getHint().toString().length()>0){
//                et.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        cluedialog.setClueNumber(Integer.valueOf(et.getHint().toString())-1);
//                        cluedialog.show(getActivity().getFragmentManager(),"");
//                    }
//                });
//
//            }
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

    public void processOuterClick(View v){
        Log.d("VBG MOD", "Click listened: id:" + v.getId());
        switch (v.getId()){
            case R.id.bt_previous:
                setPreviousPage();
                break;
            case R.id.bt_next:
                setNextPage();
                break;
            case R.id.bt_play:
               playSound(v);
                break;
            case R.id.bt_return_to:
                return_to_layout((Integer) v.getTag());
                break;
            default:
                break;
        }
        if (v.getTag() != null) {
            processButtonTag(v);
        }
    }



    private void processButtonTag(View v) {

        switch ((String)v.getTag()){
            case LocalConstants.MOD_1_R:
                markActivityAsComplete(1);
                break;
            case LocalConstants.MOD_1_CW1_VALIDATION:
                validateCrossword();
                break;
            case LocalConstants.MOD_2_R:
                markActivityAsComplete(3);
                break;
            case LocalConstants.MOD_2_Q1_VALIDATION:
                validateQuestionary(4,getString(R.string.mod_2_q1_not_acerted_message) );
                break;
            case LocalConstants.MOD_3_R:
                markActivityAsComplete(5);
                break;
            case LocalConstants.MOD_3_Q1:
                validateQuestionary(6, getString(R.string.course_1_35_bad));
                break;
            case LocalConstants.MOD_3_Q2:
                validateQuestionary(7, getString(R.string.course_1_38_bad));
                break;
            case LocalConstants.MOD_3_Q3:
                validateQuestionary(8, getString(R.string.course_1_41_bad));
                break;
            case LocalConstants.MOD_3_Q4:
                validateQuestionary(9,getString(R.string.course_1_44_bad));
                break;
            case LocalConstants.MOD_4_R:
                markActivityAsComplete(10);
                break;
            case LocalConstants.MOD_4_Q1:
                validateQuestionary(11, getResources().getStringArray(R.array.errorResponseQuestionaryMod4)[0]);
                break;
            case LocalConstants.MOD_4_Q2:
                validateQuestionary(11, getResources().getStringArray(R.array.errorResponseQuestionaryMod4)[1]);
                break;
            case LocalConstants.MOD_4_Q3:
                validateQuestionary(11, getResources().getStringArray(R.array.errorResponseQuestionaryMod4)[2]);
                break;
            case LocalConstants.MOD_4_Q4:
                validateQuestionary(11, getResources().getStringArray(R.array.errorResponseQuestionaryMod4)[3]);
                break;
            case LocalConstants.MOD_4_Q5:
                validateQuestionary(11, getResources().getStringArray(R.array.errorResponseQuestionaryMod4)[4]);
                break;
            case LocalConstants.MOD_4_Q6:
                validateQuestionary(11, getResources().getStringArray(R.array.errorResponseQuestionaryMod4)[5]);
                break;
            case LocalConstants.MOD_4_Q7:
                validateQuestionary(11, getResources().getStringArray(R.array.errorResponseQuestionaryMod4)[6]);
                break;
            case LocalConstants.MOD_4_Q8:
                validateQuestionary(11, getResources().getStringArray(R.array.errorResponseQuestionaryMod4)[7]);
                break;
            default:
                break;
        }
    }

    private void validateQuestionary(int activity, String errorString) {
        Log.d("VBG","In validate questionary: " + (mActualQuestionaryPage != null)
                + " " + (mActualQuestionaryPage == (LinearLayout) view.findViewWithTag(LocalConstants.HAS_QUESTIONARY)));
        if (mActualQuestionaryPage != null ){
            Log.d("VBG","actual questionary not null");
            boolean isCorrect = true;

            for (int i = 0 ; i < mActualQuestionaryPage.getChildCount(); i++){
                View v = mActualQuestionaryPage.getChildAt(i);
                if (v instanceof CheckBox) {
                    if (v.getTag() != null) {
                        if (v instanceof CheckBox) {
                            Log.d("VBG", "Is instance of checkbox");
                            if (!(v.getTag().equals(LocalConstants.CORRECT_OPTION) && ((CheckBox) v).isChecked())) {
                                isCorrect = false;
                                break;
                            }
                        } else if (v instanceof RadioButton) {
                            Log.d("VBG", "Is instance of radiobutton");
                            if (!(v.getTag().equals(LocalConstants.CORRECT_OPTION) && ((RadioButton) v).isChecked())) {
                                isCorrect = false;
                                break;
                            }
                        } else {
                            Log.d("VBG", "Is instance of nothing");
                        }
                    } else if (((CheckBox) v).isChecked()) {
                        isCorrect = false;
                        break;
                    }
                }


            }
            //if (isCorrect || LocalConstants.DEV_VERSION){
            if (isCorrect){
                setNextPage();
                markActivityAsComplete(activity);
            } else {
                final NotAcertedCrosswordDialog notAcerted = new NotAcertedCrosswordDialog();
                notAcerted.mMesaggeText = errorString;
                notAcerted.show(getActivity().getFragmentManager(),"");
            }
        }
    }

    private void validateCrossword() {
        boolean error = false;

        for (int i = 0; i< mCrossword.getColumnCount()*mCrossword.getRowCount(); i++){
            try {
                EditText et = (EditText)mCrossword.getChildAt(i);
                String tag = (String)et.getTag();
                if (tag !=null && !tag.equals(et.getText().toString().toLowerCase())){
                    error = true;
                    continue;
                }
            } catch (Exception ea){
                ea.printStackTrace();
            }
        }

        if (error && !LocalConstants.DEV_VERSION){
            final NotAcertedCrosswordDialog notAcerted = new NotAcertedCrosswordDialog();
            notAcerted.show(getActivity().getFragmentManager(),"");
        } else {
            setNextPage();
            mainInterface.saveActivityCompleted(2);
        }

    }

    public void playSound(final View button) {

        String tag = (String) button.getTag();
        int audioResourceid = LocalConstants.AUDIO_ARRAY_LIST.get(LocalConstants.AUDIO_TAG_INDEX.get(tag))[mAvatarGender-1];

        //Se instancia reporductor
        if (player1 == null) {
            player1 = MediaPlayer.create(mCtx, audioResourceid);

        }
        //Se instancia previo botòn para manejar drawable de estado
        if (preLastClickedButton == null) {
            preLastClickedButton = (Button) button;
        }
        if (player1 != null) {
            //Está reproduciendo
            if (player1.isPlaying()) {
                //Se da click sobre el mismo botón = Pausa()
                if (actualPlaying == audioResourceid) {
                    player1.pause();
                    ((Button) button).setSelected(false);
                    button.setBackground(getResources().getDrawable(R.drawable.play));
                } else { //Se da click sobre otro audio mientras el otro se està reproduciendo, se detiene el actual y se inicia el otro
                    player1.stop();
                    player1.release();
                    player1 = MediaPlayer.create(mCtx, audioResourceid);
                    preLastClickedButton.setBackground(getResources().getDrawable(R.drawable.play));
                    button.setBackground(getResources().getDrawable(R.drawable.pausa));
                    player1.start();
                    player1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            button.setBackground(getResources().getDrawable(R.drawable.play));
                        }
                    });
                }
            } else {
                //Se inicio reproducciòn del mismo audio, mientras player1 estaba detenido
                if (actualPlaying == audioResourceid) {
                    player1.start();
                    button.setBackground(getResources().getDrawable(R.drawable.pausa));
                    player1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            button.setBackground(getResources().getDrawable(R.drawable.play));
                        }
                    });
                } else { //Se empezò la reproducciòn de otro audio, mientras el player1 estaba detenido
                    player1.stop();
                    player1.release();
                    player1 = MediaPlayer.create(mCtx, audioResourceid);
                    player1.start();
                    button.setBackground(getResources().getDrawable(R.drawable.pausa));
                }
            }
            actualPlaying = audioResourceid;
            preLastClickedButton = (Button) button;
        }
    }

}
