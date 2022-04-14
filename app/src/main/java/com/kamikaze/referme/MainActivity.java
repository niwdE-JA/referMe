package com.kamikaze.referme;


import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Intent login;
    private Intent dashboard;
    private Activity currentActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startpage); // change to main layout

        login =new Intent( this , LoginActivity.class);
        dashboard= new Intent( this, HomeActivity.class);

        mAuth = FirebaseAuth.getInstance();
        //all transition animations are sorted out here.
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                loader();
            }
        }, 1500);
    }
    private void loader(){
        if(isCached()) {
            SharedPreferences user_info = getApplication().getSharedPreferences("com.kamikaze.referme.userInfo", Context.MODE_PRIVATE);
            String mail = user_info.getString("email", "");
            String pxxd = user_info.getString("password", "");
            mAuth.signInWithEmailAndPassword(mail, pxxd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        startActivity(dashboard);
                    } else {//something goes here, I guess.
                        try {
                            throw task.getException();
                        } catch(FirebaseAuthInvalidUserException | FirebaseAuthInvalidCredentialsException e){
                            //show error.
                        } catch (Exception e){
                            //show default error
                        }
                    }
                }
            });

        }else{
            startActivity(login);
        }
    }

    public void showLoginError(){

    }

    private  boolean isCached() {
        SharedPreferences user_info = getApplication().getSharedPreferences("com.kamikaze.referme.userInfo", Context.MODE_PRIVATE);
        if (user_info.getBoolean("loggedIn", false)) {
            return true; //*Implemented inefficiently for readability.
        }
        return false;
    }

}