package com.sarafizand.sarafizand.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class AskForFavouriteFragment extends DialogFragment implements Button.OnClickListener  {

    TextView mTvDialogMSg;
    Button mBtnYes;
    Button mBtnNo;
    int transactionIdd;
    String message;
    public static AskForFavouriteFragment fragment;
    public static AskForFavouriteFragment newInstance() {
        fragment = new AskForFavouriteFragment();
        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        GlobalVariables.hideKeyboard(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView=inflater.inflate(R.layout.fragment_ask_for_favourite, container, false);

        mBtnNo =  rootView.findViewById(R.id.btn_no);
        mBtnYes =  rootView.findViewById(R.id.btn_yes);
        mTvDialogMSg =  rootView.findViewById(R.id.tv_msg);

        setCancelable(false);
        GlobalVariables.hideKeyboard(getActivity());
        mBtnYes.setOnClickListener(this);
        mBtnNo.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        int i=view.getId();
        switch (i){
            case R.id.btn_yes:
                createFavourite(PreferenceManager.getInstance().getTransactionId());
                try {

                }catch (Exception e){
                    Log.e("ess",e.getMessage());
                }


                dismiss();
                DialogUtil.showBrief(getFragmentManager());
                fragment =null;
//                getActivity().finish();
                break;
            case R.id.btn_no:
                dismiss();
                DialogUtil.showBrief(getFragmentManager());
                fragment =null;
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


    private void createFavourite(int transactionId) {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("transaction_id", transactionId);
        JSONObject js = new JSONObject(params);
        Log.e("test", js.toString());
        JsonObjectRequest jss = new JsonObjectRequest(Request.Method.POST
                , ((GlobalVariables) getActivity().getApplication()).getCreateFavourite(), js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("hello",response.toString());

//                        try {
//                            Toast.makeText(getActivity(), response.getString("message"), Toast.LENGTH_LONG).show();
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }


//                        progressBar.setVisibility(View.GONE);
//                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        DialogUtil.removeProgressDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                progressBar.setVisibility(View.GONE);
//                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

//                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                Log.e("res", error.toString());
                DialogUtil.removeProgressDialog();

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences prefs = getActivity().getSharedPreferences("zandPref", 0);
                String myVariable = prefs.getString("accessToken", "");
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
        volleySingleton.getInstance(getContext()).addToRequestQueue(jss);
//        progressBar.setVisibility(View.VISIBLE);
//        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
//                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        DialogUtil.showProgressDialog("Loading",getFragmentManager());
    }

}
