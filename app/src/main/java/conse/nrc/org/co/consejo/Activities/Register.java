package conse.nrc.org.co.consejo.Activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import conse.nrc.org.co.consejo.Fragments.ProfileFragment;
import conse.nrc.org.co.consejo.Fragments.ProgressFragment;
import conse.nrc.org.co.consejo.Fragments.VBG_Course_1.VbgCourse1Start;
import conse.nrc.org.co.consejo.Utils.ConseApp;
import conse.nrc.org.co.consejo.Utils.DataBase;
import conse.nrc.org.co.consejo.Utils.LocalConstants;
import conse.nrc.org.co.consejo.Utils.Models;
import conse.nrc.org.co.consejo.Utils.RequestTask;
import conse.nrc.org.co.consejo.Utils.ServerRequest;
import conse.nrc.org.co.consejo.Utils.UtilsFunctions;
import conse.nrc.org.co.consejo.R;

public class Register extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ProfileFragment fragment = new ProfileFragment();
        fragment.editionMode = false;
        getSupportFragmentManager().beginTransaction().replace(R.id.ly_content, fragment).commitAllowingStateLoss();
        DataBase dataBase = new DataBase(this);
        dataBase.getInstance(this);
        dataBase.clearTopicActivity();
    }

}
