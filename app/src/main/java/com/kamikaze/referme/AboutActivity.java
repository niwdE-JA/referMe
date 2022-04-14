package com.kamikaze.referme;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AboutActivity extends AppCompatActivity {


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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);


        Toolbar toolbar = (Toolbar) findViewById(R.id.aboutToolbar);
        setSupportActionBar(toolbar);

        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
    }


    public  void logout() {
        Intent signout = new Intent(this, LoginActivity.class);
        signout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        //this logic is stupid. You should be clearing cache. Fix it.
        SharedPreferences user_info = getApplication().getSharedPreferences("com.kamikaze.referme.userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = user_info.edit();
        editor.putBoolean("loggedIn", false);
        editor.apply();/*
        mAuth.signOut();*/
        startActivity(signout, ActivityOptions.makeSceneTransitionAnimation(this).toBundle() );
        finish();

    }

}
