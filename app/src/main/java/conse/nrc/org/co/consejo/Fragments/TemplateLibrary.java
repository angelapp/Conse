package conse.nrc.org.co.consejo.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import conse.nrc.org.co.consejo.Adapters.PagerAdapterFragment;
import conse.nrc.org.co.consejo.Interfaces.MainInterface;
import conse.nrc.org.co.consejo.R;
import conse.nrc.org.co.consejo.Utils.LocalConstants;

/**
 * Created by apple on 12/26/17.
 */

public class TemplateLibrary extends Fragment {

    private MainInterface mainInterface;
    Context mCtx;
    LinearLayout mLyPrincipalContent;


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCtx = context;
        mainInterface = (MainInterface)mCtx;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.template_library, container, false);
        mLyPrincipalContent = (LinearLayout)view.findViewById(R.id.ly_content);
        fillList();
        return view;
    }

    private void fillList() {
        mLyPrincipalContent.removeAllViews();
        for (Pair doc : LocalConstants.TEMPLATE_LIBRARY_LIST){
            drawItem(doc);
        }

    }

    private void drawItem(Pair doc) {
        final LayoutInflater inflater = (LayoutInflater) this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View library_piece = inflater.inflate(R.layout.template_library_item, null, false);
        ((TextView)library_piece.findViewById(R.id.tv_name)).setText((String)doc.first);
        ((Button)library_piece.findViewById(R.id.bt_download)).setTag(doc.second);
        ((Button)library_piece.findViewById(R.id.bt_share)).setTag(doc.second);

        ((Button)library_piece.findViewById(R.id.bt_download)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFile((String)v.getTag());
            }
        });

        ((Button)library_piece.findViewById(R.id.bt_share)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share((String)v.getTag());
            }
        });

        mLyPrincipalContent.addView(library_piece);
    }

    private void share(String tag) {

        mainInterface.shareDoc(tag);


    }

    private void openFile(String tag) {

        mainInterface.openDoc(tag);

    }

}
