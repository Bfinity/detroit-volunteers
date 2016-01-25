package com.detroitlabs.detroitvolunteers.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.detroitlabs.detroitvolunteers.R;
import com.detroitlabs.detroitvolunteers.adapters.VolunteerOpportunityAdapter;
import com.detroitlabs.detroitvolunteers.client.SearchOpportunitiesCallBack;
import com.detroitlabs.detroitvolunteers.client.VolunteerMatchRetrofit;
import com.detroitlabs.detroitvolunteers.client.models.OpportunitiesSearchResponse;
import com.detroitlabs.detroitvolunteers.client.models.VolunteerOpportunity;
import com.detroitlabs.detroitvolunteers.firebase.GetSavedOpportunitiesCallback;
import com.detroitlabs.detroitvolunteers.firebase.UserFavoritesFirebase;
import com.detroitlabs.detroitvolunteers.firebase.VolunteerMatchFirebase;
import com.detroitlabs.detroitvolunteers.models.User;

import java.util.ArrayList;

import roboguice.inject.InjectView;

public class ListResultsFragment extends BaseOpportunityFragment{

    private static final String USER_BUNDLE_KEY = "userKey";

    @InjectView(R.id.resultsList)
    ListView resultsList;

    @InjectView(R.id.textview_list_displayed)
    TextView listDisplayed;

    private User user;

    private VolunteerMatchRetrofit retrofitInstance = new VolunteerMatchRetrofit();

    private boolean isFavoritesShowing = false;

    private Parcelable savedListInstance;

    private ArrayList<VolunteerOpportunity> searchList = new ArrayList<VolunteerOpportunity>();
    private ArrayList<VolunteerOpportunity> favList = new ArrayList<VolunteerOpportunity>();

    private VolunteerOpportunityAdapter adapter;

    public static ListResultsFragment newInstance(User user){
        Bundle bundle = new Bundle();
        bundle.putParcelable(USER_BUNDLE_KEY, user);
        ListResultsFragment listResultsFragment = new ListResultsFragment();
        listResultsFragment.setArguments(bundle);
        return listResultsFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(getActivity() instanceof AppCompatActivity){
            Toolbar toolbar = (Toolbar) ((AppCompatActivity) getActivity()).findViewById(R.id.toolbar);
            toolbar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isFavoritesShowing){
                        showSearchResults();
                    }
                    else{
                        showFavorites();
                    }
                }
            });
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_listview, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_refresh:
                searchVolunteerOpportunities();
                return true;
            default:
              return  super.onOptionsItemSelected(item);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_listresults, container, false);
        user = getArguments().getParcelable(USER_BUNDLE_KEY);
        adapter = new VolunteerOpportunityAdapter(getContext(), R.layout.list_item_opportunity);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(savedListInstance != null){
            resultsList.onRestoreInstanceState(savedListInstance);
        }
        resultsList.setAdapter(adapter);
        resultsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VolunteerOpportunity opportunity = (VolunteerOpportunity) parent.getItemAtPosition(position);
                showDetailsFragment(opportunity);
            }
        });
        listDisplayed.setText(R.string.textview_search_results);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(searchList.isEmpty()) {
            searchVolunteerOpportunities();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        ListView lv = (ListView) getView().findViewById(R.id.resultsList);
        savedListInstance = lv.onSaveInstanceState();
    }

    private void showDetailsFragment(VolunteerOpportunity opportunity){
        DetailsFragment detailsFragment = DetailsFragment.newInstance(user, opportunity);
        getFragmentManager().beginTransaction()
                .hide(this)
                .add(R.id.container, detailsFragment)
                .addToBackStack("DetailsFragment")
                .commit();
    }

    private void showSearchResults(){
        isFavoritesShowing = false;
        listDisplayed.setText(R.string.textview_search_results);
        if(searchList.isEmpty()){
            searchVolunteerOpportunities();
        }
        else {
            updateListViewAdapter(searchList);
        }
    }

    private void searchVolunteerOpportunities(){
        retrofitInstance.searchForVolunteerOpportunities(new SearchOpportunitiesCallBack() {
            @Override
            public void onSuccess(OpportunitiesSearchResponse response) {
                searchList = response.getList();
                updateListViewAdapter(searchList);
            }

            @Override
            public void onError(int statusCode) {
                Toast.makeText(getContext(), "An error occurred", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(getContext(), "An error occurred", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showFavorites(){
        isFavoritesShowing = true;
        listDisplayed.setText(R.string.textview_favorites);
        if(favList.isEmpty()) {
            getSavedOpportunities();
        }
        else{
            updateListViewAdapter(favList);
        }
    }

    private void getSavedOpportunities() {
        UserFavoritesFirebase firebase = new UserFavoritesFirebase(user, new VolunteerMatchFirebase());
        firebase.getSavedOpportunities(user, new GetSavedOpportunitiesCallback() {
            @Override
            public void onSuccess(ArrayList<VolunteerOpportunity> listOfOpportunities) {
                favList = listOfOpportunities;
                updateListViewAdapter(favList);
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

    private void updateListViewAdapter(ArrayList<VolunteerOpportunity> listToDisplay){
        adapter.clear();
        adapter.addAll(listToDisplay);
        adapter.notifyDataSetChanged();
    }
}
