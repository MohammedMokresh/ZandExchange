package com.sarafizand.sarafizand.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sarafizand.sarafizand.GlobalVariables;
import com.sarafizand.sarafizand.R;
import com.squareup.picasso.Picasso;

public class TransactionsHistoryDetailsActivity extends AppCompatActivity {
    TextView benficiaryNameTextView, ben_acc_no_textview, iban_textview, ben_address_textview,
            tel_textview, ben_id_textview, reg_no_textview, id_no_textview, from_currency_textview, to_currency_textview, country_textview, collection_textview, institution_no_textview,
            bank_textview, swift_textview, card_no_textview
            , sheba_no_textview, local_bank_code_textview, is_business_textview
            , bank_address_textview, rejection_reason_textview, created_at_textview, amountTextView, refTextView,statusTextView;

    String benficiaryNameString, ben_acc_no_String, iban_String, ben_address_String,
            tel_String, ben_id_String, reg_no_String, id_no_String, from_currency_String, to_currency_String, country_String, institution_no_String,
            bank_String, swift_String, card_no_String, sheba_no_String, local_bank_code_String, is_business_String, bank_address_String, rejection_reason_String, created_at_String;
    int rate, amount, isSender, senderId, formId, status, collection_String;
    String txRecipeString, adminIdString, refString, updatedAt;
    EditText amountEditText;
    TextInputLayout amountTil;
    Button sendButton;
    //    ProgressBar progressBar;
    ImageView recipeImageView;
    Bitmap recipeBitmap;
    String recipeString;
    String a,b;
    AppCompatImageView benIDImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions_history_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Transaction History Details");
        refTextView = findViewById(R.id.ref_textview);
        amountTextView = findViewById(R.id.amount_textview);
        benficiaryNameTextView = findViewById(R.id.ben_name_textview);
        ben_acc_no_textview = findViewById(R.id.ben_acc_no_textview);
        iban_textview = findViewById(R.id.iban_textview);
        tel_textview = findViewById(R.id.tel_textview);
        ben_id_textview = findViewById(R.id.ben_id_textview);
        reg_no_textview = findViewById(R.id.reg_no_textview);
        id_no_textview = findViewById(R.id.id_no_textview);
        from_currency_textview = findViewById(R.id.from_currency_textview);
        ben_address_textview = findViewById(R.id.ben_address_textview);
        to_currency_textview = findViewById(R.id.to_currency_textview);
        country_textview = findViewById(R.id.country_textview);
        collection_textview = findViewById(R.id.collection_textview);
        institution_no_textview = findViewById(R.id.institution_no_textview);
        bank_textview = findViewById(R.id.bank_textview);
        swift_textview = findViewById(R.id.swift_textview);
        card_no_textview = findViewById(R.id.card_no_textview);
        sheba_no_textview = findViewById(R.id.sheba_no_textview);
        local_bank_code_textview = findViewById(R.id.local_bank_code_textview);
        is_business_textview = findViewById(R.id.is_business_textview);
        bank_address_textview = findViewById(R.id.bank_address_textview);
        rejection_reason_textview = findViewById(R.id.rejection_reason_textview);
        created_at_textview = findViewById(R.id.created_at_textview);
        recipeImageView=(ImageView) findViewById(R.id.recipe_imageview);
        benIDImageView=findViewById(R.id.ben_id_imageview);
        statusTextView= findViewById(R.id.status);
        GlobalVariables.hideKeyboard(this);
        Intent intent = getIntent();

        int sta=intent.getIntExtra("status",-1);
        if (sta==0){
        statusTextView.setText(getResources().getString(R.string.status)+"Waiting for turn");
        }else if (sta==1){
            statusTextView.setText(getResources().getString(R.string.status)+"is processing");

        }else if (sta==2){
            statusTextView.setText(getResources().getString(R.string.status)+"is approved");

        }else if (sta==3){
            statusTextView.setText(getResources().getString(R.string.status)+"is rejected");

        }else if (sta==4){
            statusTextView.setText(getResources().getString(R.string.status)+"is paid");

        }else {
            statusTextView.setText(getResources().getString(R.string.status)+"");
        }

        benficiaryNameTextView.setText(getResources().getString(R.string.beneficiary_name)+" : "+intent.getStringExtra("ben_name"));
        ben_acc_no_textview.setText(getResources().getString(R.string.beneficiary_account_number)+" : " +intent.getStringExtra("ben_acc_no"));
        iban_textview.setText(getResources().getString(R.string.iban)+" : "+ intent.getStringExtra("iban"));
        ben_address_textview.setText(getResources().getString(R.string.beneficiary_address)+" : "+intent.getStringExtra("ben_address"));
        tel_textview.setText(getResources().getString(R.string.phone)+" : " +intent.getStringExtra("tel"));
        reg_no_textview.setText(getResources().getString(R.string.company_registration_number)+" : "+intent.getStringExtra("reg_no"));
        id_no_textview.setText(getResources().getString(R.string.id_number)+" : "+intent.getStringExtra("id_no"));
        from_currency_textview.setText(getResources().getString(R.string.from_currency)+" : "+intent.getStringExtra("from_currency"));
        to_currency_textview.setText(getResources().getString(R.string.to_currency)+" : "+intent.getStringExtra("to_currency"));
        amountTextView.setText(getResources().getString(R.string.amount)+" : " + intent.getIntExtra("amount", -1));
        country_textview.setText(getResources().getString(R.string.country)+" : "+ intent.getStringExtra("country"));
        collection_textview.setText( getResources().getString(R.string.collection)+" : "+ intent.getStringExtra("collection"));
        institution_no_textview.setText(getResources().getString(R.string.institution_number)+" : " + intent.getStringExtra("institution_no"));
        bank_textview.setText(getResources().getString(R.string.bank_name)+" : " + intent.getStringExtra("bank"));
        swift_textview.setText(getResources().getString(R.string.swift_code)+" : " + intent.getStringExtra("swift"));
        card_no_textview.setText(getResources().getString(R.string.card_number)+" : "+ intent.getStringExtra("card_no"));
        sheba_no_textview.setText(getResources().getString(R.string.shepa)+" : " + intent.getStringExtra("sheba_no"));
        local_bank_code_textview.setText(getResources().getString(R.string.local_bank_code)+" : " + intent.getStringExtra("local_bank_code"));

        bank_address_textview.setText(getResources().getString(R.string.bank_address)+" : " + intent.getStringExtra("bank_address"));
        is_business_textview.setText(getResources().getString(R.string.is_buis)+" : " + intent.getStringExtra("is_business"));
        refTextView.setText(getResources().getString(R.string.reference)+" : " + intent.getStringExtra("ref"));
        rejection_reason_textview.setText(getResources().getString(R.string.rejection)+" : " + intent.getStringExtra("rejection_reason"));
        created_at_textview.setText(getResources().getString(R.string.created)+" : " + intent.getStringExtra("created_at"));
        a=intent.getStringExtra("tx_receipt");
        b=intent.getStringExtra("ben_id");
        Glide.with(this).load("http://staging-admin.zandexchange.com"+a).into(recipeImageView);
        Glide.with(this).load("http://staging-admin.zandexchange.com"+b).into(benIDImageView);

    }
}
