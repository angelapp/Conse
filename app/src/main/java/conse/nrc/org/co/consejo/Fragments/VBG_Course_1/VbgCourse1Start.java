package conse.nrc.org.co.consejo.Fragments.VBG_Course_1;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import conse.nrc.org.co.consejo.R;

/**
 * Created by apple on 11/27/17.
 */

public class VbgCourse1Start extends Fragment {


    private View view;
    private Context mCtx;
    private LayoutInflater inflater;

    private LinearLayout courseContainer;
    private int index;

    private int [] layouts;

    @Override
    public void onStart() {
        super.onStart();
        index = 0;
        layouts = new int[]{R.layout.vbg_course_1_0,R.layout.vbg_course_1_1,R.layout.vbg_course_1_2,R.layout.vbg_course_1_3,
                R.layout.vbg_course_1_4,R.layout.vbg_course_1_5,R.layout.vbg_course_1_6,R.layout.vbg_course_1_7,
                R.layout.vbg_course_1_8,R.layout.vbg_course_1_9,R.layout.vbg_course_1_11,
                R.layout.vbg_course_1_12,R.layout.vbg_course_1_13};


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inflater =  inflater;

        view = inflater.inflate(R.layout.vbg_course_1_start, container, false);

        Button btInit = (Button) view.findViewById(R.id.bt_start_vbg);

        courseContainer = (LinearLayout) view.findViewById(R.id.course_container);

        btInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goForward();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCtx = context;
    }


    private void goForward(){
        switch (getIndexPlusOne()){
            case 0:
                drawPage0();
                break;
            case 1:
                drawPage1();
                break;
            case 2:
                drawPage2();
                break;
            case 3:
                drawPage3();
                break;
            case 4:
                drawPage4();
                break;
            case 5:
                drawPage5();
                break;
            case 6:
                drawPage6();
                break;
            case 7:
                drawPage7();
                break;
            case 8:
                drawPage8();
                break;
            case 9:
                drawPage9();
                break;
            case 10:
                drawPage10();
                break;
            case 11:
                drawPage11();
                break;
            case 12:
                drawPage12();
                break;
            case 13:
                drawPage13();
                break;

        }
    }

    private void goBackward(){
        switch (getIndexLessOne()){
            case 0:
                drawPage0();
                break;
            case 1:
                drawPage1();
                break;
            case 2:
                drawPage2();
                break;
            case 3:
                drawPage3();
                break;
            case 4:
                drawPage4();
                break;
            case 5:
                drawPage5();
                break;
            case 6:
                drawPage6();
                break;
            case 7:
                drawPage7();
                break;
            case 8:
                drawPage8();
                break;
            case 9:
                drawPage9();
                break;
            case 10:
                drawPage10();
                break;
            case 11:
                drawPage11();
                break;
            case 12:
                drawPage12();
                break;
            case 13:
                drawPage13();
                break;

        }
    }

    private int getIndexPlusOne(){
        if (index < layouts.length) {index ++; return index;}
        else {return index;}
    }

    private int getIndexLessOne(){
        if (index > 0){index --; return index;}
        else {return index;}
    }


    private void drawPage0(){
        courseContainer.removeAllViews();

        view = inflater.inflate(layouts[index], null, false);

        Button btInit = (Button) view.findViewById(R.id.bt_start_vbg);

        btInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goForward();
            }
        });


        courseContainer.addView(view);
    }

    private void drawPage1(){
        courseContainer.removeAllViews();

        view = inflater.inflate(layouts[index], null, false);

        Button btBack = (Button) view.findViewById(R.id.bt_back);
        Button btForward = (Button) view.findViewById(R.id.bt_forward);


        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackward();
            }
        });

        btForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goForward();
            }
        });

        courseContainer.addView(view);
    }

    private void drawPage2(){
        courseContainer.removeAllViews();

        view = inflater.inflate(layouts[index], null, false);

        Button btBack = (Button) view.findViewById(R.id.bt_back);
        Button btForward = (Button) view.findViewById(R.id.bt_forward);


        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackward();
            }
        });

        btForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goForward();
            }
        });

        courseContainer.addView(view);
    }

    private void drawPage3(){
        courseContainer.removeAllViews();

        view = inflater.inflate(layouts[index], null, false);

        Button btBack = (Button) view.findViewById(R.id.bt_back);
        Button btForward = (Button) view.findViewById(R.id.bt_forward);


        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackward();
            }
        });

        btForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goForward();
            }
        });

        courseContainer.addView(view);
    }

    private void drawPage4(){
        courseContainer.removeAllViews();

        view = inflater.inflate(layouts[index], null, false);

        Button btBack = (Button) view.findViewById(R.id.bt_back);
        Button btForward = (Button) view.findViewById(R.id.bt_forward);


        final MediaPlayer player1  = MediaPlayer.create(getActivity(), R.raw.audio_1);
        final MediaPlayer player2  = MediaPlayer.create(getActivity(), R.raw.audio_2);
        final MediaPlayer player3  = MediaPlayer.create(getActivity(), R.raw.audio_3);
        final MediaPlayer player4  = MediaPlayer.create(getActivity(), R.raw.audio_4);
        final Button play1 = (Button) view.findViewById(R.id.bt_play_1);
        final Button play2 = (Button) view.findViewById(R.id.bt_play_2);
        final Button play3 = (Button) view.findViewById(R.id.bt_play_3);
        final Button play4 = (Button) view.findViewById(R.id.bt_play_4);

        play1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (play1.isSelected()){
                    play1.setSelected(!play1.isSelected());
                    player1.pause();
                    play1.setBackground(getResources().getDrawable(R.drawable.play));
                }else{
                    play1.setSelected(!play1.isSelected());
                    player1.start();
                    play1.setBackground(getResources().getDrawable(R.drawable.pausa));
                    player1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            play1.setBackground(getResources().getDrawable(R.drawable.play));
                            play1.setSelected(false);

                        }
                    });
                }

            }
        });

        play2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (play2.isSelected()){
                    play2.setSelected(!play2.isSelected());
                    player2.pause();
                    play2.setBackground(getResources().getDrawable(R.drawable.play));
                }else{
                    play2.setSelected(!play2.isSelected());
                    player2.start();
                    play2.setBackground(getResources().getDrawable(R.drawable.pausa));
                    player2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            play2.setBackground(getResources().getDrawable(R.drawable.play));
                            play2.setSelected(false);

                        }
                    });
                }
            }
        });


        play3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (play3.isSelected()){
                    play3.setSelected(false);
                    player3.pause();
                    play3.setBackground(getResources().getDrawable(R.drawable.play));
                }else{
                    play3.setSelected(true);
                    player3.start();
                    play3.setBackground(getResources().getDrawable(R.drawable.pausa));
                    player3.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            play3.setBackground(getResources().getDrawable(R.drawable.play));
                            play3.setSelected(false);

                        }
                    });
                }
            }
        });

        play4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (play4.isSelected()){
                    play4.setSelected(false);
                    player4.pause();
                    play4.setBackground(getResources().getDrawable(R.drawable.play));
                }else{
                    play4.setSelected(true);
                    player4.start();
                    play4.setBackground(getResources().getDrawable(R.drawable.pausa));
                    player4.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            play4.setBackground(getResources().getDrawable(R.drawable.play));
                            play4.setSelected(false);

                        }
                    });
                }

            }
        });


        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackward();
            }
        });

        btForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goForward();
            }
        });

        courseContainer.addView(view);
    }

    private void drawPage5(){
        courseContainer.removeAllViews();

        view = inflater.inflate(layouts[index], null, false);

        Button btBack = (Button) view.findViewById(R.id.bt_back);
        Button btForward = (Button) view.findViewById(R.id.bt_forward);


        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackward();
            }
        });

        btForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goForward();
            }
        });

        courseContainer.addView(view);
    }

    private void drawPage6(){
        courseContainer.removeAllViews();

        view = inflater.inflate(layouts[index], null, false);

        Button btBack = (Button) view.findViewById(R.id.bt_back);
        Button btForward = (Button) view.findViewById(R.id.bt_forward);

        final MediaPlayer player1  = MediaPlayer.create(getActivity(), R.raw.audio_5);
        final MediaPlayer player2  = MediaPlayer.create(getActivity(), R.raw.audio_6);
        final MediaPlayer player3  = MediaPlayer.create(getActivity(), R.raw.audio_7);
        final MediaPlayer player4  = MediaPlayer.create(getActivity(), R.raw.audio_8);
        final Button play1 = (Button) view.findViewById(R.id.bt_play_1);
        final Button play2 = (Button) view.findViewById(R.id.bt_play_2);
        final Button play3 = (Button) view.findViewById(R.id.bt_play_3);
        final Button play4 = (Button) view.findViewById(R.id.bt_play_4);

        play1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (play1.isSelected()){
                    play1.setSelected(!play1.isSelected());
                    player1.pause();
                    play1.setBackground(getResources().getDrawable(R.drawable.play));
                }else{
                    play1.setSelected(!play1.isSelected());
                    player1.start();
                    play1.setBackground(getResources().getDrawable(R.drawable.pausa));
                    player1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            play1.setBackground(getResources().getDrawable(R.drawable.play));
                            play1.setSelected(false);

                        }
                    });
                }

            }
        });

        play2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (play2.isSelected()){
                    play2.setSelected(!play2.isSelected());
                    player2.pause();
                    play2.setBackground(getResources().getDrawable(R.drawable.play));
                }else{
                    play2.setSelected(!play2.isSelected());
                    player2.start();
                    play2.setBackground(getResources().getDrawable(R.drawable.pausa));
                    player2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            play2.setBackground(getResources().getDrawable(R.drawable.play));
                            play2.setSelected(false);

                        }
                    });
                }
            }
        });


        play3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (play3.isSelected()){
                    play3.setSelected(false);
                    player3.pause();
                    play3.setBackground(getResources().getDrawable(R.drawable.play));
                }else{
                    play3.setSelected(true);
                    player3.start();
                    play3.setBackground(getResources().getDrawable(R.drawable.pausa));
                    player3.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            play3.setBackground(getResources().getDrawable(R.drawable.play));
                            play3.setSelected(false);

                        }
                    });
                }
            }
        });

        play4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (play4.isSelected()){
                    play4.setSelected(false);
                    player4.pause();
                    play4.setBackground(getResources().getDrawable(R.drawable.play));
                }else{
                    play4.setSelected(true);
                    player4.start();
                    play4.setBackground(getResources().getDrawable(R.drawable.pausa));
                    player4.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            play4.setBackground(getResources().getDrawable(R.drawable.play));
                            play4.setSelected(false);

                        }
                    });
                }

            }
        });


        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackward();
            }
        });

        btForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goForward();
            }
        });

        courseContainer.addView(view);
    }

    private void drawPage7(){
        courseContainer.removeAllViews();

        view = inflater.inflate(layouts[index], null, false);

        Button btBack = (Button) view.findViewById(R.id.bt_back);
        Button btForward = (Button) view.findViewById(R.id.bt_forward);

        final MediaPlayer player1  = MediaPlayer.create(getActivity(), R.raw.audio_9);
        final Button play1 = (Button) view.findViewById(R.id.bt_play_1);


        play1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (play1.isSelected()){
                    play1.setSelected(!play1.isSelected());
                    player1.pause();
                    play1.setBackground(getResources().getDrawable(R.drawable.play));
                }else{
                    play1.setSelected(!play1.isSelected());
                    player1.start();
                    play1.setBackground(getResources().getDrawable(R.drawable.pausa));
                    player1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            play1.setBackground(getResources().getDrawable(R.drawable.play));
                            play1.setSelected(false);

                        }
                    });
                }

            }
        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackward();
            }
        });

        btForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goForward();
            }
        });

        courseContainer.addView(view);
    }

    private void drawPage8(){
        courseContainer.removeAllViews();

        view = inflater.inflate(layouts[index], null, false);

        Button btBack = (Button) view.findViewById(R.id.bt_back);
        Button btForward = (Button) view.findViewById(R.id.bt_forward);


        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackward();
            }
        });

        btForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goForward();
            }
        });

        courseContainer.addView(view);
    }

    //Crossword

    private void drawPage9(){
        courseContainer.removeAllViews();

        view = inflater.inflate(layouts[index], null, false);

        Button btForward = (Button) view.findViewById(R.id.bt_forward);

        final ClueDialog cluedialog = new ClueDialog();

        final EditText p1_1 = (EditText) view.findViewById(R.id.p1_1);
        final EditText p1_2 = (EditText) view.findViewById(R.id.p1_2);
        final EditText p1_3 = (EditText) view.findViewById(R.id.p1_3);
        final EditText p1_4 = (EditText) view.findViewById(R.id.p1_4);
        final EditText p1_5 = (EditText) view.findViewById(R.id.p1_5);
        final EditText p1_6 = (EditText) view.findViewById(R.id.p1_6);
        final EditText p1_7 = (EditText) view.findViewById(R.id.p1_7);
        final EditText p1_8 = (EditText) view.findViewById(R.id.p1_8);
        final EditText p1_9 = (EditText) view.findViewById(R.id.p1_9);
        final EditText p1_10 = (EditText) view.findViewById(R.id.p1_10);
        final EditText p1_11 = (EditText) view.findViewById(R.id.p1_11);
        final EditText p2_1 = (EditText) view.findViewById(R.id.p2_1);
        final EditText p2_2 = (EditText) view.findViewById(R.id.p2_2);
        final EditText p2_4 = (EditText) view.findViewById(R.id.p2_4);
        final EditText p2_5 = (EditText) view.findViewById(R.id.p2_5);
        final EditText p2_6 = (EditText) view.findViewById(R.id.p2_6);
        final EditText p3_1 = (EditText) view.findViewById(R.id.p3_1);
        final EditText p3_2 = (EditText) view.findViewById(R.id.p3_2);
        final EditText p3_3 = (EditText) view.findViewById(R.id.p3_3);
        final EditText p3_4 = (EditText) view.findViewById(R.id.p3_4);
        final EditText p3_5 = (EditText) view.findViewById(R.id.p3_5);
        final EditText p3_6 = (EditText) view.findViewById(R.id.p3_6);
        EditText p4_2 = (EditText) view.findViewById(R.id.p4_2);
        final EditText p4_3 = (EditText) view.findViewById(R.id.p4_3);
        final EditText p4_4 = (EditText) view.findViewById(R.id.p4_4);
        final EditText p4_5 = (EditText) view.findViewById(R.id.p4_5);
        final EditText p4_6 = (EditText) view.findViewById(R.id.p4_6);
        final EditText p4_7 = (EditText) view.findViewById(R.id.p4_7);
        EditText p4_9 = (EditText) view.findViewById(R.id.p4_9);
        final EditText p5_1 = (EditText) view.findViewById(R.id.p5_1);
        final EditText p5_2 = (EditText) view.findViewById(R.id.p5_2);
        final EditText p5_3 = (EditText) view.findViewById(R.id.p5_3);
        EditText p5_5 = (EditText) view.findViewById(R.id.p5_5);
        final EditText p5_6 = (EditText) view.findViewById(R.id.p5_6);
        final EditText p5_7 = (EditText) view.findViewById(R.id.p5_7);
        final EditText p6_1 = (EditText) view.findViewById(R.id.p6_1);
        final EditText p6_2 = (EditText) view.findViewById(R.id.p6_2);
        final EditText p6_3 = (EditText) view.findViewById(R.id.p6_3);
        EditText p6_5 = (EditText) view.findViewById(R.id.p6_5);
        final EditText p6_6 = (EditText) view.findViewById(R.id.p6_6);

        p1_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                p1_2.requestFocus();

            }

            @Override
            public void afterTextChanged(Editable s) {
                p1_2.requestFocus();
            }
        });


        p1_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                p1_3.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
                p1_3.requestFocus();
            }
        });
        p1_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                p1_4.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
                p1_4.requestFocus();
            }
        });


        p1_4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                p1_5.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
                p1_5.requestFocus();
            }
        });


        p1_5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                p1_6.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
                p1_6.requestFocus();
            }
        });


        p1_6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                p1_7.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
                p1_7.requestFocus();
            }
        });


        p1_7.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                p1_8.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
                p1_8.requestFocus();
            }
        });


        p1_8.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                p1_9.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
                p1_9.requestFocus();
            }
        });


        p1_9.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                p1_10.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
                p1_10.requestFocus();
            }
        });


        p1_10.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                p1_11.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
                p1_11.requestFocus();
            }
        });


        p1_11.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                p1_1.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
                p1_1.requestFocus();
            }
        });


        p2_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                p2_2.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
                p2_2.requestFocus();
            }
        });


        p2_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                p1_2.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
                p1_2.requestFocus();
            }
        });


        p2_4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                p2_5.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
                p2_5.requestFocus();
            }
        });


        p2_5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                p2_6.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
                p2_6.requestFocus();

            }
        });


        p2_6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                p2_1.requestFocus();

            }

            @Override
            public void afterTextChanged(Editable s) {
                p2_1.requestFocus();

            }
        });


        p3_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                p3_2.requestFocus();

            }

            @Override
            public void afterTextChanged(Editable s) {
                p3_2.requestFocus();

            }
        });


        p3_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                p3_3.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
                p3_3.requestFocus();
            }
        });


        p3_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                p3_4.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
                p3_4.requestFocus();
            }
        });


        p3_4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                p3_5.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
                p3_5.requestFocus();
            }
        });


        p3_5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                p3_6.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
                p3_6.requestFocus();
            }
        });


        p3_6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                p3_1.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
                p3_1.requestFocus();
            }
        });


        p4_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                p4_3.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
                p4_3.requestFocus();
            }
        });


        p4_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                p4_4.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
                p4_4.requestFocus();
            }
        });


        p4_4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                p4_5.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
                p4_5.requestFocus();
            }
        });


        p4_5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                p4_6.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
                p4_6.requestFocus();
            }
        });


        p4_6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                p4_7.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
                p4_7.requestFocus();
            }
        });


        p4_7.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                p1_4.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
                p1_4.requestFocus();
            }
        });


        p4_9.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                p3_2.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
                p3_2.requestFocus();
            }
        });


        p5_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                p5_2.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
                p5_2.requestFocus();
            }
        });


        p5_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                p5_3.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
                p5_3.requestFocus();
            }
        });


        p5_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                p1_9.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
                p1_9.requestFocus();
            }
        });


        p5_5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                p5_6.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
                p5_6.requestFocus();
            }
        });


        p5_6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                p5_7.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
                p5_7.requestFocus();
            }
        });


        p5_7.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                p5_1.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
                p5_1.requestFocus();
            }
        });


        p6_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                p6_2.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
                p6_2.requestFocus();
            }
        });


        p6_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                p6_3.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
                p6_3.requestFocus();
            }
        });


        p6_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                p1_11.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
                p1_11.requestFocus();
            }
        });


        p6_5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                p6_6.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
                p6_6.requestFocus();
            }
        });


        p6_6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                p6_1.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
                p6_1.requestFocus();
            }
        });



        p1_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cluedialog.setClueNumber(0);
                cluedialog.show(getActivity().getFragmentManager(),"");
            }
        });

        p2_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cluedialog.setClueNumber(1);
                cluedialog.show(getActivity().getFragmentManager(),"");
            }
        });

        p3_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cluedialog.setClueNumber(2);
                cluedialog.show(getActivity().getFragmentManager(),"");
            }
        });

        p3_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cluedialog.setClueNumber(3);
                cluedialog.show(getActivity().getFragmentManager(),"");
            }
        });

        p5_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cluedialog.setClueNumber(4);
                cluedialog.show(getActivity().getFragmentManager(),"");
            }
        });

        p6_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cluedialog.setClueNumber(5);
                cluedialog.show(getActivity().getFragmentManager(),"");
            }
        });

        final String word1 = p1_1.getText().toString()+p1_2.getText().toString()+p1_3.getText().toString()
                +p1_4.getText().toString()+p1_5.getText().toString()+p1_6.getText().toString()+p1_7.getText().toString()
                +p1_8.getText().toString()+p1_9.getText().toString()+p1_10.getText().toString()+p1_11.getText().toString();

        final String word2 = p2_1.getText().toString()+p2_2.getText().toString()+p1_2.getText().toString()+p2_4.getText().toString()
                +p2_5.getText().toString()+p2_6.getText().toString();

        final String word3 = p3_1.getText().toString()+p3_2.getText().toString()+p3_3.getText().toString()
                +p3_4.getText().toString()+p3_5.getText().toString()+p3_6.getText().toString();

        final String word4 = p3_2.getText().toString()+p4_2.getText().toString()+p4_3.getText().toString()+p4_4.getText().toString()
                +p4_5.getText().toString()+p4_6.getText().toString()+p4_7.getText().toString()+p1_4.getText().toString()
                +p4_9.getText().toString();

        final String word5 = p5_1.getText().toString()+p5_2.getText().toString()+p5_3.getText().toString()
                +p1_9.getText().toString()+p5_5.getText().toString()+p5_6.getText().toString()+p5_7.getText().toString();

        final String word6 = p6_1.getText().toString()+p6_2.getText().toString()+p6_3.getText().toString()
                +p1_11.getText().toString()+p6_5.getText().toString()+p6_6.getText().toString();


        final NotAcertedCrosswordDialog notAcerted = new NotAcertedCrosswordDialog();


        btForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (word1.equals(getString(R.string.clue_1)) &&
//                        word2.equals(getString(R.string.clue_2)) &&
//                        word3.equals(getString(R.string.clue_3)) &&
//                        word4.equals(getString(R.string.clue_4)) &&
//                        word5.equals(getString(R.string.clue_5)) &&
//                        word6.equals(getString(R.string.clue_6))){
                if(true){
                    goForward();
                }else{
                    notAcerted.show(getActivity().getFragmentManager(),"");
                }


            }
        });

        courseContainer.addView(view);
    }

    private void drawPage10(){
        courseContainer.removeAllViews();

        view = inflater.inflate(layouts[index], null, false);

        Button btFinish = (Button) view.findViewById(R.id.bt_finish);


        btFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goForward();
            }
        });


        courseContainer.addView(view);
    }

    private void drawPage11(){
        courseContainer.removeAllViews();

        view = inflater.inflate(layouts[index], null, false);


        Button btBack = (Button) view.findViewById(R.id.bt_back);
        Button btForward = (Button) view.findViewById(R.id.bt_forward);

        final MediaPlayer player1  = MediaPlayer.create(getActivity(), R.raw.audio_10);
        final Button play1 = (Button) view.findViewById(R.id.bt_play_1);


        play1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (play1.isSelected()){
                    play1.setSelected(!play1.isSelected());
                    player1.pause();
                    play1.setBackground(getResources().getDrawable(R.drawable.play));
                }else{
                    play1.setSelected(!play1.isSelected());
                    player1.start();
                    play1.setBackground(getResources().getDrawable(R.drawable.pausa));
                    player1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            play1.setBackground(getResources().getDrawable(R.drawable.play));
                            play1.setSelected(false);

                        }
                    });
                }

            }
        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackward();
            }
        });

        btForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goForward();
            }
        });

        courseContainer.addView(view);
    }

    private void drawPage12(){
        courseContainer.removeAllViews();

        view = inflater.inflate(layouts[index], null, false);

        Button btBack = (Button) view.findViewById(R.id.bt_back);
        Button btForward = (Button) view.findViewById(R.id.bt_forward);


        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackward();
            }
        });

        btForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //goForward();
            }
        });

        courseContainer.addView(view);
    }

    private void drawPage13(){
        courseContainer.removeAllViews();

        view = inflater.inflate(layouts[index], null, false);

        Button btBack = (Button) view.findViewById(R.id.bt_back);
        Button btForward = (Button) view.findViewById(R.id.bt_forward);


        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackward();
            }
        });

        btForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        courseContainer.addView(view);
    }





}
