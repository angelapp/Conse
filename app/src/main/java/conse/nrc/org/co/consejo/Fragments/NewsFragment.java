package conse.nrc.org.co.consejo.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import conse.nrc.org.co.consejo.Adapters.PagerAdapterFragment;
import conse.nrc.org.co.consejo.Interfaces.MainInterface;
import conse.nrc.org.co.consejo.R;
import conse.nrc.org.co.consejo.Utils.LocalConstants;
import conse.nrc.org.co.consejo.Utils.Models;
import conse.nrc.org.co.consejo.Utils.RequestTask;
import conse.nrc.org.co.consejo.Utils.ServerRequest;

/**
 * Created by apple on 12/9/17.
 */

public class NewsFragment extends Fragment implements RequestTask.OnRequestCompleted {

    private MainInterface mainInterface;
    private ViewPager news_view_pager;
    private PagerSlidingTabStrip news_tabs_pager;
    private PagerAdapterFragment pagerAdapterFragment;
    private List<Fragment> fragmentList;
    private List<String> tab_tittle;
    private List<Models.NewsCategory> newsCategoryList = new ArrayList<>();
    Context mCtx;
    ProgressDialog listener;
    View mView;


    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCtx = context;
        mainInterface = (MainInterface) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView =  inflater.inflate(R.layout.news_fragment, container, false);
        news_view_pager = (ViewPager) mView.findViewById(R.id.news_content);
        news_tabs_pager = (PagerSlidingTabStrip) mView.findViewById(R.id.news_tabs_pager);
        fragmentList = new ArrayList<Fragment>();
        tab_tittle = new ArrayList<String>();
        getNews();
        return mView;
    }

    @Override
    public void onRequestResponse(Object response, int taskId) {
        switch (taskId){
            case LocalConstants.GET_NEWS_TASK_ID:
                for (Models.NewsCategory type : (Models.NewsCategory[]) response){
                    if (type.hasNews()) {
                        newsCategoryList.add(type);
                    }
                }
                fillList();
                break;
            default:
                break;
        }

    }

    @Override
    public void onRequestError(int errorCode, String errorMsg, int taskId) {
        switch (taskId){
            case LocalConstants.GET_NEWS_TASK_ID:
                Toast.makeText(mCtx, errorMsg, Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }

    private void fillList() {
        List<Models.NewsOrderByCity> newOrdered = getNewsOrderByCity();

        Log.d("News Fragment", "Cities: " + newOrdered.size() +
        " Categories for 0: " + newOrdered.get(0).news.size());

        for (Models.NewsOrderByCity city : newOrdered){
            NewsTabFragment fragment = new NewsTabFragment();
            fragment.newsOrderByCity = city;
            fragmentList.add(fragment);
            tab_tittle.add(city.city.name);
            Log.d("News Fragment", "Added Tab: " + city.city.name);
        }

        Log.d("News Fragment", "Tabs number: " + tab_tittle.size());

        pagerAdapterFragment = new PagerAdapterFragment(getChildFragmentManager(),fragmentList,tab_tittle);
        news_view_pager.setAdapter(pagerAdapterFragment);
        news_tabs_pager.setViewPager(news_view_pager);

    }

    private boolean containsCity(List<Models.City> list, int city_id){

        for (Models.City city:list){
            if (city.id == city_id){
                return true;
            }
        }

        return false;
    }


    public List<Models.NewsOrderByCity> getNewsOrderByCity(){
        List<Models.City> cities = new ArrayList<>();
        List<Models.NewsOrderByCity> newOrdered = new ArrayList<>();

        for (Models.NewsCategory cat : newsCategoryList){
            for (Models.News n : cat.news_category){
                if (!containsCity(cities, n.city.id)){
                    cities.add(n.city);
                    Log.d("News Fragemnt", "Added city: " + n.city.name);
                }
            }
        }

        Log.d("News Fragment", "City number: " + cities.size());

        for(Models.City city : cities){
            Models.NewsOrderByCity list = new Models.NewsOrderByCity();
            list.city = city;
            for(Models.NewsCategory n: newsCategoryList){
                list.news.add(n.NewsFilterByCityId(city.id));
                Log.d("News Fragemnt", "City: " + city.name + " Cat= " + n.name + " News Size: " + n.NewsFilterByCityId(city.id).news_category.size());
            }
            newOrdered.add(list);
        }

        return newOrdered;
    }



    public void getNews() {
        if (newsCategoryList.size()==0) {
            listener = new ProgressDialog(mCtx);
            listener.setMessage(getString(R.string.getting_news));
            new ServerRequest.GetNews(mCtx, this, listener, LocalConstants.GET_NEWS_TASK_ID).executeGetList();
        }
    }
}
