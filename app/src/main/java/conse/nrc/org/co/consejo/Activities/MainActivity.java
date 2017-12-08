package conse.nrc.org.co.consejo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Toast;

import conse.nrc.org.co.consejo.Fragments.AlertDialog;
import conse.nrc.org.co.consejo.Fragments.ContactFormFragment;
import conse.nrc.org.co.consejo.Fragments.CourseSelectionFragment;
import conse.nrc.org.co.consejo.Fragments.VBG_Course_1.VbgCourse1Start;
import conse.nrc.org.co.consejo.Fragments.aboutNrcFragment;
import conse.nrc.org.co.consejo.Interfaces.MainInterface;
import conse.nrc.org.co.consejo.R;
import conse.nrc.org.co.consejo.Utils.UtilsFunctions;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, MainInterface {

    Button mBtMenu, mBtSendAlert;
    LinearLayout mLyMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        this.setTheme(R.style.AppTheme);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        mBtMenu = (Button)findViewById(R.id.bt_ham);
        mBtSendAlert = (Button)findViewById(R.id.bt_alert);
        mBtSendAlert.setOnClickListener(this);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        toolbar.setVisibility(View.GONE);

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

    }

    private void setCourseSelectionFragment() {

        getSupportFragmentManager().beginTransaction().replace(R.id.ly_home_content, new CourseSelectionFragment()).addToBackStack(null).commitAllowingStateLoss();

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
        switch (v.getId()){
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
            case R.id.bt_close_sesion:
                closeSesion();
                break;
            case R.id.bt_about_nrc:
                openAboutNrc();
                break;
            case R.id.bt_vbg:
                initVbgCourse();
            default:
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    private void setContactForm() {
        getSupportFragmentManager().beginTransaction().replace(R.id.ly_home_content, new ContactFormFragment()).addToBackStack(null).commitAllowingStateLoss();
    }

    private void sendAlert() {
        AlertDialog alertDialog = new AlertDialog();
        alertDialog.isTest = false;
        alertDialog.show(getSupportFragmentManager(), "tag");
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
        getSupportFragmentManager().beginTransaction().replace(R.id.ly_home_content, new VbgCourse1Start()).addToBackStack(null).commitAllowingStateLoss();
    }

    @Override
    public void startVbgCourse() {
        initVbgCourse();
    }

    @Override
    public void setPreviousFragment() {
        super.onBackPressed();
    }
}
