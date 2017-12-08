package conse.nrc.org.co.consejo.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import conse.nrc.org.co.consejo.R;
import conse.nrc.org.co.consejo.Utils.ConseApp;

/**
 * Created by apple on 12/5/17.
 */

public class ProfileEditionFragment extends Fragment {

    View mView;
    Context mCtx;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.contact_form_fragment, container, false);

        ((TextView)mView.findViewById(R.id.tv_about_nrc)).setText(ConseApp.appConfiguration.about_noruegan_council);

        return mView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCtx = context;
    }
}
