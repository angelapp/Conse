package conse.nrc.org.co.consejo.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

import conse.nrc.org.co.consejo.Adapters.PagerAdapterFragment;
import conse.nrc.org.co.consejo.Interfaces.MainInterface;
import conse.nrc.org.co.consejo.R;

/**
 * Created by apple on 12/4/17.
 */

public class aboutNrcFragment extends Fragment {

    private MainInterface mainInterface;
    private ViewPager about_nrc_view_pager;
    private PagerSlidingTabStrip about_nrc_tabs_pager;
    private PagerAdapterFragment pagerAdapterFragment;
    private List<Fragment> fragmentList;
    private List<String> tab_tittle;
    Context mCtx;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.about_noruegan_council, container, false);
        about_nrc_view_pager = (ViewPager) view.findViewById(R.id.about_content);
        about_nrc_tabs_pager = (PagerSlidingTabStrip) view.findViewById(R.id.about_nrc_tabs_pager);
        fragmentList = new ArrayList<Fragment>();
        tab_tittle = new ArrayList<String>();
        AboutSubfragment fr1 = new AboutSubfragment();
        AboutSubfragment fr2 = new AboutSubfragment();
        AboutSubfragment fr3 = new AboutSubfragment();
        fr1.layout = R.layout.about_nrc_page_1;
        fr2.layout = R.layout.about_nrc_page_2;
        fr3.layout = R.layout.about_nrc_page_3;
        fragmentList.add(fr1);
        fragmentList.add(fr2);
        fragmentList.add(fr3);
        for (String tab : getResources().getStringArray(R.array.about_nrc_tabs_tittles)){
            tab_tittle.add(tab);
            Log.d("Progress Fragment", "Added page for tab: " + tab);
        }
        pagerAdapterFragment = new PagerAdapterFragment(getChildFragmentManager(),fragmentList,tab_tittle);
        about_nrc_view_pager.setAdapter(pagerAdapterFragment);
        about_nrc_tabs_pager.setViewPager(about_nrc_view_pager);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCtx = context;
        //alertTestInterfaces = (AlertTestInterfaces) mCtx;
    }


    public static class AboutSubfragment extends Fragment {

        public int layout;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view =  inflater.inflate(layout, container, false);
            return view;
        }
    }
}

