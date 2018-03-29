package com.sarafizand.sarafizand.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.sarafizand.sarafizand.GlobalVariables;
import com.sarafizand.sarafizand.R;
import com.sarafizand.sarafizand.preference.PreferenceManager;

public class TransactionsHistoryActivity extends AppCompatActivity {
    CardView senderCardView,RecieverCardView;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions_history);
        toolbar = findViewById(R.id.toolbar_favourites);
        toolbar.setTitle("Transaction History");
        GlobalVariables.hideKeyboard(this);
        senderCardView=findViewById(R.id.sender_card);
        RecieverCardView=findViewById(R.id.reciever_card);
        senderCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferenceManager.getInstance().setIsSender(true);
                Intent i = new Intent(TransactionsHistoryActivity.this,SentAndReceiveHistoryActivity.class);
                i.putExtra("SOR",1);
                startActivity(i);



            }
        });
        RecieverCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferenceManager.getInstance().setIsSender(false);
                Intent i = new Intent(TransactionsHistoryActivity.this,SentAndReceiveHistoryActivity.class);
                i.putExtra("SOR",2);
                startActivity(i);


            }
        });
    }
}
