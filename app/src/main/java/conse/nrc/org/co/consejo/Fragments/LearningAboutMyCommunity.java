package conse.nrc.org.co.consejo.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import conse.nrc.org.co.consejo.Interfaces.MainInterface;
import conse.nrc.org.co.consejo.R;
import conse.nrc.org.co.consejo.Utils.ExpandableJurisprudenceDocsList;
import conse.nrc.org.co.consejo.Utils.JurisprudenceDocsExpandableAdapter;
import conse.nrc.org.co.consejo.Utils.LearningCommunityDocsList;
import conse.nrc.org.co.consejo.Utils.LearningFormatsExpandableAdapter;
import conse.nrc.org.co.consejo.Utils.LocalConstants;
import conse.nrc.org.co.consejo.Utils.Models;
import conse.nrc.org.co.consejo.Utils.RequestTask;
import conse.nrc.org.co.consejo.Utils.ServerRequest;

/**
 * Created by apple on 12/28/17.
 */

public class LearningAboutMyCommunity extends Fragment {

    private MainInterface mainInterface;
    Context mCtx;
    ExpandableListView mElvMenuLeft;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    List<Models.DocumentTextType> documentLists = new ArrayList<>();
    LinkedHashMap<String, List<Models.LearningItem>> expandableListDetail;

    ProgressDialog listener;


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCtx = context;
        mainInterface = (MainInterface)mCtx;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.learning_my_community_fragment, container, false);
        mElvMenuLeft = (ExpandableListView) view.findViewById(R.id.elv_list);
        fillList();

        return view;
    }


    private void fillList() {
        LearningCommunityDocsList data = new LearningCommunityDocsList(mCtx);
        expandableListDetail = data.getData();
        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
        expandableListAdapter = new LearningFormatsExpandableAdapter(mCtx, expandableListTitle, expandableListDetail);
        mElvMenuLeft.setAdapter(expandableListAdapter);

        mElvMenuLeft.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                ImageView sign = (ImageView) v.findViewById(R.id.iv_button);
                if (sign.getTag() == null){
                    sign.setTag(LocalConstants.opened);
                } else if ((Integer)sign.getTag() == LocalConstants.opened){
                    sign.setTag(LocalConstants.closed);
                } else if ((Integer)sign.getTag() == LocalConstants.closed){
                    sign.setTag(LocalConstants.opened);
                }

                switch ((Integer)sign.getTag()){
                    case LocalConstants.opened:
                        sign.setImageResource(R.drawable.acordeon_cierra);
                        break;
                    case LocalConstants.closed:
                        sign.setImageResource(R.drawable.acordeon_abre);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }
}
