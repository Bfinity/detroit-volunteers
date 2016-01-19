package com.detroitlabs.detroitvolunteers.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.detroitlabs.detroitvolunteers.R;
import com.detroitlabs.detroitvolunteers.client.models.VolunteerOpportunity;

import java.util.ArrayList;

import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;

public class ListResultsFragment extends RoboFragment {

    @InjectView(R.id.resultsList)
    ListView resultsList;

    ArrayList<VolunteerOpportunity> listToDisplay;

    ListAdapter adapter;

    public static ListResultsFragment newInstance(ArrayList<VolunteerOpportunity> listToDisplay){
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("listToDisplay", listToDisplay);
        ListResultsFragment listResultsFragment = new ListResultsFragment();
        listResultsFragment.setArguments(bundle);
        return listResultsFragment;
    }

    //todo display custom view of list item
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_listresults, container, false);
        listToDisplay = getArguments().getParcelableArrayList("listToDisplay");
        ArrayList<String> list = new ArrayList<>();
        list.add(listToDisplay.get(0).getOpportunityTitle());
        list.add(String.valueOf(listToDisplay.get(0).getAvailability().isOngoing()));
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        resultsList.setAdapter(adapter);
        resultsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DetailsFragment detailsFragment = DetailsFragment.newInstance(listToDisplay.get(position));
                getFragmentManager().beginTransaction().replace(R.id.container, detailsFragment).commit();
            }
        });
    }

}
