package conse.nrc.org.co.consejo.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.LinkedHashMap;
import java.util.List;

import conse.nrc.org.co.consejo.Interfaces.MainInterface;
import conse.nrc.org.co.consejo.R;

/**
 * Created by apple on 1/9/17.
 */

public class LearningFormatsExpandableAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private LinkedHashMap<String, List<Models.LearningItem>> expandableListDetail;
    MainInterface mainInterface;

    public LearningFormatsExpandableAdapter(Context context, List<String> expandableListTitle, LinkedHashMap<String, List<Models.LearningItem>> expandableListDetail) {
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
        final Models.LearningItem expandedListText = (Models.LearningItem) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.learning_my_community_item, null);
        }
        //((TextView)convertView.findViewById(R.id.tv_tittle)).setText(expandedListText.tittle);
        ((TextView)convertView.findViewById(R.id.tv_description)).setText(expandedListText.description);
        ((TextView)convertView.findViewById(R.id.tv_download_name)).setText(context.getString(R.string.about_community_download));

        ((Button)convertView.findViewById(R.id.bt_download)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainInterface.openDoc(expandedListText.file_name);
            }
        });

        ((Button)convertView.findViewById(R.id.bt_share)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainInterface.shareDoc(expandedListText.file_name);
            }
        });

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
