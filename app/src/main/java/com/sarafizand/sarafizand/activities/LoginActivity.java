package com.sarafizand.sarafizand.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    TextView signUpTextView, forgotPasswordTextView;

    EditText emailEditText, passwordEditText;
    TextInputLayout emailTextName, passwordTextName;
    Button signIn;

    String emailString, passwordString;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailTextName = (TextInputLayout) findViewById(R.id.EmailTextName);
        passwordTextName = (TextInputLayout) findViewById(R.id.passwordTextName);
        emailEditText = (EditText) findViewById(R.id.EmailText);
        passwordEditText = (EditText) findViewById(R.id.passwordText);
        signIn = (Button) findViewById(R.id.signIn);
        signUpTextView = (TextView) findViewById(R.id.signUpTextView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_cyclic);
        forgotPasswordTextView = findViewById(R.id.forgot_password_text);
        progressBar.setVisibility(View.GONE);


        emailEditText.addTextChangedListener(new MyTextWatcher(emailEditText));
        passwordEditText.addTextChangedListener(new MyTextWatcher(passwordEditText));

        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(i);

            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_login);
        toolbar.setTitle("Sign in");
        //toolbar.setSubtitle("Subtitle");
        forgotPasswordTextView.setPaintFlags(forgotPasswordTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        setSupportActionBar(toolbar);

        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), ActivitySignUp.class);
                startActivity(i);
                finish();
            }
        });


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailString = emailEditText.getText().toString();
                passwordString = passwordEditText.getText().toString();
                submitForm(emailString, passwordString);

            }
        });


        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
        try {
            Log.e("ald",appLinkData.getLastPathSegment());
            Log.e("ald",appLinkData.getPathSegments().toString());
            Log.e("ald",appLinkData.toString());
            emailverification(appLinkData.toString());
        }catch (Exception e){
            Log.e("ald","aaaaaaaaa");
        }
    }

    private void submitForm(String email, String pass) {
        if (!validateEmail()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }
        try {
            SignINCall(email, pass);
        } catch (NullPointerException e) {

        }
    }

    private boolean validateEmail() {
        String email = emailEditText.getText().toString().trim();

        if (email.isEmpty()) {
            emailTextName.setError(getString(R.string.invalid_email));
            requestFocus(emailEditText);
            return false;
        } else {
            emailTextName.setErrorEnabled(false);
        }

        return true;
    }

    public boolean validatePassword() {
        if (passwordEditText.getText().toString().trim().isEmpty()) {
            passwordTextName.setError(getString(R.string.password_empty));
            requestFocus(passwordEditText);
            return false;
        } else {
            passwordTextName.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.EmailText:
                    validateEmail();
                    break;
                case R.id.passwordText:
                    validatePassword();
                    break;
            }
        }

    }

    private void SignINCall(String email, String pass) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("password", pass);
        JSONObject js = new JSONObject(params);


        JsonObjectRequest jss = new JsonObjectRequest(Request.Method.POST, ((GlobalVariables) getApplication()).getSignInUrl(),
                js, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONObject ss = null;
                try {
                    ss = response.getJSONObject("response");
                    Toast.makeText(getBaseContext(), ss.getString("message"), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                Toast.makeText(getBaseContext(),response.toString(),Toast.LENGTH_LONG).show();
                Log.e("mo", response.toString());
                try {
                    JSONObject s = response.getJSONObject("client_token");
//                    ((GlobalVariables) getApplication()).setClient_token(s.getString("client_token"));
                    PreferenceManager.getInstance().setClient_token(s.getString("client_token"));

                    JSONObject accessTokens = ss.getJSONObject("AccessTokens");
                    String accessToken = accessTokens.getString("access_token");
                    String refreshToken = accessTokens.getString("refresh_token");

                    SharedPreferences prefs = getSharedPreferences("zandPref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("accessToken", accessToken);
                    editor.putString("refreshToken", refreshToken);
                    editor.apply(); //important, otherwise it wouldn't save.

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
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
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + PreferenceManager.getInstance().getClient_token());
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                return headers;

            }

        };
        jss.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        volleySingleton.getInstance(this).addToRequestQueue(jss);
        DialogUtil.showProgressDialog("Loading",getSupportFragmentManager());
    }



    private void emailverification(String url) {

        JsonObjectRequest jss = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONObject ss = null;

                try {
                    ss = response.getJSONObject("response");
                    Toast.makeText(getBaseContext(), ss.getString("message"), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                Toast.makeText(getBaseContext(),response.toString(),Toast.LENGTH_LONG).show();
                Log.e("mo", response.toString());
                try {
                    JSONObject s = response.getJSONObject("client_token");
                    PreferenceManager.getInstance().setClient_token(s.getString("client_token"));

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
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + PreferenceManager.getInstance().getClient_token());
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                return headers;

            }

        };
        jss.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        volleySingleton.getInstance(this).addToRequestQueue(jss);
        DialogUtil.showProgressDialog("Loading",getSupportFragmentManager());
    }


}
