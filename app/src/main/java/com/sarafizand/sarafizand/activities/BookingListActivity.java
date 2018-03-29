package com.sarafizand.sarafizand.activities;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.sarafizand.sarafizand.GlobalVariables;
import com.sarafizand.sarafizand.R;
import com.sarafizand.sarafizand.adapters.BookingAdapter;
import com.sarafizand.sarafizand.models.Booking;
import com.sarafizand.sarafizand.models.Curencies;
import com.sarafizand.sarafizand.preference.PreferenceManager;
import com.sarafizand.sarafizand.utils.DialogUtil;
import com.sarafizand.sarafizand.utils.MyDialogCloseListener;
import com.sarafizand.sarafizand.utils.volleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingListActivity extends AppCompatActivity implements MyDialogCloseListener{


    public static Toolbar toolbar;

    public RecyclerView bookingRecyclerView;
    private BookingAdapter bookingAdapter;
    private List<Booking> bookingList = new ArrayList<>();
    LinearLayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_list);
        bookingRecyclerView = findViewById(R.id.bookingRecyclerView);
        toolbar = findViewById(R.id.toolbar_currencies);
        toolbar.setTitle("Booking List");
        getBooking();
        GlobalVariables.hideKeyboard(this);

    }

    public void getBooking() {
        bookingList = new ArrayList<>();
        JsonObjectRequest jss = new JsonObjectRequest(Request.Method.GET, ((GlobalVariables) getApplication()).getBookingListURL(),
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray bookingArray = response.getJSONArray("booking");
                    for (int i = 0; i < bookingArray.length(); i++) {
                        JSONObject currentBooking = bookingArray.getJSONObject(i);
                        int bookingStatus = currentBooking.getInt("status");
                        int bookingid = currentBooking.getInt("id");


                        String Currenciestitle = currentBooking.getJSONArray("currency").getJSONObject(0).getString("name");
                        int currenciesId = currentBooking.getJSONArray("currency").getJSONObject(0).getInt("id");
                        String currenciesFlag = currentBooking.getJSONArray("currency").getJSONObject(0).getString("flag");
                        int currenciesSell = currentBooking.getJSONArray("currency").getJSONObject(0).getInt("sell");
                        String currenciesBuy = currentBooking.getJSONArray("currency").getJSONObject(0).getString("buy");
                        String currenciesStatus = currentBooking.getJSONArray("currency").getJSONObject(0).getString("status");
                        String currencieslastUpdate = currentBooking.getJSONArray("currency").getJSONObject(0).getString("created_at");


                        Curencies curencies = new Curencies();
                        curencies.setBuyPrice(currenciesBuy);
                        curencies.setCurrencyName(Currenciestitle);
                        curencies.setFlagName(currenciesFlag);
                        curencies.setLastUpdate(currencieslastUpdate);
                        curencies.setSellPrice(currenciesSell);
                        curencies.setStatus(currenciesStatus);
                        curencies.setCurrincyId(currenciesId);



                        Booking booking= new Booking();
                        booking.setCurrency(curencies);
                        booking.setStatus(bookingStatus);
                        booking.setId(bookingid);



                        bookingList.add(booking);

                    }


                    mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    bookingRecyclerView.setLayoutManager(mLayoutManager);
                    bookingAdapter = new BookingAdapter(getBaseContext(), bookingList,getSupportFragmentManager());
                    bookingRecyclerView.setAdapter(bookingAdapter);
                    YoYo.with(Techniques.SlideInUp)
                            .duration(700)
                            .playOn(findViewById(R.id.bookingRecyclerView));

                    bookingAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    Log.e("mo", response.toString());
                    e.printStackTrace();
                }


                Log.e("mo", response.toString());
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
                Log.e("mo", myVariable);
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
        volleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jss);
        DialogUtil.showProgressDialog("Loading", getSupportFragmentManager());
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        if (PreferenceManager.getInstance().getBack()){
            getBooking();
            PreferenceManager.getInstance().setBack(false);
        }

    }
}
