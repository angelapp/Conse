package conse.nrc.org.co.consejo.Utils;

import android.app.ProgressDialog;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import static conse.nrc.org.co.consejo.Utils.LocalConstants.LATITUDE;
import static conse.nrc.org.co.consejo.Utils.LocalConstants.LONGITUDE;

/**
 * Created by apple on 11/19/16.
 */

public class ServerRequest {

    public static class GetAppConfiguration extends RequestTask{
        public GetAppConfiguration(OnRequestCompleted listener, ProgressDialog loader, int taskId){
            super(listener, loader, taskId);
            setUrl(LocalConstants.SERVER_DOMAIN + LocalConstants.API_DIRECTORY + LocalConstants.GET_APP_CONFIGURATION );
            setResponse(new Models.ApplicationConfiguration());
        }
    }

    public static class RegisterUser extends RequestTask{
        public RegisterUser(Context ctx,OnRequestCompleted listener, ProgressDialog loader, int taskId, Models.RegisterUserProfileModel user){
            super(ctx, listener, loader, taskId);
            setUrl(LocalConstants.SERVER_DOMAIN + LocalConstants.API_DIRECTORY + LocalConstants.POST_USER_PROFILE );

            setRequest(user);
            setResponse(new Models.RegisterUserResponse());
        }

    }

    public static class PasswordRecovery extends RequestTask{
        public PasswordRecovery(Context ctx,OnRequestCompleted listener, ProgressDialog loader, int taskId, Models.RegisterUserProfileModel user){
            super(ctx, listener, loader, taskId);
            setUrl(LocalConstants.SERVER_DOMAIN + LocalConstants.API_DIRECTORY + LocalConstants.POST_PASSWORD_RECOVERY );
            setRequest(user);
            setResponse(new Models.SimpleResponseModel());
        }

    }

    public static class LogginUser extends RequestTask{
        public LogginUser(Context ctx,OnRequestCompleted listener, ProgressDialog loader, int taskId, Models.RegisterUserProfileModel user){
            super(ctx, listener, loader, taskId);
            setUrl(LocalConstants.SERVER_DOMAIN + LocalConstants.API_DIRECTORY + LocalConstants.POST_USER_LOGGIN);
            setRequest(user);
            setResponse(new Models.RegisterUserResponse());
        }

    }

    public static class UpdateUserProfile extends RequestTask{
        public UpdateUserProfile(Context ctx, OnRequestCompleted listener, ProgressDialog loader, int taskId, Models.RegisterUserProfileModel user){
            super(ctx, listener, loader, taskId);
            setUrl(LocalConstants.SERVER_DOMAIN + LocalConstants.API_DIRECTORY +
                    LocalConstants.PUT_USER_PROFILE_EDIT + ConseApp.getActualUser(ctx).profile.id +"/");
            setRequest(user);
            setResponse(new Models.RegisterUserResponse());
        }

    }

    public static class PostContactForm extends RequestTask{
        public PostContactForm(Context ctx, OnRequestCompleted listener, ProgressDialog loader, int taskId, Models.ContactForm form){
            super(ctx, listener, loader, taskId);
            setUrl(LocalConstants.SERVER_DOMAIN + LocalConstants.API_DIRECTORY + LocalConstants.POST_CONTACT_FORM );

            setRequest(form);
            setResponse(new Models.ContactForm());
        }

    }

    public static class PostUserAvatarPieces extends RequestTask{
        public PostUserAvatarPieces(Context ctx, OnRequestCompleted listener, ProgressDialog loader, int taskId, List<Models.UserAvatar> avatarList){
            super(ctx, listener, loader, taskId);
            setUrl(LocalConstants.SERVER_DOMAIN + LocalConstants.API_DIRECTORY + LocalConstants.POST_AVATAR_LIST );

            setRequest(avatarList);
            setResponse(new ArrayList<Models.UserAvatar>());
        }

    }

    public static class GetLibraryDocs extends RequestTask{
        public GetLibraryDocs(Context ctx, OnRequestCompleted listener, ProgressDialog loader, int taskId){
            super(ctx, listener, loader, taskId);
            setUrl(LocalConstants.SERVER_DOMAIN + LocalConstants.API_DIRECTORY + LocalConstants.GET_LIBRARY_DOCS );
            Models.DocumentTextType[] list = new Models.DocumentTextType[]{};
            setResponse(list);
        }
    }

    public static class GetNews extends RequestTask{
        public GetNews(Context ctx, OnRequestCompleted listener, ProgressDialog loader, int taskId){
            super(ctx, listener, loader, taskId);
            setUrl(LocalConstants.SERVER_DOMAIN + LocalConstants.API_DIRECTORY + LocalConstants.GET_NEWS );
            Models.NewsCategory[] list = new Models.NewsCategory[]{};
            setResponse(list);
        }
    }

    public static class GetShieldList extends RequestTask{
        public GetShieldList(Context ctx, OnRequestCompleted listener, ProgressDialog loader, int taskId, float lat, float lng){
            super(ctx, listener, loader, taskId);
            if (lat != 0 && lng != 0) {
                setUrl(LocalConstants.SERVER_DOMAIN +
                        LocalConstants.API_DIRECTORY +
                        LocalConstants.GET_SHIELDS + "?" +
                LATITUDE + String.valueOf(lat) + "&" +
                LONGITUDE + String.valueOf(lng));
            } else {
                setUrl(LocalConstants.SERVER_DOMAIN + LocalConstants.API_DIRECTORY + LocalConstants.GET_SHIELDS);
            }
            Models.OrganizationType[] list = new Models.OrganizationType[]{};
            setResponse(list);
        }
    }

    public static class PostUserActivityProgress extends RequestTask{
        public PostUserActivityProgress(Context ctx, OnRequestCompleted listener,
                                        ProgressDialog loader, int taskId, List<Models.UserActivityProgress> userActivityProgressList){
            super(ctx, listener, loader, taskId);
            setUrl(LocalConstants.SERVER_DOMAIN + LocalConstants.API_DIRECTORY + LocalConstants.POST_USER_PROGRESS_LIST );

            setRequest(userActivityProgressList);
            setResponse(new ArrayList<Models.UserActivityProgress>());
        }

    }

//    public static class LogginActivity extends RequestTask{
//        public LogginActivity(OnRequestCompleted listener,ProgressDialog loader, int taskId, String email, String password ){
//            super(listener, loader, taskId);
//            setUrl(LocalConstants.URL + LocalConstants.SERVER_CUSTOMER_OPERATIONS + LocalConstants.SERVER_LOGGIN_TASK );
//            setRequest(new DataModels.LogginUserModel(email, password));
//            setResponse(new DataModels.RegisterLogginUserResponse());
//        }
//    }
//
//    public static class GetProducts extends RequestTask{
//
//        public GetProducts(OnRequestCompleted listener, ProgressDialog loader, int taskId) {
//            super(listener, loader, taskId);
//            setUrl(LocalConstants.URL + LocalConstants.SERVER_GET_PRODUCT_LIST_TASK);
//            setResponse(new DataModels.ProductsCategoriesResponse());
//        }
//    }
//
//    public static class GetSearchProducts extends RequestTask{
//
//        private String urlParams = "?search=";
//
//        public GetSearchProducts(OnRequestCompleted listener, ProgressDialog loader, int taskId, String term) {
//            super(listener, loader, taskId);
//            setUrl(LocalConstants.URL + LocalConstants.SERVER_PRODUCTS_OPERATIONS
//                    + LocalConstants.SERVER_GET_SEARCH_PRODUCT_TASK + getUrlComplement(term));
//            setResponse(new DataModels.ProductsCategoriesResponse());
//        }
//
//        private String getUrlComplement(String term) {
//            return urlParams+term;
//        }
//    }
//
//    public static class GetUserProfile extends RequestTask{
//
//        private String urlParams = "?id=";
//
//        public GetUserProfile(OnRequestCompleted listener, ProgressDialog loader, int taskId, String userToken) {
//            super(listener, loader, taskId);
//            setUrl(LocalConstants.URL + LocalConstants.SERVER_CUSTOMER_OPERATIONS + LocalConstants.SERVER_USER_PROFILE_TASK + getUrlComplement(userToken));
//            setResponse(new DataModels.RegisterLogginUserResponse());
//        }
//
//        private String getUrlComplement(String userToken) {
//            return urlParams+userToken;
//        }
//    }
//
//    public static class MakeOrder extends RequestTask{
//
//        public MakeOrder(OnRequestCompleted listener, ProgressDialog loader, int taskId, DataModels.SetOrderModel orderDetail) {
//            super(listener, loader, taskId);
//            setUrl(LocalConstants.URL + LocalConstants.SERVER_ORDER_OPERATIONS + LocalConstants.SERVER_CREATE_ORDER_TASK);
//            setRequest(orderDetail);
//            setResponse(new DataModels.OrderDetailModel());
//        }
//    }
//
//    public static class UpdateUserData extends RequestTask{
//
//        public UpdateUserData(OnRequestCompleted listener, ProgressDialog loader, int taskId, DataModels.UserModel data) {
//            super(listener, loader, taskId);
//            setUrl(LocalConstants.URL + LocalConstants.SERVER_CUSTOMER_OPERATIONS + LocalConstants.SERVER_UPDATE_USER_DATA_TASK);
//            setRequest(data);
//            setResponse(new DataModels.UpdateResponseModel());
//        }
//    }
//
//    public static class SetFavoriteProduct extends RequestTask{
//
//        public SetFavoriteProduct(OnRequestCompleted listener, ProgressDialog loader, int taskId, DataModels.PostFavoriteProductModel data) {
//            super(listener, loader, taskId);
//            setUrl(LocalConstants.URL + LocalConstants.SERVER_PRODUCTS_OPERATIONS + LocalConstants.SERVER_SET_FAVORITE_PRODUCT);
//            setRequest(data);
//            setResponse(new DataModels.UpdateResponseModel());
//
//        }
//    }
//
//    public static class DeleteFavoriteProduct extends RequestTask{
//
//        public DeleteFavoriteProduct(OnRequestCompleted listener, ProgressDialog loader, int taskId, DataModels.PostFavoriteProductModel data) {
//            super(listener, loader, taskId);
//            setUrl(LocalConstants.URL + LocalConstants.SERVER_PRODUCTS_OPERATIONS + LocalConstants.SERVER_DELETE_FAVORITE_PRODUCT);
//            setRequest(data);
//            setResponse(new DataModels.UpdateResponseModel());
//
//        }
//    }
//
//    public static class InformProductAvailability extends RequestTask{
//
//        public InformProductAvailability(OnRequestCompleted listener, ProgressDialog loader, int taskId, DataModels.PostProductInformAvailabilityModel data) {
//            super(listener, loader, taskId);
//            setUrl(LocalConstants.URL + LocalConstants.SERVER_PRODUCTS_OPERATIONS + LocalConstants.SERVER_POST_PRODUCT_INFORM_AVAILABILITY_TASK);
//            setRequest(data);
//            setResponse(new DataModels.UpdateResponseModel());
//
//        }
//    }
//
//    public static class CreateUserDirection extends RequestTask{
//
//        public CreateUserDirection(OnRequestCompleted listener, ProgressDialog loader, int taskId, DataModels.DirectionForCreateModel data) {
//            super(listener, loader, taskId);
//            setUrl(LocalConstants.URL + LocalConstants.SERVER_CUSTOMER_OPERATIONS + LocalConstants.SERVER_CREATE_DIRECTION_TASK);
//            setRequest(data);
//            setResponse(new DataModels.UpdateResponseModel());
//        }
//    }
//
//    public static class EditUserDirection extends RequestTask{
//
//        public EditUserDirection(OnRequestCompleted listener, ProgressDialog loader, int taskId, DataModels.DirectionForCreateModel data) {
//            super(listener, loader, taskId);
//            setUrl(LocalConstants.URL + LocalConstants.SERVER_CUSTOMER_OPERATIONS + LocalConstants.SERVER_EDIT_DIRECTION_TASK);
//            setRequest(data);
//            setResponse(new DataModels.UpdateResponseModel());
//        }
//    }
//
//    public static class DeleteUserDirection extends RequestTask{
//
//        public DeleteUserDirection(OnRequestCompleted listener, ProgressDialog loader, int taskId, DataModels.DirectionForCreateModel data) {
//            super(listener, loader, taskId);
//            setUrl(LocalConstants.URL + LocalConstants.SERVER_CUSTOMER_OPERATIONS + LocalConstants.SERVER_DELETE_DIRECTION_TASK);
//            setRequest(data);
//            setResponse(new DataModels.UpdateResponseModel());
//        }
//    }
//
//    public static class PasswordRecoveryActivity extends RequestTask{
//        public PasswordRecoveryActivity(OnRequestCompleted listener, ProgressDialog loader, int taskId, DataModels.PasswordRecoveryModel user){
//            super(listener, loader, taskId);
//            setUrl(LocalConstants.URL + LocalConstants.SERVER_CUSTOMER_OPERATIONS + LocalConstants.SERVER_RECOVER_PASSWORD_TASK );
//            setRequest(user);
//            setResponse(new DataModels.PasswordRecoveryResponse());
//        }
//    }
//
//    public static class GetAvailablesZone extends RequestTask{
//        public GetAvailablesZone(OnRequestCompleted listener, ProgressDialog loader, int taskId){
//            super(listener, loader, taskId);
//            setUrl(LocalConstants.URL + LocalConstants.SERVER_CUSTOMER_OPERATIONS + LocalConstants.SERVER_GET_AVAILABLES_ZONE );
//            setResponse(new DataModels.AvailablesZone());
//        }
//    }
//
//    public static class CheckPointAvailablesZone extends RequestTask{
//        public CheckPointAvailablesZone(OnRequestCompleted listener, ProgressDialog loader, int taskId, DataModels.ValidationAvailableZone info){
//            super(listener, loader, taskId);
//            setUrl(LocalConstants.URL + LocalConstants.SERVER_CUSTOMER_OPERATIONS + LocalConstants.SERVER_POST_CHECK_AVAILABLES_ZONE_POINT );
//            setRequest(info);
//            setResponse(new DataModels.ValidationAvailableZoneResponse());
//        }
//    }
}
