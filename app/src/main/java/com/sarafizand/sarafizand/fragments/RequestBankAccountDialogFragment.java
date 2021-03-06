package com.sarafizand.sarafizand.fragments;


import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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
import com.sarafizand.sarafizand.utils.DialogUtil;
import com.sarafizand.sarafizand.utils.volleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class RequestBankAccountDialogFragment extends DialogFragment implements Button.OnClickListener {

    TextView mTvDialogMSg;
    Button mBtnYes;
    Button mBtnNo;
    EditText amountEditText,countryEditText;

    TextInputLayout amountTextInputLayout,countryTextInputLayout;
    @SuppressLint("StaticFieldLeak")
    public static RequestBankAccountDialogFragment fragment;

    public RequestBankAccountDialogFragment() {
    }

    public static RequestBankAccountDialogFragment newInstance() {
        RequestBankAccountDialogFragment fragment = new RequestBankAccountDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_request_bank_account_dialog, container, false);
        mBtnNo = rootView.findViewById(R.id.btn_no);
        mBtnYes = rootView.findViewById(R.id.btn_yes);
        mTvDialogMSg = rootView.findViewById(R.id.tv_msg);
        amountEditText= rootView.findViewById(R.id.Amount);
        amountTextInputLayout= rootView.findViewById(R.id.amount_til);
        countryEditText=rootView.findViewById(R.id.country_edittext);
        countryTextInputLayout=rootView.findViewById(R.id.country_til);


        amountEditText.addTextChangedListener(new MyTextWatcher(amountEditText));
        countryEditText.addTextChangedListener(new MyTextWatcher(countryEditText));
        setCancelable(false);
        GlobalVariables.hideKeyboard(getActivity());
        mBtnYes.setOnClickListener(this);
        mBtnNo.setOnClickListener(this);
        return rootView;

    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        switch (i) {

            case R.id.btn_yes:
                if (validateAmount()&& validateCountry()){
                    requestBank(amountEditText.getText().toString(),countryEditText.getText().toString());
                }else return;

                fragment = null;
                break;

            case R.id.btn_no:
                dismiss();
                fragment = null;
                break;
        }
    }
    @Override
    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

        super.onResume();
    }


    public void requestBank(String amount,String country) {
        Map<String, String> params = new HashMap<>();
        params.put("amount", amount);
        params.put("country", country);
        JSONObject js = new JSONObject(params);

        JsonObjectRequest jss = new JsonObjectRequest(Request.Method.POST, ((GlobalVariables)getActivity().getApplication()).getRequestbankURL(),
                js, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Toast.makeText(getContext(),response.getString("message"),6000).show();
                    dismiss();


                    DialogUtil.removeProgressDialog();
                } catch (JSONException e) {
                    e.printStackTrace();
                    DialogUtil.removeProgressDialog();
                }
                Log.e("book", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("book", error.toString());


                DialogUtil.removeProgressDialog();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences prefs =getActivity().getSharedPreferences("zandPref", 0);
                String myVariable = prefs.getString("accessToken", "");
                Log.e("token", myVariable);
                HashMap<String, String> headers = new HashMap<>();
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
        volleySingleton.getInstance(getContext()).addToRequestQueue(jss);
        DialogUtil.showProgressDialog("Loading", getFragmentManager());

    }
    private boolean validateAmount() {
        if (amountEditText.getText().toString().trim().isEmpty() ) {
            amountTextInputLayout.setError(getString(R.string.empty_amount));
            requestFocus(amountEditText);
            return false;
        } else {
            amountTextInputLayout.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateCountry() {
        if (countryEditText.getText().toString().trim().isEmpty() ) {
            countryTextInputLayout.setError(getString(R.string.empty_country));
            requestFocus(countryEditText);
            return false;
        } else {
            countryTextInputLayout.setErrorEnabled(false);
        }

        return true;
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
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
                case R.id.Amount:
                    validateAmount();
                    break;
                case R.id.country_edittext:
                    validateCountry();
                    break;

            }
        }

    }

}
