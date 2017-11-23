package conse.nrc.org.co.consejo.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import conse.nrc.org.co.consejo.R;

public class LetStart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_let_start);
        Button bt_start = (Button) findViewById(R.id.bt_let_start);

        bt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMain();
            }
        });
    }

    private void startMain() {

        startActivity(new Intent(this, MainActivity.class));
        this.finish();
    }
}
