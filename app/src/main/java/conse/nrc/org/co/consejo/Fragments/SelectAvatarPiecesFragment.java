package conse.nrc.org.co.consejo.Fragments;

import android.app.ProgressDialog;
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
import conse.nrc.org.co.consejo.Utils.RequestTask;
import conse.nrc.org.co.consejo.Utils.ServerRequest;
import conse.nrc.org.co.consejo.Utils.UtilsFunctions;

/**
 * Created by apple on 11/22/17.
 */

public class SelectAvatarPiecesFragment extends Fragment implements RequestTask.OnRequestCompleted{

    View mView;
    Context mCtx;
    public static int mGender;
    AvatarInterfaces avatarInterfaces;
    List<RadioGroup> mRgList = new ArrayList<>();
//    List<ImageView> mIvList = new ArrayList<>();
    List<Integer> mSelectedPiecesList = new ArrayList<>();
    FrameLayout mFlPreview;
    LinearLayout mLyPrincipalContent;
    private com.nostra13.universalimageloader.core.ImageLoader imageLoader;
    DisplayImageOptions options, optionsPreview;
    ProgressDialog listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.select_avatar_pieces_fragment, container, false);

        mLyPrincipalContent = (LinearLayout) mView.findViewById(R.id.ly_principal_content);


        mRgList.add((RadioGroup) mView.findViewById(R.id.rg_head));
        mRgList.add((RadioGroup) mView.findViewById(R.id.rg_eyes));
        mRgList.add((RadioGroup) mView.findViewById(R.id.rg_nose));
        mRgList.add((RadioGroup) mView.findViewById(R.id.rg_hair));
        mRgList.add((RadioGroup) mView.findViewById(R.id.rg_accesory));

        mSelectedPiecesList.add(0);
        mSelectedPiecesList.add(0);
        mSelectedPiecesList.add(0);
        mSelectedPiecesList.add(0);
        mSelectedPiecesList.add(0);

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
                .showImageOnLoading(R.drawable.circulo)
                .showImageForEmptyUri(R.drawable.circulo)
                .showImageOnFail(R.drawable.circulo)
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

        listener = new ProgressDialog(mCtx);
        listener.setMessage(getString(R.string.registering_avatar_list));

        return mView;
    }

    private void fillLayouts() {
        int layout_index = 1;
        for(int i : LocalConstants.AVATAR_BODY_PARTS_ORDER) {
            fillPieceList(layout_index ,ConseApp.appConfiguration.getAvatarPiecesByGenderAndPart(mGender, i));
            layout_index++;
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

                RadioButton radioButton = (RadioButton) avatarPiece.findViewById(R.id.rb_avatar_piece);
                radioButton.setTag(tags);
                piece_icon.setTag(tags);
                avatarPiece.setTag(tags);
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
        mSelectedPiecesList.set(tag.get(0)-1, tag.get(1));
        //imageLoader.displayImage(ConseApp.appConfiguration.getAvatarPieceById(tag.get(1)).icon, mIvList.get(tag.get(0)-1), optionsPreview);
        changeRadioGroupSelection(tag.get(0), v);
        savePiecesSelected(tag);
        mFlPreview.removeAllViews();
        mFlPreview.addView(ConseApp.getAvatarFrame(mCtx, imageLoader, optionsPreview),0);
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
        for(int i : mSelectedPiecesList ){
            if(i == 0){
                miss_selection = true;
            }
        }

        if(!miss_selection){
            sendToServerAvatarPieces();
        } else {
            Toast.makeText(mCtx, R.string.missing_avatar_selection, Toast.LENGTH_SHORT).show();
        }
    }

    private void sendToServerAvatarPieces() {
        new ServerRequest.PostUserAvatarPieces(mCtx, this, listener, LocalConstants.POST_AVATAR_LIST_TASK_ID, ConseApp.getUserAvatarList(mCtx)).executePostList();
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

    @Override
    public void onRequestResponse(Object response, int taskId) {

        switch (taskId){
            case LocalConstants.POST_AVATAR_LIST_TASK_ID:
                Toast.makeText(mCtx, getString(R.string.avatar_list_successful), Toast.LENGTH_SHORT).show();
                nextActivity();
                break;
            default:
                break;
        }

    }

    @Override
    public void onRequestError(int errorCode, String errorMsg, int taskId) {

        switch (taskId){
            case LocalConstants.REGISTER_USER_TASK_ID:
                Toast.makeText(mCtx, errorMsg, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

    }
}
