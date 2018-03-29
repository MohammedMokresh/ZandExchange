package com.sarafizand.sarafizand.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sarafizand.sarafizand.GlobalVariables;
import com.sarafizand.sarafizand.R;
import com.sarafizand.sarafizand.preference.PreferenceManager;
import com.sarafizand.sarafizand.utils.DialogUtil;
import com.sarafizand.sarafizand.utils.volleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class FavouriteDetailsActivity extends AppCompatActivity {
    TextView benficiaryNameTextView, ben_acc_no_textview, iban_textview, ben_address_textview,
            tel_textview, ben_id_textview, reg_no_textview, id_no_textview, from_currency_textview, to_currency_textview, country_textview, collection_textview, institution_no_textview,
            bank_textview, swift_textview, card_no_textview, sheba_no_textview, local_bank_code_textview, is_business_textview, bank_address_textview, rejection_reason_textview, created_at_textview;

    String benficiaryNameString, ben_acc_no_String, iban_String, ben_address_String,
            tel_String, ben_id_String, reg_no_String, id_no_String, from_currency_String, to_currency_String, country_String, institution_no_String,
            bank_String, swift_String, card_no_String, sheba_no_String, local_bank_code_String, is_business_String, bank_address_String, rejection_reason_String, created_at_String;
    int rate, amount, isSender, formId, status,collection_String;
    String txRecipeString, adminIdString, refString, updatedAt,senderID;
    EditText amountEditText;
    TextInputLayout amountTil;
    Button sendButton;
    //    ProgressBar progressBar;
    ImageView recipeImageView;
    Bitmap recipeBitmap;
    String recipeString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_details);
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
        recipeImageView=findViewById(R.id.recipe_imageview);
        amountEditText = findViewById(R.id.Amount);
        amountTil = findViewById(R.id.amount_til);
        sendButton = findViewById(R.id.send_button);
//        progressBar = findViewById(R.id.progressBar_cyclic);
//        progressBar.setVisibility(View.GONE);
        GlobalVariables.hideKeyboard(this);
        final String value = getIntent().getExtras().getString("fav_id");
        getFavourites(Integer.parseInt(value));
        recipeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                startActivityForResult(intent, 1);

            }
        });
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getFavourites(Integer.parseInt(value));
                recipeString = encodeTobase64(recipeBitmap);
                sendRequest(Integer.parseInt(amountEditText.getText().toString()),recipeString );
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }
            try {
                recipeBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());

                recipeImageView.setImageBitmap(recipeBitmap);
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

//        Log.e("LOOK", imageEncoded);
        return imageEncoded;
    }

    public void getFavourites(int favouriteId) {
        JsonObjectRequest jss = new JsonObjectRequest(Request.Method.GET, ((GlobalVariables) getApplication()).getFavouriteDetails() + favouriteId,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    Log.e("erroe",response.toString());
//                    JSONObject favouritesArray = response.getJSONObject("favorite");
                    JSONObject currentFavourit = response.getJSONObject("favorite");
                    Log.e("erroe",response.toString());
                    try {
                        benficiaryNameTextView.setText(getResources().getString(R.string.beneficiary_name) + currentFavourit.getString("ben_name"));
                        benficiaryNameString = currentFavourit.getString("ben_name");
                        if (currentFavourit.getString("ben_name").equals("null")) {
                            benficiaryNameTextView.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        benficiaryNameTextView.setVisibility(View.GONE);
                    }
                    try {
                        ben_acc_no_textview.setText(getResources().getString(R.string.beneficiary_account_number) + currentFavourit.getString("ben_acc_no"));
                        ben_acc_no_String = currentFavourit.getString("ben_acc_no");
                        if (currentFavourit.getString("ben_acc_no").equals("null")) {
                            ben_acc_no_textview.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        ben_acc_no_textview.setVisibility(View.GONE);
                    }
                    try {
                        iban_textview.setText(getResources().getString(R.string.iban)+" : "+ currentFavourit.getString("iban"));
                        iban_String = currentFavourit.getString("iban");
                        if (currentFavourit.getString("iban").equals("null")) {
                            iban_textview.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        iban_textview.setVisibility(View.GONE);
                    }
                    try {
                        ben_address_textview.setText(getResources().getString(R.string.beneficiary_address)+" : " + currentFavourit.getString("ben_address"));
                        ben_address_String = currentFavourit.getString("ben_address");
                        if (currentFavourit.getString("ben_address").equals("null")) {
                            ben_address_textview.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        ben_address_textview.setVisibility(View.GONE);
                    }
                    try {
                        tel_textview.setText(getResources().getString(R.string.phone)+" : " + currentFavourit.getString("tel"));
                        tel_String = currentFavourit.getString("tel");
                        if (currentFavourit.getString("tel").equals("null")) {
                            tel_textview.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        tel_textview.setVisibility(View.GONE);
                    }
                    try {
                        reg_no_textview.setText(getResources().getString(R.string.company_registration_number)+" : " + currentFavourit.getString("reg_no"));
                        reg_no_String = currentFavourit.getString("reg_no");
                        if (currentFavourit.getString("reg_no").equals("null")) {
                            reg_no_textview.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        reg_no_textview.setVisibility(View.GONE);
                    }
                    try {
                        id_no_textview.setText(getResources().getString(R.string.card_number)+" : " + currentFavourit.getString("id_no"));
                        id_no_String = currentFavourit.getString("id_no");
                        if (currentFavourit.getString("id_no").equals("null")) {
                            id_no_textview.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        id_no_textview.setVisibility(View.GONE);
                    }
                    try {
                        from_currency_textview.setText(getResources().getString(R.string.from_currency)+" : " + currentFavourit.getString("from_currency"));
                        from_currency_String = currentFavourit.getString("from_currency");
                        if (currentFavourit.getString("from_currency").equals("null")) {
                            from_currency_textview.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        from_currency_textview.setVisibility(View.GONE);
                    }
                    try {
                        to_currency_textview.setText(getResources().getString(R.string.to_currency)+" : "+ currentFavourit.getString("to_currency"));
                        to_currency_String = currentFavourit.getString("to_currency");
                        if (currentFavourit.getString("to_currency").equals("null")) {

                            to_currency_textview.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        to_currency_textview.setVisibility(View.GONE);
                    }
                    try {
                        country_textview.setText(getResources().getString(R.string.country)+" : "+ currentFavourit.getString("country"));
                        country_String = currentFavourit.getString("country");
                        if (currentFavourit.getString("country").equals("null")) {
                            country_textview.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        country_textview.setVisibility(View.GONE);
                    }
                    try {
                        collection_textview.setText(getResources().getString(R.string.collection)+" : "+ currentFavourit.getInt("collection"));
                        collection_String = currentFavourit.getInt("collection");
                        if (currentFavourit.getString("collection").equals("null")) {
                            collection_textview.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        collection_textview.setVisibility(View.GONE);
                    }
                    try {
                        institution_no_textview.setText(getResources().getString(R.string.institution_number)+" : " + currentFavourit.getString("institution_no"));
                        institution_no_String = currentFavourit.getString("institution_no");
                        if (currentFavourit.getString("institution_no").equals("null")) {
                            institution_no_textview.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        institution_no_textview.setVisibility(View.GONE);
                    }
                    try {
                        bank_textview.setText(getResources().getString(R.string.bank_name)+" : " + currentFavourit.getString("bank"));
                        bank_String = currentFavourit.getString("bank");
                        if (currentFavourit.getString("bank").equals("null")) {
                            bank_textview.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        bank_textview.setVisibility(View.GONE);
                    }
                    try {
                        swift_textview.setText(getResources().getString(R.string.swift_code)+" : " + currentFavourit.getString("swift"));
                        swift_String = currentFavourit.getString("swift");
                        if (currentFavourit.getString("swift").equals("null")) {
                            swift_textview.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        swift_textview.setVisibility(View.GONE);
                    }
                    try {
                        card_no_textview.setText(getResources().getString(R.string.card_number)+" : " + currentFavourit.getString("card_no"));
                        card_no_String = currentFavourit.getString("card_no");
                        if (currentFavourit.getString("card_no").equals("null")) {
                            card_no_textview.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        card_no_textview.setVisibility(View.GONE);
                    }
                    try {
                        sheba_no_textview.setText(getResources().getString(R.string.shepa)+" : "+ currentFavourit.getString("shepa_no"));
                        sheba_no_String = currentFavourit.getString("shepa_no");
                        if (currentFavourit.getString("shepa_no").equals("null")) {
                            sheba_no_textview.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        sheba_no_textview.setVisibility(View.GONE);
                    }
                    try {
                        local_bank_code_textview.setText(getResources().getString(R.string.local_bank_code)+" : " + currentFavourit.getString("local_bank_code"));
                        local_bank_code_String = currentFavourit.getString("local_bank_code");
                        if (currentFavourit.getString("local_bank_code").equals("null")) {
                            local_bank_code_textview.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        local_bank_code_textview.setVisibility(View.GONE);
                    }
                    try {
                        is_business_textview.setText(getResources().getString(R.string.is_buis)+" : "+ currentFavourit.getString("is_business"));
                        is_business_String = currentFavourit.getString("is_business");
                        if (currentFavourit.getString("is_business").equals("null")) {
                            is_business_textview.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        is_business_textview.setVisibility(View.GONE);
                    }
                    try {
                        bank_address_textview.setText(getResources().getString(R.string.bank_address)+" : " + currentFavourit.getString("bank_address"));
                        bank_address_String = currentFavourit.getString("bank_address");
                        if (currentFavourit.getString("bank_address").equals("null")) {
                            bank_address_textview.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        bank_address_textview.setVisibility(View.GONE);
                    }
                    try {
                        rejection_reason_textview.setText(getResources().getString(R.string.rejection)+" : "+ currentFavourit.getString("rejection_reason"));
                        rejection_reason_String = currentFavourit.getString("rejection_reason");
                        if (currentFavourit.getString("rejection_reason").equals("null")) {
                            rejection_reason_textview.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        rejection_reason_textview.setVisibility(View.GONE);
                    }
                    try {
                        created_at_textview.setText(getResources().getString(R.string.created)+" : " + currentFavourit.getString("created_at"));
                        created_at_String = currentFavourit.getString("created_at");
                        if (currentFavourit.getString("created_at").equals("null")) {
                            created_at_textview.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        created_at_textview.setVisibility(View.GONE);
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
                        senderID = currentFavourit.getString("sender_id");
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
                } catch (Exception e) {

                }

//                    progressBar.setVisibility(View.GONE);
//                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            DialogUtil.removeProgressDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            Log.e("asdsd",error.getMessage());
//                progressBar.setVisibility(View.GONE);
//                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
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
//        progressBar.setVisibility(View.VISIBLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
//                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        DialogUtil.showProgressDialog("Loading",getSupportFragmentManager());

    }

    private void sendRequest(int amount, String recipt) {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("amount", amount);
        params.put("bank", bank_String);
        params.put("bank_address", bank_address_String);
        params.put("ben_acc_no", ben_acc_no_String);
        params.put("ben_address", ben_address_String);
        params.put("ben_id", ben_id_String);
        params.put("ben_name", benficiaryNameString);
        params.put("collection", collection_String);
        params.put("country", country_String);
        params.put("form_id", formId);
        params.put("from_currency", from_currency_String);
        params.put("is_business", is_business_String);
        params.put("is_sender", isSender);
        params.put("reg_no", reg_no_String);
        params.put("swift", swift_String);
        params.put("rate", rate);
        params.put("ref", refString);
        params.put("tel", tel_String);
        params.put("tx_receipt", txRecipeString);
        params.put("to_currency", to_currency_String);
        params.put("sender_id", senderID);
        params.put("iban", iban_String);
        params.put("reg_no", reg_no_String);
        params.put("id_no", id_no_String);
        params.put("institution_no", institution_no_String);
        params.put("card_no", card_no_String);
        params.put("sheba_no", sheba_no_String);
        params.put("local_bank_code", local_bank_code_String);
        params.put("admin_id", adminIdString);
        params.put("status", status);
        params.put("rejection_reason", rejection_reason_String);
        params.put("created_at", created_at_String);
        params.put("updated_at", updatedAt);

        JSONObject js = new JSONObject(params);

        Log.e("test", js.toString());
        JsonObjectRequest jss = new JsonObjectRequest(Request.Method.POST
                , ((GlobalVariables) getApplication()).getTransactionAPI(), js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("test", response.toString());
                        try {
                            Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                            PreferenceManager.getInstance().setTransactionId(response.getInt("transation_id"));
                            DialogUtil.showAskForFavouriteDialog(getSupportFragmentManager());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    DialogUtil.removeProgressDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            DialogUtil.removeProgressDialog();
//                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                Log.e("res", error.toString());

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
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        volleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jss);
        DialogUtil.showProgressDialog("Loading",getSupportFragmentManager());
    }


}
