package conse.nrc.org.co.consejo.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import conse.nrc.org.co.consejo.Fragments.ProgressFragment;
import conse.nrc.org.co.consejo.R;

/**
 * Created by apple on 12/9/17.
 */

public class ProgressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_activity);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, new ProgressFragment()).commitAllowingStateLoss();
    }
}
