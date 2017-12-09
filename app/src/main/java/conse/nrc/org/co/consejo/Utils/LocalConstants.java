package conse.nrc.org.co.consejo.Utils;

import conse.nrc.org.co.consejo.BuildConfig;

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

    public static final int CONTACT_NUMBER = 2;

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


    //Task id for server views
    public static final int GET_APP_CONF_TASK_ID = 19;
    public static final int REGISTER_USER_TASK_ID = 20;
    public static final int LOGGIN_USER_TASK_ID = 21;
    public static final int CONTACT_FORM_TASK_ID = 22;
    public static final int POST_AVATAR_LIST_TASK_ID = 23;
    public static final int POST_USER_PROGRESS_LIST_TASK_ID = 24;


}
