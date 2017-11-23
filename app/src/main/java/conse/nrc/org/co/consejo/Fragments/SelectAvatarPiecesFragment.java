package conse.nrc.org.co.consejo.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;
import java.util.List;

import conse.nrc.org.co.consejo.Activities.LetStart;
import conse.nrc.org.co.consejo.Interfaces.AvatarInterfaces;
import conse.nrc.org.co.consejo.R;
import conse.nrc.org.co.consejo.Utils.ConseApp;
import conse.nrc.org.co.consejo.Utils.Models;

import static android.R.attr.button;
import static conse.nrc.org.co.consejo.Utils.LocalConstants.FEMALE;
import static conse.nrc.org.co.consejo.Utils.LocalConstants.MALE;

/**
 * Created by apple on 11/22/17.
 */

public class SelectAvatarPiecesFragment extends Fragment {

    View mView;
    Context mCtx;
    public static int mGender;
    AvatarInterfaces avatarInterfaces;
    LinearLayout mLyHead, mLyHair, mLyNose, mLyEyes, mLyAccesory;
    List<LinearLayout> mLyList = new ArrayList<>();
    List<RadioGroup> mRgList = new ArrayList<>();
    List<Integer> mSelectedPiecesList = new ArrayList<>();
    FrameLayout mFlPreview;
    //RadioGroup mRgHead, mRgHair, mRgNose, mRgEyes, mRgAccesory;
    private com.nostra13.universalimageloader.core.ImageLoader imageLoader;
    DisplayImageOptions options;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.select_avatar_pieces_fragment, container, false);

        mLyHead = (LinearLayout) mView.findViewById(R.id.ly_head);
        mLyHair = (LinearLayout) mView.findViewById(R.id.ly_hair);
        mLyNose = (LinearLayout) mView.findViewById(R.id.ly_nose);
        mLyEyes = (LinearLayout) mView.findViewById(R.id.ly_eyes);
        mLyAccesory = (LinearLayout) mView.findViewById(R.id.ly_accesory);

        mLyList.add(mLyAccesory);
        mLyList.add(mLyHair);
        mLyList.add(mLyHead);
        mLyList.add(mLyEyes);
        mLyList.add(mLyNose);

        mFlPreview = (FrameLayout)mView.findViewById(R.id.fl_preview);


        Button btNext = (Button) mView.findViewById(R.id.bt_next);
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateSelection();
            }
        });

        imageLoader = imageLoader.getInstance();

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.conse)
                .showImageForEmptyUri(R.drawable.conse)
                .showImageOnFail(R.drawable.conse)
                .cacheInMemory(false)
                .cacheOnDisk(false)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        fillLayouts();

        return mView;
    }

    private void fillLayouts() {
        mRgList.clear();
        for(int i = 1; i<=5; i++) {
            fillPieceList(i, mLyList.get(i-1), ConseApp.appConfiguration.getAvatarPiecesByGenderAndPart(mGender, i));
        }
    }

    private void fillPieceList(int index, LinearLayout bodyPartLine, List<Models.AvatarPiece> avatarPiecesByGender) {
        bodyPartLine.removeAllViews();
        //mRgList.add(new RadioGroup(mCtx));
        if(avatarPiecesByGender.size()> 0) {
            for(Models.AvatarPiece piece : avatarPiecesByGender) {
                final LayoutInflater inflater = (LayoutInflater) this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View avatarPiece = inflater.inflate(R.layout.avatar_piece_item, null, false);
                ImageView piece_icon = (ImageView) avatarPiece.findViewById(R.id.iv_piece);
                piece_icon.setTag(piece.id);
                RadioButton radioButton = (RadioButton) avatarPiece.findViewById(R.id.rb_avatar_piece);
                //(mRgList.get(mRgList.size()-1)).addView(radioButton);
                imageLoader.displayImage(piece.icon, piece_icon, options);
                avatarPiece.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setAvatarPreview(v);
                    }
                });
                bodyPartLine.addView(avatarPiece);
            }
        }
    }

    private void setAvatarPreview(View v) {
        ImageView image = (ImageView)v.findViewById(R.id.iv_piece);
        mFlPreview.addView(image);
    }

    private void validateSelection() {
        if(true){
            savePiecesSelected();
            nextActivity();
        }

    }

    private void savePiecesSelected() {

    }

    private void nextActivity() {
        avatarInterfaces.finishAvatarConstruction();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCtx = context;
        avatarInterfaces = (AvatarInterfaces) mCtx;
    }
}
