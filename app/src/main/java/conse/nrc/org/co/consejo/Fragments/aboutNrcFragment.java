package conse.nrc.org.co.consejo.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import conse.nrc.org.co.consejo.Interfaces.AlertTestInterfaces;
import conse.nrc.org.co.consejo.R;
import conse.nrc.org.co.consejo.Utils.ConseApp;

/**
 * Created by apple on 12/4/17.
 */

public class aboutNrcFragment extends Fragment {

    View mView;
    Context mCtx;
    AlertTestInterfaces alertTestInterfaces;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.about_nrc_fragment, container, false);

        ((TextView)mView.findViewById(R.id.tv_about_nrc)).setText(ConseApp.appConfiguration.about_noruegan_council);

        return mView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCtx = context;
        //alertTestInterfaces = (AlertTestInterfaces) mCtx;
    }
}

