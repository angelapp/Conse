package conse.nrc.org.co.consejo.Fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Pattern;

import conse.nrc.org.co.consejo.Activities.SelectContact;
import conse.nrc.org.co.consejo.R;
import conse.nrc.org.co.consejo.Utils.ConseApp;
import conse.nrc.org.co.consejo.Utils.LocalConstants;
import conse.nrc.org.co.consejo.Utils.Models;
import conse.nrc.org.co.consejo.Utils.RequestTask;
import conse.nrc.org.co.consejo.Utils.ServerRequest;
import conse.nrc.org.co.consejo.Utils.UtilsFunctions;

/**
 * Created by apple on 12/9/17.
 */

public class ProfileFragment extends Fragment implements DatePickerDialog.OnDateSetListener, RequestTask.OnRequestCompleted{

    View mView;
    Context mCtx;
    public boolean editionMode = false;
    EditText mEtBirthdate, mEtName, mEtLastname, mEtDocumentNumber, mEtEmail, mEtPassword, mEtPasswordConfirm;
    Spinner mSpGender, mSpDocumentType, mSpEthnicGroup, mSpGeographicLocation, mSpCondition, mSpOriginTown, mSpRole;
    CheckBox mCbIsNrcBeneficiary, mCbAcceptTermsConditions;
    Button mBtNext;
    ProgressDialog listener;
    String birthDateTosave;
    List<String> gender_list, document_type_list, ethnic_group_list, geographic_location_list, condition_list, origin_town_list, role_list;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.register_fragment, container, false);

        mEtBirthdate = (EditText)mView.findViewById(R.id.et_birthdate);
        mEtName = (EditText)mView.findViewById(R.id.et_name);
        mEtLastname = (EditText)mView.findViewById(R.id.et_last_name);
        mEtDocumentNumber = (EditText)mView.findViewById(R.id.et_document_number);
        mEtEmail = (EditText)mView.findViewById(R.id.et_email);
        mEtPassword = (EditText)mView.findViewById(R.id.et_password);
        mEtPasswordConfirm = (EditText)mView.findViewById(R.id.et_confirm_password);

        mSpGender = (Spinner)mView.findViewById(R.id.sp_gender);
        mSpDocumentType = (Spinner)mView.findViewById(R.id.sp_document_type);
        mSpEthnicGroup = (Spinner)mView.findViewById(R.id.sp_ethnic_group);
        mSpGeographicLocation = (Spinner)mView.findViewById(R.id.sp_geographic_location);
        mSpCondition = (Spinner)mView.findViewById(R.id.sp_condition_type);
        mSpOriginTown = (Spinner)mView.findViewById(R.id.sp_origin_town);
        mSpRole = (Spinner)mView.findViewById(R.id.sp_user_profile);

        fillSpinnersData();

        fillSpinners();

        mEtPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    Toast.makeText(mCtx, ConseApp.getAppConfiguration(mCtx).psw_error_recomendation, Toast.LENGTH_LONG).show();
                }
            }
        });

        mCbAcceptTermsConditions = (CheckBox)mView.findViewById(R.id.cb_accept_terms_conditions);
        mCbIsNrcBeneficiary = (CheckBox)mView.findViewById(R.id.cb_is_nrc_beneficiary);

        mBtNext = (Button)mView.findViewById(R.id.bt_register);

        mBtNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });


        mCbIsNrcBeneficiary.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mView.findViewById(R.id.ly_nrc_data).setVisibility(View.VISIBLE);
                } else{
                    mView.findViewById(R.id.ly_nrc_data).setVisibility(View.GONE);
                }
            }
        });

        listener = new ProgressDialog(mCtx);



        //Setting Calendar field
        setBirthdatePicker();

        if (editionMode){
            loadValues();
        } else {
            mView.findViewById(R.id.ly_nrc_data).setVisibility(View.GONE);
        }

        return mView;
    }

    private void loadValues() {
        Models.RegisterUserResponse user = ConseApp.getActualUser(mCtx);
        mCbAcceptTermsConditions.setVisibility(View.GONE);
        mEtPassword.setVisibility(View.GONE);
        mEtPasswordConfirm.setVisibility(View.GONE);

        mEtEmail.setText(user.user.email);
        mEtEmail.setEnabled(false);

        mEtBirthdate.setText(user.profile.birthdate);
        mEtName.setText(user.user.first_name);
        mEtLastname.setText(user.user.last_name);
        mSpGender.setSelection(user.profile.gender.id);


        if (user.profile.isNRCBeneficiary){
            mCbIsNrcBeneficiary.setChecked(true);

            try{
                mEtDocumentNumber.setText(user.profile.document_number);
            } catch (Exception ea){
                ea.printStackTrace();
            }

            try{
                mSpDocumentType.setSelection(user.profile.document_type.id);
            } catch (Exception ea){
                ea.printStackTrace();
            }

            try{
                mSpEthnicGroup.setSelection(user.profile.ethnic_group.id);
            } catch (Exception ea){
                ea.printStackTrace();
            }
            try{
                mSpGeographicLocation.setSelection(user.profile.origin_city.id);

            } catch (Exception ea){
                ea.printStackTrace();
            }
            try{
                mSpCondition.setSelection(user.profile.condition.id);

            } catch (Exception ea){
                ea.printStackTrace();
            }
            try{

                mSpOriginTown.setSelection(user.profile.origin_city.id);
            } catch (Exception ea){
                ea.printStackTrace();
            }
            try{
                mSpRole.setSelection(user.profile.role.id);

            } catch (Exception ea){
                ea.printStackTrace();
            }
        } else {
            mView.findViewById(R.id.ly_nrc_data).setVisibility(View.GONE);
        }
    }


    private void validateData() {

        boolean error = false;
        boolean password_confirm = false;
        int gender=0, documentType=0, ethnicGroup=0, geographicLocation=0, condition=0, originTown=0, role=0;

        if(mEtBirthdate.getText().length() == 0){
            mEtBirthdate.setError(getString(R.string.must_fill_field));
            error = true;
        }
        if(mEtName.getText().length() == 0){
            mEtName.setError(getString(R.string.must_fill_field));
            error = true;
        }
        if(mEtLastname.getText().length() == 0){
            mEtLastname.setError(getString(R.string.must_fill_field));
            error = true;
        }

        if(mEtEmail.getText().length() == 0){
            mEtEmail.setError(getString(R.string.must_fill_field));
            error = true;
        }

        if(!editionMode) {

            if (mEtPassword.getText().length() == 0) {
                mEtPassword.setError(getString(R.string.must_fill_field));
                error = true;
            } else if(!Pattern.compile(ConseApp.getAppConfiguration(mCtx).psw_regular_expression).matcher(mEtPassword.getText().toString()).matches()){
                Toast.makeText(mCtx, ConseApp.getAppConfiguration(mCtx).psw_error_recomendation, Toast.LENGTH_LONG ).show();
                error = true;
            }
            else {
                if (mEtPassword.getText().toString().equals(mEtPasswordConfirm.getText().toString())) {
                    password_confirm = true;
                } else {
                    password_confirm = false;
                }
            }
        } else {
            password_confirm = true;
        }

        if (mSpGender.getSelectedItemPosition() > 0){
            gender = ConseApp.appConfiguration.gender_list.get(mSpGender.getSelectedItemPosition()-1).id;
        } else {
            ((TextView)((LinearLayout)(mSpGender.getChildAt(0))).getChildAt(0)).setError(getString(R.string.must_select_option));
            error = true;
        }


        if(mCbIsNrcBeneficiary.isChecked()) {

            if(mEtDocumentNumber.getText().length() == 0){
                mEtDocumentNumber.setError(getString(R.string.must_fill_field));
                error = true;
            }

            if (mSpDocumentType.getSelectedItemPosition() > 0){
                documentType = ConseApp.appConfiguration.document_type_list.get(mSpDocumentType.getSelectedItemPosition()-1).id;
            } else {
                ((TextView)((LinearLayout)(mSpDocumentType.getChildAt(0))).getChildAt(0)).setError(getString(R.string.must_select_option));
                error = true;
            }

            if (mSpEthnicGroup.getSelectedItemPosition() > 0) {
                ethnicGroup = ConseApp.appConfiguration.ethnic_group_list.get(mSpEthnicGroup.getSelectedItemPosition() - 1).id;
            } else {
                ((TextView) ((LinearLayout) (mSpEthnicGroup.getChildAt(0))).getChildAt(0)).setError(getString(R.string.must_select_option));
                error = true;
            }

            if (mSpGeographicLocation.getSelectedItemPosition() > 0) {
                geographicLocation = ConseApp.appConfiguration.city_list.get(mSpGeographicLocation.getSelectedItemPosition() - 1).id;
            } else {
                ((TextView) ((LinearLayout) (mSpGeographicLocation.getChildAt(0))).getChildAt(0)).setError(getString(R.string.must_select_option));
                error = true;
            }

            if (mSpCondition.getSelectedItemPosition() > 0) {
                condition = ConseApp.appConfiguration.condition_list.get(mSpCondition.getSelectedItemPosition() - 1).id;
            } else {
                ((TextView) ((LinearLayout) (mSpCondition.getChildAt(0))).getChildAt(0)).setError(getString(R.string.must_select_option));
                error = true;
            }

            if (mSpOriginTown.getSelectedItemPosition() > 0) {
                originTown = ConseApp.appConfiguration.city_list.get(mSpOriginTown.getSelectedItemPosition() - 1).id;
            } else {
                ((TextView) ((LinearLayout) (mSpOriginTown.getChildAt(0))).getChildAt(0)).setError(getString(R.string.must_select_option));
                error = true;
            }

            if (mSpRole.getSelectedItemPosition() > 0) {
                role = ConseApp.appConfiguration.role_list.get(mSpRole.getSelectedItemPosition() - 1).id;
            } else {
                ((TextView) ((LinearLayout) (mSpRole.getChildAt(0))).getChildAt(0)).setError(getString(R.string.must_select_option));
                error = true;
            }
        }

        if(!mCbAcceptTermsConditions.isChecked() && !editionMode){
            mCbAcceptTermsConditions.setError(getString(R.string.you_must_accept_t_c));
            error = true;
        }

        if(!error){
            if(password_confirm) {
                registerUser(gender, documentType, ethnicGroup, condition, role, geographicLocation, originTown);
            } else {
                Toast.makeText(mCtx, getString(R.string.password_not_matches), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(mCtx, getString(R.string.must_complete_information), Toast.LENGTH_SHORT).show();
        }
    }

    private void registerUser(int gender, int documentType, int ethnicGroup,
                              int condition, int role, int geographicLocation, int originTown) {

        Models.RegisterUserProfileModel user = new Models.RegisterUserProfileModel();
        user.first_name = mEtName.getText().toString();
        user.last_name = mEtLastname.getText().toString();
        user.email = mEtEmail.getText().toString();
        user.birthdate = birthDateTosave;

        user.gender = gender;

        user.isNRCBeneficiary = mCbIsNrcBeneficiary.isChecked();
//        user.contact_phone = ;
//        user.address = ;
        if(mCbIsNrcBeneficiary.isChecked()){
            user.document_number = mEtDocumentNumber.getText().toString();
            user.document_type = documentType;
            user.role = role;
            user.ethnic_group = ethnicGroup;
            user.condition = condition;
            user.origin_city = originTown;
            user.actual_city = geographicLocation;
        }
//        user.latitude = ;
//        user.longitude = ;

        if (!editionMode){
            user.password = mEtPassword.getText().toString();
            listener.setMessage(getString(R.string.registering_user));
            new ServerRequest.RegisterUser(mCtx,this, listener, LocalConstants.REGISTER_USER_TASK_ID,user).executePost();
        } else{
            listener.setMessage(getString(R.string.updating_profile_data));
            new ServerRequest.UpdateUserProfile(mCtx, this,listener,LocalConstants.PUT_USER_PROFILE_EDIT_TASK_ID, user).executePut();
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCtx = context;
    }

    private void fillSpinnersData() {
        gender_list = new ArrayList<>();
        document_type_list = new ArrayList<>();
        ethnic_group_list = new ArrayList<>();
        geographic_location_list = new ArrayList<>();
        condition_list = new ArrayList<>();
        origin_town_list = new ArrayList<>();
        role_list = new ArrayList<>();

        gender_list.add(getString(R.string.select_hint));
        document_type_list.add(getString(R.string.select_hint));
        ethnic_group_list.add(getString(R.string.select_hint));
        geographic_location_list.add(getString(R.string.select_hint));
        condition_list.add(getString(R.string.select_hint));
        origin_town_list.add(getString(R.string.select_hint));
        role_list.add(getString(R.string.select_hint));

        if(ConseApp.appConfiguration.gender_list != null){
            for (Models.Gender gender : ConseApp.appConfiguration.gender_list){
                gender_list.add(gender.name);
            }
        }
        if(ConseApp.appConfiguration.document_type_list != null){
            for (Models.DocumentType documentType : ConseApp.appConfiguration.document_type_list){
                document_type_list.add(documentType.name);
            }
        }
        if(ConseApp.appConfiguration.ethnic_group_list != null){
            for (Models.EthnicGroup ethnicGroup : ConseApp.appConfiguration.ethnic_group_list){
                ethnic_group_list.add(ethnicGroup.name);
            }
        }
        if(ConseApp.appConfiguration.city_list != null){
            for (Models.City city: ConseApp.appConfiguration.city_list){
                geographic_location_list.add(city.name);
            }
        }
        if(ConseApp.appConfiguration.condition_list != null){
            for (Models.Condition condition: ConseApp.appConfiguration.condition_list){
                condition_list.add(condition.name);
            }
        }
        if(ConseApp.appConfiguration.city_list != null){
            for (Models.City city: ConseApp.appConfiguration.city_list){
                origin_town_list.add(city.name);
            }
        }
        if(ConseApp.appConfiguration.role_list!= null){
            for (Models.Role role: ConseApp.appConfiguration.role_list){
                role_list.add(role.name);
            }
        }

    }

    private void fillSpinners() {

        Log.d("Register", gender_list.size() + " - " + document_type_list.size());

        ArrayAdapter<String> spinerGenderArrayAdapter =
                new ArrayAdapter<String>(mCtx, R.layout.spinner_header_item, R.id.tv_direction, gender_list){

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
        spinerGenderArrayAdapter.setDropDownViewResource(R.layout.spinner_direction_list_item);
        mSpGender.setAdapter(spinerGenderArrayAdapter);

        ArrayAdapter<String> spinerDocumentTypeArrayAdapter =
                new ArrayAdapter<String>(mCtx, R.layout.spinner_header_item, R.id.tv_direction, document_type_list){

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
        spinerDocumentTypeArrayAdapter.setDropDownViewResource(R.layout.spinner_direction_list_item);
        mSpDocumentType.setAdapter(spinerDocumentTypeArrayAdapter);

        ArrayAdapter<String> spinerEthnicGroupArrayAdapter =
                new ArrayAdapter<String>(mCtx, R.layout.spinner_header_item, R.id.tv_direction, ethnic_group_list){

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
        spinerEthnicGroupArrayAdapter.setDropDownViewResource(R.layout.spinner_direction_list_item);
        mSpEthnicGroup.setAdapter(spinerEthnicGroupArrayAdapter);

        ArrayAdapter<String> spinerGeographicLocationArrayAdapter =
                new ArrayAdapter<String>(mCtx, R.layout.spinner_header_item, R.id.tv_direction, geographic_location_list){

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
        spinerGeographicLocationArrayAdapter.setDropDownViewResource(R.layout.spinner_direction_list_item);
        mSpGeographicLocation.setAdapter(spinerGeographicLocationArrayAdapter);

        ArrayAdapter<String> spinerConditionArrayAdapter =
                new ArrayAdapter<String>(mCtx, R.layout.spinner_header_item, R.id.tv_direction, condition_list){

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
        spinerConditionArrayAdapter.setDropDownViewResource(R.layout.spinner_direction_list_item);
        mSpCondition.setAdapter(spinerConditionArrayAdapter);

        ArrayAdapter<String> spinerOriginTownArrayAdapter =
                new ArrayAdapter<String>(mCtx, R.layout.spinner_header_item, R.id.tv_direction, origin_town_list){

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
        spinerOriginTownArrayAdapter.setDropDownViewResource(R.layout.spinner_direction_list_item);
        mSpOriginTown.setAdapter(spinerOriginTownArrayAdapter);

        ArrayAdapter<String> spinerRoleArrayAdapter =
                new ArrayAdapter<String>(mCtx, R.layout.spinner_header_item, R.id.tv_direction, role_list){

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
        spinerRoleArrayAdapter.setDropDownViewResource(R.layout.spinner_direction_list_item);
        mSpRole.setAdapter(spinerRoleArrayAdapter);

    }

    private void setBirthdatePicker(){
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        final DatePickerDialog picker = new DatePickerDialog(mCtx, this, 1940,01,01);
        picker.getDatePicker().setMaxDate(System.currentTimeMillis());

        mEtBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picker.show();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        int _birthYear = year;
        int _month = month + 1;
        int _day = dayOfMonth;
        birthDateTosave = _birthYear + "-" + _month +"-" + _day;
        mEtBirthdate.setText(_day + "/" + _month +"/" + _birthYear);
    }

    private void goToSelectContacts() {
        Intent next = new Intent(mCtx, SelectContact.class);
        startActivity(next);
        //this.finish();
    }

    @Override
    public void onRequestResponse(Object response, int taskId) {
        switch (taskId){
            case LocalConstants.REGISTER_USER_TASK_ID:
                Models.RegisterUserResponse res = (Models.RegisterUserResponse) response;
                ConseApp.setActualUser(mCtx, res);
                UtilsFunctions.saveSharedString(mCtx, LocalConstants.USER_PSW, mEtPassword.getText().toString());
                //UtilsFunctions.saveSharedString(this, LocalConstants.USER_TOKEN, res.token);
                goToSelectContacts();
                break;
            case LocalConstants.PUT_USER_PROFILE_EDIT_TASK_ID:
                Models.RegisterUserResponse updated = (Models.RegisterUserResponse) response;
                ConseApp.setActualUser(mCtx, updated);
                Toast.makeText(mCtx,getString(R.string.updated_profile_info), Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestError(int errorCode, String errorMsg, int taskId) {

    }
}
