package conse.nrc.org.co.consejo.Utils;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import conse.nrc.org.co.consejo.R;

/**
 * Created by apple on 11/18/17.
 */

public class ConseApp extends Application {

    public static Models.ApplicationConfiguration appConfiguration;
    public static Models.RegisterUserResponse user;

    private com.nostra13.universalimageloader.core.ImageLoader imageLoader;
    DisplayImageOptions options;

    @Override
    public void onCreate(){

        imageLoader = imageLoader.getInstance();

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(null)
                .showImageForEmptyUri(null)
                .showImageOnFail(null)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

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

        for(int i : LocalConstants.AVATAR_BODY_PARTS_ORDER){
            try {
                userAvatarsList.add(new Models.UserAvatar(user.user.id,
                        UtilsFunctions.getSharedInteger(ctx, LocalConstants.AVATAR_SELECTED_PART_ + String.valueOf(i))));
            } catch (Exception ea){
                userAvatarsList.add(null);
                ea.printStackTrace();
            }
        }

        return userAvatarsList;

    }

    public static FrameLayout getAvatarFrame(Context ctx, ImageLoader imageLoader, DisplayImageOptions options){


        List<Models.UserAvatar> userAvatars = getUserAvatarList(ctx);
        final LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View avatarLayer = inflater.inflate(R.layout.avatar_layout, null, false);
        FrameLayout frameLayout = (FrameLayout) avatarLayer.findViewById(R.id.fl_avatar);
        int i = 0;
        for (Models.UserAvatar avatar: userAvatars){
            if (avatar != null && avatar.avatar_piece != 0) {
                Models.AvatarPiece avatarPiece = ConseApp.appConfiguration.getAvatarPieceById(avatar.avatar_piece);
                Log.d("Avatar", "Avatar id: " + avatar.avatar_piece);
                imageLoader.displayImage(ConseApp.appConfiguration.getAvatarPieceById(avatar.avatar_piece).icon,
                        (ImageView) frameLayout.findViewWithTag(String.valueOf(avatarPiece.body_part)), options);
            }
            i++;
        }

        return frameLayout;
    }

    public static void clearAvatar(Context ctx){

        for(int i = 1; i<=5; i++){
            UtilsFunctions.deleteKeySharedPreferences(ctx, LocalConstants.AVATAR_SELECTED_PART_ + String.valueOf(i));
            i++;
        }

    }

    public static int getAvatarGender(Context ctx){
        return UtilsFunctions.getSharedInteger(ctx, LocalConstants.AVATAR_GENDER_ID_);
    }

}
