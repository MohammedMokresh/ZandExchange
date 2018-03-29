package com.sarafizand.sarafizand.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.sarafizand.sarafizand.GlobalVariables;
import com.sarafizand.sarafizand.R;
import com.sarafizand.sarafizand.adapters.FavouriteAdapter;
import com.sarafizand.sarafizand.adapters.HistoryAdapter;
import com.sarafizand.sarafizand.models.Favourite;
import com.sarafizand.sarafizand.utils.ClickListener;
import com.sarafizand.sarafizand.utils.DialogUtil;
import com.sarafizand.sarafizand.utils.RecyclerTouchListener;
import com.sarafizand.sarafizand.utils.volleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SentAndReceiveHistoryActivity extends AppCompatActivity {
    public static RecyclerView favouriteRecyvleView;
    public static Toolbar toolbar;
    private HistoryAdapter favouriteAdapter;
    private List<Favourite> favouriteList = new ArrayList<>();
    LinearLayoutManager mLayoutManager;
    String benficiaryNameString, ben_acc_no_String, iban_String, ben_address_String,
            tel_String, ben_id_String, reg_no_String, id_no_String, from_currency_String, to_currency_String, country_String, institution_no_String,
            bank_String, swift_String, card_no_String, sheba_no_String, local_bank_code_String, is_business_String, bank_address_String, rejection_reason_String, created_at_String;
    int rate, amount, isSender, senderId, formId, status, collection_String, recieverID;
    String txRecipeString, adminIdString, refString, updatedAt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_and_receive_history);
        favouriteRecyvleView = findViewById(R.id.favourite_recyclevieww);
        toolbar = findViewById(R.id.toolbar_favourites);
        toolbar.setTitle("Transaction History");
        Intent intent = getIntent();
        int value = intent.getIntExtra("SOR", -1);
        if (value == 1) {
            getFavouritesSent();
        } else {
            getFavouritesRecieve();
        }
        GlobalVariables.hideKeyboard(this);
    }
    JSONArray favouritesArray;
    public void getFavouritesSent() {
        favouriteList = new ArrayList<>();
        JsonObjectRequest jss = new JsonObjectRequest(Request.Method.GET, ((GlobalVariables) getApplication()).getTransactionHisttoryURL(),
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("erroe", response.toString());
                try {

                    JSONArray favouritesArray = response.getJSONArray("sent");
                    if (favouritesArray.get(0).toString().equals("There is no transactions sent.")){
                        Toast.makeText(getApplicationContext(), favouritesArray.get(0).toString(), Toast.LENGTH_LONG).show();

                    }

                    for (int i = 0; i < favouritesArray.length(); i++) {
                        JSONObject currentFavourit = favouritesArray.getJSONObject(i);

                        try {
                            benficiaryNameString = currentFavourit.getString("ben_name");
                        } catch (Exception e) {
                            Log.e("sss",e.getMessage());

                        }
                        try {
                            ben_acc_no_String = currentFavourit.getString("ben_acc_no");

                        } catch (Exception e) {
                        }
                        try {
                            iban_String = currentFavourit.getString("iban");

                        } catch (Exception e) {
                        }
                        try {
                            ben_address_String = currentFavourit.getString("ben_address");

                        } catch (Exception e) {
                        }
                        try {
                            tel_String = currentFavourit.getString("tel");

                        } catch (Exception e) {
                        }
                        try {
                            reg_no_String = currentFavourit.getString("reg_no");

                        } catch (Exception e) {
                        }
                        try {
                            id_no_String = currentFavourit.getString("id_no");

                        } catch (Exception e) {
                        }
                        try {
                            from_currency_String = currentFavourit.getString("from_currency");

                        } catch (Exception e) {
                        }
                        try {
                            to_currency_String = currentFavourit.getString("to_currency");

                        } catch (Exception e) {
                        }
                        try {
                            country_String = currentFavourit.getString("country");

                        } catch (Exception e) {
                        }
                        try {
                            collection_String = currentFavourit.getInt("collection");

                        } catch (Exception e) {
                        }
                        try {
                            institution_no_String = currentFavourit.getString("institution_no");

                        } catch (Exception e) {
                        }
                        try {
                            bank_String = currentFavourit.getString("bank");

                        } catch (Exception e) {
                        }
                        try {
                            swift_String = currentFavourit.getString("swift");

                        } catch (Exception e) {
                        }
                        try {
                            card_no_String = currentFavourit.getString("card_no");

                        } catch (Exception e) {
                        }
                        try {
                            sheba_no_String = currentFavourit.getString("shepa_no");

                        } catch (Exception e) {
                        }
                        try {
                            local_bank_code_String = currentFavourit.getString("local_bank_code");

                        } catch (Exception e) {
                        }
                        try {
                            is_business_String = currentFavourit.getString("is_business");

                        } catch (Exception e) {
                        }
                        try {
                            bank_address_String = currentFavourit.getString("bank_address");

                        } catch (Exception e) {
                        }
                        try {
                            rejection_reason_String = currentFavourit.getString("rejection_reason");

                        } catch (Exception e) {
                        }
                        try {
                            created_at_String = currentFavourit.getString("created_at");

                        } catch (Exception e) {
                        }

                        try {
                            txRecipeString = currentFavourit.getString("tx_receipt");
                        } catch (Exception e) {
                        }

                        try {
                            adminIdString = currentFavourit.getString("admin_id");

                        } catch (Exception e) {
                        }
                        try {
                            refString = currentFavourit.getString("ref");
                        } catch (Exception e) {
                        }
                        try {
                            updatedAt = currentFavourit.getString("updated_at");
                        } catch (Exception e) {
                        }

                        try {
                            rate = currentFavourit.getInt("rate");
                        } catch (Exception e) {
                        }

                        try {
                            isSender = currentFavourit.getInt("is_sender");
                        } catch (Exception e) {
                        }
                        try {
                            senderId = currentFavourit.getInt("sender_id");
                        } catch (Exception e) {
                        }
                        try {
                            formId = currentFavourit.getInt("form_id");
                        } catch (Exception e) {
                        }
                        try {
                            status = currentFavourit.getInt("status");
                        } catch (Exception e) {
                        }

                        try {
                            ben_id_String = currentFavourit.getString("ben_id");
                        } catch (Exception e) {
                        }
                        try {
                            txRecipeString = currentFavourit.getString("tx_receipt");
                        } catch (Exception e) {
                        }
                        try {
                            amount = currentFavourit.getInt("amount");
                        } catch (Exception e) {
                        }
                        try {
                            recieverID = currentFavourit.getInt("receiver_id");
                        } catch (Exception e) {
                        }
                        Favourite favourite = new Favourite();

                        favourite.setBen_name(benficiaryNameString);
                        favourite.setBen_acc_no(ben_acc_no_String);
                        favourite.setBank(bank_String);
                        favourite.setCountry(country_String);
                        favourite.setTo_currency(to_currency_String);

                        favouriteList.add(favourite);

                    }
                    mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    favouriteRecyvleView.setLayoutManager(mLayoutManager);
                    favouriteAdapter = new HistoryAdapter(getBaseContext(), favouriteList);
                    favouriteRecyvleView.setAdapter(favouriteAdapter);
                    YoYo.with(Techniques.SlideInUp)
                            .duration(700)
                            .playOn(findViewById(R.id.favourite_recyclevieww));

                    favouriteAdapter.notifyDataSetChanged();
                    favouriteRecyvleView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), favouriteRecyvleView, new ClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            Intent i = new Intent(SentAndReceiveHistoryActivity.this, TransactionsHistoryDetailsActivity.class);
                            i.putExtra("ben_name", benficiaryNameString);
                            i.putExtra("ben_acc_no", ben_acc_no_String);
                            i.putExtra("iban", iban_String);
                            i.putExtra("ben_address", ben_address_String);
                            i.putExtra("tel", tel_String);
                            i.putExtra("ben_id", ben_id_String);
                            i.putExtra("reg_no", reg_no_String);
                            i.putExtra("id_no", id_no_String);
                            i.putExtra("from_currency", from_currency_String);
                            i.putExtra("to_currency", to_currency_String);
                            i.putExtra("rate", rate);
                            i.putExtra("amount", amount);
                            i.putExtra("country", country_String);
                            i.putExtra("collection", collection_String);
                            i.putExtra("institution_no", institution_no_String);
                            i.putExtra("bank", bank_String);
                            i.putExtra("swift", swift_String);
                            i.putExtra("card_no", card_no_String);
                            i.putExtra("sheba_no", sheba_no_String);
                            i.putExtra("local_bank_code", local_bank_code_String);
                            i.putExtra("tx_receipt", txRecipeString);
                            i.putExtra("bank_address", bank_address_String);
                            i.putExtra("is_sender", isSender);
                            i.putExtra("sender_id", senderId);
                            i.putExtra("receiver_id", recieverID);
                            i.putExtra("admin_id", adminIdString);
                            i.putExtra("is_business", is_business_String);
                            i.putExtra("ref", refString);
                            i.putExtra("form_id", formId);
                            i.putExtra("status", status);
                            i.putExtra("rejection_reason", rejection_reason_String);
                            i.putExtra("created_at", created_at_String);
                            i.putExtra("updated_at", updatedAt);

                            startActivity(i);
                        }

                        @Override
                        public void onLongClick(View view, int position) {

                        }
                    }));


                } catch (JSONException e) {
                    Log.e("mo", response.toString());
                    e.printStackTrace();
                }
                DialogUtil.removeProgressDialog();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                DialogUtil.removeProgressDialog();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences prefs = getSharedPreferences("zandPref", 0);
                String myVariable = prefs.getString("accessToken", "");
                Log.e("token", myVariable);
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + myVariable);
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                return headers;

            }

        };
        jss.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        volleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jss);
        DialogUtil.showProgressDialog("Loading", getSupportFragmentManager());

    }

    public void getFavouritesRecieve() {
        favouriteList = new ArrayList<>();
        JsonObjectRequest jss = new JsonObjectRequest(Request.Method.GET, ((GlobalVariables) getApplication()).getTransactionHisttoryURL(),
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("erroe", response.toString());
                try {

                    JSONArray favouritesArray = response.getJSONArray("received");
                    if (favouritesArray.get(0).toString().equals("There is no transactions received.")){
                        Toast.makeText(getApplicationContext(), favouritesArray.get(0).toString(), Toast.LENGTH_LONG).show();

                    }

                    for (int i = 0; i < favouritesArray.length(); i++) {
                        JSONObject currentFavourit = favouritesArray.getJSONObject(i);

                        try {
                            benficiaryNameString = currentFavourit.getString("ben_name");
                        } catch (Exception e) {
                            Log.e("sss",e.getMessage());

                        }
                        try {
                            ben_acc_no_String = currentFavourit.getString("ben_acc_no");

                        } catch (Exception e) {
                        }
                        try {
                            iban_String = currentFavourit.getString("iban");

                        } catch (Exception e) {
                        }
                        try {
                            ben_address_String = currentFavourit.getString("ben_address");

                        } catch (Exception e) {
                        }
                        try {
                            tel_String = currentFavourit.getString("tel");

                        } catch (Exception e) {
                        }
                        try {
                            reg_no_String = currentFavourit.getString("reg_no");

                        } catch (Exception e) {
                        }
                        try {
                            id_no_String = currentFavourit.getString("id_no");

                        } catch (Exception e) {
                        }
                        try {
                            from_currency_String = currentFavourit.getString("from_currency");

                        } catch (Exception e) {
                        }
                        try {
                            to_currency_String = currentFavourit.getString("to_currency");

                        } catch (Exception e) {
                        }
                        try {
                            country_String = currentFavourit.getString("country");

                        } catch (Exception e) {
                        }
                        try {
                            collection_String = currentFavourit.getInt("collection");

                        } catch (Exception e) {
                        }
                        try {
                            institution_no_String = currentFavourit.getString("institution_no");

                        } catch (Exception e) {
                        }
                        try {
                            bank_String = currentFavourit.getString("bank");

                        } catch (Exception e) {
                        }
                        try {
                            swift_String = currentFavourit.getString("swift");

                        } catch (Exception e) {
                        }
                        try {
                            card_no_String = currentFavourit.getString("card_no");

                        } catch (Exception e) {
                        }
                        try {
                            sheba_no_String = currentFavourit.getString("shepa_no");

                        } catch (Exception e) {
                        }
                        try {
                            local_bank_code_String = currentFavourit.getString("local_bank_code");

                        } catch (Exception e) {
                        }
                        try {
                            is_business_String = currentFavourit.getString("is_business");

                        } catch (Exception e) {
                        }
                        try {
                            bank_address_String = currentFavourit.getString("bank_address");

                        } catch (Exception e) {
                        }
                        try {
                            rejection_reason_String = currentFavourit.getString("rejection_reason");

                        } catch (Exception e) {
                        }
                        try {
                            created_at_String = currentFavourit.getString("created_at");

                        } catch (Exception e) {
                        }

                        try {
                            txRecipeString = currentFavourit.getString("tx_receipt");
                        } catch (Exception e) {
                        }

                        try {
                            adminIdString = currentFavourit.getString("admin_id");

                        } catch (Exception e) {
                        }
                        try {
                            refString = currentFavourit.getString("ref");
                        } catch (Exception e) {
                        }
                        try {
                            updatedAt = currentFavourit.getString("updated_at");
                        } catch (Exception e) {
                        }

                        try {
                            rate = currentFavourit.getInt("rate");
                        } catch (Exception e) {
                        }

                        try {
                            isSender = currentFavourit.getInt("is_sender");
                        } catch (Exception e) {
                        }
                        try {
                            senderId = currentFavourit.getInt("sender_id");
                        } catch (Exception e) {
                        }
                        try {
                            formId = currentFavourit.getInt("form_id");
                        } catch (Exception e) {
                        }
                        try {
                            status = currentFavourit.getInt("status");
                        } catch (Exception e) {
                        }

                        try {
                            ben_id_String = currentFavourit.getString("ben_id");
                        } catch (Exception e) {
                        }
                        try {
                            txRecipeString = currentFavourit.getString("tx_receipt");
                        } catch (Exception e) {
                        }
                        try {
                            amount = currentFavourit.getInt("amount");
                        } catch (Exception e) {
                        }
                        try {
                            recieverID = currentFavourit.getInt("receiver_id");
                        } catch (Exception e) {
                        }
                        Favourite favourite = new Favourite();

                        favourite.setBen_name(benficiaryNameString);
                        favourite.setBen_acc_no(ben_acc_no_String);
                        favourite.setBank(bank_String);
                        favourite.setCountry(country_String);
                        favourite.setTo_currency(to_currency_String);

                        favouriteList.add(favourite);

                    }
                    mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    favouriteRecyvleView.setLayoutManager(mLayoutManager);
                    favouriteAdapter = new HistoryAdapter(getBaseContext(), favouriteList);
                    favouriteRecyvleView.setAdapter(favouriteAdapter);
                    YoYo.with(Techniques.SlideInUp)
                            .duration(700)
                            .playOn(findViewById(R.id.favourite_recyclevieww));

                    favouriteAdapter.notifyDataSetChanged();
                    favouriteRecyvleView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), favouriteRecyvleView, new ClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            Intent i = new Intent(SentAndReceiveHistoryActivity.this, TransactionsHistoryDetailsActivity.class);
                            i.putExtra("ben_name", benficiaryNameString);
                            i.putExtra("ben_acc_no", ben_acc_no_String);
                            i.putExtra("iban", iban_String);
                            i.putExtra("ben_address", ben_address_String);
                            i.putExtra("tel", tel_String);
                            i.putExtra("ben_id", ben_id_String);
                            i.putExtra("reg_no", reg_no_String);
                            i.putExtra("id_no", id_no_String);
                            i.putExtra("from_currency", from_currency_String);
                            i.putExtra("to_currency", to_currency_String);
                            i.putExtra("rate", rate);
                            i.putExtra("amount", amount);
                            i.putExtra("country", country_String);
                            i.putExtra("collection", collection_String);
                            i.putExtra("institution_no", institution_no_String);
                            i.putExtra("bank", bank_String);
                            i.putExtra("swift", swift_String);
                            i.putExtra("card_no", card_no_String);
                            i.putExtra("sheba_no", sheba_no_String);
                            i.putExtra("local_bank_code", local_bank_code_String);
                            i.putExtra("tx_receipt", txRecipeString);
                            i.putExtra("bank_address", bank_address_String);
                            i.putExtra("is_sender", isSender);
                            i.putExtra("sender_id", senderId);
                            i.putExtra("receiver_id", recieverID);
                            i.putExtra("admin_id", adminIdString);
                            i.putExtra("is_business", is_business_String);
                            i.putExtra("ref", refString);
                            i.putExtra("form_id", formId);
                            i.putExtra("status", status);
                            i.putExtra("rejection_reason", rejection_reason_String);
                            i.putExtra("created_at", created_at_String);
                            i.putExtra("updated_at", updatedAt);

                            startActivity(i);
                        }

                        @Override
                        public void onLongClick(View view, int position) {

                        }
                    }));


                } catch (JSONException e) {
                    Log.e("mo", response.toString());
                    e.printStackTrace();
                }
                DialogUtil.removeProgressDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                DialogUtil.removeProgressDialog();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences prefs = getSharedPreferences("zandPref", 0);
                String myVariable = prefs.getString("accessToken", "");
                Log.e("token", myVariable);
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + myVariable);
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                return headers;

            }

        };
        jss.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        volleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jss);
        DialogUtil.showProgressDialog("Loading", getSupportFragmentManager());

    }


}
