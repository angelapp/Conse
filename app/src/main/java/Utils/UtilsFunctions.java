package Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static void resetSharedPreferences(Context ctx){
        SharedPreferences preferences = ctx.getSharedPreferences(LocalConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        preferences.edit().clear().apply();
    }

    public static boolean checkRegEx(String string, String regEx){
        Pattern pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher dateMatcher = pattern.matcher(string);
        return dateMatcher.find();
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


}