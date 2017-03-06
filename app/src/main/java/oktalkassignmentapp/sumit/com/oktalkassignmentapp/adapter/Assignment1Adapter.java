package oktalkassignmentapp.sumit.com.oktalkassignmentapp.adapter;

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
import oktalkassignmentapp.sumit.com.oktalkassignmentapp.model.NameValuePairItem;

/**
 * Created by sumit on 3/5/17.
 */

public class Assignment1Adapter extends RecyclerView.Adapter {

    private final LayoutInflater mInflater;
    private Context mContext;
    private ArrayList<NameValuePairItem> mNameValuePairList = null;

    public Assignment1Adapter(Context context, ArrayList<NameValuePairItem> nameValueList) {
        mContext = context;
        mNameValuePairList = nameValueList;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = mInflater.inflate(R.layout.li_name_value_pair, parent, false);
        return new Assignment1ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Assignment1ViewHolder assignment1ViewHolder = (Assignment1ViewHolder) holder;

        NameValuePairItem nameValuePairItem = mNameValuePairList.get(position);

        if (nameValuePairItem.getValue() < 0) {
            assignment1ViewHolder.listItemLl.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_7190de));
            assignment1ViewHolder.titleTv.setText(nameValuePairItem.getName());
            assignment1ViewHolder.valueTv.setVisibility(View.GONE);

        } else {
            assignment1ViewHolder.listItemLl.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_ffffff));
            assignment1ViewHolder.titleTv.setText(nameValuePairItem.getName());
            assignment1ViewHolder.valueTv.setText(String.valueOf(nameValuePairItem.getValue()));
            assignment1ViewHolder.valueTv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mNameValuePairList != null ? mNameValuePairList.size() : 0;
    }

    private class Assignment1ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout listItemLl;
        private TextView titleTv;
        private TextView valueTv;

        Assignment1ViewHolder(View itemView) {
            super(itemView);
            titleTv = (TextView) itemView.findViewById(R.id.title_tv);
            valueTv = (TextView) itemView.findViewById(R.id.value_tv);
            listItemLl = (LinearLayout) itemView.findViewById(R.id.list_item);
        }
    }
}
