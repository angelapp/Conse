package conse.nrc.org.co.consejo.Utils;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by apple on 1/9/17.
 */

public class ExpandableNewsList {

    private static Context mCtx;

    public ExpandableNewsList(Context ctx){
        mCtx = ctx;
    }

    public static LinkedHashMap<String,List<Models.News>> getData(List<Models.NewsCategory> newsTypeList) {
        LinkedHashMap<String, List<Models.News>> expandableListDetail = new LinkedHashMap<>();

        for (Models.NewsCategory type : newsTypeList){
            if (type.hasNews()) {
                List<Models.News> docsList = new ArrayList<>();
                for (Models.News doc : type.news_category) {
                    docsList.add(doc);
                }
                Log.d("Getting news data", "Size of cat: " + docsList.size());
                String categoryName = type.name;
                expandableListDetail.put(categoryName, docsList);
            } else {
                Log.d("Getting news data", "Empty category: " + type.name);
            }
        }
        Log.d("Getting news data", "Total categories with data: " + expandableListDetail.size());
        return expandableListDetail;
    }

}
