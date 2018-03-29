package com.sarafizand.sarafizand.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.sarafizand.sarafizand.GlobalVariables;
import com.sarafizand.sarafizand.R;
import com.sarafizand.sarafizand.models.Banks;
import com.sarafizand.sarafizand.models.CountriesForTransaction;
import com.sarafizand.sarafizand.models.Curencies;
import com.sarafizand.sarafizand.models.TransactionData;
import com.sarafizand.sarafizand.preference.PreferenceManager;
import com.sarafizand.sarafizand.utils.volleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransActionsFragment extends Fragment {
    RadioButton personalTransferRadioButton, buisinessTransferRadioButton, cashCollectionRadioButton, cashDepositRadioButton, bankTransferRadioButton;
    RadioGroup pOrB, transferMethod;
    private ArrayList<TransactionData> transactionDataList = new ArrayList<>();
    private ArrayList<Banks> allBanks = new ArrayList<>();
    private ArrayList<CountriesForTransaction> allCountries = new ArrayList<>();
    MaterialSpinner spinnerFromCurrencies, spinnerToCurrencies, spinnerFromCountry, spinnerToCountry;
    AutoCompleteTextView bankAutoCompleteTextView, swiftCodeAutoCompleteTextView;
    private List<Curencies> currenciesList = new ArrayList<>();
    Toolbar toolbar;
    ArrayAdapter<String> adapterFromCountry;
    int check = 0;
    int check1 = 0;
    int check2 = 0;
    int check3 = 0;
    int check4 = 0;
    CardView countryCardView, currencyCardView;
    ArrayAdapter<String> adapterFromCurrencies;
    ArrayAdapter<String> adapterTOCurrencies;
    Button next;
    ArrayList<String> currencieslist = new ArrayList<String>();
    ArrayList<String> currenciesCodeslist = new ArrayList<String>();
    ArrayList<String> countriesCodeslist = new ArrayList<String>();

    public static TransActionsFragment newInstance() {

        Bundle args = new Bundle();

        TransActionsFragment fragment = new TransActionsFragment();
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
        final View v = inflater.inflate(R.layout.fragment_trans_actions, container, false);
        PreferenceManager.getInstance().setWayOfTransfer(3);
        toolbar = v.findViewById(R.id.toolbar_transaction);
        toolbar.setTitle("Transactions");
        spinnerFromCountry = v.findViewById(R.id.spinner_from_countries);
        spinnerFromCurrencies = v.findViewById(R.id.spinner_from_currencies);
        spinnerToCurrencies = v.findViewById(R.id.spinner_to_currencies);
        spinnerToCountry = v.findViewById(R.id.spinner_to_countries);
        bankAutoCompleteTextView = v.findViewById(R.id.banks_autoCompleteTextView);
        swiftCodeAutoCompleteTextView = v.findViewById(R.id.swiftcode_autoCompleteTextView);
        personalTransferRadioButton = v.findViewById(R.id.personal_transfer);
        buisinessTransferRadioButton = v.findViewById(R.id.buisiness_transfer);
        cashCollectionRadioButton = v.findViewById(R.id.cash_collection);
        cashDepositRadioButton = v.findViewById(R.id.cash_deposit);
        bankTransferRadioButton = v.findViewById(R.id.bank_transfer);
        countryCardView = v.findViewById(R.id.country_card);
        currencyCardView = v.findViewById(R.id.currency_card);
        pOrB = v.findViewById(R.id.pOrB);
        transferMethod = v.findViewById(R.id.transferMethod);
        pOrB.setVisibility(View.GONE);
        transferMethod.setVisibility(View.VISIBLE);
        next = v.findViewById(R.id.next_button);

        bankAutoCompleteTextView.setVisibility(View.GONE);
        swiftCodeAutoCompleteTextView.setVisibility(View.GONE);
        GlobalVariables.hideKeyboard(getActivity());

        switch (PreferenceManager.getInstance().getTransactionCategory()) {
            case "cat1":
                pOrB.setVisibility(View.GONE);
                personalTransferRadioButton.setVisibility(View.GONE);
                buisinessTransferRadioButton.setVisibility(View.GONE);
                cashDepositRadioButton.setVisibility(View.GONE);
                getCurrencies1("4");
                final ArrayList<String> fromCurrencies = new ArrayList<>();
                fromCurrencies.add("Malaysian Ringgit");
                fromCurrencies.add("Iranian Rial");
                updateFromCurrenciesSpinner(fromCurrencies);
                updateTOCurrenciesSpinner(fromCurrencies);
                swiftCodeAutoCompleteTextView.setVisibility(View.GONE);
                spinnerFromCurrencies.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (++check3 > 1) {
                            String text = spinnerFromCurrencies.getSelectedItem().toString();
                            if (text.equals("Malaysian Ringgit")) {
                                PreferenceManager.getInstance().setCasetrans(0);
                                String[] banksMalaysia = {};

                                final ArrayList<String> ToCurrencies = new ArrayList<>();
                                final ArrayList<String> fromCountries = new ArrayList<>();
                                final ArrayList<String> ToCountries = new ArrayList<>();

                                ToCurrencies.add("Iranian Rial");
                                updateTOCurrenciesSpinner(ToCurrencies);
                                fromCountries.add("Malaysia");
                                updateFromCountrySpinner(fromCountries);
                                ToCountries.add("Islamic Republic of Iran");
                                updateToCountrySpinner(ToCountries);
                                cashCollectionRadioButton.setVisibility(View.INVISIBLE);
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                        (getContext(), android.R.layout.select_dialog_item, banksMalaysia);
                                swiftCodeAutoCompleteTextView.setVisibility(View.GONE);
                                //Getting the instance of AutoCompleteTextView
                                bankAutoCompleteTextView.setThreshold(1);//will start working from first character
                                bankAutoCompleteTextView.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
                                bankAutoCompleteTextView.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDarkTeansparent));
                                bankAutoCompleteTextView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        bankAutoCompleteTextView.showDropDown();
                                    }
                                });


                            } else {
                                PreferenceManager.getInstance().setCasetrans(1);
                                String[] banksMalaysia = {"Affin Bank", "Am Bank", "BSN Bank", "CIMB Bank", "Hong Leong Bank", "HSBC Bank", "Maybank - Malayan Banking Berhad", "Muamalat Bank", "Public Bank", "RHB Bank"};

                                final ArrayList<String> ToCurrencies = new ArrayList<>();
                                final ArrayList<String> fromCountries = new ArrayList<>();
                                final ArrayList<String> ToCountries = new ArrayList<>();
                                ToCurrencies.add("Malaysian Ringgit");
                                updateTOCurrenciesSpinner(ToCurrencies);
                                fromCountries.add("Islamic Republic of Iran");
                                updateFromCountrySpinner(fromCountries);
                                ToCountries.add("Malaysia");
                                updateToCountrySpinner(ToCountries);
                                cashCollectionRadioButton.setVisibility(View.VISIBLE);
                                swiftCodeAutoCompleteTextView.setVisibility(View.GONE);
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                        (getContext(), android.R.layout.select_dialog_item, banksMalaysia);
                                //Getting the instance of AutoCompleteTextView
                                bankAutoCompleteTextView.setThreshold(1);//will start working from first character
                                bankAutoCompleteTextView.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
                                bankAutoCompleteTextView.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDarkTeansparent));
                                bankAutoCompleteTextView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        bankAutoCompleteTextView.showDropDown();
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });


                spinnerToCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        transferMethod.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            PreferenceManager.getInstance().setFromCurrencies(spinnerFromCurrencies.getSelectedItem().toString());
                        } catch (Exception e) {
                            spinnerFromCurrencies.setError("Please choose Currency");
                            return;
                        }
                        try {
                            PreferenceManager.getInstance().setToCurrency(spinnerToCurrencies.getSelectedItem().toString());

                        } catch (Exception e) {
                            spinnerToCurrencies.setError("Please choose Currency");
                            return;
                        }
                        try {
                            PreferenceManager.getInstance().setFromCountry(spinnerFromCountry.getSelectedItem().toString());

                        } catch (Exception e) {
                            spinnerFromCountry.setError("Please choose Country");
                            return;
                        }
                        try {
                            PreferenceManager.getInstance().setToCountry(spinnerToCountry.getSelectedItem().toString());

                        } catch (Exception e) {
                            spinnerToCountry.setError("Please choose Country");
                            return;
                        }


                        if (PreferenceManager.getInstance().getWayOfTransfer() == 3) {
                            Toast.makeText(getContext(), "Please choose the way of transfer", Toast.LENGTH_LONG).show();
                            return;

                        } else {
                            switch (PreferenceManager.getInstance().getWayOfTransfer()) {
                                case 0:
                                    if (bankAutoCompleteTextView.getText().toString().equals("")) {
                                        Toast.makeText(getContext(), "Please Fill the bank", Toast.LENGTH_LONG).show();
                                        return;
                                    } else {
                                        PreferenceManager.getInstance().setBank(bankAutoCompleteTextView.getText().toString());
                                    }
                                case 1:
                                    if (bankAutoCompleteTextView.getText().toString().equals("")) {
                                        Toast.makeText(getContext(), "Please Fill the bank", Toast.LENGTH_LONG).show();
                                        return;
                                    } else {
                                        PreferenceManager.getInstance().setBank(bankAutoCompleteTextView.getText().toString());
                                    }
                                    break;


                            }

                        }

                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.frame_layout2, TransactionSecondFragment.newInstance());
                        ft.addToBackStack(null);
                        ft.commit();

                    }
                });


                break;


            case "cat2":
                cashCollectionRadioButton.setVisibility(View.GONE);
                final ArrayList<String> fromCurrenciesCase2 = new ArrayList<>();
                final ArrayList<String> fromCountriesCase2 = new ArrayList<>();
                fromCurrenciesCase2.add("Iranian Rial");
                fromCountriesCase2.add("Islamic Republic of Iran");
                updateFromCurrenciesSpinner(fromCurrenciesCase2);
                updateFromCountrySpinner(fromCountriesCase2);
                getData();

                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            if (spinnerToCountry.getSelectedItem().toString().equals("United states of America")) {


                            }
                        } catch (Exception e) {
                            if (PreferenceManager.getInstance().getPorb() == 2) {

                                Toast.makeText(getContext(), "Please select Personal or Business Transfer ", Toast.LENGTH_LONG).show();
                                return;
                            }
                        }

                        try {
                            PreferenceManager.getInstance().setFromCurrencies("IRR");
                        } catch (Exception e) {
                            spinnerFromCurrencies.setError("Please choose Currency");
                            return;
                        }
                        try {
                            PreferenceManager.getInstance().setToCurrency(currenciesCodeslist.get(spinnerToCurrencies.getSelectedItemPosition() - 1));

                        } catch (Exception e) {
                            spinnerToCurrencies.setError("Please choose Currency");
                            return;
                        }
                        try {
                            PreferenceManager.getInstance().setFromCountry(spinnerFromCountry.getSelectedItem().toString());

                        } catch (Exception e) {
                            spinnerFromCountry.setError("Please choose Country");
                            return;
                        }
                        try {
                            PreferenceManager.getInstance().setToCountry(countriesCodeslist.get(spinnerToCountry.getSelectedItemPosition() - 1));

                        } catch (Exception e) {
                            spinnerToCountry.setError("Please choose Country");
                            return;
                        }


                        if (PreferenceManager.getInstance().getWayOfTransfer() == 3) {
                            Toast.makeText(getContext(), "Please choose the way of transfer", Toast.LENGTH_LONG).show();
                            return;

                        } else {
                            switch (PreferenceManager.getInstance().getWayOfTransfer()) {
                                case 0:
                                    if (bankAutoCompleteTextView.getText().toString().equals("")) {
                                        Toast.makeText(getContext(), "Please Fill the bank", Toast.LENGTH_LONG).show();
                                        return;
                                    } else {
                                        PreferenceManager.getInstance().setBank(bankAutoCompleteTextView.getText().toString());
                                    }
                                    ////
                                    try {
                                        if (spinnerToCountry.getSelectedItem().toString().equals("Australia") || spinnerToCountry.getSelectedItem().toString().equals("United Kingdom")) {

                                        } else {
                                            if (swiftCodeAutoCompleteTextView.getText().toString().equals("")) {
                                                Toast.makeText(getContext(), "Please Fill the swift Code", Toast.LENGTH_LONG).show();
                                                return;
                                            } else {
                                                PreferenceManager.getInstance().setSwiftcode(swiftCodeAutoCompleteTextView.getText().toString());
                                            }
                                            break;
                                        }
                                    } catch (Exception e) {

                                    }

                                case 1:
                                    if (bankAutoCompleteTextView.getText().toString().equals("")) {
                                        Toast.makeText(getContext(), "Please Fill the bank", Toast.LENGTH_LONG).show();
                                        return;
                                    } else {
                                        PreferenceManager.getInstance().setBank(bankAutoCompleteTextView.getText().toString());
                                    }
                                    break;


                            }

                        }

                        Log.e("way2", String.valueOf(PreferenceManager.getInstance().getWayOfTransfer()));
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.frame_layout2, TransactionSecondFragment.newInstance());
                        ft.addToBackStack(null);
                        ft.commit();

                    }
                });

                break;

            case "cat3":
                final ArrayList<String> fromCurrenciesCase3 = new ArrayList<>();
                fromCurrenciesCase3.add("Iranian Rial");
                updateFromCountrySpinner(fromCurrenciesCase3);
                getCurrencies("6");
                countryCardView.setVisibility(View.GONE);
                cashDepositRadioButton.setVisibility(View.GONE);
                bankTransferRadioButton.setVisibility(View.GONE);
                bankAutoCompleteTextView.setVisibility(View.GONE);
                swiftCodeAutoCompleteTextView.setVisibility(View.GONE);


                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        try {
                            PreferenceManager.getInstance().setFromCurrencies(spinnerFromCountry.getSelectedItem().toString());
                        } catch (Exception e) {
                            spinnerFromCountry.setError("Please choose Currency");
                            return;
                        }
                        try {
                            PreferenceManager.getInstance().setToCurrency(spinnerToCountry.getSelectedItem().toString());

                        } catch (Exception e) {
                            spinnerToCountry.setError("Please choose Currency");
                            return;
                        }

                        if (PreferenceManager.getInstance().getWayOfTransfer() == 3) {
                            Toast.makeText(getContext(), "Please choose the way of transfer", Toast.LENGTH_LONG).show();
                            return;
                        }

                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.frame_layout2, TransactionSecondFragment.newInstance());
                        ft.addToBackStack(null);
                        ft.commit();

                    }
                });
                break;

            case "cat4":
                countryCardView.setVisibility(View.GONE);
                cashDepositRadioButton.setVisibility(View.GONE);
                bankTransferRadioButton.setVisibility(View.GONE);
                bankAutoCompleteTextView.setVisibility(View.GONE);
                swiftCodeAutoCompleteTextView.setVisibility(View.GONE);
                final ArrayList<String> fromCurrenciesCase4 = new ArrayList<>();
                fromCurrenciesCase4.add("Iranian Rial");
                updateFromCountrySpinner(fromCurrenciesCase4);
                getCurrencies("7");
                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            PreferenceManager.getInstance().setFromCurrencies(spinnerFromCountry.getSelectedItem().toString());
                        } catch (Exception e) {
                            spinnerFromCountry.setError("Please choose Currency");
                            return;
                        }
                        try {
                            PreferenceManager.getInstance().setToCurrency(spinnerToCountry.getSelectedItem().toString());

                        } catch (Exception e) {
                            spinnerToCountry.setError("Please choose Currency");
                            return;
                        }

                        if (PreferenceManager.getInstance().getWayOfTransfer() == 3) {
                            Toast.makeText(getContext(), "Please choose the way of transfer", Toast.LENGTH_LONG).show();
                            return;
                        }


                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.frame_layout2, TransactionSecondFragment.newInstance());
                        ft.addToBackStack(null);
                        ft.commit();

                    }
                });

                break;


        }

        cashCollectionRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    PreferenceManager.getInstance().setWayOfTransfer(2);
                    bankAutoCompleteTextView.setVisibility(View.GONE);
                    swiftCodeAutoCompleteTextView.setVisibility(View.GONE);

                }
            }
        });
        cashDepositRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    PreferenceManager.getInstance().setWayOfTransfer(1);
                    bankAutoCompleteTextView.setVisibility(View.VISIBLE);
                    swiftCodeAutoCompleteTextView.setVisibility(View.GONE);
                    try {
                        if (spinnerToCountry.getSelectedItem().toString().equals("Canada")) {
//
                            bankAutoCompleteTextView.setInputType(0);
                        }

                    } catch (Exception e) {

                    }
                    if (PreferenceManager.getInstance().getTransactionCategory().equals("cat1")) {
                        swiftCodeAutoCompleteTextView.setVisibility(View.GONE);
                    }
                }
            }
        });

        bankTransferRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {

                    PreferenceManager.getInstance().setWayOfTransfer(0);

                    bankAutoCompleteTextView.setVisibility(View.VISIBLE);
                    try {
                        if (spinnerToCountry.getSelectedItem().toString().equals("Australia") || spinnerToCountry.getSelectedItem().toString().equals("United Kingdom")) {
                            swiftCodeAutoCompleteTextView.setVisibility(View.GONE);
                        } else {
                            swiftCodeAutoCompleteTextView.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {

                    }
                    if (PreferenceManager.getInstance().getTransactionCategory().equals("cat1")) {
                        swiftCodeAutoCompleteTextView.setVisibility(View.GONE);
                    }
                }
            }
        });

        return v;
    }

    void updateFromCurrenciesSpinner(ArrayList<String> arr) {
        adapterFromCurrencies = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arr);
        adapterFromCurrencies.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFromCurrencies.setAdapter(adapterFromCurrencies);
    }

    void updateTOCurrenciesSpinner(ArrayList<String> arr) {
        adapterTOCurrencies = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arr);
        adapterTOCurrencies.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerToCurrencies.setAdapter(adapterTOCurrencies);
    }

    void updateFromCountrySpinner(ArrayList<String> arr) {
        adapterFromCountry = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arr);
        adapterFromCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFromCountry.setAdapter(adapterFromCountry);
    }

    void updateToCountrySpinner(ArrayList<String> arr) {
        ArrayAdapter<String> adapterToCountry = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arr);
        adapterToCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerToCountry.setAdapter(adapterToCountry);

    }

    void updateBankSpinner(ArrayList<String> arr) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.select_dialog_item, arr);
        //Getting the instance of AutoCompleteTextView
        bankAutoCompleteTextView.setThreshold(1);//will start working from first character
        bankAutoCompleteTextView.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        bankAutoCompleteTextView.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDarkTeansparent));
        bankAutoCompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bankAutoCompleteTextView.showDropDown();
            }
        });
    }

    void updateSwiftSpinner(ArrayList<String> arr) {
        ArrayAdapter<String> adapterSWIFT = new ArrayAdapter<String>
                (getContext(), android.R.layout.select_dialog_item, arr);
        //Getting the instance of AutoCompleteTextView
        swiftCodeAutoCompleteTextView.setThreshold(1);//will start working from first character
        swiftCodeAutoCompleteTextView.setAdapter(adapterSWIFT);//setting the adapter data into the AutoCompleteTextView
        swiftCodeAutoCompleteTextView.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDarkTeansparent));
        swiftCodeAutoCompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swiftCodeAutoCompleteTextView.showDropDown();
            }
        });


    }


    private void getData() {

        StringRequest jss = new StringRequest(Request.Method.GET
                , "http://staging-admin.zandexchange.com/api/v1/transactions/countries/data",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Banks banks = new Banks();

                        CountriesForTransaction countriesForTransaction = new CountriesForTransaction();
                        try {
                            JSONArray jsonArrayData = new JSONArray(response);

                            for (int i = 0; i < jsonArrayData.length(); i++) {
                                JSONObject currencyData = jsonArrayData.getJSONObject(i);
                                String currencyCode = currencyData.getString("code");
                                String currencyName = currencyData.getString("name");
                                JSONArray countriesJsonArray = currencyData.getJSONArray("countries");
                                for (int j = 0; j < countriesJsonArray.length(); j++) {
                                    JSONObject countryData = countriesJsonArray.getJSONObject(j);
                                    String countryCode = countryData.getString("code");
                                    String countryName = countryData.getString("name");
                                    JSONArray banksJsonArray = countryData.getJSONArray("banks");
                                    for (int v = 0; v < banksJsonArray.length(); v++) {
                                        JSONObject banksData = banksJsonArray.getJSONObject(v);
                                        String swiftCode = banksData.getString("swift");
                                        String bankName = banksData.getString("bank");

                                        banks = new Banks();
                                        banks.setSwiftCode(swiftCode);
                                        banks.setBankName(bankName);
                                        allBanks.add(banks);


                                    }
                                    countriesForTransaction = new CountriesForTransaction();
                                    countriesForTransaction.setCountryCode(countryCode);
                                    countriesForTransaction.setCountryName(countryName);
                                    countriesForTransaction.setBanks(allBanks);
                                    allBanks = new ArrayList<>();
                                    allCountries.add(countriesForTransaction);

                                }
                                TransactionData transactionData = new TransactionData();
                                transactionData.setCurrencyCode(currencyCode);
                                transactionData.setCurrencyName(currencyName);
                                transactionData.setCountriesForTransactions(allCountries);
                                allCountries = new ArrayList<>();
                                transactionDataList.add(transactionData);
                            }

                            currencieslist = new ArrayList<String>();
                            currenciesCodeslist = new ArrayList<String>();
                            final ArrayList<ArrayList<Banks>> banksObjects = new ArrayList<>();

                            final ArrayList<ArrayList<CountriesForTransaction>> countriesObjects = new ArrayList<ArrayList<CountriesForTransaction>>();
                            for (int cc = 0; cc < transactionDataList.size(); cc++) {
                                currencieslist.add(transactionDataList.get(cc).getCurrencyName());
                                currenciesCodeslist.add(transactionDataList.get(cc).getCurrencyCode());
                                countriesObjects.add(transactionDataList.get(cc).getCountriesForTransactions());


                            }
                            final HashMap<String, ArrayList<Banks>> test = new HashMap<>();
                            for (int j = 0; j < transactionDataList.size(); j++) {
                                for (int i = 0; i < transactionDataList.get(j).getCountriesForTransactions().size(); i++) {
                                    test.put(transactionDataList.get(j).getCountriesForTransactions().get(i).getCountryName(), transactionDataList.get(j).getCountriesForTransactions().get(i).getBanks());

                                }
                            }
                            ArrayAdapter<String> adapterToCurrencies = null;
                            try {
                               adapterToCurrencies = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, currencieslist);

                            } catch (Exception e) {
                                
                            }

                            adapterToCurrencies.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerToCurrencies.setAdapter(adapterToCurrencies);
                            spinnerToCurrencies.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, final int position, long l) {
                                    if (++check1 > 1) {

                                        try {
                                            if (spinnerToCurrencies.getSelectedItem().toString().equals("Australian Dollar") || spinnerToCurrencies.getSelectedItem().toString().equals("British Pound")) {
                                                swiftCodeAutoCompleteTextView.setVisibility(View.GONE);
                                            } else {
                                                if (bankTransferRadioButton.isChecked()) {
                                                    swiftCodeAutoCompleteTextView.setVisibility(View.VISIBLE);
                                                }
                                            }
                                        } catch (Exception e) {

                                        }
                                        try {
                                            if (currenciesCodeslist.get(position).equals("USD")) {
                                                final ArrayList<String> countrieslist1 = new ArrayList<String>();
                                                updateToCountrySpinner(countrieslist1);
                                                pOrB.setVisibility(View.VISIBLE);
                                                personalTransferRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                    @Override
                                                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                                        if (b) {

                                                            PreferenceManager.getInstance().setPorb(0);
                                                            final ArrayList<String> countrieslist = new ArrayList<String>();
                                                            countrieslist.add("United states of America");
                                                            countriesCodeslist.add("USA");
                                                            updateToCountrySpinner(countrieslist);
                                                        }

                                                    }
                                                });
                                                buisinessTransferRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                    @Override
                                                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                                        if (b) {
                                                            PreferenceManager.getInstance().setPorb(1);
                                                            final ArrayList<String> countrieslist3 = new ArrayList<String>();
                                                            for (int i = 0; i < countriesObjects.get(position).size(); i++) {
                                                                countrieslist3.add(countriesObjects.get(position).get(i).getCountryName());
                                                                countriesCodeslist.add(countriesObjects.get(position).get(i).getCountryCode());
                                                            }
                                                            updateToCountrySpinner(countrieslist3);

                                                        }
                                                    }
                                                });


                                            } else {
                                                pOrB.setVisibility(View.INVISIBLE);

                                                final ArrayList<String> countrieslist2 = new ArrayList<String>();
                                                for (int i = 0; i < countriesObjects.get(position).size(); i++) {
                                                    countrieslist2.add(countriesObjects.get(position).get(i).getCountryName());
                                                    countriesCodeslist.add(countriesObjects.get(position).get(i).getCountryCode());
                                                }

                                                updateToCountrySpinner(countrieslist2);
                                            }
                                        } catch (Exception e) {

                                        }

                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });

                            spinnerToCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                                    if (++check2 > 1) {
                                        final ArrayList<String> bankslist = new ArrayList<String>();
                                        final ArrayList<String> swiftlist = new ArrayList<String>();

                                        try {
                                            String text = spinnerToCountry.getSelectedItem().toString();

                                            if (text.equals("Australia") || text.equals("Canada")
                                                    || text.equals("United Kingdom")) {
                                                cashDepositRadioButton.setVisibility(View.VISIBLE);
                                            } else {
                                                cashDepositRadioButton.setVisibility(View.GONE);

                                            }
                                            if (text.equals("United states of America")) {
                                                bankslist.add("Bank of America ,N.A.");
                                                bankslist.add("Citibank N.A.");
                                                bankslist.add("JP morgan Chase Bank");
                                                bankslist.add("Suntrust Bank");
                                                bankslist.add("TD Bank, N.A.");
                                                bankslist.add("Wells fargo Bank, N.A.");
                                                updateBankSpinner(bankslist);
                                                swiftlist.add("B0fAUS3N");
                                                swiftlist.add("CITIU33");
                                                swiftlist.add("CHASUS33");
                                                swiftlist.add("SNTRUS3N");
                                                swiftlist.add("NRTHUS33");
                                                swiftlist.add("WfBIUS6S");
                                                updateSwiftSpinner(swiftlist);
                                            } else {
                                                for (int j = 0; j < test.get(text).size(); j++) {
                                                    swiftlist.add(test.get(text).get(j).getSwiftCode());
                                                    bankslist.add(test.get(text).get(j).getBankName());
                                                }
                                                updateSwiftSpinner(swiftlist);
                                                updateBankSpinner(bankslist);
                                            }
                                        } catch (Exception e) {

                                        }


                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });

                        } catch (JSONException e1) {
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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
    }

    public void getCurrencies(final String categoryID) {
        final ArrayList<String> tocurrencies = new ArrayList<>();
        JsonObjectRequest jss = new JsonObjectRequest(Request.Method.GET, ((GlobalVariables) getActivity().getApplication()).getCurrenciesApi() + categoryID,
                null, new Response.Listener<JSONObject>() {
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
                        tocurrencies.add(Currenciestitle);

                    }
                    updateToCountrySpinner(tocurrencies);


                    spinnerToCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                            if (++check2 > 1) {
                                PreferenceManager.getInstance().setRateCurrencyid(currenciesList.get(position).getCurrincyId());
                                PreferenceManager.getInstance().setSell(currenciesList.get(position).getSellPrice());
                                PreferenceManager.getInstance().setToCurrency(currenciesList.get(position).getCurrencyName());
                                transferMethod.setVisibility(View.VISIBLE);

                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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

    }

    public void getCurrencies1(final String categoryID) {
        final ArrayList<String> tocurrencies = new ArrayList<>();
        JsonObjectRequest jss = new JsonObjectRequest(Request.Method.GET, ((GlobalVariables) getActivity().getApplication()).getCurrenciesApi() + categoryID,
                null, new Response.Listener<JSONObject>() {
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
                        int currenciesBuy = currentCurrencies.getInt("buy");
                        String currenciesStatus = currentCurrencies.getString("status");
                        String currencieslastUpdate = currentCurrencies.getString("created_at");


                        if (Currenciestitle.equals("حواله اعضا")) {
                            PreferenceManager.getInstance().setRateCurrencyid(currenciesId);
                            PreferenceManager.getInstance().setBuy(currenciesBuy);
                            PreferenceManager.getInstance().setSell(currenciesSell);
                        }
                        tocurrencies.add(Currenciestitle);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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

    }


}
