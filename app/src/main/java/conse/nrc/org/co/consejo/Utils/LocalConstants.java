package conse.nrc.org.co.consejo.Utils;

import android.content.Context;
import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import conse.nrc.org.co.consejo.BuildConfig;
import conse.nrc.org.co.consejo.R;

/**
 * Created by apple on 11/18/17.
 */

public class LocalConstants {

    public static final String SHARED_PREFERENCES = "CONSE";
    public static final String API_KEY = BuildConfig.API_KEY;
    public static final boolean DEV_VERSION = BuildConfig.DEV_VERSION;

    public final static String SMS_CONTACT_PREFIX = "smsto:";

    public static final int MALE = 1;
    public static final int FEMALE = 2;
    public static final int TRANSGEN = 3;

    public static final float BUTTON_SEND_ALERT_MIN_ALPHA = 0.5f;
    public static final float BUTTON_SEND_ALERT_MAX_ALPHA = 1f;

    public static final float DIALOG_DIM_ALPHA = 0.9f;

    public static final int opened = 1;
    public static final int closed = 2;

    public static final int crowd_horizontal = 1;
    public static final int crowd_vertical = 2;

    public final static String DOWNLOAD_VIDEOS_DIRECTORY = "VideosConse";

    public final static String DOWNLOAD_VIDEOS_ROOT_DIRECTORY = "http://nrc.org.co/conse/";

    public final static String YOUTUBE_VIDEO_ID = "KuUWKzD8e00";
    public final static String VBG_VIDEO_ID = "Moz4tszhujU";
    public final static String VBG_VIDEO_DOWNLOAD_URL = "http://nrc.org.co/conse/Violecia_Basada_genero.mp4";
    public final static String LEADERS_VIDEO_ID = "7t0rIdNhayM";
    public final static String LEADERS_VIDEO_DOWNLOAD_URL = "http://nrc.org.co/conse/Lideres_Lideresas.mp4";
    public final static String MEDIA_VIDEO_ID = "trNPQr6VIsQ";
    public final static String MEDIA_VIDEO_DOWNLOAD_URL = "http://nrc.org.co/conse/Medios_Comunitarios.mp4";
    public static final String YOUTUBE_API_KEY = "AIzaSyAcvZs2JqEhMsKEYU5lkoRTCkbcbSFQMkM";

    public static final int MAX_CONTACT_NUMBER = 3;
    public static final int MIN_CONTACT_NUMBER = 1;


    public static final String office_url = "http://www.nrc.org.co/directorio-oficinas-nrc/";
    public static final String facebook_url = "https://www.facebook.com/consejonoruegopararefugiados/";
    public static final String twitter_url = "https://twitter.com/NRC_LAC";
    public static final String nrc_url = "http://www.nrc.org.co/";


    //Shared Preferences Keys
    public static final String USER_DATA = "USER_DATA";
    public static final String USER_PSW = "USER_PSW";
    public static final String IS_USER_LOGGED_IN = "IS_USER_LOGGED_IN";
    public static final String USER_IS_IN_DEVICE = "USER_IS_IN_DEVICE";
    public static final String APP_CONFIGURATION = "APP_CONFIGURATION";
    public static final String USER_TOKEN = "USER_TOKEN";
    public static final String CONTACT_SIZE = "CONTACT_SIZE";
    public static final String CONTACT_NUMBER_ = "CONTACT_NUMBER_";
    public static final String CONTACT_NAME_ = "CONTACT_NAME_";
    public static final String CONTACT_ID_ = "CONTACT_ID_";
    public static final String AVATAR_GENDER_ID_ = "AVATAR_GENDER_ID_";

    public static final String AVATAR_SELECTED_PART_ = "AVATAR_SELECTED_PART_";

    public static final String VIDEO_ID = "VIDEO_ID";
    public static final String ACTUAL_VIDEO_TIME = "ACTUAL_VIDEO_TIME";



    //Endpoints
    public static final String SERVER_DOMAIN = BuildConfig.SERVER_URL;
    public static final String API_DIRECTORY = "api/";
    public static final String GET_APP_CONFIGURATION = "applicationConfiguration/1/";
    public static final String POST_USER_PROFILE = "create_user/";
    public static final String POST_CONTACT_FORM = "contact_form/";
    public static final String POST_AVATAR_LIST = "user_avatar/";
    public static final String POST_USER_PROGRESS_LIST = "user_progress/";
    public static final String PUT_USER_PROFILE_EDIT = "profile/";
    public static final String POST_USER_LOGGIN = "user_auth/";
    public static final String POST_PASSWORD_RECOVERY = "user_password_recovery/";
    public static final String GET_LIBRARY_DOCS = "get_library_docs/";
    public static final String GET_SHIELDS = "get_corporate_phone_book";
    public static final String GET_NEWS = "get_news_list/";



    //Task id for server views
    public static final int GET_APP_CONF_TASK_ID = 19;
    public static final int REGISTER_USER_TASK_ID = 20;
    public static final int LOGGIN_USER_TASK_ID = 21;
    public static final int CONTACT_FORM_TASK_ID = 22;
    public static final int POST_AVATAR_LIST_TASK_ID = 23;
    public static final int POST_USER_PROGRESS_LIST_TASK_ID = 24;
    public static final int PUT_USER_PROFILE_EDIT_TASK_ID = 25;
    public static final int POST_USER_LOGGIN_TASK_ID = 26;
    public static final int POST_PASSWORD_RECERY_TASK_ID = 27;
    public static final int GET_LIBRARY_DOCS_TASK_ID = 27;
    public static final int GET_SHIELDS_TASK_ID = 28;
    public static final int GET_NEWS_TASK_ID = 28;

    //GET PARAMETERS
    public static final String LATITUDE = "latitude=";
    public static final String LONGITUDE = "longitude=";


    public static final String PROVIDER_NAME = ".conse";


    //LAYOUTS TAGS
    public static final String NEED_AVATAR_TAG = "need_avatar"; //Indica que el layout necesita avatar
    public static final String HERE_AVATAR_TAG = "here_avatar"; // Indica el framelayout donde va el avatar
    public static final String NEED_YOUTUBE_VIDEO_TAG = "need_youtube_video"; //Indica que el layout necesita avatar
    public static final String HERE_VIDEO_TAG = "here_video"; // Indica el framelayout donde va el avatar
    public static final String HAS_QUESTIONARY = "has_questionary"; //Indica que el layout tiene un cuestionario
    public static final String QUESTIONARY_CONTAINER = "questionary_container"; //Indica el linear layout donde están los checkbos o radio buttons con las respuestas
    public static final String CORRECT_OPTION="correct_option"; // Opcion que debe ser marcada como correcta
    public static final String MOD_1_R = "MOD_1_R"; //Lectura mòdulo 1
    public static final String MOD_1_CW1_VALIDATION = "validate_crossword_mod_1"; //Validar crucigrama mod 1
    public static final String MOD_1_CW1_SCREEN = "MOD_1_CW1_SCREEN"; //Carga ventana de crucigrama modulo 1
    public static final String MOD_2_R = "MOD_2_R"; // Lectura modulo 2
    public static final String MOD_2_Q1_VALIDATION = "MOD_2_Q1_VALIDATION"; // Validar cuestionario modulo 2
    public static final String MOD_3_R = "MOD_3_R";
    public static final String MOD_3_Q1 = "MOD_3_Q1";
    public static final String MOD_3_Q2 = "MOD_3_Q2";
    public static final String MOD_3_Q3 = "MOD_3_Q3";
    public static final String MOD_3_Q4 = "MOD_3_Q4";
    public static final String MOD_4_R = "MOD_4_R";
    public static final String MOD_4_Q1 = "MOD_4_Q1";
    public static final String MOD_4_Q2 = "MOD_4_Q2";
    public static final String MOD_4_Q3 = "MOD_4_Q3";
    public static final String MOD_4_Q4 = "MOD_4_Q4";
    public static final String MOD_4_Q5 = "MOD_4_Q5";
    public static final String MOD_4_Q6 = "MOD_4_Q6";
    public static final String MOD_4_Q7 = "MOD_4_Q7";
    public static final String MOD_4_Q8 = "MOD_4_Q8";

    public static final String MOD_4_Q3_CORRECT_ANSWER = "155";


    //Tags for leaders course activities
    public static final String L_MOD_1_R = "L_MOD_1_R";
    public static final String L_MOD_1_COMPLETE_VALIDATION = "L_MOD_1_COMPLETE_VALIDATION";
    public static final String L_MOD_2_R = "L_MOD_2_R";
    public static final String L_MOD_2_Q1 = "L_MOD_2_Q1";
    public static final String L_MOD_2_Q2 = "L_MOD_2_Q2";
    public static final String L_MOD_3_R= "L_MOD_3_R";
    public static final String L_MOD_3_Q1 = "L_MOD_3_Q1";
    public static final String L_MOD_3_Q2 = "L_MOD_3_Q2";
    public static final String L_MOD_3_Q3 = "L_MOD_3_Q3";
    public static final String L_MOD_4_R= "L_MOD_4_R";
    public static final String L_MOD_4_VIDEO = "L_MOD_4_VIDEO";
    public static final String L_MOD_4_Q1 = "L_MOD_4_Q1";
    public static final String L_MOD_4_Q2 = "L_MOD_4_Q2";
    public static final String L_MOD_4_Q3 = "L_MOD_4_Q3";
    public static final String L_MOD_4_CUESTIONARY = "L_MOD_4_CUESTIONARY";

    public static final String TAG_END_MOD_1 = "end_mod_1";
    public static final String TAG_END_MOD_2 = "end_mod_2";
    public static final String TAG_END_MOD_3 = "end_mod_3";
    public static final String TAG_END_MOD_4 = "end_mod_4";

    public static final String sskd = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d!$%@#£€*?&]{8,}$";
    public static final int PROGRESS_PAGE_RESULTS = 45;
    public static final String EDITING_CONTACTS = "editing_contacts";


    public static final int LOCATION_PERMISSION_CODE = 1;
    public static final int CONTACTS_PERMISSION_CODE = 2;
    public static final int PHONE_PERMISSION_CODE = 3;
    public static final int STORAGE_PERMISSION_CODE = 4;

    public static List<String> getLeadersCourseStringValidation(Context ctx){
        List<String> list = new ArrayList<>();
        for(int indice=0; indice< ctx.getResources().getStringArray(R.array.leaders_validation_chain).length; indice++){
            list.add(ctx.getResources().getStringArray(R.array.leaders_validation_chain)[indice]);
        }
        return list;
    }

//    public static final List<String> LEADERS_COURSE_STRING_VALIDATION = new ArrayList<String>(){{
//        add(get(R.string.lideres_09_03_01));
//        add(get(R.string.lideres_09_03_02));
//        add(get(R.string.lideres_09_03_03));
//        add(get(R.string.lideres_09_03_04));
//        add(get(R.string.lideres_09_08_01));
//        add(get(R.string.lideres_09_08_02));
//        add(get(R.string.lideres_09_08_03));
//        add(get(R.string.lideres_09_13_01));
//        add(get(R.string.lideres_09_13_02));
//        add(get(R.string.lideres_09_13_03));
//    }};

    public static final HashMap<Integer, Integer> MODULES_INDEX = new HashMap<Integer, Integer>(){{
        //put(id_topic_en_server, indice de pagina en VBG_LAYOUT_LIST o en LEADERS_LAYOUT_LIST)
        put(1, 0); //vbg_course_1_0
        put(2, 12); //vbg_course_1_12
        put(3, 16); //vbg_course_1_19
        put(4, 35); //vbg_course_1_47
        put(5, 0); //leaders_1
        put(6, 10); //leaders_12
        put(7, 28); //leaders_32
        put(8, 46); //leaders_51
    }};




    public static final int VBG_COURSE_ID = 1;
    public static final int LEADERS_COURSE_ID = 2;


    //Arrays of constant

    public static final List<Integer> AVATAR_BODY_PARTS_ORDER = new ArrayList<Integer>(){{
        add(3); //Head
        add(5); //Eyes
        add(4); //Nose
        add(2); //Hair
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
        add(new int[]{R.raw.audio_21_m, R.raw.audio_21}); //20


        add(new int[]{R.raw.audio_1_m_leaders, R.raw.audio_1_leaders}); //21
        add(new int[]{R.raw.audio_2_m_leaders, R.raw.audio_2_leaders}); //22
        add(new int[]{R.raw.audio_3_m_leaders, R.raw.audio_3_leaders}); //23
        add(new int[]{R.raw.audio_4_m_leaders, R.raw.audio_4_leaders});//24
        add(new int[]{R.raw.audio_5_m_leaders, R.raw.audio_5_leaders});//25
        add(new int[]{R.raw.audio_6_m_leaders, R.raw.audio_6_leaders});//26
        add(new int[]{R.raw.audio_7_m_leaders, R.raw.audio_7_leaders});//27
        add(new int[]{R.raw.audio_8_m_leaders, R.raw.audio_8_leaders});//28
        add(new int[]{R.raw.audio_9_m_leaders, R.raw.audio_9_leaders});//29
        add(new int[]{R.raw.audio_10_m_leaders, R.raw.audio_10_leaders});//30 cuando_se_trata_mujeres
        add(new int[]{R.raw.audio_11_m_leaders, R.raw.audio_11_leaders});//31 ??????????????????
        add(new int[]{R.raw.audio_12_m_leaders, R.raw.audio_12_leaders});//32 la_secretaria_tecnica
        add(new int[]{R.raw.audio_13_m_leaders, R.raw.audio_13_leaders});//33 liderar_la_politica
        add(new int[]{R.raw.audio_14_m_leaders, R.raw.audio_14_leaders});//34 la_resolucion_805
        add(new int[]{R.raw.audio_15_m_leaders, R.raw.audio_15_leaders});//35 sea_cual_sea_tu_caso
        add(new int[]{R.raw.audio_16_m_leaders, R.raw.audio_16_leaders});//36
        add(new int[]{R.raw.audio_17_m_leaders, R.raw.audio_17_leaders});//37 una_vez_radicado_formulario
        add(new int[]{R.raw.audio_18_m_leaders, R.raw.audio_18_leaders});//38 una_vez_radicada_solicitud
        add(new int[]{R.raw.audio_19_m_leaders, R.raw.audio_19_leaders});//39
        add(new int[]{R.raw.audio_20_m_leaders, R.raw.audio_20_leaders});//40 las_medidas_de_proteccion
        add(new int[]{R.raw.audio_21_m_leaders, R.raw.audio_21_leaders});//41
        add(new int[]{R.raw.audio_22_m_leaders, R.raw.audio_22_leaders});//42
        add(new int[]{R.raw.audio_23_m_leaders, R.raw.audio_23_leaders});//43
        add(new int[]{R.raw.audio_24_m_leaders, R.raw.audio_24_leaders});//44 el_cerrem_podra
        add(new int[]{R.raw.audio_25_m_leaders, R.raw.audio_25_leaders});//45 ???????????????????
        add(new int[]{R.raw.audio_26_m_leaders, R.raw.audio_26_leaders});//46 una_vez_radicada_proteccion_colectiva
        add(new int[]{R.raw.audio_27_m_leaders, R.raw.audio_27_leaders});//47 cuando_el_caso_sea_grupo_solicitudes
        add(new int[]{R.raw.audio_28_m_leaders, R.raw.audio_28_leaders});//48 el_caso_sera_presentado_cerrem
        add(new int[]{R.raw.audio_29_m_leaders, R.raw.audio_29_leaders});//49 estara_a_cargo_direccion

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
        put("existen_varios_tipos", 13);
        put("las_entidades_del_sector", 14);
        put("existen_medidas_de_proteccion", 15);
        put("asistencia_en_salud", 16);
        put("medidas_estabilizacion_economica", 17);
        put("medidas_reparacion_integral", 18);
        put("para_solicitar_la_inscripcion", 19);
        put("ten_presente_que_si_una_entidad", 20);

        put("si_te_preocupas", 21);//1
        put("si_son_defensoras", 22);//2
        put("en_el_contexto", 23);//3
        put("es_el_derecho", 24);//4
        put("si_la_corte_constitucional", 25);//5
        put("cuando_estas_sometido", 26);//6
        put("como_persona_estas_expuesta", 27);//7
        put("es_especifico_y_se_puede", 28);//8
        put("camilo_es_lider", 29);//9
        put("cuando_se_trata_mujeres", 30);//10
        ///////////////////////////////31  - 11
        put("la_secretaria_tecnica", 32);//12
        put("liderar_la_politica", 33);//13
        put("la_resolucion_805", 34);//14
        put("sea_cual_sea_tu_caso", 35);//15
        put("puedes_diligenciar_formulario", 36);//16
        put("una_vez_radicado_formulario", 37);//17
        put("una_vez_radicada_solicitud", 38);//18
        put("cuando_sea_el_caso", 39);//19
        put("las_medidas_de_proteccion", 40);//20
        put("el_director_de_unidad", 41);//21
        put("estudio_nivel_riesgo", 42);//22
        put("en_caso_existir_riesgo", 43);//23
        put("el_cerrem_podra", 44);//24
        ///////////////////////////// 45 - 25
        put("una_vez_radicada_proteccion_colectiva", 46);//26
        put("cuando_el_caso_sea_grupo_solicitudes", 47);//27
        put("el_caso_sera_presentado_cerrem", 48);//28
        put("estara_a_cargo_direccion", 49);//29
//        put("", 48);//28
//        put("", 49);//29

    }};


    public static final List<Integer> COURSES_END_MODULE_BACKGROUNDS = new ArrayList<Integer>(){
        {
            add(R.drawable.bg_modulo_1);
            add(R.drawable.fondo_ros);
            add(R.drawable.fondo_45);
            add(R.drawable.fondo_66);
        }};

        public static final List<Integer> VBG_LAYOUT_LIST = new ArrayList<Integer>(){{
            
            add(R.layout.vbg_course_1_0);
            add(R.layout.vbg_course_1_1);
            add(R.layout.vbg_course_1_2);
            add(R.layout.vbg_course_1_3);
            add(R.layout.vbg_course_1_4);
            add(R.layout.vbg_course_1_5);
            add(R.layout.vbg_course_1_6_5);
            add(R.layout.vbg_course_1_6);
            add(R.layout.vbg_course_1_7);
            add(R.layout.vbg_course_1_8);
            add(R.layout.vbg_course_1_9);
            add(R.layout.vbg_course_1_11);
            add(R.layout.vbg_course_1_12); //12
            add(R.layout.vbg_course_1_13);
            add(R.layout.vbg_course_1_16);
            add(R.layout.vbg_course_1_18);
            add(R.layout.vbg_course_1_19); //16
            add(R.layout.vbg_course_1_20);
            add(R.layout.vbg_course_1_21);
            add(R.layout.vbg_course_1_22);
            add(R.layout.vbg_course_1_23);//20
            add(R.layout.vbg_course_1_24);
            add(R.layout.vbg_course_1_25);
            add(R.layout.vbg_course_1_26);
            add(R.layout.vbg_course_1_27);
            add(R.layout.vbg_course_1_28);
            add(R.layout.vbg_course_1_29);
            add(R.layout.vbg_course_1_30);
            add(R.layout.vbg_course_1_31);
            add(R.layout.vbg_course_1_32);
            add(R.layout.vbg_course_1_33);
            add(R.layout.vbg_course_1_36);
            add(R.layout.vbg_course_1_39);
            add(R.layout.vbg_course_1_42);
            add(R.layout.vbg_course_1_45);
            add(R.layout.vbg_course_1_47);//35
            add(R.layout.vbg_course_1_48);
            add(R.layout.vbg_course_1_48_1);
            add(R.layout.vbg_course_1_49);
            add(R.layout.vbg_course_1_50);
            add(R.layout.vbg_course_1_51);
            add(R.layout.vbg_course_1_52);
            add(R.layout.vbg_course_1_53);
            add(R.layout.vbg_course_1_54);
            add(R.layout.vbg_course_1_55);
            add(R.layout.vbg_course_1_56);
            add(R.layout.vbg_course_1_57);
            add(R.layout.vbg_course_1_58);
            add(R.layout.vbg_course_1_59);
            add(R.layout.vbg_course_1_60);
            add(R.layout.vbg_course_1_61);
            add(R.layout.vbg_course_1_62);
            add(R.layout.vbg_course_1_63);
            add(R.layout.vbg_course_1_64);
            add(R.layout.vbg_course_1_66);

    }};

    public static final List<Integer> LEADERS_LAYOUT_LIST = new ArrayList<Integer>(){
        {
            add(R.layout.leaders_1); //0
            add(R.layout.leaders_2);
            add(R.layout.leaders_3);
            add(R.layout.leaders_4);
            add(R.layout.leaders_5);
            add(R.layout.leaders_6);
            add(R.layout.leaders_7);
            add(R.layout.leaders_8);
            add(R.layout.leaders_9);
            add(R.layout.leaders_11);
            add(R.layout.leaders_12);//10
            add(R.layout.leaders_13);
            add(R.layout.leaders_14);
            add(R.layout.leaders_15);
            add(R.layout.leaders_16);
            add(R.layout.leaders_17);
            add(R.layout.leaders_18);
            add(R.layout.leaders_19);
            add(R.layout.leaders_22);
            add(R.layout.leaders_23);
            add(R.layout.leaders_24);//20
            add(R.layout.leaders_24_1);//21
            add(R.layout.leaders_25);
            add(R.layout.leaders_26);
            add(R.layout.leaders_27);
            add(R.layout.leaders_28);
            add(R.layout.leaders_29);
            add(R.layout.leaders_31);
            add(R.layout.leaders_32);//28
            add(R.layout.leaders_33);
            add(R.layout.leaders_34);
            add(R.layout.leaders_35);//31
            add(R.layout.leaders_36);
            add(R.layout.leaders_37);
            add(R.layout.leaders_38);
            add(R.layout.leaders_39);
            add(R.layout.leaders_40);
            add(R.layout.leaders_41);
            add(R.layout.leaders_42);
            add(R.layout.leaders_43);
            add(R.layout.leaders_44);
            add(R.layout.leaders_45);//41
            add(R.layout.leaders_46);
            add(R.layout.leaders_47);
            add(R.layout.leaders_48);
            add(R.layout.leaders_50);
            add(R.layout.leaders_51);//46
            add(R.layout.leaders_52);
            add(R.layout.leaders_52_1);
            add(R.layout.leaders_53);
            add(R.layout.leaders_54);
            add(R.layout.leaders_55);
            add(R.layout.leaders_56);
            add(R.layout.leaders_57);
            add(R.layout.leaders_58);
            add(R.layout.leaders_59);
            add(R.layout.leaders_60);
            add(R.layout.leaders_61);
            add(R.layout.leaders_62);
            add(R.layout.leaders_64);
        }};

    public static final List<Integer> VBG_PROTECION_PATH_LAYOUT_LIST = new ArrayList<Integer>(){
        {
            add(R.layout.protection_path_vbg_1);
            add(R.layout.protection_path_vbg_2);
            add(R.layout.protection_path_vbg_3);
            add(R.layout.protection_path_vbg_4);
            add(R.layout.protection_path_vbg_5);
            add(R.layout.protection_path_vbg_6);
        }};

    public static final List<Integer> LEADERS_PROTECION_PATH_LAYOUT_LIST = new ArrayList<Integer>(){
        {
            add(R.layout.protection_path_leaders_1_1);
            add(R.layout.protection_path_leaders_1_2);
            add(R.layout.protection_path_leaders_1_3);
            add(R.layout.protection_path_leaders_1_4);
            add(R.layout.protection_path_leaders_1_5);
            add(R.layout.protection_path_leaders_1_6);
            add(R.layout.protection_path_leaders_1_7);
        }};

    public static final List<Integer> LEADERS_PROTECION_PATH_LAYOUT_LIST_2 = new ArrayList<Integer>(){
        {
            add(R.layout.protection_path_leaders_2_8);
            add(R.layout.protection_path_leaders_2_9);
            add(R.layout.protection_path_leaders_2_10);
            add(R.layout.protection_path_leaders_2_11);
            add(R.layout.protection_path_leaders_2_12);
        }};



    public static final List<Pair> TEMPLATE_LIBRARY_LIST = new ArrayList<Pair>(){{

        add(new Pair("Acción de tutela", "accion_de_tutela.docx"));
        add(new Pair("Derecho de petición Alcaldías y Gobernaciones","derecho_de_peticion_alcaldias_y_gobernaciones.docx" ));
        add(new Pair("Derecho de petición UNP", "derecho_de_peticion_unp.docx"));
        add(new Pair("Formulario de inscripción UNP para la Ruta de Protección Colectiva","formulario_inscripcion_unp_para_ruta_proteccion_colectiva.pdf" ));
        add(new Pair("Formulario de inscripciòn UNP Protección Individual", "formulario_inscripcion_unp_proteccion_individual.pdf"));
        add(new Pair("Recurso de reposición y apelación", "recurso_de_reposicion_y_apelacion.docx"));
    }};


    public static final List<String> LEARNING_MY_COMMUNITY_LIST = new ArrayList<String>(){{
        add("auditoria_vbg.pdf");
        add("auditoria_lideres.pdf");
    }};

    public static List<Models.LearningItem> getLearningMyCommunityList(Context ctx){

        List<Models.LearningItem> list = new ArrayList<>();
        for (int endice =0; endice< LEARNING_MY_COMMUNITY_LIST.size();endice++){
            list.add(new Models.LearningItem(ctx.getResources().getStringArray(R.array.about_my_community)[endice], LEARNING_MY_COMMUNITY_LIST.get(endice)));
        }

        return list;
    }

}
