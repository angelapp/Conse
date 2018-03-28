package conse.nrc.org.co.consejo.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import conse.nrc.org.co.consejo.Fragments.*;
import conse.nrc.org.co.consejo.R;
import conse.nrc.org.co.consejo.Interfaces.*;
import conse.nrc.org.co.consejo.Utils.LocalConstants;

public class SendAlert extends AppCompatActivity implements AlertTestInterfaces {

    RadioGroup mRgIndicators;
    boolean inEditionMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_alert);
        mRgIndicators = (RadioGroup) findViewById(R.id.rg_indicators);
        mRgIndicators.check(R.id.rb_indicator_1);
        inEditionMode = getIntent().getBooleanExtra(LocalConstants.EDITING_CONTACTS, false);
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
        //Si no está en flujo de ediciòn es porque está en flujo inicial
        if (!inEditionMode) {
            Intent intent = new Intent(this, Avatar.class);
            startActivity(intent);
        } else { //Si está en flujo de edición, envía al home
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        this.finish();
    }
}
