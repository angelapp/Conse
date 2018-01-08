package conse.nrc.org.co.consejo.Utils;

import android.content.Context;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by apple on 1/9/17.
 */

public class LearningCommunityDocsList {

    private static List<String> LearningCategories = new ArrayList<String>(){{
        add("Auditoría de seguridad de Violencia Basada en Género");
        add("Evaluación de riesgos de protección para líderes y lideresas");
    }};

    private static Context mCtx;
    private static List<Integer> icon_list = new ArrayList<>();

    public LearningCommunityDocsList(Context ctx){
        mCtx = ctx;
    }

    public static LinkedHashMap<String,List<Models.LearningItem>> getData() {
        LinkedHashMap<String, List<Models.LearningItem>> expandableListDetail = new LinkedHashMap<>();
        for (int i = 0; i< LearningCategories.size(); i++){
            final int a = i;
            expandableListDetail.put(LearningCategories.get(i),new ArrayList<Models.LearningItem>(){{
               add(LocalConstants.LEARNING_MY_COMMUNITY_LIST.get(a));
            }});
        }
        return expandableListDetail;
    }
}
