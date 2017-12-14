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

import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

import conse.nrc.org.co.consejo.Adapters.PagerAdapterFragment;
import conse.nrc.org.co.consejo.Interfaces.MainInterface;
import conse.nrc.org.co.consejo.R;
import conse.nrc.org.co.consejo.Utils.ConseApp;
import conse.nrc.org.co.consejo.Utils.Models;

/**
 * Created by apple on 12/9/17.
 */

public class ProgressFragment extends Fragment {

    private MainInterface mainInterface;
    private ViewPager progress_view_pager;
    private PagerSlidingTabStrip progress_tabs_pager;
    private PagerAdapterFragment pagerAdapterFragment;
    private List<Fragment> fragmentList;
    private List<String> tab_tittle;
    Context mCtx;


    public ProgressFragment() {
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
        //mainInterface = (MainInterface) context;
//        homeInterface.changeTopToolbar(ConfigurationFile.BAR_WITHOUT_BUTTONS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.progress_fragment, container, false);
        progress_view_pager = (ViewPager) view.findViewById(R.id.progress_view_pager);
        progress_tabs_pager = (PagerSlidingTabStrip) view.findViewById(R.id.progress_tabs_pager);
        fragmentList = new ArrayList<Fragment>();
        tab_tittle = new ArrayList<String>();
        for (Models.Course course : ConseApp.getAppConfiguration(mCtx).course_list){
            ProgressPageFragment page = new ProgressPageFragment();
            page.mCourseId = course.id;
            fragmentList.add(page);
            tab_tittle.add(course.name);
            Log.d("Progress Fragment", "Added page for course id: " + course.id);
        }
        pagerAdapterFragment = new PagerAdapterFragment(getChildFragmentManager(),fragmentList,tab_tittle);
        progress_view_pager.setAdapter(pagerAdapterFragment);
        progress_tabs_pager.setViewPager(progress_view_pager);


        ((Button) view.findViewById(R.id.bt_previous)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
                getActivity().onBackPressed();
                //mainInterface.hideToolBar(false);
            }
        });

        return view;
    }


}
