package conse.nrc.org.co.consejo.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import conse.nrc.org.co.consejo.Interfaces.MainInterface;
import conse.nrc.org.co.consejo.R;
import conse.nrc.org.co.consejo.Utils.ConseApp;
import conse.nrc.org.co.consejo.Utils.LocalConstants;
import conse.nrc.org.co.consejo.Utils.Models;
import conse.nrc.org.co.consejo.Utils.RequestTask;
import conse.nrc.org.co.consejo.Utils.ServerRequest;
import conse.nrc.org.co.consejo.Utils.UtilsFunctions;

/**
 * Created by apple on 12/8/17.
 */

public class ContactFormFragment extends Fragment implements RequestTask.OnRequestCompleted{

    View mView;
    Context mCtx;
    ProgressDialog listener;
    List<String> contactFormTypeList = new ArrayList<>();
    Spinner mSpContactFormType;
    EditText mEtMessage, mEtPhone;
    int contactType;
    MainInterface mainInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.contact_form_fragment, container, false);
        mSpContactFormType = (Spinner) mView.findViewById(R.id.sp_contact_form_type);
        mEtMessage = (EditText) mView.findViewById(R.id.et_contact_message);
        mEtPhone = (EditText) mView.findViewById(R.id.et_contact_phone);
        fillSpinnersData();
        fillSpinner();

        ((Button)mView.findViewById(R.id.bt_send_contact)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });

        return mView;
    }

    private void validateData() {

        boolean error = false;

        if(mEtMessage.getText().length() == 0){
            mEtMessage.setError(getString(R.string.must_fill_field));
            error = true;
        }
        if(mEtPhone.getText().length() == 0){
            mEtPhone.setError(getString(R.string.must_fill_field));
            error = true;
        }

        if (mSpContactFormType.getSelectedItemPosition() > 0){
            contactType = ConseApp.appConfiguration.contact_form_type_list.get(mSpContactFormType.getSelectedItemPosition()-1).id;
        } else {
            ((TextView)((LinearLayout)(mSpContactFormType.getChildAt(0))).getChildAt(0)).setError(getString(R.string.must_select_option));
            error = true;
        }

        if(!error){
            sendContactForm();

        } else {
            Toast.makeText(mCtx, getString(R.string.must_complete_information), Toast.LENGTH_SHORT).show();
        }

    }

    private void sendContactForm() {

        Models.ContactForm form = new Models.ContactForm();
        form.user = ConseApp.user.user.id;
        form.message_type = contactType;
        form.detail = String.format(getString(R.string.message_template), ConseApp.getActualUser(mCtx).user.first_name,
                ConseApp.getActualUser(mCtx).user.last_name, ConseApp.getActualUser(mCtx).user.email,
                mEtPhone.getText().toString(), mEtMessage.getText().toString());

        listener = new ProgressDialog(mCtx);
        listener.setMessage(getString(R.string.sending_contact_form));

        new ServerRequest.PostContactForm(mCtx, this, listener, LocalConstants.CONTACT_FORM_TASK_ID, form).executePost();

    }

    private void fillSpinner() {
        ArrayAdapter<String> spinerContactFormTypeArrayAdapter =
                new ArrayAdapter<String>(mCtx, R.layout.spinner_header_item, R.id.tv_direction, contactFormTypeList){
                    @Override
                    public boolean isEnabled(int position){
                        if(position == 0)
                        {
                            return false;
                        }
                        else
                        {
                            return true;
                        }
                    }
                };
        spinerContactFormTypeArrayAdapter.setDropDownViewResource(R.layout.spinner_direction_list_item);
        mSpContactFormType.setAdapter(spinerContactFormTypeArrayAdapter);
    }

    private void fillSpinnersData() {
        contactFormTypeList.clear();
        contactFormTypeList.add(getString(R.string.select_hint));
        if(ConseApp.getAppConfiguration(mCtx).contact_form_type_list != null){
            for (Models.ContactFormType type : ConseApp.getAppConfiguration(mCtx).contact_form_type_list){
                contactFormTypeList.add(type.name);
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCtx = context;
        mainInterface = (MainInterface) mCtx;
    }

    @Override
    public void onRequestResponse(Object response, int taskId) {
        switch (taskId){
            case LocalConstants.CONTACT_FORM_TASK_ID:
                Toast.makeText(mCtx, getString(R.string.send_contact_success), Toast.LENGTH_SHORT).show();
                mainInterface.setPreviousFragment();
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestError(int errorCode, String errorMsg, int taskId) {

        switch (taskId){
            case LocalConstants.CONTACT_FORM_TASK_ID:
                Toast.makeText(mCtx, errorMsg, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

    }
}
