package conse.nrc.org.co.consejo.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
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
import conse.nrc.org.co.consejo.Utils.ExpandableShieldsList;
import conse.nrc.org.co.consejo.Utils.GeocodingLocation;
import conse.nrc.org.co.consejo.Utils.JurisprudenceDocsExpandableAdapter;
import conse.nrc.org.co.consejo.Utils.LocalConstants;
import conse.nrc.org.co.consejo.Utils.Models;
import conse.nrc.org.co.consejo.Utils.RequestTask;
import conse.nrc.org.co.consejo.Utils.ServerRequest;
import conse.nrc.org.co.consejo.Utils.ShieldListExpandableAdapter;
import conse.nrc.org.co.consejo.Utils.UtilsFunctions;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.R.id.list;
import static android.location.LocationManager.GPS_PROVIDER;
import static android.location.LocationManager.NETWORK_PROVIDER;
import static com.android.volley.VolleyLog.TAG;

/**
 * Created by apple on 12/28/17.
 */

public class ShieldsList extends Fragment implements RequestTask.OnRequestCompleted{

    private MainInterface mainInterface;
    Context mCtx;
    LinearLayout mLyPrincipalContent;
    ExpandableListView mElvMenuLeft;
    ExpandableListAdapter expandableListAdapter;
    TextView mTvUbication;
    List<String> expandableListTitle;
    List<Models.OrganizationType> documentLists = new ArrayList<>();
    LinkedHashMap<String, List<Models.CorporatePhoneBook>> expandableListDetail;
    LocationManager mLocManager;

    ProgressDialog listener;


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        reload();

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

        View view =  inflater.inflate(R.layout.shield_list_fragment, container, false);
        mLyPrincipalContent = (LinearLayout)view.findViewById(R.id.ly_content);
        mElvMenuLeft = (ExpandableListView) view.findViewById(R.id.elv_list);

        mTvUbication = (TextView)view.findViewById(R.id.tv_ubication_name);
        mLocManager = (LocationManager)
                mCtx.getSystemService(Context.LOCATION_SERVICE);

        reload();


        return view;
    }

    public void reload(){
        float[] coordinates = UtilsFunctions.requestCoordinates(mCtx);
        if (documentLists.size()==0) {
            listener = new ProgressDialog(mCtx);
            listener.setMessage(getString(R.string.getting_shield_list));
            if (coordinates.length > 0){
                Log.d("Shields List", "Of course we have coordinates: " + coordinates[0] + ", " + coordinates[1]);
                new ServerRequest.GetShieldList(mCtx, this, listener, LocalConstants.GET_SHIELDS_TASK_ID, coordinates[0], coordinates[1]).executeGetList();
            } else{
                Log.d("Shields List", "We dont have coordinates");
                new ServerRequest.GetShieldList(mCtx, this, listener, LocalConstants.GET_SHIELDS_TASK_ID, 0,0).executeGetList();
            }

        } else {
            fillList();
        }
        if (coordinates.length > 0){
            updateUbicationField((double) coordinates[0],(double) coordinates[1]);
        }
    }

    private void fillList() {
        ExpandableShieldsList data = new ExpandableShieldsList(mCtx);
        expandableListDetail = data.getData(documentLists);
        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
        expandableListAdapter = new ShieldListExpandableAdapter(mCtx, expandableListTitle, expandableListDetail);
        mElvMenuLeft.setAdapter(expandableListAdapter);
//        mElvMenuLeft.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v,
//                                        int groupPosition, int childPosition, long id) {
//                onMenuItemClick(groupPosition, childPosition);
//                return false;
//            }
//        });

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

//    private void onMenuItemClick(int groupPosition, int childPosition) {
//
//        ShowDetail dialog = new ShowDetail();
//        dialog.doc = documentLists.get(groupPosition).document_by_type.get(childPosition);
//        dialog.show(getFragmentManager(), "");
//
//    }


    @Override
    public void onRequestResponse(Object response, int taskId) {
        switch (taskId){
            case LocalConstants.GET_SHIELDS_TASK_ID:
                for (Models.OrganizationType type : (Models.OrganizationType[]) response){
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
            case LocalConstants.GET_SHIELDS_TASK_ID:
                Toast.makeText(mCtx, errorMsg, Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }


//    public static class ShowDetail extends DialogFragment{
//
//        public static Models.Document doc;
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
//            View view = inflater.inflate(R.layout.jurisprudence_doc_detail, container, false);
//
//            ((TextView)view.findViewById(R.id.tv_doc_tittle)).setText(doc.name);
//            ((TextView)view.findViewById(R.id.tv_doc_description)).setText(doc.description);
//            Button btBack = (Button) view.findViewById(R.id.bt_download);
//
//
//            btBack.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(doc.file)));
//                }
//            });
//
//            return view;
//        }
//
//
//        @Override
//        public Dialog onCreateDialog(Bundle savedInstanceState) {
//            Dialog dialog = super.onCreateDialog(savedInstanceState);
//            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//            dialog.onBackPressed();
//            dialog.cancel();
//            return dialog;
//        }
//
//    }


    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            Bundle bundle;
            switch (message.what) {
                case 1:
                    bundle = message.getData();
                    Double latitude = bundle.getDouble(LocalConstants.LATITUDE);
                    Double longitude = bundle.getDouble(LocalConstants.LONGITUDE);
                    Log.d("ShieldsList", "Latitude: " + latitude + " Longitude: " + longitude);
                    break;
                case 2:
                    bundle = message.getData();
                    String resp = bundle.getString("city");
                    mTvUbication.setText(resp);
                    Log.d("ShieldList", "Address received: " + resp);

                    break;
                default:
                   break;
            }
        }
    }

    private void updateUbicationField(Double lat, Double lng) {
        try{
            GeocodingLocation getAddress = new GeocodingLocation();
            getAddress.getCityFromLocation(lat, lng, mCtx, new GeocoderHandler());
        } catch (Exception ea){
            ea.printStackTrace();
        }
    }
}
