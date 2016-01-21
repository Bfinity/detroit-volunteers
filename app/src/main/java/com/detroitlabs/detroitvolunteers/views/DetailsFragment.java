package com.detroitlabs.detroitvolunteers.views;

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

import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;

public class DetailsFragment extends RoboFragment implements SaveOpportunityCallback{
    //todo update view

    @InjectView(R.id.textview_save)
    TextView addToFavorites;

    @InjectView(R.id.opportunityTitle)
    TextView oppTitle;

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
        addToFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFavorites();
            }
        });
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
