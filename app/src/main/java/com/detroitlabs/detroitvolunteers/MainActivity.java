package com.detroitlabs.detroitvolunteers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.detroitlabs.detroitvolunteers.client.SearchOpportunitiesCallBack;
import com.detroitlabs.detroitvolunteers.client.VolunteerMatchRetrofit;
import com.detroitlabs.detroitvolunteers.client.models.OpportunitiesResponse;
import com.detroitlabs.detroitvolunteers.client.models.VolunteerOpportunity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //todo move the search logic into the login fragment

    private ListView listView;
    private ArrayAdapter<String> listAdapter;
    private ArrayList<String> arrayList;
    private final VolunteerMatchRetrofit retrofitOb = new VolunteerMatchRetrofit();


    private static String url = "http://www.volunteermatch.org/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        arrayList = new ArrayList<String>();
        listView = (ListView) findViewById(R.id.list);
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(listAdapter);

        retrofitOb.searchForVolunteerOpportunities(new SearchOpportunitiesCallBack() {
            @Override
            public void onSuccess(OpportunitiesResponse response) {
                updateOpportunitiesList(response);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateOpportunitiesList(@NonNull OpportunitiesResponse listOfOp) {
        ArrayList<VolunteerOpportunity> listOfOps = listOfOp.getList();
        if (!listOfOps.isEmpty()) {
            for (VolunteerOpportunity op : listOfOps) {
                arrayList.add(op.getOpportunityTitle());
            }
            listAdapter.notifyDataSetChanged();
        }
    }

}
