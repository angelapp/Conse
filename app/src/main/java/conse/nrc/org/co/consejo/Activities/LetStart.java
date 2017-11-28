package conse.nrc.org.co.consejo.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;
import java.util.List;

import conse.nrc.org.co.consejo.R;
import conse.nrc.org.co.consejo.Utils.ConseApp;
import conse.nrc.org.co.consejo.Utils.LocalConstants;
import conse.nrc.org.co.consejo.Utils.UtilsFunctions;

public class LetStart extends AppCompatActivity {

    List<ImageView> mIvList = new ArrayList<>();
    DisplayImageOptions optionsPreview;
    private com.nostra13.universalimageloader.core.ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_let_start);
        Button bt_start = (Button) findViewById(R.id.bt_let_start);


        mIvList.add((ImageView) findViewById(R.id.iv_head));
        mIvList.add((ImageView) findViewById(R.id.iv_hair));
        mIvList.add((ImageView) findViewById(R.id.iv_accesory));
        mIvList.add((ImageView) findViewById(R.id.iv_eyes));
        mIvList.add((ImageView) findViewById(R.id.iv_nose));


        optionsPreview = new DisplayImageOptions.Builder()
                .showImageOnLoading(null)
                .showImageForEmptyUri(null)
                .showImageOnFail(null)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        imageLoader = imageLoader.getInstance();

        drawAvatar();

        bt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMain();
            }
        });
    }

    private void drawAvatar() {
        for (int i = 1; i<= mIvList.size() ; i++){
            int pieceId = UtilsFunctions.getSharedInteger(this, LocalConstants.AVATAR_SELECTED_PART_ + String.valueOf(i));
            imageLoader.displayImage(ConseApp.appConfiguration.getAvatarPieceById(pieceId).icon, mIvList.get(i-1), optionsPreview);
        }
    }

    private void startMain() {

        startActivity(new Intent(this, MainActivity.class));
        this.finish();
    }
}
