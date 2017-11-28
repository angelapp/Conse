package conse.nrc.org.co.consejo.Fragments.VBG_Course_1;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import conse.nrc.org.co.consejo.Interfaces.AlertTestInterfaces;
import conse.nrc.org.co.consejo.R;

/**
 * Created by apple on 11/27/17.
 */

public class VbgCourse1Start extends Fragment {


    View mView;
    Context mCtx;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.vbg_course_1_start, container, false);

        Button btInit = (Button) mView.findViewById(R.id.bt_start_vbg);


        return mView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCtx = context;
    }
}
