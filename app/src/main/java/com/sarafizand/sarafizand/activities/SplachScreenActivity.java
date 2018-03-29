package com.sarafizand.sarafizand.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sarafizand.sarafizand.GlobalVariables;
import com.sarafizand.sarafizand.R;
import com.sarafizand.sarafizand.preference.PreferenceManager;
import com.sarafizand.sarafizand.utils.DialogUtil;
import com.sarafizand.sarafizand.utils.volleySingleton;
import com.tozny.crypto.android.AesCbcWithIntegrity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

public class SplachScreenActivity extends AppCompatActivity {
    String clientToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splach_screen);
        auth(((GlobalVariables) getApplication()).getUrl());


    }

    public void auth(String url) {
        String plainTextClientID=null;
        String plainTextClientSecret=null;

        AesCbcWithIntegrity.CipherTextIvMac cipherTextClientId = new AesCbcWithIntegrity.CipherTextIvMac(((GlobalVariables) getApplication()).getCipherTextClientIdString());
        AesCbcWithIntegrity.CipherTextIvMac cipherTextClientSecret = new AesCbcWithIntegrity.CipherTextIvMac(((GlobalVariables) getApplication()).getCipherTextClientSecretString());

        try {
            plainTextClientID = AesCbcWithIntegrity.decryptString(cipherTextClientId, ((GlobalVariables) getApplication()).getKeys());
            plainTextClientSecret = AesCbcWithIntegrity.decryptString(cipherTextClientSecret, ((GlobalVariables) getApplication()).getKeys());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        HashMap<String, String> params2 = new HashMap<String, String>();
        params2.put("client_id", plainTextClientID);
        params2.put("client_secret", plainTextClientSecret);
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST,
                url
                , new JSONObject(params2),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            clientToken = response.getString("client_token");
                            Log.e("clienttoken",clientToken);
                            PreferenceManager.getInstance().setClient_token(clientToken);
                            setTokens();

                            Intent i =new Intent(SplachScreenActivity.this,MainActivity.class);
                            startActivity(i);
                            finish();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        // Show timeout error message
                        Toast.makeText(getApplicationContext(),
                                "Oops. Timeout error!",
                                Toast.LENGTH_LONG).show();
                    }
                }
                Log.e("mohammed", error.toString());

            }
        }) {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();

                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                return headers;
            }


        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        volleySingleton.getInstance(this).addToRequestQueue(postRequest);


    }


    public void setTokens() {
//        ((GlobalVariables) this.getApplication()).setClient_token(clientToken);
        PreferenceManager.getInstance().setClient_token(clientToken);
    }
}
