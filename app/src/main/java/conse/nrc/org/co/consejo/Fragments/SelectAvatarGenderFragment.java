package conse.nrc.org.co.consejo.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import conse.nrc.org.co.consejo.Interfaces.AvatarInterfaces;
import conse.nrc.org.co.consejo.R;
import conse.nrc.org.co.consejo.Utils.LocalConstants;
import conse.nrc.org.co.consejo.Utils.UtilsFunctions;

import static conse.nrc.org.co.consejo.Utils.LocalConstants.FEMALE;
import static conse.nrc.org.co.consejo.Utils.LocalConstants.MALE;

/**
 * Created by apple on 11/22/17.
 */

public class SelectAvatarGenderFragment extends Fragment{

    View mView;
    Context mCtx;
    AvatarInterfaces avatarInterfaces;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.select_avatar_gender_fragment, container, false);

        Button btMale = (Button) mView.findViewById(R.id.bt_avatar_male);
        Button btFemale = (Button) mView.findViewById(R.id.bt_avatar_female);

        btMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchAvatarSelection(MALE);
                UtilsFunctions.saveSharedInteger(mCtx, LocalConstants.AVATAR_GENDER_ID_, MALE);
            }
        });

        btFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchAvatarSelection(FEMALE);
                UtilsFunctions.saveSharedInteger(mCtx, LocalConstants.AVATAR_GENDER_ID_, FEMALE);
            }
        });

        return mView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCtx = context;
        avatarInterfaces = (AvatarInterfaces) mCtx;
    }

    private void launchAvatarSelection(int gender) {
        avatarInterfaces.genderSelected(gender);
    }
}
