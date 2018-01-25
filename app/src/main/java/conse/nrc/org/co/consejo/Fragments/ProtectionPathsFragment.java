package conse.nrc.org.co.consejo.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

import conse.nrc.org.co.consejo.Adapters.PagerAdapterFragment;
import conse.nrc.org.co.consejo.Interfaces.MainInterface;
import conse.nrc.org.co.consejo.R;

/**
 * Created by apple on 12/9/17.
 */

public class ProtectionPathsFragment extends Fragment {

    Context mCtx;
    FrameLayout flContent;


    public ProtectionPathsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
//        homeInterface.changeTopToolbar(ConfigurationFile.BAR_WITHOUT_BUTTONS);
//        homeInterface.changeBottomToolbar(1);
//        FirebaseClass.setFragmentScreen(getActivity(),ConfigurationFile.RULES_FRAGMENT);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCtx = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.protection_paths_fragment, container, false);
        flContent = (FrameLayout)view.findViewById(R.id.ly_paths_content);

        ((Button) view.findViewById(R.id.bt_left)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToVbgPaths();
            }
        });

        ((Button) view.findViewById(R.id.bt_right)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLeadersPaths();
            }
        });

        return view;
    }

    private void goToLeadersPaths() {

        flContent.removeAllViews();
        ProtectionPathsCourseFragment pathsCourseFragment = new ProtectionPathsCourseFragment();
        pathsCourseFragment.course_id = 2;
        getFragmentManager().beginTransaction().replace(R.id.ly_paths_content, pathsCourseFragment).addToBackStack(null).commitAllowingStateLoss();

    }

    private void goToVbgPaths() {

        flContent.removeAllViews();
        ProtectionPathsCourseFragment pathsCourseFragment = new ProtectionPathsCourseFragment();
        pathsCourseFragment.course_id = 1;
        getFragmentManager().beginTransaction().replace(R.id.ly_paths_content, pathsCourseFragment).addToBackStack(null).commitAllowingStateLoss();

    }

}
