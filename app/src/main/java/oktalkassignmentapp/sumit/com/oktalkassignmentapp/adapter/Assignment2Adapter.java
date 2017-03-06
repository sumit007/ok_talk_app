package oktalkassignmentapp.sumit.com.oktalkassignmentapp.adapter;

/**
 * Created by sumit on 3/6/17.
 */

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import oktalkassignmentapp.sumit.com.oktalkassignmentapp.R;
import oktalkassignmentapp.sumit.com.oktalkassignmentapp.model.NameValuePairItem2;

/**
 * Created by sumit on 3/5/17.
 */

public class Assignment2Adapter extends RecyclerView.Adapter {

    private final LayoutInflater mInflater;
    private Context mContext;
    private ArrayList<NameValuePairItem2> mNameValuePairList = null;

    public Assignment2Adapter(Context context, ArrayList<NameValuePairItem2> nameValueList) {
        mContext = context;
        mNameValuePairList = nameValueList;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = mInflater.inflate(R.layout.li_name_value_pair, parent, false);
        return new Assignment2ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Assignment2ViewHolder assignment2ViewHolder = (Assignment2ViewHolder) holder;

        NameValuePairItem2 nameValuePairItem = mNameValuePairList.get(position);

        assignment2ViewHolder.titleTv.setText(nameValuePairItem.getName());
        assignment2ViewHolder.valueTv.setText(String.valueOf(nameValuePairItem.getValue()));
    }

    @Override
    public int getItemCount() {
        return mNameValuePairList != null ? mNameValuePairList.size() : 0;
    }

    private class Assignment2ViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTv;
        private TextView valueTv;

        Assignment2ViewHolder(View itemView) {
            super(itemView);
            titleTv = (TextView) itemView.findViewById(R.id.title_tv);
            valueTv = (TextView) itemView.findViewById(R.id.value_tv);
        }
    }
}

