package com.cancer.cancerbuddy;


import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.cancer.cancerbuddy.Auth.LoginActivity;
import com.cancer.cancerbuddy.Community.Community_FormActivity;
import com.cancer.cancerbuddy.Exercise.ExerciseActivity;
import com.cancer.cancerbuddy.Profile.ProfileActivity;
import com.cancer.cancerbuddy.Record_Sys.RecordActivity2;
import com.cancer.cancerbuddy.Record_Sys.Track_SystemActivity;
import com.cancer.cancerbuddy.food.food_DietActivity;
import com.cancer.cancerbuddy.medicine.Medicine_ManagementActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements ConnectionReceiver.ReceiverListener {


    private FirebaseAuth mAuth;
    private FirebaseUser CurrentUser;
    DatabaseReference UserRef;
    FirebaseDatabase database;

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar mToolbar;

    CardView mainT,fcard2,asst,fcard3,fcard4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainT = findViewById(R.id.mainT);
        fcard2 = findViewById(R.id.fcard2);
        asst = findViewById(R.id.asst);
        fcard3 = findViewById(R.id.fcard3);
        fcard4 = findViewById(R.id.fcard4);

        //shortcut for medicine management page
        mainT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Medicine_ManagementActivity.class);
                startActivity(intent);
            }
        });

        fcard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Track_SystemActivity.class);
                startActivity(intent);
            }
        });

        asst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, food_DietActivity.class);
                startActivity(intent);
            }
        });

        fcard3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ExerciseActivity.class);
                startActivity(intent);
            }
        });


        fcard4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Community_FormActivity.class);
                startActivity(intent);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        CurrentUser = mAuth.getCurrentUser();

        mToolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle( "Cancer Buddy App" );

        drawerLayout =  findViewById(R.id.Drawable_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        navigationView =  findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                UserMenuSelector(item);
                return false;
            }
        });

    }
    //redirects user to profile page
    @Override
    public boolean onOptionsItemSelected(MenuItem itemView)
    {

        int id = itemView.getItemId();
        if (id == R.id.action_Complaint) {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);

        }

        if(actionBarDrawerToggle.onOptionsItemSelected(itemView))
        {
            return true;
        }
        return super.onOptionsItemSelected(itemView);

    }


    @Override
    protected void onStart() {
        super.onStart();

        if (CurrentUser == null) {
            SendUserToLoginActivity();
        }
    }


    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        drawerLayout =  findViewById(R.id.Drawable_layout);

        if (drawerLayout.isDrawerOpen( GravityCompat.START )){
            drawerLayout.closeDrawer( GravityCompat.START );
        }
        else if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);

        checkConnection();
    }

    private void checkConnection() {

        // initialize intent filter
        IntentFilter intentFilter = new IntentFilter();

        // add action
        intentFilter.addAction("android.new.conn.CONNECTIVITY_CHANGE");

        // register receiver
        registerReceiver(new ConnectionReceiver(), intentFilter);

        // Initialize listener
        ConnectionReceiver.Listener = this;

        // Initialize connectivity manager
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Initialize network info
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        // get connection status
        boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();

        // display snack bar
        showSnackBar(isConnected);
    }

    private void showSnackBar(boolean isConnected) {

        // initialize color and message
        String message;
        int color;

        // check condition
        if (isConnected) {
            // set text color
            color = Color.WHITE;

        } else {

            // when internet
            // is disconnected
            // set message
            message = "Not Connected to Internet";

            Snackbar snackbar = Snackbar.make(findViewById(R.id.snackInfo), message, Snackbar.LENGTH_LONG);

            // initialize view
            View view = snackbar.getView();

            // show snack bar
            snackbar.show();

            // set text color
            color = Color.RED;
        }
    }

    @Override
    public void onNetworkChange(boolean isConnected) {
        // display snack bar
        showSnackBar(isConnected);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // call method
        checkConnection();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // call method
        checkConnection();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.navigation_option, menu );
        //  View view = menu.findItem( R.id.action_cart ).getActionView();

        return true;
    }

    //switch case for all nav bar menu options
    private void UserMenuSelector(MenuItem item)
    {
        switch (item.getItemId())
        {
             case R.id.nav_profile:
                 ProfileBar();
              break;
             case R.id.nav_medicine:
                 MedicineBar();
                 break;
              case R.id.nav_track_sym:
                TrackSys();
              break;
              case R.id.nav_track_sym1:
                ViewTrackSys();
              break;
              case R.id.nav_food_diet:
                    FoodBar();
               break;
              case R.id.nav_exercise:
                    ExerciseBar();
               break;
               case R.id.nav_communityf:
                    CommunityBar();
               break;
                case R.id.nav_about_me:
                    AboutMeBar();
               break;
                case R.id.nav_TermC:
                    TermBar();
               break;
            case R.id.nav_logout:
                mAuth.signOut();
                  SendUserToLoginActivity();
                    break;
        }
    }

    private void SendUserToLoginActivity() {

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void TermBar() {
       Intent intent = new Intent(MainActivity.this, TermActivity.class);
        startActivity(intent);
        finish();
    }

    private void AboutMeBar() {

        Intent intent = new Intent(MainActivity.this, AboutUsActivity.class);
        startActivity(intent);
        finish();
    }

    private void CommunityBar() {
        Intent intent = new Intent(MainActivity.this, Community_FormActivity.class);
        startActivity(intent);
        finish();
    }

    private void ExerciseBar() {
        Intent intent = new Intent(MainActivity.this, ExerciseActivity.class);
        startActivity(intent);
        finish();
    }

    private void FoodBar() {
        Intent intent = new Intent(MainActivity.this, food_DietActivity.class);
        startActivity(intent);
        finish();
    }

    private void TrackSys() {
      //  Intent intent = new Intent(MainActivity.this, TrackSyntomActivity.class);
        Intent intent = new Intent(MainActivity.this, Track_SystemActivity.class);
       // Intent intent = new Intent(MainActivity.this, WeekViewActivity.class);
        startActivity(intent);
        finish();
    }

    private void ViewTrackSys() {
      //  Intent intent = new Intent(MainActivity.this, TrackSyntomActivity.class);
        Intent intent = new Intent(MainActivity.this, RecordActivity2.class);
       // Intent intent = new Intent(MainActivity.this, WeekViewActivity.class);
        startActivity(intent);
        finish();
    }

    private void MedicineBar() {
        Intent intent = new Intent(MainActivity.this, Medicine_ManagementActivity.class);
        startActivity(intent);
        finish();
    }

    private void ProfileBar() {
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}