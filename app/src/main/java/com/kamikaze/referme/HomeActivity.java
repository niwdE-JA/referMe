package com.kamikaze.referme;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Intent _profile;
    Intent _refer;
    Intent _myActivity;
    Intent _about;

    AppBarLayout barLayout;
    TextView txt1;
    TextView txt2;
    LinearLayout titler;


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        if(menuItem.getItemId() == R.id.action_logout ){
            logout();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu0, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate( savedInstanceState);
        setContentView(R.layout.dashboard);
        _profile= new Intent(this, HomeActivity.class);
        _refer= new Intent(this, HomeActivity.class);
        _myActivity= new Intent(this, MyActivityActivity.class);
        _about= new Intent(this, AboutActivity.class);

        Toolbar toolbar = (Toolbar) findViewById(R.id.dashboardToolbar);
        setSupportActionBar(toolbar);

        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart(){
        super.onStart();
        txt1 = (TextView) findViewById(R.id.txt1);
        txt2 = (TextView) findViewById(R.id.txt2);
        titler=findViewById(R.id.title);

        barLayout = findViewById(R.id.appBarLayout);
        barLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {/*
                if(Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()*(2/3) ){
                cT.setTitle("My Dashboard");
                }else{cT.setTitle(" ");}*/
                float i = verticalOffset;
                /*titular.setText(String.valueOf(verticalOffset));*/
                /*txt1.setText(String.valueOf(i/120 ));*/

                txt1.setTextSize(TypedValue.COMPLEX_UNIT_DIP ,30+(10*i/120));
                txt2.setTextSize(TypedValue.COMPLEX_UNIT_DIP ,30+(10*i/120));
                titler.setTranslationX(100*i/120);
                titler.setTranslationY((8*i/120) );
            }
        });
    }


    public void profile( View v){
        startActivity(_profile, ActivityOptions.makeSceneTransitionAnimation(this).toBundle() );
    }
    public void refer(View v){
        startActivity(_refer, ActivityOptions.makeSceneTransitionAnimation(this).toBundle() );
    }
    public void myActivity(View v){
        startActivity(_myActivity, ActivityOptions.makeSceneTransitionAnimation(this).toBundle() );
    }
    public void about(View v){
        startActivity(_about, ActivityOptions.makeSceneTransitionAnimation(this).toBundle() );
    }


    public  void logout() {
        Intent signout = new Intent(this, LoginActivity.class);
        signout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        //this logic is stupid. You should be clearing cache. Fix it.
        SharedPreferences user_info = getApplication().getSharedPreferences("com.kamikaze.referme.userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = user_info.edit();
        editor.putBoolean("loggedIn", false);
        editor.apply();
        mAuth.signOut();
        startActivity(signout, ActivityOptions.makeSceneTransitionAnimation(this).toBundle() );
        finish();

    }

}
