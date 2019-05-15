package com.example.ashishpanjwani.rommies.Interfaces;

import com.example.ashishpanjwani.rommies.Models.HostelList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class HostelAPIs {
    private static String BASE_URL="https://gentle-plains-97445.herokuapp.com";

    public static FindHostel findHostel=null;

    public static FindHostel getHostelService() {

        if(findHostel==null) {
            Retrofit retrofit=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            findHostel=retrofit.create(FindHostel.class);
        }
        return findHostel;
    }

    public interface FindHostel {

        @FormUrlEncoded
        @POST("/hostels/")
        @Headers("Content-Type:application/x-www-form-urlencoded")
        Call<List<HostelList>> sendLocation(
                @Field("address") String address);

        @GET("/hostel/{hostelId}")
        Call<List<HostelList>> getHostelById(@Path("hostelId") Integer hId);

    }
}

