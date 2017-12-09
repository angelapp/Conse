package conse.nrc.org.co.consejo.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 12/9/17.
 */

public class PagerAdapterFragment extends FragmentPagerAdapter {

    private List<Fragment> fragments;


    private List<String> tab_tittle ;

    /**
     * Constructor for the pager adapter
     * @param fm Fragment manager for parent
     * @param fragmentList Fragment List for put into View Pager
     * @param tab_tittle Tab tittle for PageTabStrip, arraylist for generic use
     */
    public PagerAdapterFragment(FragmentManager fm, List<Fragment> fragmentList, List<String> tab_tittle) {
        super(fm);
        this.tab_tittle = new ArrayList<String>();
        this.tab_tittle = tab_tittle;
        this.fragments = new ArrayList<Fragment>();
        this.fragments = fragmentList;
    }

    /**
     * Method for get fragment at position
     * @param position Integer with position on ViewPager
     * @return return the fragment at fragment list
     */
    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    /**
     * Method for return the size  of arrayList of fragments
     * @return Integer of size of arraylist
     */
    @Override
    public int getCount() {
        return this.fragments.size();
    }

    /**
     * Method tht retun the tittle for the PagerTabStrip
     * @param position Position of the viewpager
     * @return String from tab_tittle arraylist at the position
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return this.tab_tittle.get(position);
    }



}