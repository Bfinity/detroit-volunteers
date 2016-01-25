package com.detroitlabs.detroitvolunteers.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.detroitlabs.detroitvolunteers.R;
import com.detroitlabs.detroitvolunteers.client.models.VolunteerOpportunity;
import com.detroitlabs.detroitvolunteers.firebase.SaveOpportunityCallback;
import com.detroitlabs.detroitvolunteers.firebase.UserFavoritesFirebase;
import com.detroitlabs.detroitvolunteers.firebase.VolunteerMatchFirebase;
import com.detroitlabs.detroitvolunteers.models.User;

import roboguice.inject.InjectView;

public class DetailsFragment extends BaseOpportunityFragment implements SaveOpportunityCallback{
    //todo update view

    @InjectView(R.id.opportunityTitle)
    TextView oppTitle;

    @InjectView(R.id.opportunityLink)
    TextView oppLink;

    @InjectView(R.id.opportunityDescription)
    TextView oppDescript;

    @InjectView(R.id.opportunityParentOrgName)
    TextView oppParentOrg;

    private User user;

    private VolunteerOpportunity volunteerOpportunity;

    private UserFavoritesFirebase firebase;

    private AlertDialogView alertDialogView;

    public static DetailsFragment newInstance(User user, VolunteerOpportunity volunteerOpportunity){
        Bundle bundle = new Bundle();
        bundle.putParcelable("volunteerOp", volunteerOpportunity);
        bundle.putParcelable("user", user);
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
        user = getArguments().getParcelable("user");
        firebase = new UserFavoritesFirebase(user, new VolunteerMatchFirebase());
        alertDialogView = new AlertDialogView(getContext());
        return view;
    }

    @Override
    public void onViewCreated(View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        oppTitle.setText(volunteerOpportunity.getOpportunityTitle());
        oppDescript.setText(volunteerOpportunity.getOpportunityDescription());
        oppParentOrg.setText(volunteerOpportunity.getParentOrg().getName());
        if(volunteerOpportunity.getOpportunityUrl() != null) {
            oppLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String oppUri = volunteerOpportunity.getOpportunityUrl();
                    Intent showPage = new Intent(Intent.ACTION_VIEW);
                    String uri2 = Uri.decode(oppUri);
                    showPage.setData(Uri.parse(uri2));
                    startActivity(showPage);
                }
            });
        }
        else{
            oppLink.setVisibility(View.INVISIBLE);
            oppLink.setClickable(false);
        }
    }

    private void saveToFavorites(){
        firebase.saveOpportunityToFavorites(volunteerOpportunity, this);
    }

    @Override
    public void onSaveSuccess() {
        Toast.makeText(getContext(), "Saved", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(String errorMessage) {
        alertDialogView.showErrorAlertDialog(errorMessage);
    }
}
