package com.detroitlabs.detroitvolunteers.client;

import com.detroitlabs.detroitvolunteers.client.models.OpportunitiesResponse;
import com.detroitlabs.detroitvolunteers.client.service.VolunteerMatchApiService;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

public class VolunteerMatchRetrofit {

    private static String url = "http://www.volunteermatch.org/api/";

    private Retrofit generateBuildRetroFitClient(){
        final String wsse = WSSEGenerator.createWSSE();
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
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

        return new Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void searchForVolunteerOpportunities(final SearchOpportunitiesCallBack callBack){
        Retrofit retrofit = generateBuildRetroFitClient();
        VolunteerMatchApiService service = retrofit.create(VolunteerMatchApiService.class);
        Call<OpportunitiesResponse> call = service.getAllVolunteerOpportunities();
        call.enqueue(new Callback<OpportunitiesResponse>() {
            @Override
            public void onResponse(retrofit2.Response<OpportunitiesResponse> response) {
                if(response.body() != null){
                    callBack.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

}
