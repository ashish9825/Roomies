package com.example.ashishpanjwani.rommies;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ashishpanjwani.rommies.Utils.SharedPrefManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    CircleImageView userImage;
    TextView userName, userEmail;
    private String nameOfUser, nameOfEmail;
    SharedPrefManager sharedPrefManager;
    Context mContext = this;
    LinearLayout linearHeaderProgress;
    RelativeLayout relativeLayout;
    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;
    TextView logOutText;
    CardView logOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_profile);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);

        mAuth = com.google.firebase.auth.FirebaseAuth.getInstance();

        userImage = findViewById(R.id.user_image);
        userName = findViewById(R.id.user_name);
        userEmail = findViewById(R.id.user_email);
        linearHeaderProgress = findViewById(R.id.linlaPHeaderProgress);
        relativeLayout = findViewById(R.id.profile_details);
        logOutButton = findViewById(R.id.logout_button);
        logOutText = findViewById(R.id.logout_text);

        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/GOTHIC.TTF");
        logOutText.setTypeface(typeface);

        //Create Object of SharedPrefManager and get data stored in it
        sharedPrefManager = new SharedPrefManager(mContext);
        nameOfUser =sharedPrefManager.getName();
        nameOfEmail = sharedPrefManager.getUserEmail();
        String uri = sharedPrefManager.getPhoto();
        Uri photoUri = Uri.parse(uri);

        userName.setText(nameOfUser);
        userEmail.setText(nameOfEmail);
        Picasso.with(mContext)
                .load(photoUri)
                .placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon)
                .into(userImage);

        designDetails();
        configureSignIn();
        logoutButton();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    //This method configures Google Sign In
    public void configureSignIn() {

        //Configure SignIn to request the useer's basic profile like name and email
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        //Build a GoogleApiClient with access to GoogleSignInApi and the options above
        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,options)
                .build();
        mGoogleApiClient.connect();
    }

    private void logoutButton() {

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
    }

    //Method to logout
    private void signOut() {
        new SharedPrefManager(mContext).clear();
        mAuth.signOut();

        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                Intent intent = new Intent(ProfileActivity.this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    private void designDetails() {

        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/Jaapokkisubtract-Regular.otf");
        userName.setTypeface(typeface);
        Typeface typeface1 = Typeface.createFromAsset(getAssets(),"fonts/Comfortaa-Regular.ttf");
        userEmail.setTypeface(typeface1);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ProfileActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        startActivity(intent);
        finish();
    }

    /*@Override
    protected void onResume() {
        relativeLayout.setVisibility(View.GONE);
        linearHeaderProgress.setVisibility(View.VISIBLE);
        super.onResume();
    }

    @Override
    protected void onPause() {
        linearHeaderProgress.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.VISIBLE);
        super.onPause();
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
