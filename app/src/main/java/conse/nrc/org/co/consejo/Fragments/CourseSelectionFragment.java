package conse.nrc.org.co.consejo.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import conse.nrc.org.co.consejo.Interfaces.MainInterface;
import conse.nrc.org.co.consejo.R;

/**
 * Created by apple on 11/26/17.
 */

public class CourseSelectionFragment extends Fragment implements View.OnClickListener{


    View mView;
    Context mCtx;
    MainInterface mainInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.course_selection_fragment, container, false);

        ((Button)mView.findViewById(R.id.bt_vbg)).setOnClickListener(this);
        ((Button)mView.findViewById(R.id.bt_leaders)).setOnClickListener(this);

        return mView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCtx = context;
        mainInterface = (MainInterface)mCtx;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.bt_vbg:
                mainInterface.startVbgCourse();
                break;
            case R.id.bt_leaders:
                mainInterface.startLeadersCourse();
                break;
            default:
                break;

        }
    }
}
