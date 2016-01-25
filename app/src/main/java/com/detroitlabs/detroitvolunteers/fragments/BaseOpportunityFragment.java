package com.detroitlabs.detroitvolunteers.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.detroitlabs.detroitvolunteers.R;

import roboguice.fragment.RoboFragment;

public class BaseOpportunityFragment extends RoboFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setIcon(R.drawable.ic_folder_24dp);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_baseopp, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_logout:
                getFragmentManager().beginTransaction()
                        .remove(this)
                        .replace(R.id.container, new SignInFragment())
                        .commit();
                return true;
            default:
                return  super.onOptionsItemSelected(item);
        }
    }
}
