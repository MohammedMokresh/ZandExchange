package com.sarafizand.sarafizand.activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActivitySignUp extends AppCompatActivity {
    EditText userNameEdittext;
    EditText phoneEditText;
    EditText DoB;
    TextInputLayout userNameTextInputLayout, dateTextInputLayout, phoneTextInputLayout;
    Calendar myCalendar = Calendar.getInstance();
    TextView signInTextView;
    EditText emailEditText, passwordEditText, confirmPasswordEditText;
    TextInputLayout emailTextName, passwordTextName, confirmPasswordTextName;
    Button signUp;
    private ProgressBar progressBar;

    String nameString, emailString, passwordString, phoneString, dobString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        emailTextName =  findViewById(R.id.EmailTextName);
        passwordTextName =  findViewById(R.id.passwordTextName);
        confirmPasswordTextName =  findViewById(R.id.confirmpasswordTextName);
        emailEditText =  findViewById(R.id.EmailText);
        passwordEditText =  findViewById(R.id.passwordText);
        confirmPasswordEditText =  findViewById(R.id.confirmpasswordEditText);
        signUp =  findViewById(R.id.signUpButton);
        signInTextView =  findViewById(R.id.loginTextView);
        userNameTextInputLayout =  findViewById(R.id.user_name_TextInputLayout);
        dateTextInputLayout =  findViewById(R.id.date_TextInputLayout);
        phoneTextInputLayout =  findViewById(R.id.phone_TextInputLayout);
        userNameEdittext =  findViewById(R.id.username);
        DoB =  findViewById(R.id.DateOfBirth);
        phoneEditText =  findViewById(R.id.phone);
        progressBar =  findViewById(R.id.progressBar_cyclic);
        progressBar.setVisibility(View.GONE);



        emailEditText.addTextChangedListener(new MyTextWatcher(emailEditText));
        passwordEditText.addTextChangedListener(new MyTextWatcher(passwordEditText));
        confirmPasswordEditText.addTextChangedListener(new MyTextWatcher(confirmPasswordEditText));
        userNameEdittext.addTextChangedListener(new MyTextWatcher(userNameEdittext));
        DoB.addTextChangedListener(new MyTextWatcher(DoB));
        phoneEditText.addTextChangedListener(new MyTextWatcher(phoneEditText));
        Toolbar toolbar =  findViewById(R.id.toolbar1);
        toolbar.setTitle("Sign up");

        setSupportActionBar(toolbar);

        //  initialize the calender
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };


        DoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(ActivitySignUp.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dpd.show();
                dpd.setTitle("Select Date");


            }
        });


        signInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(i);
                finish();
            }
        });


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameString = userNameEdittext.getText().toString();
                passwordString = passwordEditText.getText().toString();
                emailString = emailEditText.getText().toString();
                dobString = DoB.getText().toString();
                phoneString = phoneEditText.getText().toString();
                submitForm(nameString, emailString, dobString, phoneString, passwordString);


            }
        });


    }
    private void submitForm(String name, String email, String dob, String phone, String password) {
        if (!validateusername()) {
            return;
        }
        if (!validateEmail()) {
            return;
        }
        if (!validatePassword()) {
            return;
        }
        if (!validateConfirmPassword()) {
            return;
        }

        if (!validatedate()) {
            return;
        }
        if (!validatephone()) {
            return;
        }
        try {
            SignUpCall(name, email, dob, phone, password);
        } catch (NullPointerException e) {

        }
    }
    private void updateLabel() {

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        DoB.setText(sdf.format(myCalendar.getTime()));
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
            emailTextName.setError(getString(R.string.invalid_email));

            requestFocus(emailEditText);
            return false;
        } else {
            emailTextName.setErrorEnabled(false);
        }

        return true;
    }
    public boolean validatePassword() {
        if (passwordEditText.getText().toString().trim().isEmpty() || passwordEditText.getText().toString().length() < 8) {


            passwordTextName.setError(getString(R.string.password_not_right));
            requestFocus(passwordEditText);
            return false;
        } else {
            passwordTextName.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateConfirmPassword() {
        if (confirmPasswordEditText.getText().toString().trim().isEmpty() || !confirmPasswordEditText.getText().toString().equals(passwordEditText.getText().toString())) {
            confirmPasswordTextName.setError(getString(R.string.password_not_match));
            requestFocus(confirmPasswordEditText);
            return false;
        } else {
            confirmPasswordTextName.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateusername() {
        if (userNameEdittext.getText().toString().trim().isEmpty()) {
            userNameTextInputLayout.setError(getString(R.string.empty_name));
            requestFocus(userNameEdittext);
            return false;
        } else {
            userNameTextInputLayout.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validatedate() {
        if (DoB.getText().toString().trim().isEmpty()) {
            dateTextInputLayout.setError(getString(R.string.empty_date));
            requestFocus(DoB);
            return false;
        } else {
            dateTextInputLayout.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validatephone() {
        if (phoneEditText.getText().toString().trim().isEmpty()) {
            phoneTextInputLayout.setError(getString(R.string.empty_phone));
            requestFocus(phoneEditText);
            return false;
        } else {
            phoneTextInputLayout.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    private void SignUpCall(final String name, final String email, final String dob, final String phonenum, final String pass) {
        SharedPreferences prefs = getSharedPreferences("zandPref", 0);
        String myVariable = prefs.getString("FCMToken", "");

        Map<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("password", pass);
        params.put("phone", phonenum);
        params.put("dob", dob);
        params.put("name", name);
        params.put("firebase_token", myVariable);
        JSONObject js = new JSONObject(params);


        JsonObjectRequest jss = new JsonObjectRequest(Request.Method.POST, ((GlobalVariables) getApplication()).getUrlRegister(),
                js, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject s = response.getJSONObject("client_token");
//                    ((GlobalVariables) getApplication()).setClient_token(s.getString("client_token"));
                    PreferenceManager.getInstance().setClient_token(s.getString("client_token"));
                    JSONObject ss = response.getJSONObject("response");
                    Toast.makeText(getBaseContext(), ss.getString("message"), Toast.LENGTH_LONG).show();
                    if(ss.getString("status").equals("true")) {
                    Intent intent=new Intent(ActivitySignUp.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
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
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        volleySingleton.getInstance(this).addToRequestQueue(jss);
        DialogUtil.showProgressDialog("Loading",getSupportFragmentManager());
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
                case R.id.confirmpasswordEditText:
                    validateConfirmPassword();
                    break;
                case R.id.username:
                    validateusername();
                    break;
                case R.id.DateOfBirth:
                    validatedate();
                    break;
                case R.id.phone:
                    validatephone();
                    break;
//                case R.id.Address:
//                    validateaddress();
//                    break;

            }
        }

    }
}
