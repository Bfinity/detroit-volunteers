package com.detroitlabs.detroitvolunteers.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.detroitlabs.detroitvolunteers.R;
import com.detroitlabs.detroitvolunteers.client.models.VolunteerOpportunity;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class VolunteerOpportunityAdapter extends ArrayAdapter<VolunteerOpportunity> {

    private static class ViewHolder{

        TextView opportunityTitle;

        TextView opportunityParentOrgName;

        RelativeLayout viewDate;

        TextView textViewDate;

        TextView textViewMonth;

        ImageView imageDate;

    }


    public VolunteerOpportunityAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VolunteerOpportunity opportunity = getItem(position);
        ViewHolder viewHolder;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item_opportunity, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.opportunityTitle = (TextView) convertView.findViewById(R.id.textview_opportunity_title);
            viewHolder.opportunityParentOrgName = (TextView) convertView.findViewById(R.id.textview_opportunity_parent_org_name);
            viewHolder.viewDate = (RelativeLayout) convertView.findViewById(R.id.view_date);
            viewHolder.textViewDate = (TextView) viewHolder.viewDate.findViewById(R.id.textview_date);
            viewHolder.textViewMonth = (TextView) viewHolder.viewDate.findViewById(R.id.textview_month);
            viewHolder.imageDate = (ImageView) viewHolder.viewDate.findViewById(R.id.image_date);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.opportunityTitle.setText(opportunity.getOpportunityTitle());
        viewHolder.opportunityParentOrgName.setText(opportunity.getParentOrg().getName());
        viewHolder = cleardateimage(viewHolder);
        if(opportunity.getAvailability().getEndDate() != null){
            String dateString = opportunity.getAvailability().getEndDate();
            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
            LocalDate date = formatter.parseLocalDate(dateString);
            viewHolder.textViewMonth.setText(String.valueOf(date.getMonthOfYear()));
            viewHolder.textViewDate.setText(String.valueOf(date.getDayOfMonth()));
        }
        else {
            viewHolder.imageDate.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    private ViewHolder cleardateimage(ViewHolder viewHolder){
        viewHolder.imageDate.setVisibility(View.GONE);
        viewHolder.textViewDate.setText("");
        viewHolder.textViewMonth.setText("");
        return viewHolder;
    }
}
