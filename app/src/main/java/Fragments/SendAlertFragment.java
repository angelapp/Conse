package Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import conse.nrc.org.co.conse.R;

/**
 * Created by apple on 11/20/17.
 */

public class SendAlertFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.send_alert_fragment, container, false);

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
}
