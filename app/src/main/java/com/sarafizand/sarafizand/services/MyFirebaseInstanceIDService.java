package com.sarafizand.sarafizand.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.sarafizand.sarafizand.GlobalVariables;
import com.sarafizand.sarafizand.preference.PreferenceManager;
import com.sarafizand.sarafizand.utils.volleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class    MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e(TAG, "Refreshed token: " + refreshedToken);
//            ((GlobalVariables) getApplication()).setFCMToken(refreshedToken);
        SharedPreferences prefs = getSharedPreferences("zandPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("FCMToken", refreshedToken);
        editor.apply(); //important, otherwise it wouldn't save.
        // If you want to send messages to this application instance or.
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendFCMTokenToServer(((GlobalVariables) getApplication()).getSendFCMTokenUrl(),refreshedToken);
    }
    // [END refresh_token]



        public void sendFCMTokenToServer(String url, final String FCMToken) {

        HashMap<String, String> params2 = new HashMap<String, String>();
        params2.put("firebase_token", FCMToken);
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST,
                url
                , new JSONObject(params2),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject s = response.getJSONObject("client_token");
//                            ((GlobalVariables) getApplication()).setClient_token(s.getString("client_token"));
                            PreferenceManager.getInstance().setClient_token(s.getString("client_token"));
                            ((GlobalVariables) getApplication()).setFCMToken(FCMToken);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + PreferenceManager.getInstance().getClient_token());
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                return headers;

            }

        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                1000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        volleySingleton.getInstance(this).addToRequestQueue(postRequest);


    }
    public String trimMessage(String json, String key) {
        String trimmedString = null;

        try {
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }

    //Somewhere that has access to a context
    public void displayMessage(String toastString) {
        Toast.makeText(getBaseContext(), toastString, Toast.LENGTH_LONG).show();
    }
}