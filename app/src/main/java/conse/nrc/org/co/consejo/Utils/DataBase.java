package conse.nrc.org.co.consejo.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 12/8/17.
 */

public class DataBase extends SQLiteOpenHelper {

    public static Context ctx;
    private final String TAG = this.getClass().getSimpleName();

    private static DataBase instance = new DataBase(ctx);

    //Database Name
    private static String DB_NAME = "CONSE_DB";

    //Tables Name
    private String USER_TOPIC_PROGRESS = "USER_TOPIC_PROGRESS";
    private String USER_COURSE_PAGE_VIEWS = "USER_COURSE_PAGE_VIEWS";
    private String USER_PROTECTION_PATHS_PAGE_VIEWS = "USER_PROTECTION_PATHS_PAGE_VIEWS";

    //USER_TOPIC_PROGRESS Columns
    public String topic_activity = "topic_activity";
    public String date_completed = "date_completed";
    public String send_to_server = "send_to_server";

    //USER_COURSE_PAGE_VIEWS Columns
    public String course_id = "course_id";
    public String last_page_read = "last_page_read";

    //Scripts Create Tables
    private String queryCreateTopicActivity = "CREATE TABLE " + USER_TOPIC_PROGRESS + " ("
            + topic_activity + " INTEGER, " + date_completed + " TEXT, " + send_to_server + " INTEGER )";

    private String queryCreateCoursePages = "CREATE TABLE " + USER_COURSE_PAGE_VIEWS + " ("
            + course_id + " INTEGER, " + last_page_read + " INTEGER)";


    private String queryCreateProtectionPages = "CREATE TABLE " + USER_PROTECTION_PATHS_PAGE_VIEWS + " ("
            + course_id + " INTEGER, " + last_page_read + " INTEGER)";

    //Scripts Delete
    public String queryDeleteTopicActivity =
            "DELETE FROM " + USER_TOPIC_PROGRESS;

    public String queryDeleteUserCourseReadPages =
            "DELETE FROM " + USER_COURSE_PAGE_VIEWS;

    public String queryDeleteUserProtectionReadPages =
            "DELETE FROM " + USER_PROTECTION_PATHS_PAGE_VIEWS;


    //Scripts to mark activity as sent
    private String scriptMarkActivityAsSent(int topic_activity) {
        return "UPDATE " + USER_TOPIC_PROGRESS + " SET " + send_to_server + "= 1"
                + " WHERE " + this.topic_activity + " = " + String.valueOf(topic_activity);
    }

    //Scripts to update page read for an course
    private String scriptUpdateLastPageReadForCourse(int course, int page) {
        return "UPDATE " + USER_COURSE_PAGE_VIEWS + " SET " + last_page_read + "= " + String.valueOf(page)
                + " WHERE " + course_id + " = " + String.valueOf(course);
    }

    //Scripts to update page read for an protection path
    private String scriptUpdateLastPageReadForProtectionPath(int course, int page) {
        return "UPDATE " + USER_PROTECTION_PATHS_PAGE_VIEWS + " SET " + last_page_read + "= " + String.valueOf(page)
                + " WHERE " + course_id + " = " + String.valueOf(course);
    }

    //Query for get activity
    private String queryTopicActivity(int topic_activity) {
        return "SELECT * FROM " + USER_TOPIC_PROGRESS +
                " WHERE " + this.topic_activity + " = " + String.valueOf(topic_activity);
    }

    //Query for get last page for course
    private String queryLastPageForCourse(int course) {
        return "SELECT * FROM " + USER_COURSE_PAGE_VIEWS +
                " WHERE " + this.course_id + " = " + String.valueOf(course);
    }

    //Query for get last page for course
    private String queryLastPageForProtectionPath(int course) {
        return "SELECT * FROM " + USER_PROTECTION_PATHS_PAGE_VIEWS +
                " WHERE " + this.course_id + " = " + String.valueOf(course);
    }

    private String queryTopicActivities() {
        return "SELECT * FROM " + USER_TOPIC_PROGRESS;
    }

    private String queryTopicActivitiesNotSent() {
        return "SELECT * FROM " + USER_TOPIC_PROGRESS + " WHERE " + send_to_server + " = 0";
    }


    public DataBase(Context context) {
        super(context, DB_NAME, null, 1);
    }

    public static DataBase getInstance(Context context) {
        ctx = context;
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(queryCreateTopicActivity);
        sqLiteDatabase.execSQL(queryCreateCoursePages);
        sqLiteDatabase.execSQL(queryCreateProtectionPages);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(queryCreateTopicActivity);
        sqLiteDatabase.execSQL(queryCreateCoursePages);
        sqLiteDatabase.execSQL(queryCreateProtectionPages);
    }


    //Metodo para Crear registro del producto
    public void insertTopicActivity(Models.UserActivityProgress topic_activity) {
        Cursor cursor = this.getReadableDatabase().rawQuery(queryTopicActivity(topic_activity.topic_activity), null);
        if (!cursor.moveToFirst()) {
            ContentValues values = new ContentValues();
            values.put(this.topic_activity, topic_activity.topic_activity);
            values.put(this.date_completed, topic_activity.date_completed);
            values.put(this.send_to_server, 0);
            long id = this.getWritableDatabase().insert(USER_TOPIC_PROGRESS, null, values);
            Log.d(TAG, " PRODUCT REGISTER: ROW: " + id + " TOPIC_ACTIVITY: "
                    + topic_activity + " DATE: " + topic_activity.date_completed);
        }
        cursor.close();
    }

    //Metodo para insertar/actualizar ùltima pàgina para cierto curso
    public void insertUpdateLastPage(int course, int page) {
        Cursor cursor = this.getReadableDatabase().rawQuery(queryLastPageForCourse(course), null);
        if (!cursor.moveToFirst()) {
            ContentValues values = new ContentValues();
            values.put(course_id, course);
            values.put(last_page_read, page);
            long id = this.getWritableDatabase().insert(USER_COURSE_PAGE_VIEWS, null, values);
            Log.d(TAG, " PAGE REGISTER: ROW: " + id + " COURSE: "
                    + course + " PAGE: " + page);
        } else {
            this.getWritableDatabase().execSQL(scriptUpdateLastPageReadForCourse(course, page));
        }
        cursor.close();
    }

    //Metodo para insertar/actualizar ùltima pàgina para cierto ruta de protecci[on
    public void insertUpdateLastPageProtectionPath(int course, int page) {
        Cursor cursor = this.getReadableDatabase().rawQuery(queryLastPageForCourse(course), null);
        if (!cursor.moveToFirst()) {
            ContentValues values = new ContentValues();
            values.put(course_id, course);
            values.put(last_page_read, page);
            long id = this.getWritableDatabase().insert(USER_PROTECTION_PATHS_PAGE_VIEWS, null, values);
            Log.d(TAG, " PAGE REGISTER: ROW: " + id + " COURSE: "
                    + course + " PAGE: " + page);
        } else {
            this.getWritableDatabase().execSQL(scriptUpdateLastPageReadForCourse(course, page));
        }
        cursor.close();
    }

    public void clearTopicActivity(){
        this.getWritableDatabase().execSQL(queryDeleteTopicActivity);
    }

    public void clearPagesRead(){
        this.getWritableDatabase().execSQL(queryDeleteUserCourseReadPages);
    }

    public void clearPagesReadProtectionPaths(){
        this.getWritableDatabase().execSQL(queryDeleteUserProtectionReadPages);
    }


    public int getLastPageReadForCurse(int course){
        Cursor cursor = this.getReadableDatabase().rawQuery(queryLastPageForCourse(course),null);
        if (!cursor.moveToFirst()){
            return 0;
        } else {
            return cursor.getInt(1);
        }
    }

    public int getLastPageReadForProtectionPath(int course){
        Cursor cursor = this.getReadableDatabase().rawQuery(queryLastPageForProtectionPath(course),null);
        if (!cursor.moveToFirst()){
            return 0;
        } else {
            return cursor.getInt(1);
        }
    }

    public void updateTopicActivitySent(int topic_activity){
        this.getWritableDatabase().execSQL(scriptMarkActivityAsSent(topic_activity));
    }

    public void updateTopicActivitySent(List<Models.UserActivityProgress> topic_activity_list){
        for(Models.UserActivityProgress topic : topic_activity_list){
            updateTopicActivitySent(topic.topic_activity);
        }
    }

    public List<Models.TopicActivity> getTopicActivitiesCompleted(){
        List<Models.TopicActivity> topicActivitiesList = new ArrayList<>();
        Cursor cursor = this.getReadableDatabase().rawQuery(queryTopicActivities(),null);

        if(cursor.moveToFirst()){
            do {
                topicActivitiesList.add(ConseApp.appConfiguration.getTopicActivityById(cursor.getInt(0)));

            }while (cursor.moveToNext());
        }
        return topicActivitiesList;
    }

    public List<Models.UserActivityProgress> getTopicActivitiesCompletedNotSent(){
        List<Models.UserActivityProgress> topicActivitiesList = new ArrayList<>();
        Cursor cursor = this.getReadableDatabase().rawQuery(queryTopicActivitiesNotSent(),null);

        if(cursor.moveToFirst()){
            do {
                topicActivitiesList.add(new Models.UserActivityProgress(
                        cursor.getInt(0), ConseApp.user.user.id, cursor.getString(1)
                ));
            }while (cursor.moveToNext());
        }
        return topicActivitiesList;
    }



}