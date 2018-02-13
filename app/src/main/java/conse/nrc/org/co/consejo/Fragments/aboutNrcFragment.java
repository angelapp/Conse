package conse.nrc.org.co.consejo.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

import conse.nrc.org.co.consejo.Adapters.PagerAdapterFragment;
import conse.nrc.org.co.consejo.Interfaces.MainInterface;
import conse.nrc.org.co.consejo.R;
import conse.nrc.org.co.consejo.Utils.ConseApp;
import conse.nrc.org.co.consejo.Utils.LocalConstants;

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
        Context mCtx;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view =  inflater.inflate(layout, container, false);


            try {
                TextView office = (TextView) view.findViewById(R.id.tv_office_link);
                Spanned tittleString;
                tittleString = Html.fromHtml("<FONT COLOR=#eb5c3f><a href=\"" + LocalConstants.office_url + "\">"
                        + getString(R.string.about_nrc_page3_2) + " </a></font>"
                );
                office.setText(tittleString);
                office.setMovementMethod(LinkMovementMethod.getInstance());
            } catch (Exception ea){
                ea.printStackTrace();
            }

            try {
                TextView facebook = (TextView) view.findViewById(R.id.tv_facebook_link);
                Spanned tittleString;
                tittleString = Html.fromHtml("<FONT COLOR=#eb5c3f><a href=\"" + LocalConstants.facebook_url + "\">"
                        + getString(R.string.about_nrc_page3_3) + " </a></font>"
                );
                facebook.setText(tittleString);
                facebook.setMovementMethod(LinkMovementMethod.getInstance());
            } catch (Exception ea){
                ea.printStackTrace();
            }

            try {
                TextView twitter = (TextView) view.findViewById(R.id.tv_twitter_link);
                Spanned tittleString;
                tittleString = Html.fromHtml("<FONT COLOR=#eb5c3f><a href=\"" + LocalConstants.twitter_url + "\">"
                        + getString(R.string.about_nrc_page3_4) + " </a></font>"
                );
                twitter.setText(tittleString);
                twitter.setMovementMethod(LinkMovementMethod.getInstance());
            } catch (Exception ea){
                ea.printStackTrace();
            }

            try {
                TextView site = (TextView) view.findViewById(R.id.tv_nrc_site_link);
                Spanned tittleString;
                tittleString = Html.fromHtml("<FONT COLOR=#eb5c3f><a href=\"" + LocalConstants.nrc_url + "\">"
                        + getString(R.string.about_nrc_page3_5) + " </a></font>"
                );
                site.setText(tittleString);
                site.setMovementMethod(LinkMovementMethod.getInstance());
            } catch (Exception ea){
                ea.printStackTrace();
            }

            return view;
        }


        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            mCtx = context;
            //alertTestInterfaces = (AlertTestInterfaces) mCtx;
        }
    }
}

