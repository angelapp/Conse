package conse.nrc.org.co.consejo.Utils;

import android.content.Context;
import android.support.v4.util.Pair;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by apple on 1/9/17.
 */

public class ExpandableShieldsList {

    private static Context mCtx;
    private static List<Integer> icon_list = new ArrayList<>();

    public ExpandableShieldsList(Context ctx){
        mCtx = ctx;
    }

    public static LinkedHashMap<String,List<Models.CorporatePhoneBook>> getData(List<Models.OrganizationType> documentTextTypeList) {
        LinkedHashMap<String, List<Models.CorporatePhoneBook>> expandableListDetail = new LinkedHashMap<>();

        for (Models.OrganizationType type : (List<Models.OrganizationType>)documentTextTypeList){
            if (type.organization_by_type.size() > 0) {
                List<Models.CorporatePhoneBook> docsList = new ArrayList<Models.CorporatePhoneBook>();
                for (Models.CorporatePhoneBook doc : type.organization_by_type) {
                    docsList.add(doc);
                }
                Log.d("Getting adapter data", "Size of cat: " + docsList.size());
                String categoryName = type.name;
                expandableListDetail.put(categoryName, docsList);
            } else {
                Log.d("Getting adapter data", "Empty category: " + type.name);
            }
        }
        Log.d("Getting adapter data", "Total categories with data: " + expandableListDetail.size());
        return expandableListDetail;
    }

}
