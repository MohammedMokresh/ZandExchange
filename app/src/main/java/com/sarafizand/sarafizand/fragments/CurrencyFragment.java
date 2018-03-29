package com.sarafizand.sarafizand.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.sarafizand.sarafizand.preference.PreferenceManager;
import com.sarafizand.sarafizand.utils.DialogUtil;
import com.sarafizand.sarafizand.utils.RecyclerTouchListener;
import com.sarafizand.sarafizand.adapters.CurrenciesAdapter;
import com.sarafizand.sarafizand.adapters.CurrenciesCategoryAdapter;
import com.sarafizand.sarafizand.models.Curencies;
import com.sarafizand.sarafizand.models.CurrenciesCategory;
import com.sarafizand.sarafizand.utils.ClickListener;
import com.sarafizand.sarafizand.utils.volleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class CurrencyFragment extends Fragment {
    public static RelativeLayout DropDownList;
    MenuItem item;
    public static Toolbar toolbar;
    View v;


    public RecyclerView currenciesrecyclerView;
    private CurrenciesAdapter currenciesadapter;
    private List<Curencies> currenciesList = new ArrayList<>();
    LinearLayoutManager mLayoutManager;
    private ProgressBar progressBar;


    private RecyclerView currenciesCategoryRecyclerView;
    private CurrenciesCategoryAdapter currenciesCategoryAdapter;
    private List<CurrenciesCategory> currenciesCategoriesList= new ArrayList<>();

    public static CurrencyFragment newInstance() {
        Bundle args = new Bundle();
        CurrencyFragment fragment = new CurrencyFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onResume() {
        super.onResume();
        GlobalVariables.hideKeyboard(getActivity());
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        GlobalVariables.hideKeyboard(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         v=inflater.inflate(R.layout.fragment_currency, container, false);
        currenciesCategoryRecyclerView = v.findViewById(R.id.CurrencyCategory);
        currenciesrecyclerView=v.findViewById(R.id.RateRecyclerView);
        toolbar =  v.findViewById(R.id.toolbar_currencies);
        progressBar =  v.findViewById(R.id.progressBar_cyclic);
        progressBar.setVisibility(View.GONE);

        DropDownList =  v.findViewById(R.id.DropDownList);
        YoYo.with(Techniques.FadeIn)
                .duration(700)
                .playOn(v.findViewById(R.id.DropDownList));
        DropDownList.setVisibility(View.VISIBLE);
        GlobalVariables.hideKeyboard(getActivity());

        toolbar.setTitle("Currencies");
        toolbar.inflateMenu(R.menu.currencies_toolbar_menu);
        Menu menu = toolbar.getMenu();
        item = menu.findItem(R.id.categories);
        item.setVisible(true);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if(DropDownList.getVisibility()==View.VISIBLE){
                    YoYo.with(Techniques.FadeIn)
                            .duration(700)
                            .playOn(v.findViewById(R.id.DropDownList));

                    DropDownList.setVisibility(View.INVISIBLE);
                }
                else {
                    YoYo.with(Techniques.FadeIn)
                            .duration(700)
                            .playOn(v.findViewById(R.id.DropDownList));

                    DropDownList.setVisibility(View.VISIBLE);
                    getCurrenciesCategory();

                }
                return false;
            }
        });
        currenciesCategoryRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), currenciesCategoryRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                DropDownList.setVisibility(View.INVISIBLE);
                getCurrencies(currenciesCategoriesList.get(position).getCurrenciesCategoryId()+"");

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        getCurrenciesCategory();
        return v;

    }


    private void getCurrenciesCategory() {
        currenciesCategoriesList= new ArrayList<>();
        JsonObjectRequest jss = new JsonObjectRequest(Request.Method.GET, ((GlobalVariables)getActivity().getApplication()).getCurrenciesCategoryURL(),
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

//                Log.e("mo",response.toString());
                try {
                    JSONObject s = response.getJSONObject("response");
                    JSONArray categoryArray=s.getJSONArray("all_categories");
                    for (int i=0;i<categoryArray.length();i++){
                        JSONObject currentcatCurrencies = categoryArray.getJSONObject(i);
                        int currenciesCategoryId=currentcatCurrencies.getInt("id");
                        String CurrenciesCategorytitle = currentcatCurrencies.getString("currency_category");

                        CurrenciesCategory CC=new CurrenciesCategory();
                        CC.setCurrencyCategoryName(CurrenciesCategorytitle);
                        CC.setCurrenciesCategoryId(currenciesCategoryId);

                        currenciesCategoriesList.add(CC);

                    }
                    LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                    currenciesCategoryRecyclerView.setLayoutManager(llm);

                    currenciesCategoryAdapter = new CurrenciesCategoryAdapter(getContext(), currenciesCategoriesList);
                    currenciesCategoryRecyclerView.setAdapter(currenciesCategoryAdapter);
                    currenciesCategoryAdapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }


                try {
                    JSONObject s = response.getJSONObject("client_token");
//                    ((GlobalVariables)getActivity().getApplication()).setClient_token(s.getString("client_token"));
                    PreferenceManager.getInstance().setClient_token(s.getString("client_token"));
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
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " +PreferenceManager.getInstance().getClient_token());
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
        DialogUtil.showProgressDialog("Loading",getFragmentManager());


    }
    public void getCurrencies(final String categoryID) {
        currenciesList=new ArrayList<>();
        JsonObjectRequest jss = new JsonObjectRequest(Request.Method.GET, ((GlobalVariables)getActivity().getApplication()).getCurrenciesApi()+categoryID,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject s = response.getJSONObject("client_token");
//                    ((GlobalVariables)getActivity().getApplication()).setClient_token(s.getString("client_token"));
                    PreferenceManager.getInstance().setClient_token(s.getString("client_token"));



                } catch (JSONException e) {
                    Log.e("mo",response.toString());
                    e.printStackTrace();
                }
                Log.e("mo",response.toString());
                try {

                    JSONObject s = response.getJSONObject("response");
                    JSONArray currenciesArray=s.getJSONArray("currencies");
                    for (int i=0;i<currenciesArray.length();i++){
                        JSONObject currentCurrencies = currenciesArray.getJSONObject(i);
                        String Currenciestitle = currentCurrencies.getString("name");
                        int currenciesId=currentCurrencies.getInt("id");
                        String currenciesFlag=currentCurrencies.getString("flag");
                        int currenciesSell=currentCurrencies.getInt("sell");
                        String currenciesBuy=currentCurrencies.getString("buy");
                        String currenciesStatus=currentCurrencies.getString("status");
                        String currencieslastUpdate=currentCurrencies.getString("created_at");


                        Curencies curencies=new Curencies();
                        curencies.setBuyPrice(currenciesBuy);
                        curencies.setCurrencyName(Currenciestitle);
                        curencies.setFlagName(currenciesFlag);
                        curencies.setLastUpdate(currencieslastUpdate);
                        curencies.setSellPrice(currenciesSell);
                        curencies.setStatus(currenciesStatus);
                        curencies.setCurrincyId(currenciesId);


                        currenciesList.add(curencies);

                    }
                    mLayoutManager = new LinearLayoutManager(getContext());
                    currenciesrecyclerView.setLayoutManager(mLayoutManager);
                    currenciesadapter = new CurrenciesAdapter(getContext(), currenciesList);
                    currenciesrecyclerView.setAdapter(currenciesadapter);
                    YoYo.with(Techniques.SlideInUp)
                            .duration(700)
                            .playOn(v.findViewById(R.id.RateRecyclerView));

                    currenciesadapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    Log.e("mo",response.toString());
                    e.printStackTrace();
                }


                Log.e("mo",response.toString());
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
        volleySingleton.getInstance(getContext()).addToRequestQueue(jss);
        DialogUtil.showProgressDialog("Loading",getFragmentManager());
    }


}
