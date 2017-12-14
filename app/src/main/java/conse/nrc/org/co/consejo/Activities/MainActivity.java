package conse.nrc.org.co.consejo.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.security.Permission;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Manifest;

import conse.nrc.org.co.consejo.Fragments.AlertAndCallFragment;
import conse.nrc.org.co.consejo.Fragments.AlertDialog;
import conse.nrc.org.co.consejo.Fragments.ContactFormFragment;
import conse.nrc.org.co.consejo.Fragments.CourseSelectionFragment;
import conse.nrc.org.co.consejo.Fragments.ProfileFragment;
import conse.nrc.org.co.consejo.Fragments.ProgressFragment;
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

import static android.Manifest.permission.CALL_PHONE;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, MainInterface, RequestTask.OnRequestCompleted {

    Button mBtMenu, mBtSendAlert;
    LinearLayout mLyMenu;
    ProgressDialog listener;
    List<Models.UserActivityProgress> mActivityProgressListSend = new ArrayList<>();
    public static DataBase dataBase;

    private static VbgCourse1Start vbgCourse1Start = new VbgCourse1Start();
    private int actualCourse;

    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);

        setContentView(R.layout.activity_main);

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

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);

        //logCoursEstructure();
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
        if (id == R.id.action_settings) {
            return true;
        }

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
                Toast.makeText(this, "Mis documentos", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt_courses:
                setCourseSelectionFragment();
                break;
            case R.id.bt_contact:
                setContactForm();
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
            //Managing course inner buttons
            case R.id.bt_next: case R.id.bt_previous:case R.id.bt_play:case R.id.bt_finish:
                switch (actualCourse){
                    case LocalConstants.VBG_COURSE_ID:
                        if(vbgCourse1Start != null){
                            vbgCourse1Start.processOuterClick(v);
                        }
                        break;
                    case LocalConstants.LEADERS_COURSE_ID:
                        break;
                }
                break;
            case R.id.bt_call:
                makeCall(v);
                break;
            default:
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
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
        UtilsFunctions.resetSharedPreferences(this);
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
}
