package com.detroitlabs.detroitvolunteers.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.detroitlabs.detroitvolunteers.R;
import com.detroitlabs.detroitvolunteers.client.models.VolunteerOpportunity;

import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;

public class DetailsFragment extends RoboFragment {
    //todo update view

    @InjectView(R.id.opportunityTitle)
    TextView oppTitle;

    @InjectView(R.id.opportunityDescription)
    TextView oppDescript;

    @InjectView(R.id.opportunityParentOrgName)
    TextView oppParentOrg;

    VolunteerOpportunity volunteerOpportunity;

    public static DetailsFragment newInstance(VolunteerOpportunity volunteerOpportunity){
        Bundle bundle = new Bundle();
        bundle.putParcelable("volunteerOp", volunteerOpportunity);
        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(bundle);
        return detailsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        volunteerOpportunity = getArguments().getParcelable("volunteerOp");
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        oppTitle.setText(volunteerOpportunity.getOpportunityTitle());
        oppDescript.setText(volunteerOpportunity.getOpportunityDescription());
        oppParentOrg.setText(volunteerOpportunity.getParentOrg().getName());
    }
}
