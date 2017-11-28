package conse.nrc.org.co.consejo.Fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;
import java.util.List;

import conse.nrc.org.co.consejo.Interfaces.AvatarInterfaces;
import conse.nrc.org.co.consejo.R;
import conse.nrc.org.co.consejo.Utils.ConseApp;
import conse.nrc.org.co.consejo.Utils.LocalConstants;
import conse.nrc.org.co.consejo.Utils.Models;
import conse.nrc.org.co.consejo.Utils.UtilsFunctions;

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
    List<ImageView> mIvList = new ArrayList<>();
    List<Integer> mSelectedPiecesList = new ArrayList<>();
    FrameLayout mFlPreview;
    ImageView mIvHead, mIvHair, mIvNose, mIvEyes, mIvAccesory;
    RadioGroup mRgHead, mRgHair, mRgNose, mRgEyes, mRgAccesory;
    private com.nostra13.universalimageloader.core.ImageLoader imageLoader;
    DisplayImageOptions options, optionsPreview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.select_avatar_pieces_fragment, container, false);

        mLyHead = (LinearLayout) mView.findViewById(R.id.ly_head);
        mLyHair = (LinearLayout) mView.findViewById(R.id.ly_hair);
        mLyNose = (LinearLayout) mView.findViewById(R.id.ly_nose);
        mLyEyes = (LinearLayout) mView.findViewById(R.id.ly_eyes);
        mLyAccesory = (LinearLayout) mView.findViewById(R.id.ly_accesory);

        mRgHead = (RadioGroup) mView.findViewById(R.id.rg_head);
        mRgHair = (RadioGroup) mView.findViewById(R.id.rg_hair);
        mRgNose = (RadioGroup) mView.findViewById(R.id.rg_nose);
        mRgEyes = (RadioGroup) mView.findViewById(R.id.rg_eyes);
        mRgAccesory = (RadioGroup) mView.findViewById(R.id.rg_accesory);

        mIvHead = (ImageView) mView.findViewById(R.id.iv_head);
        mIvHair = (ImageView) mView.findViewById(R.id.iv_hair);
        mIvNose = (ImageView) mView.findViewById(R.id.iv_nose);
        mIvEyes = (ImageView) mView.findViewById(R.id.iv_eyes);
        mIvAccesory = (ImageView) mView.findViewById(R.id.iv_accesory);

        mLyList.add(mLyAccesory);
        mLyList.add(mLyHair);
        mLyList.add(mLyHead);
        mLyList.add(mLyEyes);
        mLyList.add(mLyNose);

        mRgList.add(mRgAccesory);
        mRgList.add(mRgHair);
        mRgList.add(mRgHead);
        mRgList.add(mRgEyes);
        mRgList.add(mRgNose);

        mIvList.add(mIvHead);
        mIvList.add(mIvHair);
        mIvList.add(mIvAccesory);
        mIvList.add(mIvEyes);
        mIvList.add(mIvNose);

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
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        optionsPreview = new DisplayImageOptions.Builder()
                .showImageOnLoading(null)
                .showImageForEmptyUri(null)
                .showImageOnFail(null)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        fillLayouts();

        return mView;
    }

    private void fillLayouts() {
        for(int i = 1; i<=5; i++) {
            fillPieceList(i ,ConseApp.appConfiguration.getAvatarPiecesByGenderAndPart(mGender, i));
        }
    }

    private void fillPieceList(int index, List<Models.AvatarPiece> avatarPiecesByGender) {
        //mRgList.get(index-1).setOnCheckedChangeListener(this);
        if(avatarPiecesByGender.size()> 0) {
            for(Models.AvatarPiece piece : avatarPiecesByGender) {
                final LayoutInflater inflater = (LayoutInflater) this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View avatarPiece = inflater.inflate(R.layout.avatar_piece_item, null, false);
                ImageView piece_icon = (ImageView) avatarPiece.findViewById(R.id.iv_piece);
                List<Integer> tags = new ArrayList<>();
                tags.add(index); // Body part
                tags.add(piece.id); // Avatar piece id
                piece_icon.setTag(tags);
                RadioButton radioButton = (RadioButton) avatarPiece.findViewById(R.id.rb_avatar_piece);
                radioButton.setTag(tags);
                radioButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setAvatarPreview(v);
                    }
                });
                (mRgList.get(index-1)).addView(avatarPiece);
                imageLoader.displayImage(piece.icon, piece_icon, options);
                avatarPiece.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setAvatarPreview(v);
                    }
                });
            }
        }
    }

    private void setAvatarPreview(View v) {
        List<Integer> tag = (List<Integer>) v.getTag();
        Log.d("Avatar", "On click listen: ");
        imageLoader.displayImage(ConseApp.appConfiguration.getAvatarPieceById(tag.get(1)).icon, mIvList.get(tag.get(0)-1), optionsPreview);
        changeRadioGroupSelection(tag.get(0), v);
        savePiecesSelected(tag);
    }

    private void changeRadioGroupSelection(Integer index, View v) {

        for (int i = 0; i < mRgList.get(index -1).getChildCount(); i++){
            if((((mRgList.get(index-1)).getChildAt(i)).findViewById(R.id.rb_avatar_piece)).getTag() == v.getTag()){
                ((RadioButton)mRgList.get(index -1).getChildAt(i).findViewById(R.id.rb_avatar_piece)).setChecked(true);
            } else {
                ((RadioButton)mRgList.get(index -1).getChildAt(i).findViewById(R.id.rb_avatar_piece)).setChecked(false);
            }
        }
    }

    private void validateSelection() {

        boolean miss_selection = false;
        for(int i = 1; i<= mIvList.size(); i++){
            if(mIvList.get(i-1).getDrawable() == null){
                miss_selection = true;
            }
        }

        if(!miss_selection){
            nextActivity();
        } else {
            Toast.makeText(mCtx, R.string.missing_avatar_selection, Toast.LENGTH_SHORT).show();
        }
    }

    private void savePiecesSelected(List<Integer> tag) {
        UtilsFunctions.saveSharedInteger(mCtx, LocalConstants.AVATAR_SELECTED_PART_+String.valueOf(tag.get(0)), tag.get(1));
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
