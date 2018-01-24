package conse.nrc.org.co.consejo.Fragments;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import conse.nrc.org.co.consejo.Activities.MainActivity;
import conse.nrc.org.co.consejo.Fragments.LEADERS_COURSE_2.LeadersCourseFragment;
import conse.nrc.org.co.consejo.Fragments.VBG_Course_1.VbgCourse1Start;
import conse.nrc.org.co.consejo.Interfaces.MainInterface;
import conse.nrc.org.co.consejo.R;

/**
 * Created by Daniel Trujillo on 01/12/2017.
 */

public class AcertedCrosswordDialog extends android.app.DialogFragment {


    public static String mMesaggeText;
    public static LeadersCourseFragment leadersCourseFragment;
    public static VbgCourse1Start vbgCourse1Start;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.accerted_crossword_dialog, container, false);

        Button btBack = (Button) view.findViewById(R.id.bt_next);


        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (leadersCourseFragment != null) {
                    leadersCourseFragment.setNextPage();
                } else if(vbgCourse1Start != null){
                    vbgCourse1Start.setNextPage();
                }
                dismiss();
            }
        });

        if(mMesaggeText != null){
            ((TextView)view.findViewById(R.id.tv_message)).setText(mMesaggeText);
        }
        return view;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.onBackPressed();
        dialog.cancel();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        return dialog;
    }

}