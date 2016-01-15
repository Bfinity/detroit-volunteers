package com.detroitlabs.detroitvolunteers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.detroitlabs.detroitvolunteers.models.OpportunitiesResponse;
import com.detroitlabs.detroitvolunteers.models.VolunteerOpportunity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> listAdapter;
    private ArrayList<String> arrayList;

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

        sendRequest();
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


    public void sendRequest() {
        final String wsse = createWSSE();
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        //     logger.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        logger.setLevel(HttpLoggingInterceptor.Level.BODY);
        Interceptor headerIntercepter = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .header("Accept-Charset", "UTF-8")
                        .header("Content-Type", "application/json")
                        .header("Authorization", "WSSE profile=\"UsernameToken\"")
                        .header("X-WSSE", wsse).build();

                return chain.proceed(request);
            }
        };

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(headerIntercepter)
                .addInterceptor(logger)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MyApiEndpointInterface apiService = retrofit.create(MyApiEndpointInterface.class);

        Call<OpportunitiesResponse> call = apiService.getResponse();
        call.enqueue(new retrofit2.Callback<OpportunitiesResponse>() {
            @Override
            public void onResponse(retrofit2.Response<OpportunitiesResponse> response) {
                OpportunitiesResponse listOfOp = response.body();
                getArrayList(listOfOp);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.i("Failure", t.getLocalizedMessage());
            }
        });
    }

    public void getArrayList(@NonNull OpportunitiesResponse listOfOp) {
        ArrayList<VolunteerOpportunity> listOfOps = listOfOp.getList();
        if (!listOfOps.isEmpty()) {
            for (VolunteerOpportunity op : listOfOps) {
                arrayList.add(op.getOpportunityTitle());
            }
            listAdapter.notifyDataSetChanged();
        }
    }

    public interface MyApiEndpointInterface {
        // http://www.volunteermatch.org/api/call?action=searchOpportunities&query="{\"name\":\"john\"}" 

        @GET("call?action=searchOpportunities&query=%7B%22location%22%3A%22detroit%2C+us%2C+mi%22%7D")
        Call<OpportunitiesResponse> getResponse();

    }



    private String createWSSE() {
        String accountName = "andrewjb";
        String apiKey = "80f51f19cce8f62e4e8831e0e4539772";
        Random random = new Random();
        String nonce = String.valueOf(random.nextInt(999999));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault());
        String timestamp = dateFormat.format(new Date());

        String digestInput = nonce + timestamp + apiKey;
        String passwordDigest = "";
        try {
            passwordDigest = Base64.encodeToString(sha256((digestInput).getBytes("UTF-8")), Base64.DEFAULT);
        } catch (UnsupportedEncodingException e) {
            Log.i("encodingException", e.getLocalizedMessage());
        }

        StringBuilder credentials = new StringBuilder();
        credentials.append("UsernameToken Username=\"").append(accountName).append("\", ");
        credentials.append("PasswordDigest=\"").append(passwordDigest.trim()).append("\", ");
        credentials.append("Nonce=\"").append(nonce.trim()).append("\", ");
        credentials.append("Created=\"").append(timestamp).append("\"");

        Log.i("creds", credentials.toString());
        return credentials.toString();
    }

    /**
     * Generates a SHA-256 hash of a payload message.
     *
     * @param payload
     * @return
     */
    private byte[] sha256(byte[] payload) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.reset();
            return digest.digest(payload);
        } catch (NoSuchAlgorithmException e) {
            Log.e("error", "error");
        }
        return null;
    }

}
