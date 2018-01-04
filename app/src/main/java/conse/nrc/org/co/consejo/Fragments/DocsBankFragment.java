package conse.nrc.org.co.consejo.Fragments;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.astuetz.PagerSlidingTabStrip;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

public class DocsBankFragment extends Fragment {

    private MainInterface mainInterface;
    private ViewPager bank_docs_view_pager;
    private PagerSlidingTabStrip bank_docs_tabs_pager;
    private PagerAdapterFragment pagerAdapterFragment;
    private List<Fragment> fragmentList;
    private List<String> tab_tittle;
    Context mCtx;


    public DocsBankFragment() {
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
        mainInterface = (MainInterface) context;
//        homeInterface.changeTopToolbar(ConfigurationFile.BAR_WITHOUT_BUTTONS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.docs_bank_fragment, container, false);
        bank_docs_view_pager = (ViewPager) view.findViewById(R.id.docs_content);
        bank_docs_tabs_pager = (PagerSlidingTabStrip) view.findViewById(R.id.bank_docs_tabs_pager);
        fragmentList = new ArrayList<Fragment>();
        tab_tittle = new ArrayList<String>();
        fragmentList.add(new TemplateLibrary());
        fragmentList.add(new JurisprudenceDocs());
        fragmentList.add(new ShieldsList());
        for (String tab : getResources().getStringArray(R.array.bank_docs_tabs_tittles)){
            tab_tittle.add(tab);
            Log.d("Progress Fragment", "Added page for tab: " + tab);
        }
        pagerAdapterFragment = new PagerAdapterFragment(getChildFragmentManager(),fragmentList,tab_tittle);
        bank_docs_view_pager.setAdapter(pagerAdapterFragment);
        bank_docs_tabs_pager.setViewPager(bank_docs_view_pager);
        bank_docs_tabs_pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1){
                    if(!mainInterface.validateNetworkStatus(true)){
                        bank_docs_view_pager.setCurrentItem(0);
                    } else {
                        ((JurisprudenceDocs)fragmentList.get(position)).reload();
                    }
                }
                if (position == 2){
                    if(!mainInterface.validateGpsStatus(true)){
                        bank_docs_view_pager.setCurrentItem(0);
                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return view;
    }

}
