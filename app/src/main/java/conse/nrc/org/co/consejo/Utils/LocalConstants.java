package conse.nrc.org.co.consejo.Utils;

import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import conse.nrc.org.co.consejo.BuildConfig;
import conse.nrc.org.co.consejo.R;

/**
 * Created by apple on 11/18/17.
 */

public class LocalConstants {

    public static final String SHARED_PREFERENCES = "CONSE";
    public static final String API_KEY = BuildConfig.API_KEY;

    public final static String SMS_CONTACT_PREFIX = "smsto:";

    public static final int MALE = 1;
    public static final int FEMALE = 2;

    public final static String YOUTUBE_VIDEO_ID = "KuUWKzD8e00";
    public static final String YOUTUBE_API_KEY = "AIzaSyAcvZs2JqEhMsKEYU5lkoRTCkbcbSFQMkM";

    public static final int MAX_CONTACT_NUMBER = 3;
    public static final int MIN_CONTACT_NUMBER = 1;


    //Shared Preferences Keys
    public static final String USER_DATA = "USER_DATA";
    public static final String USER_PSW = "USER_PSW";
    public static final String APP_CONFIGURATION = "APP_CONFIGURATION";
    public static final String USER_TOKEN = "USER_TOKEN";
    public static final String CONTACT_SIZE = "CONTACT_SIZE";
    public static final String CONTACT_NUMBER_ = "CONTACT_NUMBER_";
    public static final String CONTACT_NAME_ = "CONTACT_NAME_";
    public static final String CONTACT_ID_ = "CONTACT_ID_";
    public static final String AVATAR_GENDER_ID_ = "AVATAR_GENDER_ID_";

    public static final String AVATAR_SELECTED_PART_ = "AVATAR_SELECTED_PART_";



    //Endpoints
    public static final String SERVER_DOMAIN = BuildConfig.SERVER_URL;
    public static final String API_DIRECTORY = "api/";
    public static final String GET_APP_CONFIGURATION = "applicationConfiguration/1/";
    public static final String POST_USER_PROFILE = "create_user/";
    public static final String POST_CONTACT_FORM = "contact_form/";
    public static final String POST_AVATAR_LIST = "user_avatar/";
    public static final String POST_USER_PROGRESS_LIST = "user_progress/";
    public static final String PUT_USER_PROFILE_EDIT = "profile/";


    //Task id for server views
    public static final int GET_APP_CONF_TASK_ID = 19;
    public static final int REGISTER_USER_TASK_ID = 20;
    public static final int LOGGIN_USER_TASK_ID = 21;
    public static final int CONTACT_FORM_TASK_ID = 22;
    public static final int POST_AVATAR_LIST_TASK_ID = 23;
    public static final int POST_USER_PROGRESS_LIST_TASK_ID = 24;
    public static final int PUT_USER_PROFILE_EDIT_TASK_ID = 25;


    //LAYOUTS TAGS
    public static final String NEED_AVATAR_TAG = "need_avatar"; //Indica que el layout necesita avatar
    public static final String HERE_AVATAR_TAG = "here_avatar"; // Indica el framelayout donde va el avatar
    public static final String HAS_QUESTIONARY = "has_questionary"; //Indica que el layout tiene un cuestionario
    public static final String QUESTIONARY_CONTAINER = "questionary_container"; //Indica el linear layout donde están los checkbos o radio buttons con las respuestas
    public static final String CORRECT_OPTION="correct_option"; // Opcion que debe ser marcada como correcta
    public static final String MOD_1_R = "MOD_1_R"; //Lectura mòdulo 1
    public static final String MOD_1_CW1_VALIDATION = "validate_crossword_mod_1"; //Validar crucigrama mod 1
    public static final String MOD_1_CW1_SCREEN = "MOD_1_CW1_SCREEN"; //Carga ventana de crucigrama modulo 1
    public static final String MOD_2_R = "MOD_2_R"; // Lectura modulo 2
    public static final String MOD_2_Q1_VALIDATION = "MOD_2_Q1_VALIDATION"; // Lectura modulo 2

    public static final int VBG_COURSE_ID = 1;
    public static final int LEADERS_COURSE_ID = 2;


    //Arrays of constant

    public static final List<Integer> AVATAR_BODY_PARTS_ORDER = new ArrayList<Integer>(){{
        add(3); //Head
        add(2); //Hair
        add(5); //Eyes
        add(4); //Nose
        add(1); //Accessory
    }};

    public static final List<int[]> AUDIO_ARRAY_LIST = new ArrayList<int[]>(){{
       add(new int[]{R.raw.audio_1_m, R.raw.audio_1});
        add(new int[]{R.raw.audio_2_m, R.raw.audio_2});
        add(new int[]{R.raw.audio_3_m, R.raw.audio_3});
        add(new int[]{R.raw.audio_4_m, R.raw.audio_4});
        add(new int[]{R.raw.audio_5_m, R.raw.audio_5});
        add(new int[]{R.raw.audio_6_m, R.raw.audio_6});
        add(new int[]{R.raw.audio_7_m, R.raw.audio_7});
        add(new int[]{R.raw.audio_8_m, R.raw.audio_8});
        add(new int[]{R.raw.audio_9_m, R.raw.audio_9});
        add(new int[]{R.raw.audio_10_m, R.raw.audio_10});
        add(new int[]{R.raw.audio_11_m, R.raw.audio_11});
        add(new int[]{R.raw.audio_12_m, R.raw.audio_12});
        add(new int[]{R.raw.audio_13_m, R.raw.audio_13});
        add(new int[]{R.raw.audio_14_m, R.raw.audio_14});
        add(new int[]{R.raw.audio_15_m, R.raw.audio_15});
        add(new int[]{R.raw.audio_16_m, R.raw.audio_16});
        add(new int[]{R.raw.audio_17_m, R.raw.audio_17});
        add(new int[]{R.raw.audio_18_m, R.raw.audio_18});
        add(new int[]{R.raw.audio_19_m, R.raw.audio_19});
        add(new int[]{R.raw.audio_20_m, R.raw.audio_20});
        add(new int[]{R.raw.audio_21_m, R.raw.audio_21});
    }};

    public static HashMap<String, Integer> AUDIO_TAG_INDEX = new HashMap<String, Integer>(){{
        put("en_la_familia", 0);
        put("en_la_comunidad",1);
        put("en_el_ambito_estatal",2);
        put("en_el_conflicto_armado",3);
        put("violencia_fisica",4);
        put("violencia_sexual",5);
        put("violencia_psicologica",6);
        put("violencia_economica",7);
        put("nunca_olvides_que",8);
        put("como_mujer_victima_tienes_derecho",9);
        put("tu_eres_quien_decide", 10);
        put("en_todas_las_actuaciones", 11);
        put("todas_las_investigaciones", 12);
//        put("",);
//        put("",);
//        put("",);
//        put("",);
//        put("",);
//        put("",);
//        put("",);

    }};

    public static final List<Integer> VBG_LAYOUT_LIST = new ArrayList<Integer>(){{
        add(R.layout.vbg_course_1_0);
        add(R.layout.vbg_course_1_1);
        add(R.layout.vbg_course_1_2);
        add(R.layout.vbg_course_1_3);
        add(R.layout.vbg_course_1_4);
        add(R.layout.vbg_course_1_5);
        add(R.layout.vbg_course_1_6);
        add(R.layout.vbg_course_1_7);
        add(R.layout.vbg_course_1_8);
        add(R.layout.vbg_course_1_9);
        add(R.layout.vbg_course_1_11);
        add(R.layout.vbg_course_1_12);
        add(R.layout.vbg_course_1_13);
        add(R.layout.vbg_course_1_16);
        add(R.layout.vbg_course_1_18);
        add(R.layout.vbg_course_1_19);
        add(R.layout.vbg_course_1_20);
        add(R.layout.vbg_course_1_21);
        add(R.layout.vbg_course_1_22);
        add(R.layout.vbg_course_1_23);
        add(R.layout.vbg_course_1_24);
    }};

}
