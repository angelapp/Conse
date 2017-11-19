package Utils;

import android.app.ProgressDialog;

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
        public RegisterUser(OnRequestCompleted listener, ProgressDialog loader, int taskId, Models.RegisterUserProfileModel user){
            super(listener, loader, taskId);
            setUrl(LocalConstants.SERVER_DOMAIN + LocalConstants.API_DIRECTORY + LocalConstants.POST_USER_PROFILE );

            setRequest(user);
            setResponse(new Models.RegisterUserResponse());
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
