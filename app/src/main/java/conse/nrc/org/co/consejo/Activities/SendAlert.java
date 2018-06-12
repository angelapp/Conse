package conse.nrc.org.co.consejo.Activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.Toast;

import conse.nrc.org.co.consejo.Fragments.*;
import conse.nrc.org.co.consejo.R;
import conse.nrc.org.co.consejo.Interfaces.*;
import conse.nrc.org.co.consejo.Utils.LocalConstants;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static conse.nrc.org.co.consejo.Utils.LocalConstants.LOCATION_PERMISSION_CODE;

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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_PERMISSION_CODE:
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    int grantResult = grantResults[i];
                    switch (permission) {
                        case ACCESS_COARSE_LOCATION:
                            if (PackageManager.PERMISSION_GRANTED != grantResult) {
                                Toast.makeText(this,R.string.needs_location_permission, Toast.LENGTH_LONG).show();
                            }
                            break;
                    }
                }
                break;
        }
    }
}
