package conse.nrc.org.co.consejo.Fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Pattern;

import conse.nrc.org.co.consejo.Activities.Avatar;
import conse.nrc.org.co.consejo.Activities.SelectContact;
import conse.nrc.org.co.consejo.R;
import conse.nrc.org.co.consejo.Utils.ConseApp;
import conse.nrc.org.co.consejo.Utils.DataBase;
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
    //EditText mEtBirthdate;
    //EditText mEtName;
    //EditText mEtContactPhone;
    //EditText mEtLastname;
    //EditText mEtDocumentNumber;
    EditText mEtEmail;
    EditText mEtPassword;
    EditText mEtPasswordConfirm;

    //Spinner mSpGender;
    //Spinner mSpDocumentType;
    //Spinner mSpEthnicGroup;
    //Spinner mSpState;
    //Spinner mSpCondition;
    //Spinner mSpCity;
    //Spinner mSpRole;


    //CheckBox mCbIsNrcBeneficiary;
    CheckBox mCbAcceptTermsConditions;
    Button mBtNext, mBtContactEdit, mBtAvatarEdit;
    FrameLayout mFlAvatar;
    ProgressDialog listener;
    String birthDateTosave;
    List<String> gender_list, document_type_list, ethnic_group_list, state_list, condition_list, city_list, role_list;
    DisplayImageOptions optionsPreview;
    private com.nostra13.universalimageloader.core.ImageLoader imageLdr;


    @Override
    public void onStart() {
        super.onStart();

        optionsPreview = new DisplayImageOptions.Builder()
                .showImageOnLoading(null)
                .showImageForEmptyUri(null)
                .showImageOnFail(null)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        imageLdr = ImageLoader.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.register_fragment, container, false);

        //mEtBirthdate = (EditText)mView.findViewById(R.id.et_birthdate);
        //mEtName = (EditText)mView.findViewById(R.id.et_name);
        //mEtLastname = (EditText)mView.findViewById(R.id.et_last_name);
        //mEtContactPhone = (EditText)mView.findViewById(R.id.et_contact_phone);
        //mEtDocumentNumber = (EditText)mView.findViewById(R.id.et_document_number);
        mEtEmail = (EditText)mView.findViewById(R.id.et_email);
        mEtPassword = (EditText)mView.findViewById(R.id.et_password);
        mEtPasswordConfirm = (EditText)mView.findViewById(R.id.et_confirm_password);

        //mSpGender = (Spinner)mView.findViewById(R.id.sp_gender);
        //mSpDocumentType = (Spinner)mView.findViewById(R.id.sp_document_type);
        //mSpEthnicGroup = (Spinner)mView.findViewById(R.id.sp_ethnic_group);
        //mSpState = (Spinner)mView.findViewById(R.id.sp_state);
        //mSpCondition = (Spinner)mView.findViewById(R.id.sp_condition_type);
        //mSpCity = (Spinner)mView.findViewById(R.id.sp_origin_town);
        //mSpRole = (Spinner)mView.findViewById(R.id.sp_user_profile);

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

        //mCbIsNrcBeneficiary = (CheckBox)mView.findViewById(R.id.cb_is_nrc_beneficiary);

        mBtNext = (Button)mView.findViewById(R.id.bt_register);
        mBtAvatarEdit = (Button)mView.findViewById(R.id.bt_avatar_edit);
        mBtContactEdit = (Button)mView.findViewById(R.id.bt_contact_edit);

        mFlAvatar = (FrameLayout)mView.findViewById(R.id.fl_avatar);

        mBtNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });

        mBtAvatarEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, Avatar.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        mBtContactEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, SelectContact.class);
                intent.putExtra(LocalConstants.EDITING_CONTACTS, true);
                startActivity(intent);
            }
        });


        /*mCbIsNrcBeneficiary.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mView.findViewById(R.id.ly_nrc_data).setVisibility(View.VISIBLE);
                } else{
                    mView.findViewById(R.id.ly_nrc_data).setVisibility(View.GONE);
                }
            }
        });*/

        listener = new ProgressDialog(mCtx);



        //Setting Calendar field
        setBirthdatePicker();

        if (editionMode){
            loadValues();
        } else {
            mView.findViewById(R.id.ly_nrc_data).setVisibility(View.GONE);
            mView.findViewById(R.id.fl_edit_avatar_frame).setVisibility(View.GONE);
//            mBtAvatarEdit.setVisibility(View.GONE);
            mBtContactEdit.setVisibility(View.GONE);

            TextView term = (TextView)mView.findViewById(R.id.tv_terms_text);
            Spanned tittleString;
            tittleString = Html.fromHtml(getString(R.string.accept_terms_conditions_checkbox1)
                    + " <FONT COLOR=#eb5c3f><a href=\""+ ConseApp.getAppConfiguration(mCtx).terms_condition_url +"\">"
                    + getString(R.string.accept_terms_conditions_checkbox2) + " </a></font>" + getString(R.string.accept_terms_conditions_checkbox3)
            );
            term.setText(tittleString);

            term.setMovementMethod(LinkMovementMethod.getInstance());
            term.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCbAcceptTermsConditions.setChecked(!(mCbAcceptTermsConditions.isChecked()));
                }
            });

            //mCbAcceptTermsConditions.setText(tittleString);

            //_cb_accept_term_conditions.setClickable(true);
            //mCbAcceptTermsConditions.setMovementMethod (LinkMovementMethod.getInstance());
        }

        return mView;
    }

    private void loadValues() {

        try {
            imageLdr = ImageLoader.getInstance();
            mFlAvatar.removeAllViews();
//            FrameLayout avatar = ConseApp.getAvatarFrame(mCtx, imageLdr, optionsPreview);
//            avatar.set
            mFlAvatar.addView(ConseApp.getAvatarFrame(mCtx, imageLdr, optionsPreview));
        } catch (Exception ea) {
            ea.printStackTrace();
        }



        Models.RegisterUserResponse user = ConseApp.getActualUser(mCtx);
        ((LinearLayout)mView.findViewById(R.id.ly_term_cond)).setVisibility(View.GONE);
//        mCbAcceptTermsConditions.setVisibility(View.GONE);
        mEtPassword.setVisibility(View.GONE);
        mEtPasswordConfirm.setVisibility(View.GONE);

        mBtNext.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_update));

        ((TextView)mView.findViewById(R.id.tv_profile_tittle)).setText(R.string.edit_profile_tittle);

        mEtEmail.setText(user.user.email);
        mEtEmail.setEnabled(false);

        /*mEtBirthdate.setText(user.profile.birthdate);
        mEtName.setText(user.user.first_name);
        mEtLastname.setText(user.user.last_name);

        if (user.profile.contact_phone != null){
            mEtContactPhone.setText(user.profile.contact_phone);
        }

        mSpGender.setSelection(user.profile.gender.id);
        Log.d("ProfileFragment", "Is NRC Beneficiary?"+user.profile.isNRCBeneficiary);

        if (user.profile.isNRCBeneficiary){
            mCbIsNrcBeneficiary.setChecked(true);
            Log.d("ProfileFragment", "Is NRC Beneficiary");

            try{
                mEtDocumentNumber.setText(user.profile.document_number);
            } catch (Exception ea){
                ea.printStackTrace();
            }

            try{
                mSpDocumentType.setSelection(ConseApp.getAppConfiguration(mCtx).getRoleIndexById(user.profile.document_type.id)+1);
            } catch (Exception ea){
                ea.printStackTrace();
            }

            try{
                mSpEthnicGroup.setSelection(ConseApp.getAppConfiguration(mCtx).getRoleIndexById(user.profile.ethnic_group.id)+1);
            } catch (Exception ea){
                ea.printStackTrace();
            }
            try{
                Log.d("ProfileFragment", "Lookin for state id: "
                        + ConseApp.getAppConfiguration(mCtx).getStateIndexById(user.profile.origin_city.state));
                mSpState.setSelection(ConseApp.getAppConfiguration(mCtx).getStateIndexById(user.profile.origin_city.state)+1);
                setCityAdapter(ConseApp.getAppConfiguration(mCtx).getCityForStateId(user.profile.origin_city.state));

            } catch (Exception ea){
                ea.printStackTrace();
            }
            try{
                mSpCondition.setSelection(ConseApp.getAppConfiguration(mCtx).getRoleIndexById(user.profile.condition.id)+1);

            } catch (Exception ea){
                ea.printStackTrace();
            }
            try{
                mSpRole.setSelection(ConseApp.getAppConfiguration(mCtx).getRoleIndexById(user.profile.role.id)+1);
//                mSpRole.setSelection(1);

            } catch (Exception ea){
                ea.printStackTrace();
            }
        } else {
            mView.findViewById(R.id.ly_nrc_data).setVisibility(View.GONE);
            Log.d("ProfileFragment", "Is not NRC Beneficiary");
        }*/
    }


    private void validateData() {

        boolean error = false;
        boolean password_confirm = false;
        int gender=0, documentType=0, ethnicGroup=0, geographicLocation=0, condition=0, originTown=0, role=0;

        /*if(mEtBirthdate.getText().length() == 0){
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

        if(mEtContactPhone.getText().length() == 0){
            mEtContactPhone.setError(getString(R.string.must_fill_field));
            error = true;
        }*/

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

        /*
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

            if (mSpState.getSelectedItemPosition() > 0) {
                geographicLocation = ConseApp.appConfiguration.city_list.get(mSpState.getSelectedItemPosition() - 1).id;
            } else {
                ((TextView) ((LinearLayout) (mSpState.getChildAt(0))).getChildAt(0)).setError(getString(R.string.must_select_option));
                error = true;
            }

            if (mSpCondition.getSelectedItemPosition() > 0) {
                condition = ConseApp.appConfiguration.condition_list.get(mSpCondition.getSelectedItemPosition() - 1).id;
            } else {
                ((TextView) ((LinearLayout) (mSpCondition.getChildAt(0))).getChildAt(0)).setError(getString(R.string.must_select_option));
                error = true;
            }

            if (true) {
                originTown = ConseApp.getAppConfiguration(mCtx).getCityByName(city_list.get(mSpCity.getSelectedItemPosition()),
                        state_list.get(mSpState.getSelectedItemPosition())).id;
//                originTown = ConseApp.appConfiguration.city_list.get(mSpCity.getSelectedItemPosition() - 1).id;
            } else {
                ((TextView) ((LinearLayout) (mSpCity.getChildAt(0))).getChildAt(0)).setError(getString(R.string.must_select_option));
                error = true;
            }

            if (mSpRole.getSelectedItemPosition() > 0) {
                role = ConseApp.appConfiguration.role_list.get(mSpRole.getSelectedItemPosition() - 1).id;
            } else {
                ((TextView) ((LinearLayout) (mSpRole.getChildAt(0))).getChildAt(0)).setError(getString(R.string.must_select_option));
                error = true;
            }
        }*/

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
        //user.first_name = mEtName.getText().toString();
        //user.last_name = mEtLastname.getText().toString();
        user.email = mEtEmail.getText().toString();
        //user.contact_phone = mEtContactPhone.getText().toString();
        user.birthdate = birthDateTosave;

        user.gender = gender;

        //user.isNRCBeneficiary = mCbIsNrcBeneficiary.isChecked();
//        user.contact_phone = ;
//        user.address = ;
        /*if(mCbIsNrcBeneficiary.isChecked()){
            user.document_number = mEtDocumentNumber.getText().toString();
            user.document_type = documentType;
            user.role = role;
            user.ethnic_group = ethnicGroup;
            user.condition = condition;
            user.origin_city = originTown;
            user.actual_city = geographicLocation;
        }*/
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
        state_list = new ArrayList<>();
        condition_list = new ArrayList<>();
        city_list = new ArrayList<>();
        role_list = new ArrayList<>();

        gender_list.add(getString(R.string.select_hint));
        document_type_list.add(getString(R.string.select_hint));
        ethnic_group_list.add(getString(R.string.select_hint));
        state_list.add(getString(R.string.select_hint));
        condition_list.add(getString(R.string.select_hint));
        city_list.add(getString(R.string.select_hint));
        role_list.add(getString(R.string.select_hint));

        if (ConseApp.appConfiguration != null) {

            if (ConseApp.appConfiguration.gender_list != null) {
                for (Models.Gender gender : ConseApp.appConfiguration.gender_list) {
                    gender_list.add(gender.name);
                }
            }
            if (ConseApp.appConfiguration.document_type_list != null) {
                for (Models.DocumentType documentType : ConseApp.appConfiguration.document_type_list) {
                    document_type_list.add(documentType.name);
                }
            }
            if (ConseApp.appConfiguration.ethnic_group_list != null) {
                for (Models.EthnicGroup ethnicGroup : ConseApp.appConfiguration.ethnic_group_list) {
                    ethnic_group_list.add(ethnicGroup.name);
                }
            }
            if (ConseApp.appConfiguration.state_list != null) {
                for (Models.State state : ConseApp.appConfiguration.state_list) {
                    state_list.add(state.name);
                }
            }
            if (ConseApp.appConfiguration.condition_list != null) {
                for (Models.Condition condition : ConseApp.appConfiguration.condition_list) {
                    condition_list.add(condition.name);
                }
            }
//        if(ConseApp.appConfiguration.city_list != null){
//            for (Models.City city: ConseApp.appConfiguration.city_list){
//                city_list.add(city.name);
//            }
//        }
            if (ConseApp.appConfiguration.role_list != null) {
                for (Models.Role role : ConseApp.appConfiguration.role_list) {
                    role_list.add(role.name);
                }
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
        //mSpGender.setAdapter(spinerGenderArrayAdapter);

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
        //mSpDocumentType.setAdapter(spinerDocumentTypeArrayAdapter);

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
        //mSpEthnicGroup.setAdapter(spinerEthnicGroupArrayAdapter);

        ArrayAdapter<String> spinerGeographicLocationArrayAdapter =
                new ArrayAdapter<String>(mCtx, R.layout.spinner_header_item, R.id.tv_direction, state_list){

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
        //mSpState.setAdapter(spinerGeographicLocationArrayAdapter);
        /*mSpState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0){
                    int state_id = ConseApp.getAppConfiguration(mCtx).getStateIdByName(state_list.get(position));
                    List<Models.CityConf> citites = ConseApp.getAppConfiguration(mCtx).getCityForStateId(state_id);
                    //List<Models.City> citites = ConseApp.getAppConfiguration(mCtx).getCityForStateName(state_list.get(position));
                    Log.d("ProfileFragment",
                            "Position selected: " + position
                                    +"State Id: " + state_id
                                    + "State name selected: " + state_list.get(position)
                                    + "Cities Size: " + citites.size());
                    setCityAdapter(citites);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

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
        //mSpCondition.setAdapter(spinerConditionArrayAdapter);


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
        //mSpRole.setAdapter(spinerRoleArrayAdapter);

    }

    private void setCityAdapter(List<Models.CityConf> citites){

        city_list = new ArrayList<String>();
        for (Models.CityConf ci : citites){
            city_list.add(ci.name);
            Log.d("ProfileFragment", "City Added" + ci.name);
        }
        Log.d("ProfileFragment", "City List Size" + city_list.size());

        ArrayAdapter<String> spinerOriginTownArrayAdapter =
            new ArrayAdapter<String>(mCtx, R.layout.spinner_header_item, R.id.tv_direction, city_list){

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
        //mSpCity.setAdapter(spinerOriginTownArrayAdapter);
        Models.RegisterUserResponse user  = ConseApp.getActualUser(mCtx);
        if (editionMode && ConseApp.getActualUser(mCtx).profile.isNRCBeneficiary){
            try{
                Log.d("ProfileFragment", "Looking for city position: "
                        + ConseApp.getAppConfiguration(mCtx).getCityIndexById(user.profile.origin_city.state,
                        user.profile.origin_city.id)+1);

                /*mSpCity.setSelection(ConseApp.getAppConfiguration(mCtx).getCityIndexById(user.profile.origin_city.state,
                        user.profile.origin_city.id));*/
            } catch (Exception ea){
                ea.printStackTrace();
            }
        }
    }

    private void setBirthdatePicker(){
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        final DatePickerDialog picker = new DatePickerDialog(mCtx, this, 1940,01,01);
        picker.getDatePicker().setMaxDate(System.currentTimeMillis());

        /*mEtBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picker.show();
            }
        });*/
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        int _birthYear = year;
        int _month = month + 1;
        int _day = dayOfMonth;
        birthDateTosave = _birthYear + "-" + _month +"-" + _day;
        //mEtBirthdate.setText(_day + "/" + _month +"/" + _birthYear);
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
                UtilsFunctions.saveSharedBoolean(mCtx, LocalConstants.USER_IS_IN_DEVICE, true);
                UtilsFunctions.saveSharedBoolean(mCtx, LocalConstants.IS_USER_LOGGED_IN, true);
                DataBase dataBase = new DataBase(mCtx);
                dataBase.clearTopicActivity();
                dataBase.clearPagesRead();
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

        switch (taskId) {
            case LocalConstants.REGISTER_USER_TASK_ID:
                Toast.makeText(mCtx, errorMsg, Toast.LENGTH_SHORT).show();
                break;
            case LocalConstants.PUT_USER_PROFILE_EDIT_TASK_ID:
                Toast.makeText(mCtx, errorMsg, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

    }
}
