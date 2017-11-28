package conse.nrc.org.co.consejo.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import conse.nrc.org.co.consejo.Fragments.SelectAvatarGenderFragment;
import conse.nrc.org.co.consejo.Fragments.SelectAvatarPiecesFragment;
import conse.nrc.org.co.consejo.Interfaces.AvatarInterfaces;
import conse.nrc.org.co.consejo.R;

public class Avatar extends AppCompatActivity implements AvatarInterfaces {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);
        getSupportFragmentManager().beginTransaction().replace(R.id.ly_content, new SelectAvatarGenderFragment()).addToBackStack(null).commit();
    }

    @Override
    public void genderSelected(int gender) {

        SelectAvatarPiecesFragment piecesFragment = new SelectAvatarPiecesFragment();
        piecesFragment.mGender  = gender;
        getSupportFragmentManager().beginTransaction().replace(R.id.ly_content, new SelectAvatarPiecesFragment()).addToBackStack(null).commit();
    }

    @Override
    public void finishAvatarConstruction() {
        startActivity(new Intent(this, LetStart.class));
        this.finish();
    }
}
