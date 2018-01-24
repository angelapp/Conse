package conse.nrc.org.co.consejo.Utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;


import java.util.LinkedHashMap;
import java.util.List;

import conse.nrc.org.co.consejo.Interfaces.MainInterface;
import conse.nrc.org.co.consejo.R;

/**
 * Created by apple on 1/9/17.
 */

public class ShieldListExpandableAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private LinkedHashMap<String, List<Models.CorporatePhoneBook>> expandableListDetail;

    private MainInterface mainInterface;

    public ShieldListExpandableAdapter(Context context, List<String> expandableListTitle, LinkedHashMap<String, List<Models.CorporatePhoneBook>> expandableListDetail) {
        this.context = context;
        mainInterface = (MainInterface)context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final Models.CorporatePhoneBook input = (Models.CorporatePhoneBook) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.shield_item, null);
        }
        TextView name = (TextView) convertView.findViewById(R.id.tv_name);
        final TextView phone = (TextView) convertView.findViewById(R.id.tv_phone);
        TextView address = (TextView) convertView.findViewById(R.id.tv_direction);
        final TextView mobile = (TextView) convertView.findViewById(R.id.tv_mobile);
        final TextView email = (TextView) convertView.findViewById(R.id.tv_email);
        TextView twitter = (TextView) convertView.findViewById(R.id.tv_twitter);

        if (input.name != null && input.name.length() > 1){
            name.setText(input.name);
        } else {
            name.setVisibility(View.GONE);
        }
        if (input.phone != null && input.phone.length() > 1){
            phone.setText(input.phone);
            phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainInterface.makeCall(phone.getText().toString());
                }
            });
        } else {
            phone.setVisibility(View.GONE);
        }
        if (input.address != null && input.address.length() > 1){
            address.setText(input.address);
        } else {
            address.setVisibility(View.GONE);
        }
        if (input.mobile_phone != null && input.mobile_phone.length() > 1){
            mobile.setText(input.mobile_phone);
            mobile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainInterface.makeCall(mobile.getText().toString());
                }
            });
        } else {
            mobile.setVisibility(View.GONE);
        }
        if (input.email != null && input.email.length() > 1){
            email.setText(input.email);
            email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainInterface.sendEmail(email.getText().toString());
                }
            });
        } else {
            email.setVisibility(View.GONE);
        }
        if (input.twitter != null && input.twitter.length() > 1){
            twitter.setText(input.twitter);
        } else {
            twitter.setVisibility(View.GONE);
        }
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

//    public int getGroupIcon(int listPosition){
//        Pair<List<String>, Integer> pair = this.expandableListDetail.get(this.expandableListTitle.get(listPosition));
//        int id = pair.second;
//        return id;
//    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.jurisprudence_docs_group_item, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.tv_group_tittle);
//        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        Log.d("Shield Adapter", "Drawing cat: " + listTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }

}
