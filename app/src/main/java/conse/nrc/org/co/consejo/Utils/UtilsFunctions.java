package conse.nrc.org.co.consejo.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import conse.nrc.org.co.consejo.R;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.location.LocationManager.GPS_PROVIDER;
import static android.location.LocationManager.NETWORK_PROVIDER;
import static java.util.Calendar.DATE;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

/**
 * Created by apple on 11/7/16.
 */

public abstract class UtilsFunctions {

    public static void saveSharedString(Context ctx, String key, String value){
        SharedPreferences preferences = ctx.getSharedPreferences(LocalConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        preferences.edit().putString(key, value).apply();
    }

    public static void saveSharedInteger(Context ctx, String key, int value){
        SharedPreferences preferences = ctx.getSharedPreferences(LocalConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        preferences.edit().putInt(key, value).apply();
    }

    public static void saveSharedBoolean(Context ctx, String key, boolean value){
        SharedPreferences preferences = ctx.getSharedPreferences(LocalConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        preferences.edit().putBoolean(key, value).apply();
    }

    public static void saveSharedObject(Context ctx, String key, Object object){
        SharedPreferences preferences = ctx.getSharedPreferences(LocalConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = gson.toJson(object);
        preferences.edit().putString(key, json).apply();
    }

    public static String getSharedString(Context ctx, String key){
        SharedPreferences preferences = ctx.getSharedPreferences(LocalConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return preferences.getString(key, null);
    }


    public static int getSharedInteger(Context ctx, String key){
        SharedPreferences preferences = ctx.getSharedPreferences(LocalConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return preferences.getInt(key, 0);
    }


    public static boolean getSharedBoolean(Context ctx, String key){
        SharedPreferences preferences = ctx.getSharedPreferences(LocalConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }

    public static <GenericClass> GenericClass getSavedObjectFromPreference(Context ctx, String preferenceKey, Class<GenericClass> classType) {
        SharedPreferences preferences = ctx.getSharedPreferences(LocalConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        if (preferences.contains(preferenceKey)) {
            final Gson gson = new Gson();
            return gson.fromJson(preferences.getString(preferenceKey, ""), classType);
        }
        return null;
    }

    public static void deleteKeySharedPreferences(Context ctx, String key){
        SharedPreferences preferences = ctx.getSharedPreferences(LocalConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        preferences.edit().remove(key).commit();
    }



    public static void resetSharedPreferences(Context ctx){
        SharedPreferences preferences = ctx.getSharedPreferences(LocalConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        preferences.edit().clear().apply();
    }

    public static boolean checkRegEx(String string, String regEx){
        Pattern pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher dateMatcher = pattern.matcher(string);
        return dateMatcher.find();
    }
    public static String getDate(long milliSeconds){

        String dateFormat = "yyyy-MM-dd'T'hh:mm:ss.SSSSZ";
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }


    public static Date getDateFromString(String dateString){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return convertedDate;
    }

    public static int getYearsElapsedUntilNow(String stringDate){
        Calendar a = getCalendar(getDateFromString(stringDate));
        Calendar b = Calendar.getInstance();
        Log.d("Calculating Age", "BIRTH: " + a.get(DAY_OF_MONTH) + "/" + a.get(MONTH) + "/" + a.get(Calendar.YEAR) + "\n" +
                                a.get(DATE) + "\n" +
                                " NOW: " + b.get(DAY_OF_MONTH) + "/" + b.get(MONTH) + "/" + b.get(Calendar.YEAR) + "\n" +
                                b.get(DATE));
        int diff = b.get(YEAR) - a.get(YEAR);
        if (a.get(MONTH) > b.get(MONTH) ||
                (a.get(MONTH) == b.get(MONTH) && a.get(DATE) > b.get(DATE))) {
            diff--;
        }
        return diff;
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTime(date);
        return cal;
    }


    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
    public static String getEmegencyContactsString(Context mCtx) {

        int contacts_size = UtilsFunctions.getSharedInteger(mCtx, LocalConstants.CONTACT_SIZE);
        int i = 1;
        String emergencyContactsString = "";

        for(i = 1; i <= contacts_size; i++){
            emergencyContactsString = emergencyContactsString
                    + ";" + UtilsFunctions.getSharedString(mCtx, LocalConstants.CONTACT_NUMBER_ + String.valueOf(i));
            Log.d("Alert", UtilsFunctions.getSharedString(mCtx, LocalConstants.CONTACT_NUMBER_ + String.valueOf(i)));
        }

        return emergencyContactsString;
    }

    public static float[] requestCoordinates(Context mCtx) {
//        String recipients = LocalConstants.SMS_CONTACT_PREFIX + getEmegencyContactsString();
//        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse(recipients));
        LocationManager mLocManager = (LocationManager) mCtx.getSystemService(Context.LOCATION_SERVICE);
        float[] list = new float[2];
        if (ActivityCompat.checkSelfPermission(mCtx,
                ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            Location location = mLocManager.getLastKnownLocation(GPS_PROVIDER);
            Location wifiLocation = mLocManager.getLastKnownLocation(NETWORK_PROVIDER);
            try {
                list[0] = (float)location.getLatitude();
                list[1] = (float)location.getLongitude();
                return list;

            }catch (Exception ea){
                ea.printStackTrace();
                try {
                    list[0] = (float)wifiLocation.getLatitude();
                    list[1] = (float)wifiLocation.getLongitude();
                    return list;
                }catch (Exception eae){
                    eae.printStackTrace();
                }
            }

        }
        return list;
    }

    public static String getConseGenderTittle(Context ctx, int mod){
        if(ConseApp.getActualUser(ctx).profile != null){
            if (ConseApp.getActualUser(ctx).profile.gender.id == LocalConstants.MALE){
                return ctx.getResources().getStringArray(R.array.conse_end_mod_tittles_male)[mod-1];
            }
            else if (ConseApp.getActualUser(ctx).profile.gender.id == LocalConstants.FEMALE){
                return ctx.getResources().getStringArray(R.array.conse_end_mod_tittles_female)[mod-1];
            }
            else if (ConseApp.getActualUser(ctx).profile.gender.id == LocalConstants.TRANSGEN){
                return ctx.getResources().getStringArray(R.array.conse_end_mod_tittles_female)[mod-1];
            }
        }
        return ctx.getResources().getStringArray(R.array.conse_end_mod_tittles_female)[mod-1];
    }


}
