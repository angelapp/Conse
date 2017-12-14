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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;

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


//        //Just development
//        ((Button)view.findViewById(R.id.bt_reset)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dataBase.clearPagesRead();
//                dataBase.clearTopicActivity();
//                setInitialPageToShow();
//            }
//        });

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
        if (index < LocalConstants.VBG_LAYOUT_LIST.size()-1){
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
                default:
                    break;
            }
        } else {
            Log.d("VBg Mod", "The view tag is: null");
        }

    }

    private void setCrossWordMod1() {
        mCrossword = (GridLayout) courseContainer.findViewById(R.id.gl_crossword);
        for (int i = 0; i < mCrossword.getChildCount(); i++){
            Log.d("VBg Mod", "Index is: " + i);
            final EditText et = (EditText)mCrossword.getChildAt(i);

            if (et.isFocusable()){
                Log.d("VBg Mod", "Index is: " + i + " The tag is: " + et.getTag().toString());

                et.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        if (et.getNextFocusDownId() != View.NO_ID) {
                            mCrossword.findViewById(et.getNextFocusDownId()).requestFocus();
                        } else if(et.getNextFocusRightId() != View.NO_ID) {
                            mCrossword.findViewById(et.getNextFocusRightId()).requestFocus();
                        }

                    }
                });
            }

            final ClueDialog cluedialog = new ClueDialog();

            if(et.getHint() != null && et.getHint().toString().length()>0){
                et.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cluedialog.setClueNumber(Integer.valueOf(et.getHint().toString())-1);
                        cluedialog.show(getActivity().getFragmentManager(),"");
                    }
                });

            }
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
                validateQuestionary();
            default:
                break;
        }
    }

    private void validateQuestionary() {
        Log.d("VBG","In validate questionary: " + (mActualQuestionaryPage != null)
                + " " + (mActualQuestionaryPage == (LinearLayout) view.findViewWithTag(LocalConstants.HAS_QUESTIONARY)));
        if (mActualQuestionaryPage != null ){
            Log.d("VBG","actual questionary not null");
            boolean isCorrect = true;

            for (int i = 0 ; i < mActualQuestionaryPage.getChildCount(); i++){
                View v = mActualQuestionaryPage.getChildAt(i);
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
                } else if(((CheckBox)v).isChecked()) {
                    isCorrect = false;
                    break;
                }

            }
            if (isCorrect){
                setNextPage();
                markActivityAsComplete(4);
            } else {
                final NotAcertedCrosswordDialog notAcerted = new NotAcertedCrosswordDialog();
                notAcerted.mMesaggeText = getString(R.string.mod_2_q1_not_acerted_message);
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
                if (tag !=null && !tag.equals(et.getText().toString())){
                    error = true;
                    continue;
                }
            } catch (Exception ea){
                ea.printStackTrace();
            }
        }

        if (error){
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
