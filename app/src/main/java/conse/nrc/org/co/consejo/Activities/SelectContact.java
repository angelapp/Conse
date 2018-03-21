package conse.nrc.org.co.consejo.Activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import conse.nrc.org.co.consejo.Utils.LocalConstants;
import conse.nrc.org.co.consejo.Utils.UtilsFunctions;
import conse.nrc.org.co.consejo.R;

import static conse.nrc.org.co.consejo.Utils.LocalConstants.CONTACT_ID_;
import static conse.nrc.org.co.consejo.Utils.LocalConstants.CONTACT_NAME_;
import static conse.nrc.org.co.consejo.Utils.LocalConstants.CONTACT_NUMBER_;
import static conse.nrc.org.co.consejo.Utils.LocalConstants.CONTACT_SIZE;
import static conse.nrc.org.co.consejo.Utils.LocalConstants.MAX_CONTACT_NUMBER;
import static conse.nrc.org.co.consejo.Utils.LocalConstants.MIN_CONTACT_NUMBER;

public class SelectContact extends AppCompatActivity {

    public static boolean editingContacts = false;
    Button mBtAddContact, mBtNext;
    LinearLayout mLyContactList;
    TextView mTvPreviewMessage, mTvTittle;
    int mContactInEdition;
    boolean inEditionMode;
    View editingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_select_contact);
        mBtAddContact = (Button) findViewById(R.id.bt_add_contact);
        mBtNext = (Button)findViewById(R.id.bt_next);
        mLyContactList = (LinearLayout) findViewById(R.id.ly_contact_list);
        mTvPreviewMessage = (TextView) findViewById(R.id.tv_add_contact_message);
        mTvTittle = (TextView)findViewById(R.id.tv_add_contacts_tittle);

        editingContacts = getIntent().getBooleanExtra(LocalConstants.EDITING_CONTACTS, false);

        if (editingContacts){
            mTvTittle.setText(getString(R.string.edit_contacts_tittle));
            mBtNext.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_end_contact_list_edition));
            loadValues();
        }

        inEditionMode = false;
        mBtNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNext();
            }
        });

        mBtNext.setVisibility(View.GONE);

        mBtAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mLyContactList.getChildCount()<= MAX_CONTACT_NUMBER - 1 ) {
                    addContact();
                }
            }
        });
    }

    @Override
    public void onBackPressed(){
        if(mLyContactList.getChildCount()>=MIN_CONTACT_NUMBER){
            super.onBackPressed();
        } else {
            Toast.makeText(this, getString(R.string.add_contact_message), Toast.LENGTH_LONG).show();
        }
    }

    private void loadValues() {
        for (int i = 1; i<=MAX_CONTACT_NUMBER;i++){
            if (UtilsFunctions.getSharedString(this, CONTACT_NUMBER_ + String.valueOf(i)) != null){
                drawContact(UtilsFunctions.getSharedString(this, CONTACT_NAME_ + String.valueOf(i)),
                        UtilsFunctions.getSharedString(this, CONTACT_NUMBER_ + String.valueOf(i)),
                        UtilsFunctions.getSharedString(this, CONTACT_ID_ + String.valueOf(i))
                );
            }
        }
    }

    private void goToNext() {
        if (editingContacts){
            onBackPressed();
        } else {
            Intent intent= new Intent(this, SendAlert.class);
            startActivity(intent);
            this.finish();
        }
    }

    private void addContact() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        startActivityForResult(intent, 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Uri contactData = data.getData();
                String number = "";
                Cursor cursor = getContentResolver().query(contactData, null, null, null, null);
                cursor.moveToFirst();
                String hasPhone = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                String contactId = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                String contactName = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
                if (hasPhone.equals("1")) {
                    int index = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    number = cursor.getString(index);
                    drawContact(contactName, number, contactId);
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.contact_no_has_number), Toast.LENGTH_LONG).show();
                }
                cursor.close();
            }
        }
    }

    private void drawContact(String contactName, String number, String contactId) {
        mTvPreviewMessage.setVisibility(View.GONE);
        Log.d("Contacts", "Name: " + contactName + " Id: " + contactId + " Number: " + number);
        if(!inEditionMode) {
            final LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View contactView = inflater.inflate(R.layout.contact_item, null, false);
            ((TextView) contactView.findViewById(R.id.tv_contact_name)).setText(contactName);
            ((TextView) contactView.findViewById(R.id.tv_contact_number)).setText(number);
            mLyContactList.addView(contactView);
            final int inEdition = mLyContactList.getChildCount();

            ((Button)contactView.findViewById(R.id.bt_edit_contact)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    inEditionMode = true;
                    editingView = v;
                    mContactInEdition = inEdition;
                    addContact();
                }
            });

            ((Button)contactView.findViewById(R.id.bt_delete_contact)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mLyContactList.removeView(contactView);
                    deleteContact(mContactInEdition);
                }
            });
        } else{
            ((TextView)(((View)editingView.getParent()).findViewById(R.id.tv_contact_name))).setText(contactName);
            ((TextView)(((View)editingView.getParent()).findViewById(R.id.tv_contact_number))).setText(number);
            editingView = null;
        }
        saveContact(contactName,number,contactId);


    }

    private void deleteContact(int mContactInEdition) {
        UtilsFunctions.deleteKeySharedPreferences(this, CONTACT_NUMBER_+String.valueOf(mContactInEdition));
        UtilsFunctions.deleteKeySharedPreferences(this, CONTACT_NAME_+String.valueOf(mContactInEdition));
        UtilsFunctions.deleteKeySharedPreferences(this, CONTACT_ID_+String.valueOf(mContactInEdition));
        updateButtons();
    }

    private void updateButtons() {
        //Controla el boton de siguiente, si no hay contactos no lo muestra, si hay el mínimo lo muestra
        if(mLyContactList.getChildCount() >= MIN_CONTACT_NUMBER){
            mBtNext.setVisibility(View.VISIBLE);
        } else {
            mBtNext.setVisibility(View.GONE);
        }
        //Controla el botón de adicionar contacto, si ya están los permitidos, lo quita
        if (mLyContactList.getChildCount()>= MAX_CONTACT_NUMBER){
            mBtAddContact.setVisibility(View.GONE);
        } else {
            mBtAddContact.setVisibility(View.VISIBLE);
        }
    }

    private void saveContact(String contactName, String number, String contactId) {
        Log.d("Contact", "Saving contact: " + mContactInEdition);
        UtilsFunctions.saveSharedInteger(this, CONTACT_SIZE, mLyContactList.getChildCount());
        if (inEditionMode) {
            UtilsFunctions.saveSharedString(this, CONTACT_NUMBER_ + String.valueOf(mContactInEdition), number);
            UtilsFunctions.saveSharedString(this, CONTACT_NAME_ + String.valueOf(mContactInEdition), contactName);
            UtilsFunctions.saveSharedString(this, CONTACT_ID_ + String.valueOf(mContactInEdition), contactId);
            inEditionMode = false;
        } else{
            UtilsFunctions.saveSharedString(this, CONTACT_NUMBER_ + String.valueOf(mLyContactList.getChildCount()), number);
            UtilsFunctions.saveSharedString(this, CONTACT_NAME_ + String.valueOf(mLyContactList.getChildCount()), contactName);
            UtilsFunctions.saveSharedString(this, CONTACT_ID_ + String.valueOf(mLyContactList.getChildCount()), contactId);
        }
        updateButtons();
    }
}
