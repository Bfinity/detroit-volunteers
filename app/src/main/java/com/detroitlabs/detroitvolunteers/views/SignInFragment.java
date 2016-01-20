package com.detroitlabs.detroitvolunteers.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.detroitlabs.detroitvolunteers.R;

import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;

public class SignInFragment extends RoboFragment {


    @InjectView(R.id.edittext_username)
    EditText usernameField;

    @InjectView(R.id.button_search)
    Button searchButton;

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
                getFragmentManager().beginTransaction().replace(R.id.container, new ListResultsFragment()).commit();
            }
        });

    };
}


