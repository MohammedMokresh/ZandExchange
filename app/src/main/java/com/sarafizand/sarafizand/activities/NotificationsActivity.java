package com.sarafizand.sarafizand.activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.sarafizand.sarafizand.GlobalVariables;
import com.sarafizand.sarafizand.R;
import com.sarafizand.sarafizand.adapters.BookingAdapter;
import com.sarafizand.sarafizand.adapters.NotificationsAdapter;
import com.sarafizand.sarafizand.models.Booking;
import com.sarafizand.sarafizand.models.Curencies;
import com.sarafizand.sarafizand.models.Notifications;
import com.sarafizand.sarafizand.utils.ClickListener;
import com.sarafizand.sarafizand.utils.DialogUtil;
import com.sarafizand.sarafizand.utils.RecyclerTouchListener;
import com.sarafizand.sarafizand.utils.volleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationsActivity extends AppCompatActivity {
    private List<Notifications> notificationsList = new ArrayList<>();
    private RecyclerView notificationsRecyclerView;
    private NotificationsAdapter notificationsAdapter;
    LinearLayoutManager mLayoutManager;
    public static Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        notificationsRecyclerView =  findViewById(R.id.notifications_recycler_view);
        toolbar = findViewById(R.id.toolbar_currencies);
        toolbar.setTitle("Notifications");
        getBooking();
        GlobalVariables.hideKeyboard(this);

    }
    public void getBooking() {
        StringRequest jss = new StringRequest(Request.Method.GET, ((GlobalVariables) getApplication()).getNotificationListUrl(),
                 new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("mo", response.toString());
                try {
                    JSONObject jsonObject =new JSONObject(response);
                    JSONArray nontificationArray = jsonObject.getJSONArray("notifications");
                    for (int i = 0; i < nontificationArray.length(); i++) {
                        JSONObject currentnotification = nontificationArray.getJSONObject(i);
                        String notificationTitle = currentnotification.getString("title");
                        String message = currentnotification.getString("message");
                        String created_at = currentnotification.getString("created_at");


                        Notifications notifications = new Notifications();
                        notifications.setGenre(message);
                        notifications.setTitle(notificationTitle);
                        notifications.setYear(created_at);



                        notificationsList.add(notifications);

                    }


                    mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    notificationsRecyclerView.setLayoutManager(mLayoutManager);
                    notificationsAdapter = new NotificationsAdapter(getBaseContext(), notificationsList);
                    notificationsRecyclerView.setAdapter(notificationsAdapter);
                    YoYo.with(Techniques.SlideInUp)
                            .duration(700)
                            .playOn(findViewById(R.id.notifications_recycler_view));

                    notificationsAdapter.notifyDataSetChanged();
                    notificationsRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), notificationsRecyclerView, new ClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                        DialogUtil.showNotificationDetailsDialog(getSupportFragmentManager(),notificationsList.get(position).getTitle()
                        ,notificationsList.get(position).getGenre());
                        }

                        @Override
                        public void onLongClick(View view, int position) {
                            DialogUtil.showNotificationDetailsDialog(getSupportFragmentManager(),notificationsList.get(position).getTitle()
                                    ,notificationsList.get(position).getGenre());

                        }
                    }));

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
                Log.e("mo", error.toString());
                DialogUtil.removeProgressDialog();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences prefs = getSharedPreferences("zandPref", 0);
                String myVariable = prefs.getString("accessToken", "");

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


}
