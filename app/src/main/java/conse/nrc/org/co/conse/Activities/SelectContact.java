package conse.nrc.org.co.conse.Activities;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
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

import Utils.UtilsFunctions;
import conse.nrc.org.co.conse.R;

import static Utils.LocalConstants.CONTACT_ID_;
import static Utils.LocalConstants.CONTACT_NAME_;
import static Utils.LocalConstants.CONTACT_NUMBER;
import static Utils.LocalConstants.CONTACT_NUMBER_;
import static Utils.LocalConstants.CONTACT_SIZE;

public class SelectContact extends AppCompatActivity {

    Button mBtAddContact;
    LinearLayout mLyContactList;
    TextView mTvPreviewMessage;
    int mContactInEdition;
    boolean inEditionMode;
    View editingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_contact);

        mBtAddContact = (Button) findViewById(R.id.bt_add_contact);
        mLyContactList = (LinearLayout) findViewById(R.id.ly_contact_list);
        mTvPreviewMessage = (TextView) findViewById(R.id.tv_add_contact_message);
        inEditionMode = false;

        mBtAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mLyContactList.getChildCount()<= CONTACT_NUMBER -1 ){
                    addContact();
                } else {
                    goToNext();
                }
            }
        });
    }

    private void goToNext() {

        Intent intent= new Intent(this, SendAlert.class);
        startActivity(intent);
        this.finish();

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
//                    Cursor phones = getContentResolver().query
//                            (ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
//                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID
//                                            + " = " + contactId, null, null);
//                    while (phones.moveToNext()) {
//                        Log.d("Contacts", phones.toString());
//                        number = phones.getString(phones.getColumnIndex
//                                (ContactsContract.CommonDataKinds.Phone.NUMBER)).replaceAll("[-() ]", "");
//                    }
//                    phones.close();
                    drawContact(contactName, number, contactId);
                    //Do something with number
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
        } else{
            ((TextView)(((View)editingView.getParent()).findViewById(R.id.tv_contact_name))).setText(contactName);
            ((TextView)(((View)editingView.getParent()).findViewById(R.id.tv_contact_number))).setText(number);
            editingView = null;
        }
        saveContact(contactName,number,contactId);

        if(mLyContactList.getChildCount() >= CONTACT_NUMBER){
            mBtAddContact.setBackgroundResource(R.drawable.button_go_to_register);
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
    }
}
