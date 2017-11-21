package conse.nrc.org.co.conse.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import Fragments.SendAlertFragment;
import conse.nrc.org.co.conse.R;

public class SendAlert extends AppCompatActivity {

    RadioGroup mRgIndicators;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_alert);
        mRgIndicators = (RadioGroup) findViewById(R.id.rg_indicators);
        mRgIndicators.check(R.id.rb_indicator_1);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, new SendAlertFragment()).commit();
    }


}
