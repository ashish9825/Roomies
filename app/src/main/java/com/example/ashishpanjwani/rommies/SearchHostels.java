package com.example.ashishpanjwani.rommies;

import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ashishpanjwani.rommies.Adapter.HostelAdapter;
import com.example.ashishpanjwani.rommies.Interfaces.CustomHostelClickListener;
import com.example.ashishpanjwani.rommies.Interfaces.HostelAPIs;
import com.example.ashishpanjwani.rommies.Models.HostelList;
import com.example.ashishpanjwani.rommies.Utils.RoomieUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchHostels extends Fragment implements SearchView.OnQueryTextListener,TextWatcher{

    public static final String TAG=SearchHostels.class.getSimpleName();
    public static final String QUERY="query";
    public static final int REQ_CODE_SPEECH_INPUT=9002;
    public static int postId;
    TextView noResults;
    List<HostelList> hostelsArrayList;
    boolean _ignore=false;

    @BindView(R.id.voice_search)
    View micIcon;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.empty)
    TextView empty;

    @BindView(R.id.search_view)
    EditText searchView;

    private String query;
    HostelAdapter hostelAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_search,container,false);

        ButterKnife.bind(getActivity());
        empty = view.findViewById(R.id.empty);
        searchView = view.findViewById(R.id.search_view);
        setupSearchView();

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home :
                getActivity().finish();
                break;
        }
        return true;
    }

    public static int returnHostelId() {
       return postId;
   }

    private void setupSearchView() {
        SearchManager searchManager=(SearchManager) getActivity().getSystemService(getActivity().SEARCH_SERVICE);
        if(searchManager!=null) {
            searchView.addTextChangedListener(this);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == getActivity().RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    query=result.get(0);
                    searchView.setText(query, TextView.BufferType.EDITABLE);
                }
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(QUERY,query);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void search(String query) {
        this.query=query.trim();
        //micIcon.setVisibility(query.length() > 0 ? View.GONE : View.VISIBLE);
        hostelsName();

        empty.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        hideSoftKeyboard();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
       if (searchView.getText().toString()!="")
           search(newText);
       else
           hostelsArrayList.clear();
        return false;
    }

    private void hideSoftKeyboard() {
        RoomieUtil.hideSoftKeyboard(getActivity());
        if (searchView!=null) {
            searchView.clearFocus();
        }
    }

    @OnClick({R.id.voice_search/*,R.id.back*/})
    void searchImageView(View view) {
        switch (view.getId()) {
            case R.id.voice_search:
                startMicSearch();
                break;
            /*case R.id.back:
                finish();
                break;*/
        }
    }

    private void startMicSearch() {

        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Search your location");
        try {
            startActivityForResult(intent,REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this.getActivity(), "Sorry ! Your device doesn't support speech input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
       if (searchView.getText().toString().length()>0)
           search(s.toString());
      else {
           empty.setVisibility(View.VISIBLE);
           recyclerView.setVisibility(View.GONE);
           try {
               hostelsArrayList.clear();
           }
           catch (Exception e) {
               e.printStackTrace();
           }
       }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

   private void hostelsName() {

       Call<List<HostelList>> hostelsCall= HostelAPIs.getHostelService().sendLocation(searchView.getText().toString());

       //Creating an anonymous callback
       hostelsCall.enqueue(new Callback<List<HostelList>>() {
                               @Override
                               public void onResponse(Call<List<HostelList>> call, Response<List<HostelList>> response) {

                                   //On Response we will read the server's output
                                   Log.d("Details",""+new Gson().toJson(response.body()));
                                   final List<HostelList> hostelList=response.body();
                                   recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                   hostelAdapter=new HostelAdapter(getActivity(), hostelList, new CustomHostelClickListener() {
                                       @Override
                                       public void onHostelClick(View v, int position) {
                                           Log.d(TAG,"Clicked Position :"+position);
                                           postId=hostelList.get(position).getHid();
                                           Intent intent=new Intent(getActivity(),HostelDetails.class);
                                           startActivity(intent);
                                       }
                                   });
                                   hostelAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                                       @Override
                                       public void onChanged() {
                                           super.onChanged();
                                           hostelList.clear();
                                           empty.setVisibility(hostelAdapter.getItemCount() < 1 ? View.VISIBLE : View.GONE);
                                           recyclerView.setVisibility(hostelAdapter.getItemCount() > 0 ? View.VISIBLE : View.GONE);
                                       }
                                   });
                                   recyclerView.setAdapter(hostelAdapter);
                               }

                               @Override
                               public void onFailure(Call<List<HostelList>> call, Throwable t) {
                                   Toast.makeText(getActivity(), "Network Failure !", Toast.LENGTH_SHORT).show();
                               }
                           }
       );
   }
}
