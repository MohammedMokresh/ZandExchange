package com.sarafizand.sarafizand.activities;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import com.sarafizand.sarafizand.utils.DialogUtil;
import com.sarafizand.sarafizand.utils.volleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class changePasswordActivity extends AppCompatActivity {
    private EditText newPasswordEditText, confirmNewPassworEditText,oldPasswordEditText;
    private TextInputLayout newPasswordName, confirmNewPasswordName,oldPasswordTextinputLayout;
    private Button btnChange;

    Toolbar toolbar;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        toolbar =  findViewById(R.id.toolbar_change_pass);
        setSupportActionBar(toolbar);



        newPasswordName =  findViewById(R.id.new_password_namein);
        confirmNewPasswordName =  findViewById(R.id.confirm_new_password_namein);
        newPasswordEditText =  findViewById(R.id.new_passwordin);
        confirmNewPassworEditText =  findViewById(R.id.confirm_new_passwordin);
        oldPasswordEditText =  findViewById(R.id.old_passwordin);
        oldPasswordTextinputLayout =  findViewById(R.id.old_password_namein);
        btnChange =  findViewById(R.id.btn_changein);
        progressBar =  findViewById(R.id.progressBar_cyclic);
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
        oldPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateOldPassword();
            }
        });


        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });
    }

    private void submitForm() {
        if (!validateNewPassword()) {
            return;
        }

        if (!validateconfirmNewPassword()) {
            return;
        }
        if (!validateOldPassword()) {
            return;
        }
        changePassword(oldPasswordEditText.getText().toString(),newPasswordEditText.getText().toString(),confirmNewPassworEditText.getText().toString());
    }

    private boolean validateOldPassword() {
        if (oldPasswordEditText.getText().toString().trim().isEmpty()) {
            oldPasswordTextinputLayout.setError(getString(R.string.empty_old_pass));
            requestFocus(oldPasswordEditText);
            return false;
        } else {
            oldPasswordTextinputLayout.setErrorEnabled(false);
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


    private void changePassword( String oldPassword,  String newPassword,  String confirmNewPassword) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("old_password", oldPassword);
        params.put("new_password", newPassword);
        params.put("password_confirm", confirmNewPassword);

        JSONObject js = new JSONObject(params);


        JsonObjectRequest jss = new JsonObjectRequest(Request.Method.POST, ((GlobalVariables) getApplication()).getChangePasswordURL(),
                js, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    String ss = response.getString("message");
                    Toast.makeText(getApplicationContext(),ss,Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    String ss = response.getString("status");
                    if (ss.equals("true")){
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
                SharedPreferences prefs = getSharedPreferences("zandPref", 0);
                String myVariable = prefs.getString("accessToken", "");


                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " +myVariable);
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
