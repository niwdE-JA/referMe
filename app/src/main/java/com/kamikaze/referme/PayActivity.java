package com.kamikaze.referme;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Window;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import co.paystack.android.Paystack;
import co.paystack.android.PaystackSdk;
import co.paystack.android.Transaction;
import co.paystack.android.model.Card;
import co.paystack.android.model.Charge;

import static android.icu.lang.UCharacter.IndicPositionalCategory.LEFT;

public class PayActivity extends AppCompatActivity {
    private EditText cardNumber;
    private EditText cardExpiry;
    private EditText cardCVV;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);

        Toolbar toolbar = (Toolbar) findViewById(R.id.paymentToolbar);
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);

        cardNumber = findViewById(R.id.card_no);
        cardExpiry = findViewById(R.id.card_expiry);
        cardCVV = findViewById(R.id.card_CVV);


        cardExpiry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if( s.toString().length()== 2 && !s.toString().contains("/")) {
                    s.append("/");
                }
            }
        });
        initializePaystack();
    }

    public void initializePaystack(){
        PaystackSdk.initialize(getApplicationContext());
        PaystackSdk.setPublicKey("PSTK_PUBLIC_KEY");
    }

    public void pay(){
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");

        String chargeCardNumber = cardNumber.getText().toString();
        String chargeCardExpiry = cardExpiry.getText().toString();
        String chargeCardCVV = cardCVV.getText().toString();

        String[] cardExpiryData = chargeCardExpiry.split("/");
        int expMonth= Integer.parseInt(cardExpiryData[0]);
        int expYear= Integer.parseInt(cardExpiryData[1]);

        Card card = new Card(chargeCardNumber, expMonth, expYear, chargeCardCVV );

        if (card.isValid() ) {
            PerformCharge(card, 2000, email);
        }else{
            //invalid card.
        }
    }

    public void PerformCharge(Card card, int amt, String email){

        Charge charge = new Charge();
        charge.setAmount(amt);
        charge.setEmail(email);
        charge.setCard(card);

        PaystackSdk.chargeCard(this, charge, new Paystack.TransactionCallback() {
            @Override
            public void onSuccess(Transaction transaction) {
                //transaction success!
            }

            @Override
            public void beforeValidate(Transaction transaction) {

            }

            @Override
            public void onError(Throwable error, Transaction transaction) {

            }
        });
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.slide_out);
    }
}
