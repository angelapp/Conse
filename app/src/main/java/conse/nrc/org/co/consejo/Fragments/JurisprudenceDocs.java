package conse.nrc.org.co.consejo.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
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
import conse.nrc.org.co.consejo.Utils.LocalConstants;
import conse.nrc.org.co.consejo.Utils.Models;
import conse.nrc.org.co.consejo.Utils.RequestTask;
import conse.nrc.org.co.consejo.Utils.ServerRequest;

/**
 * Created by apple on 12/28/17.
 */

public class JurisprudenceDocs extends Fragment implements RequestTask.OnRequestCompleted{

    private MainInterface mainInterface;
    Context mCtx;
    LinearLayout mLyPrincipalContent;
    ExpandableListView mElvMenuLeft;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    List<Models.DocumentTextType> documentLists = new ArrayList<>();
    LinkedHashMap<String, List<String>> expandableListDetail;

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

        View view =  inflater.inflate(R.layout.jurisprudence_docs_fragment, container, false);
        mLyPrincipalContent = (LinearLayout)view.findViewById(R.id.ly_content);
        mElvMenuLeft = (ExpandableListView) view.findViewById(R.id.elv_list);
        reload();


        return view;
    }

    public void reload(){
        try {
            if (documentLists.size() == 0) {
                listener = new ProgressDialog(mCtx);
                listener.setMessage(getString(R.string.getting_library_docs));
                new ServerRequest.GetLibraryDocs(mCtx, this, listener, LocalConstants.GET_LIBRARY_DOCS_TASK_ID).executeGetList();
            }
        }catch (Exception ea){
            ea.printStackTrace();
            mCtx = getContext();
        }
    }

    private void fillList() {
        ExpandableJurisprudenceDocsList data = new ExpandableJurisprudenceDocsList(mCtx);
        expandableListDetail = data.getData(documentLists);
        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
        expandableListAdapter = new JurisprudenceDocsExpandableAdapter(mCtx, expandableListTitle, expandableListDetail);
        mElvMenuLeft.setAdapter(expandableListAdapter);
        mElvMenuLeft.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                onMenuItemClick(groupPosition, childPosition);
                return false;
            }
        });

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

    private void onMenuItemClick(int groupPosition, int childPosition) {

        ShowDetail dialog = new ShowDetail();
        dialog.doc = documentLists.get(groupPosition).document_by_type.get(childPosition);
        dialog.show(getFragmentManager(), "");

    }

    private void share(String tag) {

        mainInterface.shareDoc(tag);


    }

    private void openFile(String tag) {
        mainInterface.openDoc(tag);
    }

    @Override
    public void onRequestResponse(Object response, int taskId) {
        switch (taskId){
            case LocalConstants.GET_LIBRARY_DOCS_TASK_ID:
                for (Models.DocumentTextType type : (Models.DocumentTextType[]) response){
                    documentLists.add(type);
                }
//                documentLists = (List< Models.DocumentTextType>) response;
//                Log.d("Jurisprudence Docs", "Library firts category: " + documentLists.get(0).name);
                fillList();
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestError(int errorCode, String errorMsg, int taskId) {
        switch (taskId){
            case LocalConstants.GET_LIBRARY_DOCS_TASK_ID:
                Toast.makeText(mCtx, errorMsg, Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }


    public static class ShowDetail extends DialogFragment{

        public static Models.Document doc;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.jurisprudence_doc_detail, container, false);

            ((TextView)view.findViewById(R.id.tv_doc_tittle)).setText(doc.name);
            ((TextView)view.findViewById(R.id.tv_doc_description)).setText(doc.description);
            Button btBack = (Button) view.findViewById(R.id.bt_download);


            btBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(doc.file)));
                }
            });

            return view;
        }


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Dialog dialog = super.onCreateDialog(savedInstanceState);
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialog.onBackPressed();
            dialog.cancel();
            return dialog;
        }

    }
}
