package com.sarafizand.sarafizand.activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
import com.sarafizand.sarafizand.adapters.CurrenciesAdapter;
import com.sarafizand.sarafizand.adapters.FavouriteAdapter;
import com.sarafizand.sarafizand.models.Curencies;
import com.sarafizand.sarafizand.models.Favourite;
import com.sarafizand.sarafizand.preference.PreferenceManager;
import com.sarafizand.sarafizand.utils.DialogUtil;
import com.sarafizand.sarafizand.utils.volleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavouritesActivity extends AppCompatActivity {
    public static RecyclerView favouriteRecyvleView;
    public static Toolbar toolbar;
    private FavouriteAdapter favouriteAdapter;
    private List<Favourite> favouriteList = new ArrayList<>();
    LinearLayoutManager mLayoutManager;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        favouriteRecyvleView=findViewById(R.id.favourite_recycleview);
        toolbar=findViewById(R.id.toolbar_favourites);
        toolbar.setTitle("Favourites");
        progressBar =  findViewById(R.id.progressBar_cyclic);
        progressBar.setVisibility(View.GONE);

        getFavourites();
        GlobalVariables.hideKeyboard(this);

    }

    public void getFavourites() {
        favouriteList=new ArrayList<>();
        JsonObjectRequest jss = new JsonObjectRequest(Request.Method.GET, ((GlobalVariables)getApplication()).getFavouritesList(),
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray favouritesArray=response.getJSONArray("favorites");
                    for (int i=0;i<favouritesArray.length();i++){
                        JSONObject currentFavourit = favouritesArray.getJSONObject(i);
                        String favouriteId=currentFavourit.getString("favorite_id");
                        String benName=currentFavourit.getString("ben_name");
                        String benAccountNumber=currentFavourit.getString("ben_acc_no");
                        String bankNmae=currentFavourit.getString("bank");
                        String country=currentFavourit.getString("country");
                        String currency=currentFavourit.getString("to_currency");

                        Favourite favourite= new Favourite();
                        favourite.setFavorite_id(favouriteId);
                        favourite.setBen_name(benName);
                        favourite.setBen_acc_no(benAccountNumber);
                        favourite.setBank(bankNmae);
                        favourite.setCountry(country);
                        favourite.setTo_currency(currency);


                        favouriteList.add(favourite);
                        Log.e("id",favouriteId);

                    }
                    mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    favouriteRecyvleView.setLayoutManager(mLayoutManager);
                    favouriteAdapter = new FavouriteAdapter(getBaseContext(), favouriteList);
                    favouriteRecyvleView.setAdapter(favouriteAdapter);
                    YoYo.with(Techniques.SlideInUp)
                            .duration(700)
                            .playOn(findViewById(R.id.favourite_recycleview));

                    favouriteAdapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    Log.e("mo",response.toString());
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
                Log.e("token",myVariable);
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
