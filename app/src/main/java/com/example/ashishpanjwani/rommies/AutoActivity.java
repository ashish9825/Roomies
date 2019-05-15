package com.example.ashishpanjwani.rommies;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ashishpanjwani.rommies.Adapter.HostelAdapter;
import com.example.ashishpanjwani.rommies.Interfaces.CustomHostelClickListener;
import com.example.ashishpanjwani.rommies.Interfaces.HostelAPIs;
import com.example.ashishpanjwani.rommies.Models.HostelList;
import com.example.ashishpanjwani.rommies.Utils.SharedPrefManager;
import com.example.ashishpanjwani.rommies.Views.AboutActivity;
import com.example.ashishpanjwani.rommies.Views.DonateActivity;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.treebo.internetavailabilitychecker.InternetAvailabilityChecker;
import com.treebo.internetavailabilitychecker.InternetConnectivityListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import pugman.com.simplelocationgetter.SimpleLocationGetter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AutoActivity extends Fragment implements SimpleLocationGetter.OnLocationGetListener, InternetConnectivityListener {

    String TAG = "AutoActivity";
    public static int postId;
    private double lat, lon;
    private String area;
    CardView searchBar;

    @BindView(R.id.recycler_view_auto)
    RecyclerView recyclerView;

    @BindView(R.id.empty_auto)
    TextView empty;

    @BindView(R.id.shimmer_view_container)
    ShimmerFrameLayout shimmerFrameLayout;

    HostelAdapter adapter;
    private InternetAvailabilityChecker internetAvailabilityChecker;

    public AutoActivity() {

        //Required empty public constructor
    }

    public static AutoActivity newInstance(String param1,String param2) {
        AutoActivity fragment = new AutoActivity();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_main,container,false);

        shimmerFrameLayout = view.findViewById(R.id.shimmer_view_container);
        empty = view.findViewById(R.id.empty_auto);

        InternetAvailabilityChecker.init(getActivity());
        internetAvailabilityChecker=InternetAvailabilityChecker.getInstance();
        internetAvailabilityChecker.addInternetConnectivityListener(this);

        recyclerView = view.findViewById(R.id.recycler_view_auto);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        getHostels();

        return view;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        internetAvailabilityChecker.removeInternetConnectivityChangeListener(this);
    }

    public static int returnHostelId() {
        return postId;
    }

    private void getHostels() {

        Call<List<HostelList>> callHostels = HostelAPIs.getHostelService().sendLocation(""+area);

        //Creating an anonymous callback
        callHostels.enqueue(new Callback<List<HostelList>>() {
            @Override
            public void onResponse(Call<List<HostelList>> call, Response<List<HostelList>> response) {

                //On response we will read the server's output
                Log.d("Details", new Gson().toJson(response.body()));
                final List<HostelList> hostelLists = response.body();
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                adapter = new HostelAdapter(getActivity(), hostelLists, new CustomHostelClickListener() {
                    @Override
                    public void onHostelClick(View v, int position) {
                        Log.d(TAG, "Clicked Position:" + position);
                        postId = hostelLists.get(position).getHid();
                        Intent intent = new Intent(getActivity(), HostelDetailsAuto.class);
                        startActivity(intent);
                    }
                });
                recyclerView.setAdapter(adapter);

                if (hostelLists.isEmpty()) {
                    //empty = getActivity().findViewById(R.id.empty_auto);
                    empty.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }

                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<List<HostelList>> call, Throwable t) {
                Toast.makeText(getActivity(), "Network Failure !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        shimmerFrameLayout.startShimmerAnimation();
        super.onResume();
    }

    @Override
    public void onPause() {
        shimmerFrameLayout.stopShimmerAnimation();
        super.onPause();
    }

    @Override
    public void onLocationReady(Location location) {

        Log.d("LOCATION", "onLocationReady : lat=" + location.getLatitude() + " lon=" + location.getLongitude());
        lat = location.getLatitude();
        lon = location.getLongitude();

        try {
            Log.v("Location", "Reached");
            Geocoder geocoder = new Geocoder(this.getActivity(), Locale.getDefault());

            List<Address> address = geocoder.getFromLocation(lat, lon, 1);

            if (address.size() > 0 && address != null) {
                area = address.get(0).getSubLocality() + "," + address.get(0).getLocality() + "," +
                        address.get(0).getSubAdminArea() + "," + address.get(0).getAdminArea() + "," + address.get(0).getCountryName();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(String s) {

        Toast.makeText(this.getActivity(), "Cannot Fetch Location !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInternetConnectivityChanged(boolean isConnected) {

        if (isConnected) {
            recyclerView = getActivity().findViewById(R.id.recycler_view_auto);
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
            getHostels();
        }
        else
            Toast.makeText(this.getActivity(), "Check Your Connection !", Toast.LENGTH_SHORT).show();
    }

    /*@Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        onStartNewActivity();
    }

    *//*@Override
    public void startActivity(Intent intent, Bundle options) {
        super.startActivityForResult(intent, options);
    }*//*

    protected void onStartNewActivity() {
        overridePendingTransition(R.anim.enter_from_right,R.anim.exit_to_left);
    }
*/
}
