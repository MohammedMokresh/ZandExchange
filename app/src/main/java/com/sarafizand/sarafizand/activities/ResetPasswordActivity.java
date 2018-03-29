package com.sarafizand.sarafizand.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText newPasswordEditText, confirmNewPassworEditText, emailEditText;
    private TextInputLayout newPasswordName, confirmNewPasswordName, emailTextinputLayout;
    private Button btnChange;

    Toolbar toolbar;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        toolbar = findViewById(R.id.toolbar_change_pass);
        setSupportActionBar(toolbar);
        newPasswordName = findViewById(R.id.new_password_namein);
        confirmNewPasswordName = findViewById(R.id.confirm_new_password_namein);
        newPasswordEditText = findViewById(R.id.new_passwordin);
        confirmNewPassworEditText = findViewById(R.id.confirm_new_passwordin);
        emailEditText = findViewById(R.id.email_EditText);
        emailTextinputLayout = findViewById(R.id.email_textInputLayout);
        btnChange = findViewById(R.id.btn_changein);
        progressBar = findViewById(R.id.progressBar_cyclic);
        progressBar.setVisibility(View.GONE);


        newPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateNewPassword();
            }
        });
        confirmNewPassworEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateconfirmNewPassword();
            }
        });
        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateEmail();
            }
        });

        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        final Uri appLinkData = appLinkIntent.getData();
//        Toast.makeText(getApplicationContext(),appLinkData.getLastPathSegment(),Toast.LENGTH_SHORT).show();
//        Log.e("asd",appLinkData.getLastPathSegment());
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm(appLinkData.getLastPathSegment());
            }
        });

        // ATTENTION: This was auto-generated to handle app links.

    }

    private void submitForm(String token) {
        if (!validateNewPassword()) {
            return;
        }

        if (!validateconfirmNewPassword()) {
            return;
        }
        if (!validateEmail()) {
            return;
        }
        changePassword(emailEditText.getText().toString(), newPasswordEditText.getText().toString(), confirmNewPassworEditText.getText().toString(), token);
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean validateEmail() {
        String email = emailEditText.getText().toString().trim();

        if (email.isEmpty() || !isEmailValid(email)) {
            emailTextinputLayout.setError(getString(R.string.invalid_email));

            requestFocus(emailEditText);
            return false;
        } else {
            emailTextinputLayout.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateNewPassword() {
        if (newPasswordEditText.getText().toString().trim().isEmpty()) {
            newPasswordName.setError(getString(R.string.empty_new_pass));
            requestFocus(newPasswordEditText);
            return false;
        } else {
            newPasswordName.setErrorEnabled(false);
        }

        return true;
    }

    public boolean validateconfirmNewPassword() {
        if (confirmNewPassworEditText.getText().toString().trim().isEmpty()) {
            confirmNewPasswordName.setError(getString(R.string.empty_confirm_pass));
            requestFocus(confirmNewPassworEditText);
            return false;
        } else {
            confirmNewPasswordName.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    private void changePassword(String email, String newPassword, String confirmNewPassword, String tokenId) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("password", newPassword);
        params.put("password_confirm", confirmNewPassword);
        params.put("token_id", tokenId);


        JSONObject js = new JSONObject(params);


        JsonObjectRequest jss = new JsonObjectRequest(Request.Method.POST, ((GlobalVariables) getApplication()).getResetSubmitPasswordURL(),
                js, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    String ss = response.getString("message");
                    Toast.makeText(getApplicationContext(), ss, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    JSONObject s = response.getJSONObject("client_token");
//                    ((GlobalVariables) getApplication()).setClient_token(s.getString("client_token"));
                    PreferenceManager.getInstance().setClient_token(s.getString("client_token"));
                }catch (JSONException e){

                }
                try {
                    String ss = response.getString("status");
                    if (ss.equals("true")) {
                        onBackPressed();
                    }

                } catch (JSONException e) {
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
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        volleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jss);
        DialogUtil.showProgressDialog("Loading",getSupportFragmentManager());
    }


}
