package com.sarafizand.sarafizand.fragments;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sarafizand.sarafizand.GlobalVariables;
import com.sarafizand.sarafizand.R;
import com.sarafizand.sarafizand.preference.PreferenceManager;
import com.sarafizand.sarafizand.utils.DialogUtil;
import com.sarafizand.sarafizand.utils.MyDialogCloseListener;
import com.sarafizand.sarafizand.utils.volleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class AskForCancelBookingFragment extends DialogFragment implements Button.OnClickListener {

    TextView mTvDialogMSg;
    Button mBtnYes;
    Button mBtnNo;
    int transactionIdd;
    String message;
    public static AskForFavouriteFragment fragment;
    String id;

    public AskForCancelBookingFragment() {
        // Required empty public constructor
    }

    public static AskForCancelBookingFragment newInstance(String id) {

        Bundle args = new Bundle();

        AskForCancelBookingFragment fragment = new AskForCancelBookingFragment();
        args.putString("id", id);
        fragment.setArguments(args);
        return fragment;


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getString("id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_ask_for_cancel_booking, container, false);
        mBtnNo = rootView.findViewById(R.id.btn_no);
        mBtnYes = rootView.findViewById(R.id.btn_yes);
        mTvDialogMSg = rootView.findViewById(R.id.tv_msg);

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
                cancelBook(id);
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

    public void cancelBook(String id) {
        StringRequest jss = new StringRequest(Request.Method.GET, ((GlobalVariables) getActivity().getApplication()).getCancelBookURL() + id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject js= new JSONObject(response);
                            Toast.makeText(getContext(),js.getString("message"),4000).show();
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
                Log.e("can", error.toString());
                dismiss();
                DialogUtil.removeProgressDialog();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences prefs = getActivity().getSharedPreferences("zandPref", 0);
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
        volleySingleton.getInstance(getContext()).addToRequestQueue(jss);
        DialogUtil.showProgressDialog("Loading", getFragmentManager());

    }


    public void onDismiss(DialogInterface dialog)
    {
        Activity activity = getActivity();
        if(activity instanceof MyDialogCloseListener)
            ((MyDialogCloseListener)activity).handleDialogClose(dialog);
        PreferenceManager.getInstance().setBack(true);
    }

}
