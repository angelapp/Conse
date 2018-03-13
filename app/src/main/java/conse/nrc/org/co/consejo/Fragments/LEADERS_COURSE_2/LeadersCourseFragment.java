package conse.nrc.org.co.consejo.Fragments.LEADERS_COURSE_2;

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
import android.widget.TextView;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import conse.nrc.org.co.consejo.Activities.MainActivity;
import conse.nrc.org.co.consejo.Fragments.AcertedCrosswordDialog;
import conse.nrc.org.co.consejo.Fragments.VBG_Course_1.ClueDialog;
import conse.nrc.org.co.consejo.Fragments.VBG_Course_1.NotAcertedCrosswordDialog;
import conse.nrc.org.co.consejo.Interfaces.MainInterface;
import conse.nrc.org.co.consejo.R;
import conse.nrc.org.co.consejo.Utils.ConseApp;
import conse.nrc.org.co.consejo.Utils.DataBase;
import conse.nrc.org.co.consejo.Utils.LocalConstants;
import conse.nrc.org.co.consejo.Utils.UtilsFunctions;

import static conse.nrc.org.co.consejo.Utils.LocalConstants.LEADERS_COURSE_ID;

/**
 * Created by apple on 11/27/17.
 */

public class LeadersCourseFragment extends Fragment implements View.OnClickListener{



    public final static int COURSE_ID = LEADERS_COURSE_ID;
    public static int mPageAsked = 0;
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
        //Si se solicita una pagina en especifico, no se va a la ultima.
        if (mPageAsked == 0) {
            setInitialPageToShow();
        } else{
            showAskedPage();
        }
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

    private void showAskedPage() {
        index = mPageAsked;
        inflateLayout();
    }

    public static void setAskedPage(int asked){
        mPageAsked = asked;
    }

    @Override
    public void onResume(){
        super.onResume();
        if (mPageAsked == 0) {
            setInitialPageToShow();
        } else{
            showAskedPage();
        }
    }

    private void setInitialPageToShow() {
        try{
            index = dataBase.getLastPageReadForCurse(COURSE_ID);
            if (index > LocalConstants.LEADERS_LAYOUT_LIST.size()) {
                index = LocalConstants.LEADERS_LAYOUT_LIST.size();
            }
        } catch (Exception ea){
            index = 0;
            updateReadPage();
            ea.printStackTrace();
        }
        Log.d("Leaders", "Page to show: " + index);
        inflateLayout();
    }

    private void updateReadPage(){
        if (index <= LocalConstants.LEADERS_LAYOUT_LIST.size()){
            dataBase.insertUpdateLastPage(COURSE_ID, index);
        } else if(index < 0){
            dataBase.insertUpdateLastPage(COURSE_ID, 0);
        } else{
            dataBase.insertUpdateLastPage(COURSE_ID, LocalConstants.LEADERS_LAYOUT_LIST.size());
        }
    }

    public void setNextPage(){
            index++;
        if (index < LocalConstants.LEADERS_LAYOUT_LIST.size()){
            inflateLayout();
            updateReadPage();
        } else if(index >= LocalConstants.LEADERS_LAYOUT_LIST.size()) {
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
        if (tag < LocalConstants.LEADERS_LAYOUT_LIST.size() && tag >= 0){
            index = tag;
            inflateLayout();
        }
    }

    private void finishCourse() {
        mainInterface.setCourseSelectionFragment();
    }

    private void inflateLayout() {
        courseContainer.removeAllViews();
        final View view = inflater.inflate(LocalConstants.LEADERS_LAYOUT_LIST.get(index), null, false);
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

        setFullBackground(0);

        if (view.getTag() !=  null) {
            Log.d("Leaders", "The view tag is: " + view.getTag().toString());

            switch ((String) view.getTag()) {
                case LocalConstants.NEED_AVATAR_TAG:
                    setAvatar(view);
                    break;
                case LocalConstants.HAS_QUESTIONARY:
                    mActualQuestionaryPage = (LinearLayout)view.findViewWithTag(LocalConstants.QUESTIONARY_CONTAINER);
                    Log.d("Leaders Mod", "Find questionary container " + mActualQuestionaryPage.getTag().toString());
                    break;
                case LocalConstants.TAG_END_MOD_1:
                    setConseTittle(view, 1);
                    break;
                case LocalConstants.TAG_END_MOD_2:
                    setConseTittle(view, 2);
                    break;
                case LocalConstants.TAG_END_MOD_3:
                    setConseTittle(view, 3);
                    break;
                case LocalConstants.TAG_END_MOD_4:
                    setConseTittle(view, 4);
                    break;
                default:
                    break;
            }
        } else {
            Log.d("Leaders Mod", "The view tag is: null");
        }

    }

    private void setConseTittle(View view, int i) {
        setAvatar(view);
        TextView tv = (TextView) view.findViewById(R.id.tv_conse_tittle);
        String tittle = UtilsFunctions.getConseGenderTittle(mCtx, i);
        String complement = getResources().getStringArray(R.array.conse_end_mod_text_leaders)[i-1];
        String message = String.format(complement,tittle);
        tv.setText(message);
        setFullBackground(i);
    }

    private void setAvatar(View view) {
        try {
            FrameLayout avatar = (FrameLayout) view.findViewWithTag(LocalConstants.HERE_AVATAR_TAG);
            avatar.removeAllViews();
            avatar.addView(ConseApp.getAvatarFrame(mCtx, imageLoader, optionsPreview));
        } catch (Exception ea) {
            ea.printStackTrace();
        }
    }

    private void setFullBackground(int i) {
        if (i==0){ //Si se requiere quitar el backgroun image
            view.setBackgroundColor(getResources().getColor(R.color.grey_light));
        } else{
            view.setBackground(getResources().getDrawable(LocalConstants.COURSES_END_MODULE_BACKGROUNDS.get(i-1)));
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
        Log.d("Leaders MOD", "Click listened: id:" + v.getId());
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
//            case R.id.bt_return_to:
//                return_to_layout((Integer) v.getTag());
//                break;
            default:
                break;
        }
        if (v.getTag() != null) {
            processButtonTag(v);
        }
    }



    private void processButtonTag(View v) {

        switch ((String)v.getTag()){
            case LocalConstants.L_MOD_1_R:
                markActivityAsComplete(12);
                break;
            case LocalConstants.L_MOD_2_R:
                markActivityAsComplete(14);
                break;
            case LocalConstants.L_MOD_3_R:
                markActivityAsComplete(17);
                break;
            case LocalConstants.L_MOD_4_R:
                markActivityAsComplete(21);
                break;
            case LocalConstants.L_MOD_1_COMPLETE_VALIDATION:
                validateComplete(13);
                break;
            case LocalConstants.L_MOD_2_Q1:
                validateQuestionary(15, getString(R.string.lideres_20_01), getString(R.string.lideres_21_01));
                break;
            case LocalConstants.L_MOD_2_Q2:
                validateQuestionary(16, getString(R.string.lideres_30_01), null);
                break;
            case LocalConstants.L_MOD_3_Q1:
                validateQuestionary(18, getString(R.string.lideres_49_01), null);
                break;
            case LocalConstants.L_MOD_3_Q2:
                validateQuestionary(19, getString(R.string.lideres_49_01), null);
                break;
            case LocalConstants.L_MOD_3_Q3:
                validateQuestionary(20, getString(R.string.lideres_49_01), null);
                break;
            case LocalConstants.L_MOD_4_Q1:
                validateQuestionary(23, getString(R.string.lideres_63_01), null);
                break;
            case LocalConstants.L_MOD_4_VIDEO:
                markActivityAsComplete(22);
                break;
            case LocalConstants.L_MOD_4_Q2:
                validateQuestionary(24, getString(R.string.lideres_63_01), null);
                break;
            case LocalConstants.L_MOD_4_Q3:
                validateQuestionary(25, getString(R.string.lideres_63_01), null);
                break;
            default:
                //setNextPage(); //This has to die
                break;
        }
    }

    private void validateComplete(int activityId) {
        boolean error = false;
        for (String tag : LocalConstants.getLeadersCourseStringValidation(mCtx)){
            EditText editText = (EditText)view.findViewWithTag(tag);
            String[] separated = tag.split("-");
            boolean local_contains = false;
            for (String sep : separated){
                if (editText.getText() != null && editText.getText().toString().toLowerCase().trim().equals(sep)){
                    local_contains = true;
                }
            }
            if (!local_contains){
                error = true;
                editText.setError(getString(R.string.error));
            }
//            if (!editText.getText().toString().toLowerCase().equals(tag)){
//                editText.setError(getString(R.string.error));
//                error = true;
//            }

        }
        if (!error || LocalConstants.DEV_VERSION){
//        if (!error){
            markActivityAsComplete(activityId);
            setNextPage();
        } else {
            setErrorDialog(getString(R.string.lideres_10_01));
        }
    }

    private void setErrorDialog(String msg){
        final NotAcertedCrosswordDialog notAcerted = new NotAcertedCrosswordDialog();
        notAcerted.mMesaggeText = msg;
        notAcerted.show(getActivity().getFragmentManager(),"");
    }

    private void setAccertedDialog(String msg){
        final AcertedCrosswordDialog accerted = new AcertedCrosswordDialog();
        accerted.mMesaggeText = msg;
        accerted.leadersCourseFragment = this;
        accerted.show(getActivity().getFragmentManager(),"");
    }



    private boolean validateQuestionary(int activity, String errorString, String accertedString) {
        Log.d("Leaders","In validate questionary: " + (mActualQuestionaryPage != null)
                + " " + (mActualQuestionaryPage == (LinearLayout) view.findViewWithTag(LocalConstants.HAS_QUESTIONARY)));

        boolean isCorrect = true;

        if (mActualQuestionaryPage != null ){
            Log.d("Leaders","actual questionary not null");
            for (int i = 0 ; i < mActualQuestionaryPage.getChildCount(); i++){
                View v = mActualQuestionaryPage.getChildAt(i);
                if (v instanceof CheckBox) {
                    if (v.getTag() != null) {
                        if (v instanceof CheckBox) {
                            Log.d("Leaders", "Is instance of checkbox");
                            if (!(v.getTag().equals(LocalConstants.CORRECT_OPTION) && ((CheckBox) v).isChecked())) {
                                isCorrect = false;
                                ((CheckBox) v).setChecked(false);
                                break;
                            }
                        } else if (v instanceof RadioButton) {
                            Log.d("Leaders", "Is instance of radiobutton");
                            if (!(v.getTag().equals(LocalConstants.CORRECT_OPTION) && ((RadioButton) v).isChecked())) {
                                isCorrect = false;
                                ((RadioButton) v).setChecked(false);
                                break;
                            }
                        } else {
                            Log.d("Leaders", "Is instance of nothing");
                        }
                    } else if (((CheckBox) v).isChecked()) {
                        isCorrect = false;
                        ((CheckBox) v).setChecked(false);
                        break;
                    }
                }
            }
//            if(true){
            if (isCorrect || LocalConstants.DEV_VERSION){
//            if (isCorrect){
                if (accertedString != null){
                    setAccertedDialog(accertedString);
                } else {
                    setNextPage();
                }
                markActivityAsComplete(activity);
            } else {
                setErrorDialog(errorString);
            }
        }
        return isCorrect;
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
