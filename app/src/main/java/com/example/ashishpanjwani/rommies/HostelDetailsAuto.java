package com.example.ashishpanjwani.rommies;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ashishpanjwani.rommies.Adapter.HostelDetailsAdapter;
import com.example.ashishpanjwani.rommies.Interfaces.ClickListener;
import com.example.ashishpanjwani.rommies.Interfaces.HostelAPIs;
import com.example.ashishpanjwani.rommies.Models.HostelList;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HostelDetailsAuto extends AppCompatActivity {

    private static final String TAG = "HostelDetailsAuto.class";
    public static int ownerId;
    private int id;
    HostelDetailsAdapter hostelDetailsAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recylerview_hostel_details);

        id = AutoActivity.returnHostelId();

        //haveActionBar();
        getHostelDetails();

        Log.d(TAG, "" + id);

        recyclerView = findViewById(R.id.hostel_details_hostelId);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /*private void haveActionBar() {
        final ActionBar actionBar = getSupportActionBar();

        View viewActionBar = getLayoutInflater().inflate(R.layout.app_bar_layout, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.NO_GRAVITY);
        TextView textViewTitle = viewActionBar.findViewById(R.id.actionbar_textview);
        textViewTitle.setText("Roomies");
        textViewTitle.setTextSize(25);
        actionBar.setCustomView(viewActionBar, params);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeButtonEnabled(true);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Jaapokkisubtract-Regular.otf");
        textViewTitle.setTypeface(typeface);
        textViewTitle.setTextColor(Color.BLACK);
    }*/

    public static int returnOwnerId() {
        return ownerId;
    }

    public void getHostelDetails() {

        Call<List<HostelList>> call = HostelAPIs.getHostelService().getHostelById(id);
        call.enqueue(new Callback<List<HostelList>>() {
            @Override
            public void onResponse(Call<List<HostelList>> call, Response<List<HostelList>> response) {
                final List<HostelList> list = response.body();
                Log.d("TAG ", new Gson().toJson(response.body()));
                recyclerView.setLayoutManager(new LinearLayoutManager(HostelDetailsAuto.this));
                hostelDetailsAdapter = new HostelDetailsAdapter(HostelDetailsAuto.this, list);
                recyclerView.setAdapter(hostelDetailsAdapter);
            }

            @Override
            public void onFailure(Call<List<HostelList>> call, Throwable t) {

                Toast.makeText(HostelDetailsAuto.this, "Network Failure !", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

