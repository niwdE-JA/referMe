package com.kamikaze.referme;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.zip.Inflater;


public class MyActivityActivity extends AppCompatActivity {

    Fragment1 fragment1 = new Fragment1();
    Fragment2 fragment2 = new Fragment2();

    boolean[] clicked = {false, false};
    private FirebaseAuth mAuth;
    private FirebaseFirestore base= FirebaseFirestore.getInstance();

    private String email;
    private String password;
    private String phone;
    private String acn;


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu0, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        if(menuItem.getItemId() == R.id.action_logout ){
            View blank = findViewById(R.id.myActivityToolbar);
            logout(blank);
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myactivity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.myActivityToolbar);
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
/*
        mAuth= FirebaseAuth.getInstance();
        FirebaseUser currentUser= mAuth.getCurrentUser();
        email= currentUser.getEmail();

        DocumentReference userInfo= base.collection("users").document(email);
        userInfo.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document= task.getResult();
                    if(document.exists()){
                        password= document.getString("password");
                        phone= document.getString("phone");
                        acn= document.getString("acn");
                    }else{
                        //Network error information and other error warnings here
                    }
                }
            }
        });

        //. . .
        ( (TextView)findViewById(R.id.acnTxt) ).setText(email);
        ( (TextView)findViewById(R.id.pssEdit) ).setText(password);
        ( (TextView)findViewById(R.id.acnEdit) ).setText(acn);
        ( (TextView)findViewById(R.id.phoneEdit) ).setText(phone);

        */

    }


    public void accountInfo(View view){
        Float angle = 0f;
        ImageView arrow = findViewById(R.id.arrow1);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(!clicked[0]) {
            angle = 90f;
            fragmentTransaction.add(R.id.Layout1, fragment1);
        }else{
            fragmentTransaction.remove(fragment1);
        }

        ObjectAnimator toggle = ObjectAnimator.ofFloat(arrow, "rotation", angle);
        toggle.setDuration(300);
        toggle.start();

        fragmentTransaction.commit();
        clicked[0] = !clicked[0];
    }

    public void downlines(View view){
        float angle = 0f;
        ImageView arrow = findViewById(R.id.arrow2);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(!clicked[1]) {
            angle = 90f;
            fragmentTransaction.add(R.id.Layout2, fragment2);
        }else{
            fragmentTransaction.remove(fragment2);
        }
        ObjectAnimator toggle = ObjectAnimator.ofFloat(arrow, "rotation", angle);
        toggle.setDuration(300);
        toggle.start();

        fragmentTransaction.commit();
        clicked[1] = !clicked[1];
    }

    public  void logout( View view) {
        Intent signout = new Intent(this, LoginActivity.class);
        signout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        //this logic is stupid. You should be clearing cache. Fix it.
        SharedPreferences user_info = getApplication().getSharedPreferences("com.kamikaze.referme.userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = user_info.edit();
        editor.putBoolean("loggedIn", false);
        editor.apply();/*
        mAuth.signOut();*/
        startActivity(signout);
        finish();

    }

}
