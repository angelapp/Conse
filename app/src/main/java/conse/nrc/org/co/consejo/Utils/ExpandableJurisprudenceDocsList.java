package conse.nrc.org.co.consejo.Utils;

import android.content.Context;
import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by apple on 1/9/17.
 */

public class ExpandableJurisprudenceDocsList {

    private static Context mCtx;
    private static List<Integer> icon_list = new ArrayList<>();

    public ExpandableJurisprudenceDocsList(Context ctx){
        mCtx = ctx;
    }

    public static LinkedHashMap<String,List<String>> getData(List<Models.DocumentTextType> documentTextTypeList) {
        LinkedHashMap<String, List<String>> expandableListDetail = new LinkedHashMap<>();

        for (Models.DocumentTextType type : (List<Models.DocumentTextType>)documentTextTypeList){
            List<String> docsList = new ArrayList<String>();
            for (Models.Document doc : type.document_by_type){
                docsList.add(doc.name);
            }
            String categoryName = type.name + " - " + ConseApp.getAppConfiguration(mCtx).getCourseById(type.course).name;
            expandableListDetail.put(categoryName,docsList);
        }
        return expandableListDetail;
    }

}
