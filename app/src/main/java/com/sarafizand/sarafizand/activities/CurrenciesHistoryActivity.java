package com.sarafizand.sarafizand.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.sarafizand.sarafizand.GlobalVariables;
import com.sarafizand.sarafizand.R;
import com.sarafizand.sarafizand.models.Curencies;
import com.sarafizand.sarafizand.preference.PreferenceManager;
import com.sarafizand.sarafizand.utils.DialogUtil;
import com.sarafizand.sarafizand.utils.volleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrenciesHistoryActivity extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener {
    private List<Curencies> currenciesList = new ArrayList<>();
    List<Entry> entries;
    LineChart chart;
    ArrayList xVals = new ArrayList();
    ArrayList<Entry> yVals = new ArrayList<Entry>();
    ArrayList<Entry> yValsSell = new ArrayList<Entry>();
    ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
    EditText fromToEditText;
    LineDataSet set1;
    LineDataSet set2;
    DatePickerDialog dpd;
    int id;
    Button bookButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currencies_history);
        chart = (LineChart) findViewById(R.id.chart);
        entries = new ArrayList<Entry>();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        bookButton = findViewById(R.id.booking_button);
        toolbar.setTitle("Currency History");
        //toolbar.setSubtitle("Subtitle");

        setSupportActionBar(toolbar);
        GlobalVariables.hideKeyboard(this);
        fromToEditText = findViewById(R.id.calender_from_to);
        fromToEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                dpd = com.borax12.materialdaterangepicker.date.DatePickerDialog.newInstance(
                        CurrenciesHistoryActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );

                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });


        Intent intent = getIntent();
        id = intent.getIntExtra("currencyID", -1);
        getCurrenciesHistory(String.valueOf(id));

        if (PreferenceManager.getInstance().getIsMember()) {
            bookButton.setVisibility(View.VISIBLE);
        }else {
            PreferenceManager.getInstance().setIsMember(false);
            bookButton.setVisibility(View.GONE);
        }
        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                book(id);
                SharedPreferences prefs = getSharedPreferences("zandPref", 0);
                String myVariable = prefs.getString("accessToken", "");

                if (myVariable=="") {
                    PreferenceManager.getInstance().setIs_login(true);
                    finish();
                }else {
                    DialogUtil.showAskForBookingDialog(getSupportFragmentManager(), String.valueOf(id));
                }

            }
        });


    }

    public void getCurrenciesHistory(final String currenciesID) {
        JsonObjectRequest jss = new JsonObjectRequest(Request.Method.GET, ((GlobalVariables) getApplication()).getCurrenciesHistoryApi() + currenciesID,
                null, new Response.Listener<JSONObject>() {

            ArrayList<String> ar;

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject s = response.getJSONObject("client_token");
//                    ((GlobalVariables) getApplication()).setClient_token(s.getString("client_token"));
                    PreferenceManager.getInstance().setClient_token(s.getString("client_token"));

                } catch (JSONException e) {
                    Log.e("mo", response.toString());
                    e.printStackTrace();
                }
                Log.e("mo", response.toString());
                try {

                    JSONObject s = response.getJSONObject("response");
                    JSONArray currenciesArray = s.getJSONArray("currencies");
//                    for (int i=currenciesArray.length();i>=0;i--){
                    for (int i = 0; i < currenciesArray.length(); i++) {
                        JSONObject currentCurrencies = currenciesArray.getJSONObject(i);
                        String Currenciestitle = currentCurrencies.getString("name");
                        int currenciesId = currentCurrencies.getInt("id");
                        String currenciesFlag = currentCurrencies.getString("flag");
                        int currenciesSell = currentCurrencies.getInt("sell");
                        String currenciesBuy = currentCurrencies.getString("buy");
                        String currenciesStatus = currentCurrencies.getString("status");
                        String currencieslastUpdate = currentCurrencies.getString("created_at");

//                        Toast.makeText(getApplicationContext(),  Currenciestitle,Toast.LENGTH_LONG).show();
                        Curencies curencies = new Curencies();
                        curencies.setBuyPrice(currenciesBuy);
                        curencies.setCurrencyName(Currenciestitle);
                        curencies.setFlagName(currenciesFlag);
                        curencies.setLastUpdate(currencieslastUpdate);
                        curencies.setSellPrice(currenciesSell);
                        curencies.setStatus(currenciesStatus);
                        curencies.setCurrincyId(currenciesId);


                        currenciesList.add(curencies);

                        xVals.add(currencieslastUpdate);
                        yVals.add(new Entry(Float.valueOf(currenciesBuy), i));
                        yValsSell.add(new Entry(Float.valueOf(currenciesSell), i));


                        // create a dataset and give it a type
                        set2 = new LineDataSet(yValsSell, "Sell");
                        set2.setFillAlpha(110);
                        set2.setColor(getResources().getColor(R.color.blue));
                        set2.setCircleColor(getResources().getColor(R.color.blue));
                        set2.setLineWidth(4f);
                        set2.setCircleRadius(5f);
                        set2.setValueTextColor(Color.WHITE);
                        set2.setDrawCircleHole(false);
                        set2.setValueTextSize(9f);
                        set2.setFillColor(getResources().getColor(R.color.blue));
                        set2.setDrawFilled(true);
                        set2.setDrawCubic(true);


                        // create a dataset and give it a type
                        set1 = new LineDataSet(yVals, "Buy");
                        set1.setFillAlpha(110);
                        set1.setColor(getResources().getColor(R.color.colorAccent));
                        set1.setCircleColor(getResources().getColor(R.color.colorAccent));
                        set1.setLineWidth(4f);
                        set1.setCircleRadius(5f);
                        set1.setValueTextColor(Color.WHITE);
                        set1.setDrawCircleHole(false);
                        set1.setValueTextSize(9f);
                        set1.setFillColor(getResources().getColor(R.color.colorAccent));
                        set1.setDrawFilled(true);
                        set1.setDrawCubic(true);

//
//                        SimpleDateFormat datetimeFormatter1 = new SimpleDateFormat(
//                                "yyyy-MM-dd hh:mm:ss");
//                        Date lFromDate1 = datetimeFormatter1.parse(currencieslastUpdate);


//                        entries.add(new Entry(lFromDate1.getHours()+lFromDate1.getDate() ,Float.valueOf(currenciesBuy)));
//                        Toast.makeText(getApplicationContext(),  lFromDate1.getTime(),Toast.LENGTH_LONG).show();


//                        Timestamp fromTS1 = new Timestamp(lFromDate1.getTime());
                        chart.setDescription(Currenciestitle);
                        chart.setDescriptionTextSize(10f);
                        chart.setDescriptionColor(Color.WHITE);


                    }
                    try{
                        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
                        set2.setAxisDependency(YAxis.AxisDependency.LEFT);

                    }catch (Exception e){

                    }
                    dataSets.add(set2);
                    dataSets.add(set1); // add the datasets
                    try {
                        LineData data = new LineData(xVals, dataSets);
                        chart.setData(data);
                    }catch (Exception e){

                    }


                    chart.setBorderColor(Color.WHITE);
                    // get the legend (only possible after setting data)
                    Legend l = chart.getLegend();
                    chart.animateX(1000, Easing.EasingOption.EaseInCubic);

                    // modify the legend ...
                    // l.setPosition(LegendPosition.LEFT_OF_CHART);
                    l.setForm(Legend.LegendForm.CIRCLE);
                    l.setTextColor(Color.WHITE);
                    l.setTextSize(10f);


                    // data has AxisDependency.LEFT
                    YAxis left = chart.getAxisRight();
                    left.setDrawLabels(true); // no axis labels
                    left.setDrawAxisLine(true); // no axis line
                    left.setDrawGridLines(false); // no grid lines
                    left.setDrawZeroLine(true); // draw a zero line
                    left.setTextColor(Color.WHITE);
                    left.setTextSize(10f);
//                    chart.getAxisRight().setEnabled(true); // no right axis
                    chart.getAxisLeft().setEnabled(false);


                    XAxis xAxis = chart.getXAxis();
                    xAxis.setDrawLabels(true);
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setTextSize(10f);
                    xAxis.setTextColor(Color.WHITE);
                    xAxis.setDrawAxisLine(true);
                    xAxis.setDrawGridLines(false);


                    chart.invalidate(); // refresh


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
        volleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jss);
        DialogUtil.showProgressDialog("Loading", getSupportFragmentManager());
    }

    public void getCurrenciesHistoryFromTo(final String currenciesID, String from, String to) {
        xVals = new ArrayList();
        yVals = new ArrayList<Entry>();
        yValsSell = new ArrayList<Entry>();
        dataSets = new ArrayList<ILineDataSet>();

        Log.e("mgggggggggggggggo", ((GlobalVariables) getApplication()).getCurrenciesHistoryApi() + currenciesID + "/" + from + "/" + to);
        JsonObjectRequest jss = new JsonObjectRequest(Request.Method.GET, ((GlobalVariables) getApplication()).getCurrenciesHistoryApi() + currenciesID + "/" + from + "/" + to,
                null, new Response.Listener<JSONObject>() {

            ArrayList<String> ar;

            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONObject s = response.getJSONObject("client_token");
                    PreferenceManager.getInstance().setClient_token(s.getString("client_token"));


                } catch (JSONException e) {

                    e.printStackTrace();
                }

                try {

                    JSONObject s = response.getJSONObject("response");
                    JSONArray currenciesArray = s.getJSONArray("currencies");
                    for (int i = 0; i < currenciesArray.length(); i++) {
                        JSONObject currentCurrencies = currenciesArray.getJSONObject(i);
                        String Currenciestitle = currentCurrencies.getString("name");
                        int currenciesId = currentCurrencies.getInt("id");
                        String currenciesFlag = currentCurrencies.getString("flag");
                        int currenciesSell = currentCurrencies.getInt("sell");
                        String currenciesBuy = currentCurrencies.getString("buy");
                        String currenciesStatus = currentCurrencies.getString("status");
                        String currencieslastUpdate = currentCurrencies.getString("created_at");

                        Curencies curencies = new Curencies();
                        curencies.setBuyPrice(currenciesBuy);
                        curencies.setCurrencyName(Currenciestitle);
                        curencies.setFlagName(currenciesFlag);
                        curencies.setLastUpdate(currencieslastUpdate);
                        curencies.setSellPrice(currenciesSell);
                        curencies.setStatus(currenciesStatus);
                        curencies.setCurrincyId(currenciesId);


                        currenciesList.add(curencies);

                        xVals.add(currencieslastUpdate);
                        yVals.add(new Entry(Float.valueOf(currenciesBuy), i));
                        yValsSell.add(new Entry(Float.valueOf(currenciesSell), i));


                        // create a dataset and give it a type
                        set2 = new LineDataSet(yValsSell, "Sell");
                        set2.setFillAlpha(110);
                        set2.setColor(getResources().getColor(R.color.blue));
                        set2.setCircleColor(getResources().getColor(R.color.blue));
                        set2.setLineWidth(4f);
                        set2.setCircleRadius(5f);
                        set2.setValueTextColor(Color.WHITE);
                        set2.setDrawCircleHole(false);
                        set2.setValueTextSize(9f);
                        set2.setFillColor(getResources().getColor(R.color.blue));
                        set2.setDrawFilled(true);
                        set2.setDrawCubic(true);


                        // create a dataset and give it a type
                        set1 = new LineDataSet(yVals, "Buy");
                        set1.setFillAlpha(110);
                        set1.setColor(getResources().getColor(R.color.colorAccent));
                        set1.setCircleColor(getResources().getColor(R.color.colorAccent));
                        set1.setLineWidth(4f);
                        set1.setCircleRadius(5f);
                        set1.setValueTextColor(Color.WHITE);
                        set1.setDrawCircleHole(false);
                        set1.setValueTextSize(9f);
                        set1.setFillColor(getResources().getColor(R.color.colorAccent));
                        set1.setDrawFilled(true);
                        set1.setDrawCubic(true);

                        chart.setDescription(Currenciestitle);
                        chart.setDescriptionTextSize(10f);
                        chart.setDescriptionColor(Color.WHITE);


                    }
                    set1.setAxisDependency(YAxis.AxisDependency.LEFT);
                    set2.setAxisDependency(YAxis.AxisDependency.LEFT);
                    dataSets.add(set2);
                    dataSets.add(set1); // add the datasets
                    try {
                        LineData data = new LineData(xVals, dataSets);
                        chart.setData(data);
                    } catch (Exception e) {
                        chart.clear();
                    }


                    chart.setBorderColor(Color.WHITE);
                    // get the legend (only possible after setting data)
                    Legend l = chart.getLegend();
                    chart.animateX(1000, Easing.EasingOption.EaseInCubic);

                    // modify the legend ...
                    // l.setPosition(LegendPosition.LEFT_OF_CHART);
                    l.setForm(Legend.LegendForm.CIRCLE);
                    l.setTextColor(Color.WHITE);
                    l.setTextSize(10f);


                    // data has AxisDependency.LEFT
                    YAxis left = chart.getAxisRight();
                    left.setDrawLabels(true); // no axis labels
                    left.setDrawAxisLine(true); // no axis line
                    left.setDrawGridLines(false); // no grid lines
                    left.setDrawZeroLine(true); // draw a zero line
                    left.setTextColor(Color.WHITE);
                    left.setTextSize(10f);
//                    chart.getAxisRight().setEnabled(true); // no right axis
                    chart.getAxisLeft().setEnabled(false);


                    XAxis xAxis = chart.getXAxis();
                    xAxis.setDrawLabels(true);
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setTextSize(10f);
                    xAxis.setTextColor(Color.WHITE);
                    xAxis.setDrawAxisLine(true);
                    xAxis.setDrawGridLines(false);


//                    Collections.sort(entries, new EntryXComparator());
//                    LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset
//                    dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
//
//                    LineData lineData = new LineData(dataSet);
//                    chart.setData(lineData);
//
                    chart.invalidate(); // refresh


                } catch (JSONException e) {
                    Log.e("mggggggo", response.toString());
                    e.printStackTrace();
                }


                Log.e("mggggggo", response.toString());

                DialogUtil.removeProgressDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(), "heloooo2", Toast.LENGTH_LONG).show();
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
        volleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jss);
        DialogUtil.showProgressDialog("Loading", getSupportFragmentManager());
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
//        String date = "From- "+dayOfMonth+"/"+(++monthOfYear)+"/"+year+" To "+dayOfMonthEnd+"/"+(++monthOfYearEnd)+"/"+yearEnd;
//        String from=;
//        String to=;
        try {
            Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(dayOfMonth + "/" + (++monthOfYear) + "/" + year);
            Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(dayOfMonthEnd + "/" + (++monthOfYearEnd) + "/" + yearEnd);
            if (date1.compareTo(date2) <= 0) {
//                Toast.makeText(this,year+"-"+(--monthOfYear)+"-"+dayOfMonth +"to"  +yearEnd+ "-"+(--monthOfYearEnd)+"-"+dayOfMonthEnd,Toast.LENGTH_LONG).show();
                getCurrenciesHistoryFromTo(String.valueOf(id), year + "-" + (monthOfYear) + "-" + dayOfMonth, yearEnd + "-" + (monthOfYearEnd) + "-" + dayOfMonthEnd);
                fromToEditText.setText("From " + year + "-" + (monthOfYear) + "-" + dayOfMonth + " To " + yearEnd + "-" + (monthOfYearEnd) + "-" + dayOfMonthEnd);
            } else {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpdd = com.borax12.materialdaterangepicker.date.DatePickerDialog.newInstance(
                        CurrenciesHistoryActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );

                dpdd.show(getFragmentManager(), "Datepickerdialogg");

                Toast.makeText(this, getString(R.string.from_to_edit), Toast.LENGTH_LONG).show();


            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        DatePickerDialog dpd = (DatePickerDialog) getFragmentManager().findFragmentByTag("Datepickerdialog");
        if (dpd != null) dpd.setOnDateSetListener(this);
    }
}
