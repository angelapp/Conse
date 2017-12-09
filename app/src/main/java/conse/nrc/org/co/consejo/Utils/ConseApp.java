package conse.nrc.org.co.consejo.Utils;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 11/18/17.
 */

public class ConseApp extends Application {

    public static Models.ApplicationConfiguration appConfiguration = new Models.ApplicationConfiguration();
    public static Models.RegisterUserResponse user = new Models.RegisterUserResponse();

    @Override
    public void onCreate(){

    }

    public static void setAppConfiguration(Context ctx, Models.ApplicationConfiguration conf){
        UtilsFunctions.saveSharedObject(ctx, LocalConstants.APP_CONFIGURATION, conf);
        appConfiguration = conf;
    }

    public static void setActualUser(Context ctx, Models.RegisterUserResponse userResponse){
        UtilsFunctions.saveSharedObject(ctx, LocalConstants.USER_DATA, userResponse);
        user = userResponse;
    }

    public static Models.RegisterUserResponse getActualUser(Context ctx){
        user = UtilsFunctions.getSavedObjectFromPreference(ctx, LocalConstants.USER_DATA, Models.RegisterUserResponse.class);
        return user;
    }

    public static Models.ApplicationConfiguration getAppConfiguration(Context ctx){
        appConfiguration = UtilsFunctions.getSavedObjectFromPreference(ctx, LocalConstants.APP_CONFIGURATION, Models.ApplicationConfiguration.class);
        return appConfiguration;
    }

    public static List<Models.UserAvatar> getUserAvatarList(Context ctx){
        List<Models.UserAvatar> userAvatarsList = new ArrayList<>();

        for(int i = 1; i<=5; i++){
            userAvatarsList.add(new Models.UserAvatar(user.user.id,
                    UtilsFunctions.getSharedInteger(ctx, LocalConstants.AVATAR_SELECTED_PART_+String.valueOf(i))));
        }

        return userAvatarsList;

    }

}
