package conse.nrc.org.co.consejo.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import conse.nrc.org.co.consejo.Interfaces.AlertTestInterfaces;
import conse.nrc.org.co.consejo.R;

/**
 * Created by apple on 11/20/17.
 */

public class SendAlertFragment extends Fragment {

    View mView;
    Context mCtx;
    AlertTestInterfaces alertTestInterfaces;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.send_alert_test_fragment, container, false);

        Button btSendAlert = (Button) mView.findViewById(R.id.bt_send_alert);

        btSendAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchAlertDialog();
            }
        });

        return mView;
    }

    private void launchAlertDialog() {
        FragmentManager fm = getFragmentManager();
        AlertDialog alertDialog= new AlertDialog();
        alertDialog.show(fm, "");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCtx = context;
        alertTestInterfaces = (AlertTestInterfaces) mCtx;
    }
}

