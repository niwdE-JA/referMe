package com.kamikaze.referme;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    private Activity currentActivity= this;
    private ProgressBar pb;


    private FirebaseAuth mAuth;
    Intent dashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.loginToolBar);
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);

        email=  findViewById(R.id.login_email);
        password= findViewById(R.id.login_password);

        mAuth = FirebaseAuth.getInstance();
        dashboard= new Intent(this, HomeActivity.class);
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    public void login(View view){
        //validate inputs first
        showProgressBar();
        if(validInputSyntax()){
            String mail = email.getText().toString();
            String pxxd = password.getText().toString();
            mAuth.signInWithEmailAndPassword(mail, pxxd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        //LogIn success!!
                        SharedPreferences user_info = getApplication().getSharedPreferences("com.kamikaze.referme.userInfo", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = user_info.edit();
                        editor.putString("email", email.getText().toString() );
                        editor.putString("password", password.getText().toString() );
                        editor.putBoolean("loggedIn", true );
                        editor.apply();
                        startActivity(dashboard, ActivityOptions.makeSceneTransitionAnimation(currentActivity).toBundle() );
                        //GoTo Dashboard
                    }else{
                        try {
                            throw task.getException();
                        } catch(FirebaseAuthInvalidUserException | FirebaseAuthInvalidCredentialsException e){
                            showLoginError();
                        } catch (Exception e){
                            showDefaultLoginError();
                        }
                    }

                    hideProgressBar();
                    //hide progress bar
                }
            });
        }else{
            showClientError();
        }

    }

    public void showClientError(){

    }
    public void showLoginError(){
        Toast.makeText(getApplicationContext(), "Invalid Email or password. Check login details", Toast.LENGTH_LONG).show();
    }
    public void showDefaultLoginError(){
        Toast.makeText(getApplicationContext(), "Unexpected Login error. Check network and try again.", Toast.LENGTH_LONG).show();
    }

    public void showProgressBar(){
        pb = (ProgressBar) findViewById(R.id.progress);
        pb.setVisibility(ProgressBar.VISIBLE);
    }
    public void hideProgressBar(){
        pb = (ProgressBar) findViewById(R.id.progress);
        pb.setVisibility(ProgressBar.INVISIBLE);
    }

    public boolean validInputSyntax() {
        if (Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches() && password.length() >= 8 && password.length()<=12 ) {
            return true;
        }
        return false;
    }

    public void link_reg(View view){
        Intent reg_page= new Intent(this, RegActivity.class);
        startActivity(reg_page, ActivityOptions.makeSceneTransitionAnimation(this).toBundle() );
    }

    public void link_otp(View view){
        Intent otp_page =new Intent(this, otpActivity.class);
        startActivity(otp_page, ActivityOptions.makeSceneTransitionAnimation(this).toBundle() );
    }

}
