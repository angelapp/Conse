package conse.nrc.org.co.consejo.Activities;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.MailTo;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.provider.SyncStateContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Permission;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Manifest;

import conse.nrc.org.co.consejo.Fragments.AlertAndCallFragment;
import conse.nrc.org.co.consejo.Fragments.AlertDialog;
import conse.nrc.org.co.consejo.Fragments.ContactFormFragment;
import conse.nrc.org.co.consejo.Fragments.CourseSelectionFragment;
import conse.nrc.org.co.consejo.Fragments.DocsBankFragment;
import conse.nrc.org.co.consejo.Fragments.LEADERS_COURSE_2.LeadersCourseFragment;
import conse.nrc.org.co.consejo.Fragments.LearningAboutMyCommunity;
import conse.nrc.org.co.consejo.Fragments.NewsFragment;
import conse.nrc.org.co.consejo.Fragments.ProfileFragment;
import conse.nrc.org.co.consejo.Fragments.ProgressFragment;
import conse.nrc.org.co.consejo.Fragments.ProtectionPathsFragment;
import conse.nrc.org.co.consejo.Fragments.VBG_Course_1.VbgCourse1Start;
import conse.nrc.org.co.consejo.Fragments.aboutNrcFragment;
import conse.nrc.org.co.consejo.Interfaces.MainInterface;
import conse.nrc.org.co.consejo.R;
import conse.nrc.org.co.consejo.Utils.ConseApp;
import conse.nrc.org.co.consejo.Utils.DataBase;
import conse.nrc.org.co.consejo.Utils.LocalConstants;
import conse.nrc.org.co.consejo.Utils.Models;
import conse.nrc.org.co.consejo.Utils.RequestTask;
import conse.nrc.org.co.consejo.Utils.ServerRequest;
import conse.nrc.org.co.consejo.Utils.UtilsFunctions;
import io.fabric.sdk.android.Fabric;

import static android.Manifest.permission.CALL_PHONE;
import static conse.nrc.org.co.consejo.Utils.LocalConstants.TEMPLATE_LIBRARY_LIST;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, MainInterface, RequestTask.OnRequestCompleted {

    Button mBtMenu, mBtSendAlert;
    LinearLayout mLyMenu;
    ProgressDialog listener;
    List<Models.UserActivityProgress> mActivityProgressListSend = new ArrayList<>();
    public static DataBase dataBase;

    private static VbgCourse1Start vbgCourse1Start = new VbgCourse1Start();
    private static LeadersCourseFragment leadersCourseFragment= new LeadersCourseFragment();
    private int actualCourse;

    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);

        setContentView(R.layout.activity_main);

        Fabric.with(this, new Crashlytics());

        this.setTheme(R.style.AppTheme);
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        mBtMenu = (Button)findViewById(R.id.bt_ham);
        mBtSendAlert = (Button)findViewById(R.id.bt_alert);
        mBtSendAlert.setOnClickListener(this);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();

        dataBase = new DataBase(this);
        dataBase.getInstance(this);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mLyMenu = (LinearLayout) findViewById(R.id.ly_menu);


        mBtMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        setCourseSelectionFragment();
        listener = new ProgressDialog(this);
//        forceCrash();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);

        //logCoursEstructure();
    }

    public void forceCrash() {
        throw new RuntimeException("This is a crash");
    }


    private void logCoursEstructure() {

        List<Models.Course> course = ConseApp.getAppConfiguration(this).course_list;

        for (Models.Course cusero : course){
            Log.d("Curse Structures", "Curse: " + cusero.id + " - " + cusero.name);
            for (Models.Topic topic: cusero.course_topics){
                Log.d("Curse Structures", "Topic: " + topic.id + " - " + topic.name);
                for (Models.TopicActivity activity : topic.topic_activity_list){
                    Log.d("Curse Structures", "Act: " + activity.id + " - " + activity.description);
                }
            }
        }
    }

    @Override
    public void setCourseSelectionFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.ly_home_content, new CourseSelectionFragment()).commitAllowingStateLoss();
    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
//        Log.d("VBG MOD", "Click listened: id:" + v.getId());
        switch (v.getId()){
            case R.id.bt_progress:
                openProgressFragment();
                break;
            case R.id.bt_alert:
                sendAlert();
                break;
            case R.id.bt_docs:
                openLibrary();
                break;
            case R.id.bt_protection_guides:
                openProtectionPaths();
                break;
            case R.id.bt_analize:
                openLearningMyCommunity();
                break;
            case R.id.bt_courses:
                setCourseSelectionFragment();
                break;
            case R.id.bt_contact:
                setContactForm();
                break;
            case R.id.bt_videotut:
                openVideoTutorial();
                break;
            case R.id.bt_edit_profile:
                openEditProfileFragment();
                break;
            case R.id.bt_close_sesion:
                closeSesion();
                break;
            case R.id.bt_about_nrc:
                openAboutNrc();
                break;
            case R.id.bt_vbg:
                initVbgCourse();
                break;
            case R.id.bt_leaders:
                initLeadersCourse();
                break;
            case R.id.bt_news:
                openNews();
                break;
            //Managing course inner buttons
            case R.id.bt_next: case R.id.bt_previous:case R.id.bt_play:case R.id.bt_finish:
                switch (actualCourse){
                    case LocalConstants.VBG_COURSE_ID:
                        if(vbgCourse1Start != null){
                            vbgCourse1Start.processOuterClick(v);
                        }
                        break;
                    case LocalConstants.LEADERS_COURSE_ID:
                        if(leadersCourseFragment != null){
                            leadersCourseFragment.processOuterClick(v);
                        }
                        break;
                }
                break;
            case R.id.bt_call_141:
                makeCall(v);
                break;
            default:
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    private void openVideoTutorial() {
        Intent tuto = new Intent(this, VideoTutorial.class);
        startActivity(tuto);

    }

    private void initLeadersCourse() {
        actualCourse = LocalConstants.LEADERS_COURSE_ID;
        getSupportFragmentManager().beginTransaction().replace(R.id.ly_home_content, leadersCourseFragment).addToBackStack(null).commitAllowingStateLoss();
    }

    private void openProtectionPaths() {
        getSupportFragmentManager().beginTransaction().replace(R.id.ly_home_content, new ProtectionPathsFragment()).addToBackStack(null).commitAllowingStateLoss();
    }

    private void openLearningMyCommunity() {
        getSupportFragmentManager().beginTransaction().replace(R.id.ly_home_content, new LearningAboutMyCommunity()).addToBackStack(null).commitAllowingStateLoss();
    }

    private void openNews() {
        Log.d("MainActivity", "Is location enabled: " + isLocationEnabled() + " Is internet connected: " + isNetworkAvailable());
        if (!isNetworkAvailable()){
            showNetworkConnectionAlert();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.ly_home_content, new NewsFragment()).addToBackStack(null).commitAllowingStateLoss();
    }

    private void openLibrary() {
        Log.d("MainActivity", "Is location enabled: " + isLocationEnabled() + " Is internet connected: " + isNetworkAvailable());
        if (!isLocationEnabled()){
            showGpsAlert();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.ly_home_content, new DocsBankFragment()).addToBackStack(null).commitAllowingStateLoss();
    }

    private void makeCall(View v) {
        try {
            if (ActivityCompat.checkSelfPermission(this,
                    CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + (String) v.getTag()));
                startActivity(intent);
            } else {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:" + (String) v.getTag()));
                startActivity(dialIntent);
            }
        } catch (android.content.ActivityNotFoundException e){
            Toast.makeText(getApplicationContext(),R.string.app_not_found,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void makeCall(String number){
        try {
            if (ActivityCompat.checkSelfPermission(this,
                    CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
                startActivity(intent);
            } else {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:" + number));
                startActivity(dialIntent);
            }
        } catch (android.content.ActivityNotFoundException e){
            Toast.makeText(getApplicationContext(),R.string.app_not_found,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void sendEmail(String send_to) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{send_to});
        intent.setType("vnd.android.cursor.dir/email");
        //intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_from_conse));
        startActivity(Intent.createChooser(intent, getString(R.string.sending_email)));
    }

    private void openEditProfileFragment() {
        ProfileFragment fragment = new ProfileFragment();
        fragment.editionMode = true;
        getSupportFragmentManager().beginTransaction().replace(R.id.ly_home_content, fragment).addToBackStack(null).commitAllowingStateLoss();
    }

    public void openProgressFragment() {

//        getSupportFragmentManager().beginTransaction().replace(R.id.rl_total_home_content, new ProgressFragment()).addToBackStack(null).commitAllowingStateLoss();
//        hideToolBar(true);

        startActivity(new Intent(this, ProgressActivity.class));

    }

    private void setContactForm() {
        getSupportFragmentManager().beginTransaction().replace(R.id.ly_home_content, new ContactFormFragment()).addToBackStack(null).commitAllowingStateLoss();
    }

    private void sendAlert() {
        AlertAndCallFragment alertAndCallFragment = new AlertAndCallFragment();
        alertAndCallFragment.show(getSupportFragmentManager(), "tag");
    }

    private void openAboutNrc() {
        getSupportFragmentManager().beginTransaction().replace(R.id.ly_home_content, new aboutNrcFragment()).addToBackStack(null).commitAllowingStateLoss();

    }

    private void closeSesion() {
        //UtilsFunctions.resetSharedPreferences(this);
        UtilsFunctions.saveSharedBoolean(this, LocalConstants.IS_USER_LOGGED_IN, false);
        startActivity(new Intent(this, Welcome.class));
        this.finish();
    }

    private void initVbgCourse() {
        actualCourse = LocalConstants.VBG_COURSE_ID;
        getSupportFragmentManager().beginTransaction().replace(R.id.ly_home_content, vbgCourse1Start).addToBackStack(null).commitAllowingStateLoss();
    }

    @Override
    public void startVbgCourse() {
        initVbgCourse();
    }

    @Override
    public void startLeadersCourse() {
        initLeadersCourse();
    }

    @Override
    public void setPreviousFragment() {
        super.onBackPressed();
    }



    @Override
    public void saveActivityCompleted(int activity_code){
        dataBase.insertTopicActivity(new Models.UserActivityProgress(activity_code, ConseApp.user.user.id));
        mActivityProgressListSend.clear();
        mActivityProgressListSend = dataBase.getTopicActivitiesCompletedNotSent();
        listener.setMessage(getString(R.string.saving_user_progress));
        if (mActivityProgressListSend.size()>0) {
            new ServerRequest.PostUserActivityProgress(this, this, listener,
                    LocalConstants.POST_USER_PROGRESS_LIST_TASK_ID, mActivityProgressListSend).executePostList();
        }

    }

    @Override
    public void hideToolBar(boolean hide) {
//        if (hide) {
//            getSupportActionBar().hide();
////            toolbar.setVisibility(View.GONE);
//        } else {
//            getSupportActionBar().show();
////            toolbar.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    public void onRequestResponse(Object response, int taskId) {

        switch (taskId){
            case LocalConstants.POST_USER_PROGRESS_LIST_TASK_ID:
                dataBase.updateTopicActivitySent(mActivityProgressListSend);
                break;
            default:
                break;
        }

    }

    @Override
    public void onRequestError(int errorCode, String errorMsg, int taskId) {

        switch (taskId){
            case LocalConstants.POST_USER_PROGRESS_LIST_TASK_ID:
                Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    private boolean isLocationEnabled() {
        return ((LocationManager)
                this.getSystemService(Context.LOCATION_SERVICE)).isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                ((LocationManager)
                        this.getSystemService(Context.LOCATION_SERVICE)).isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    public boolean validateGpsStatus(boolean show_alert) {
        if (isLocationEnabled()){
            return true;
        } else if (show_alert){
            showGpsAlert();
        }
        return false;
    }

    @Override
    public boolean validateNetworkStatus(boolean show_alert) {
        if (isNetworkAvailable()){
            return true;
        } else if (show_alert){
            showNetworkConnectionAlert();
        }
        return false;
    }


    private void showGpsAlert() {
        final android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(this);
        dialog.setTitle(getString(R.string.inactive_geolocation))
                .setMessage(getString(R.string.conse_need_gps))
                .setPositiveButton(getString(R.string.config), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }

    private void showNetworkConnectionAlert() {
        final android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(this);
        dialog.setTitle(getString(R.string.not_network_conection))
                .setMessage(getString(R.string.conse_need_network))
                .setPositiveButton(getString(R.string.config), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }

    @Override
    public void openDoc(String doc) {
        File docFile = new File(Environment.getExternalStorageDirectory() + "/" + doc);
        if (!docFile.exists())
        {
            CopyAssets(doc);
        }

        /** PDF reader code */
        File file = new File(Environment.getExternalStorageDirectory() + "/" + doc);

        MimeTypeMap myMime = MimeTypeMap.getSingleton();
        String mimeType = myMime.getMimeTypeFromExtension(file.getName().substring(1));

        Log.d("Open doc", "Extension: " + mimeType);

        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setDataAndType(Uri.fromFile(file), mimeType);

        String extension = android.webkit.MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString());
        String mimetype = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        if (extension.equalsIgnoreCase("") || mimetype == null) {
            // if there is no extension or there is no definite mimetype, still try to open the file
            intent.setData(Uri.fromFile(file));
        } else {
            intent.setDataAndType(Uri.fromFile(file), mimetype);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try
        {
            getApplicationContext().startActivity(intent);
        }
        catch (ActivityNotFoundException e)
        {
            Toast.makeText(this, getString(R.string.not_activity_found), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void shareDoc(String doc) {

        File docFile = new File(Environment.getExternalStorageDirectory() + "/" + doc);
        if (!docFile.exists())
        {
            CopyAssets(doc);
        }

        /** PDF reader code */
        File file = new File(Environment.getExternalStorageDirectory() + "/" + doc);

        Log.d("Mailing doc", "Extension: " + doc);

        String doc_name = getTemplatyLibraryDocName(doc);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        intent.setType("vnd.android.cursor.dir/email");
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_mail_text) + " " + doc_name);
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_email_subject));
        startActivity(Intent.createChooser(intent, getString(R.string.sending_email)));
    }

    private String getTemplatyLibraryDocName(String doc) {

        for (Pair<String, String> register : TEMPLATE_LIBRARY_LIST){
            if (register.second.equals(doc)){
                return register.first;
            }
        }
        return "";
    }


    //method to write the PDFs file to sd card
    public void CopyAssets(String doc) {
        AssetManager assetManager = getAssets();
        String[] files = null;
        try
        {
            files = assetManager.list("");
        }
        catch (IOException e)
        {
            Log.e("tag", e.getMessage());
        }
        for(int i=0; i<files.length; i++)
        {
            String fStr = files[i];
            if(fStr.equalsIgnoreCase(doc))
            {
                InputStream in = null;
                OutputStream out = null;
                try
                {
                    in = assetManager.open(files[i]);
                    out = new FileOutputStream(Environment.getExternalStorageDirectory() + "/" + files[i]);
                    copyFile(in, out);
                    in.close();
                    in = null;
                    out.flush();
                    out.close();
                    out = null;
                    break;
                }
                catch(Exception e)
                {
                    Log.e("tag", e.getMessage());
                }
            }
        }
    }

    public void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }
}
