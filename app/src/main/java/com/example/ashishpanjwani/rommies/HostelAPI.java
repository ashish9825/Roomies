package com.example.ashishpanjwani.rommies;

import com.example.ashishpanjwani.rommies.Models.HostelList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class HostelAPI  {

    private static final String address="";
    private static String API_URL="https://gentle-plains-97445.herokuapp.com";

    public static HostelService hostelService=null;

    public static HostelService getHostelService() {

        if(hostelService==null) {
            Retrofit retrofit=new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            hostelService=retrofit.create(HostelService.class);
        }
        return hostelService;
    }

    public interface HostelService {

        @GET("/hostels/raipur")
        Call<List<HostelList>> getHostelList();
    }
}
