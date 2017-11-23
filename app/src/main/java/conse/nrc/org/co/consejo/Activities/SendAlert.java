package conse.nrc.org.co.consejo.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import conse.nrc.org.co.consejo.Fragments.*;
import conse.nrc.org.co.consejo.R;
import conse.nrc.org.co.consejo.Interfaces.*;

public class SendAlert extends AppCompatActivity implements AlertTestInterfaces {

    RadioGroup mRgIndicators;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_alert);
        mRgIndicators = (RadioGroup) findViewById(R.id.rg_indicators);
        mRgIndicators.check(R.id.rb_indicator_1);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, new SendAlertFragment()).commit();
    }

    @Override
    public void alertTestSuccess() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, new SendAlertSuccess()).commit();
        mRgIndicators.check(R.id.rb_indicator_2);
    }

    @Override
    public void finishAlertTest() {
        nextActivity();
    }

    private void nextActivity() {
        Intent intent = new Intent(this, Avatar.class);
        startActivity(intent);
        this.finish();
    }
}
