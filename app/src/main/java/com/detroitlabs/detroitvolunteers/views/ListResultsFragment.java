package com.detroitlabs.detroitvolunteers.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.detroitlabs.detroitvolunteers.R;
import com.detroitlabs.detroitvolunteers.client.SearchOpportunitiesCallBack;
import com.detroitlabs.detroitvolunteers.client.VolunteerMatchRetrofit;
import com.detroitlabs.detroitvolunteers.client.models.OpportunitiesSearchResponse;
import com.detroitlabs.detroitvolunteers.client.models.VolunteerOpportunity;
import com.detroitlabs.detroitvolunteers.firebase.GetSavedOpportunitiesCallback;
import com.detroitlabs.detroitvolunteers.firebase.UserFavoritesFirebase;
import com.detroitlabs.detroitvolunteers.firebase.VolunteerMatchFirebase;
import com.detroitlabs.detroitvolunteers.models.User;

import java.util.ArrayList;

import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;

public class ListResultsFragment extends RoboFragment implements SearchOpportunitiesCallBack {

    private static final String USER_BUNDLE_KEY = "userKey";

    @InjectView(R.id.resultsList)
    ListView resultsList;

    @InjectView(R.id.textview_show_fav)
    TextView showFavs;

    private User user;

    private VolunteerMatchRetrofit retrofitInstance = new VolunteerMatchRetrofit();

    private ArrayList<VolunteerOpportunity> listToDisplay;

    private boolean isFavoritesShowing = false;

    //todo replace with custom list
    private ArrayList<String> searchList = new ArrayList<String>();
    private ArrayList<String> favList = new ArrayList<String>();

    //todo replace with custom adapter
    private ArrayAdapter<String> adapter;

    public static ListResultsFragment newInstance(User user){
        Bundle bundle = new Bundle();
        bundle.putParcelable(USER_BUNDLE_KEY, user);
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
        user = getArguments().getParcelable(USER_BUNDLE_KEY);
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        resultsList.setAdapter(adapter);
        resultsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DetailsFragment detailsFragment = DetailsFragment.newInstance(user, listToDisplay.get(position));
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, detailsFragment)
                        .addToBackStack("DetailsFragment")
                        .commit();
            }
        });
        searchVolunteerOpportunities();
        showFavs.setOnClickListener(new View.OnClickListener() {
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

    private void searchVolunteerOpportunities(){
        retrofitInstance.searchForVolunteerOpportunities(this);
    }

    //todo implement favorite in bar
    //fixme
    private void showFavorites(){
        isFavoritesShowing = true;
        UserFavoritesFirebase firebase = new UserFavoritesFirebase(user, new VolunteerMatchFirebase());
        firebase.getSavedOpportunities(user, new GetSavedOpportunitiesCallback() {
            @Override
            public void onSuccess(ArrayList<VolunteerOpportunity> listOfOpportunities) {
                adapter.clear();
                favList.clear();
                favList.add(listOfOpportunities.get(0).getOpportunityTitle());
                adapter.addAll(favList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

    //fixme persist list
    private void showSearchResults(){
        isFavoritesShowing = false;
        adapter.clear();
        adapter.addAll(searchList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSuccess(OpportunitiesSearchResponse response) {
        adapter.clear();
        listToDisplay = response.getList();
        searchList.add(listToDisplay.get(0).getOpportunityTitle());
        searchList.add(String.valueOf(listToDisplay.get(0).getAvailability().isOngoing()));
        adapter.addAll(searchList);
        adapter.notifyDataSetChanged();
    }

    //todo replace with custom popup
    @Override
    public void onError(int statusCode) {
        Toast.makeText(getContext(), "An error occurred", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(getContext(), "An error occurred", Toast.LENGTH_LONG).show();
    }
}
