package com.kamikaze.referme;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class otpActivity extends AppCompatActivity {
    EditText otpbOne, otpbTwo, otpbThree, otpbFour;
    Button otpBttn;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);


        Toolbar toolbar = (Toolbar) findViewById(R.id.otpToolbar);
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);

        otpbOne = findViewById(R.id.otpb1);
        otpbTwo = findViewById(R.id.otpb2);
        otpbThree = findViewById(R.id.otpb3);
        otpbFour = findViewById(R.id.otpb4);
        otpBttn = findViewById(R.id.otpbttn);

        EditText[] edit = {otpbOne, otpbTwo, otpbThree, otpbFour};

        for(int i = 0; i<edit.length; i-= -1){
            edit[i].setText( String.valueOf(i));
        }

        otpbOne.addTextChangedListener(new GenericTextWatcher(otpbOne,edit));
        otpbTwo.addTextChangedListener(new GenericTextWatcher(otpbTwo,edit));
        otpbThree.addTextChangedListener(new GenericTextWatcher(otpbThree,edit));
        otpbFour.addTextChangedListener(new GenericTextWatcher(otpbFour,edit));

        otpBttn.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return false;
            }
        });
    }
}
