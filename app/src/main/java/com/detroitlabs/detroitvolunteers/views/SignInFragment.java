package com.detroitlabs.detroitvolunteers.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.detroitlabs.detroitvolunteers.R;
import com.detroitlabs.detroitvolunteers.client.SearchOpportunitiesCallBack;
import com.detroitlabs.detroitvolunteers.client.VolunteerMatchRetrofit;
import com.detroitlabs.detroitvolunteers.client.models.OpportunitiesResponse;

import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;

public class SignInFragment extends RoboFragment {


    @InjectView(R.id.edittext_username)
    EditText usernameField;

    @InjectView(R.id.button_search)
    Button searchButton;

    VolunteerMatchRetrofit retrofitObject = new VolunteerMatchRetrofit();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_signin, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               searchForVolunteerOpportunities();
            }
        });

    };

    public void searchForVolunteerOpportunities(){
        retrofitObject.searchForVolunteerOpportunities(new SearchOpportunitiesCallBack() {
            @Override
            public void onSuccess(OpportunitiesResponse response) {
                ListResultsFragment listResultsFragment = ListResultsFragment.newInstance(response.getList());
                getFragmentManager().beginTransaction().replace(R.id.container, listResultsFragment).commit();
            }
        });
    }
}


