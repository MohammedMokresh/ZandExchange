
package com.sarafizand.sarafizand.activities;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

public class ForgotPasswordActivity extends AppCompatActivity {
    EditText emailEditText;
    TextInputLayout emailTextName;
    Button resetPasswordButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        emailTextName = findViewById(R.id.EmailTextName);

        emailEditText = findViewById(R.id.EmailText);
        resetPasswordButton = (Button) findViewById(R.id.reset_password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_cyclic);
        progressBar.setVisibility(View.GONE);

        emailEditText.addTextChangedListener(new MyTextWatcher(emailEditText));
        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm(emailEditText.getText().toString());
            }
        });

    }

    private void submitForm(String email) {
        if (!validateEmail()) {
            return;
        }
        try {
            resetPasswordCall(email);
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
            }
        }

    }


    private void resetPasswordCall(String email) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        JSONObject js = new JSONObject(params);


        JsonObjectRequest jss = new JsonObjectRequest(Request.Method.POST, ((GlobalVariables) getApplication()).getResetPasswordURL(),
                js, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("asd", response.toString());

                JSONObject ss = null;
                try {
                    ss = response.getJSONObject("response");
                    Toast.makeText(getBaseContext(), ss.getString("message"), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    JSONObject s = response.getJSONObject("client_token");
//                    ((GlobalVariables) getApplication()).setClient_token(s.getString("client_token"));
                    PreferenceManager.getInstance().setClient_token(s.getString("client_token"));
                } catch (JSONException e) {

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
        DialogUtil.showProgressDialog("Loading", getSupportFragmentManager());
    }


}
