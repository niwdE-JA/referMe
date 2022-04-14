package com.kamikaze.referme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.transition.Slide;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Explode;
import android.transition.Fade;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegActivity extends AppCompatActivity {

    EditText email;
    EditText phone;
    EditText acn;
    EditText acnConfirm;
    EditText password;
    EditText confirm;
    Intent dashboard;

    boolean valid[] = {false, false, false, false};//change array sze when more edittext fields are added.

    private FirebaseAuth mAuth;
    private FirebaseFirestore base= FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.regToolBar);
        setSupportActionBar(toolbar);

        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);


        email= findViewById(R.id.signup_email);
        phone= findViewById(R.id.signup_phone);
        acn= findViewById(R.id.account_no);
        acnConfirm=  findViewById(R.id.confirm_acc);
        password =  findViewById(R.id.signup_pxd);
        confirm =  findViewById(R.id.confirm_pxd);

        dashboard = new Intent(this, HomeActivity.class);

        email.addTextChangedListener( new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                checkMail();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

        });

        phone.addTextChangedListener( new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                checkPhone();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

        });

        password.addTextChangedListener( new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                checkPass();
                checkConfm();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

        });

        confirm.addTextChangedListener( new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                checkConfm();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

        });

        acn.addTextChangedListener( new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                checkMail();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

        });

        acnConfirm.addTextChangedListener( new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                checkMail();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

        });



        mAuth = FirebaseAuth.getInstance();

    }



    private boolean validInfo(){
        return true;
    }

    public void checkMail(){
        if(email.length()>0){
            findViewById(R.id.mail_warn).setAlpha(1);
            if( Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()){
                ((TextView) findViewById(R.id.mail_warn)).setTextColor(Color.GREEN);
                ((TextView) findViewById(R.id.mail_warn)).setText("Email Address:");
                valid[0] = true;
            }else{
                ((TextView) findViewById(R.id.mail_warn)).setTextColor(Color.RED);
                ((TextView) findViewById(R.id.mail_warn)).setText("Enter Valid email");
                valid[0] = false;}
        }else{ findViewById(R.id.mail_warn).setAlpha(0); valid[0] = false;}
    }

    public void checkPhone(){
        if(phone.length()> 0) {
            findViewById(R.id.phone_warn).setAlpha(1);
            if ((phone.getText().toString()).matches("(\\+234|0)([7-9]0|81)[0-9]{8}")) {
                ((TextView) findViewById(R.id.phone_warn)).setTextColor(Color.GREEN);
                ((TextView) findViewById(R.id.phone_warn)).setText("Phone Number:");
                valid[1] = true;
            } else {
                ((TextView) findViewById(R.id.phone_warn)).setTextColor(Color.RED);
                ((TextView) findViewById(R.id.phone_warn)).setText("Enter Valid Phone number!");
                valid[1] = false;
            }
        }else{ findViewById(R.id.phone_warn).setAlpha(0); valid[1] = false;}
    }

    public void checkPass(){
        if(password.length()> 0){
            findViewById(R.id.pass_warn).setAlpha(1);
            if(password.length() >= 8 && password.length()<=12) { /*add more secure conditions on the character type, or check of "edittext|password" covers this*/
                ((TextView) findViewById(R.id.pass_warn)).setTextColor(Color.GREEN);
                ((TextView) findViewById(R.id.pass_warn)).setText("Password:");
                valid[2] = true;
            }else{
                ((TextView) findViewById(R.id.pass_warn)).setTextColor(Color.RED);
                ((TextView) findViewById(R.id.pass_warn)).setText("Enter a valid password:");
                valid[2] = false;
            }
        }else{findViewById(R.id.pass_warn).setAlpha(0); valid[2] = false;}
    }

    public void checkConfm(){
        if(confirm.length() < 8 || confirm.length()> 12) {
            ((TextView) findViewById(R.id.confirm_warn)).setAlpha(1);
            ((TextView) findViewById(R.id.confirm_warn)).setText("Invalid Password:");
            valid[3] = false;
            return;
        }else if(confirm.getText().toString().equals( password.getText().toString() ) ){
            ((TextView) findViewById(R.id.confirm_warn)).setAlpha(0);
            valid[3] = true;
        }else{
            ((TextView) findViewById(R.id.confirm_warn)).setText("Passwords do not match!");
            ((TextView) findViewById(R.id.confirm_warn)).setAlpha(1);
            valid[3] = false;
        }
    }

    public boolean checkAcn(){
        boolean x= false;
        if(true /*invalid acct as verified by paystack*/){
            //invalid acct

        }else if(!acnConfirm.getText().toString().equals(acn.getText().toString())/*accts do not match*/){
            //accts do not match
        }else{
            x= true;
        }
        return x;
    }


    public void login(){
        //validate inputs first
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
                        startActivity(dashboard);
                        //GoTo Dashboard
                    }else{
                        //LogIn failed. Show failure.
                    }

                    //hide progress bar
                }
            });
        }else{

        }
    }


    public void register( View v){
        if(validInfo()){//remember to login after creating user

            //research ways to make onClick tasks singular<to avoid double-clicking>

            //might be better to create database before creating user onSuccess.
            String mail= email.getText().toString();
            String pxxd= password.getText().toString();
            mAuth.createUserWithEmailAndPassword(mail, pxxd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isComplete()){
                        //No need to check if it doesn't already exist. Task would fail and throw exception if that's the case.

                        //set relevant info on firestore and go to dashboard. Dashboard should then load this info.
                        Map<String, Object> user = new HashMap<>();
                        user.put("email", email.getText().toString() );
                        user.put("password", password.getText().toString() );
                        user.put("phone", phone.getText().toString() );
                        user.put("account", acn.getText().toString() );
                        //FirebaseUser currentUser= mAuth.getCurrentUser();

                        base.collection("users").document(email.getText().toString() ).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                login();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                String s= e.getMessage();
                            }
                        });

                    }
                }
            });
        }
    }
//register in this activity!!!
    public void next(View v){
        checkAcn();
        Intent payments = new Intent(this, PayActivity.class);
        payments.putExtra("email", email.getText().toString() );
        payments.putExtra("acn", acn.getText().toString() );
        startActivity(payments, ActivityOptions.makeSceneTransitionAnimation(this).toBundle() );
        overridePendingTransition(R.anim.slide_in, R.anim.fade_out);
    }

//register in this activity!!!
    public boolean validInputSyntax() {
        if (valid[0] && valid[1] && valid[2] && valid[3] ) {
            return true;
        }
        return false;
    }

}