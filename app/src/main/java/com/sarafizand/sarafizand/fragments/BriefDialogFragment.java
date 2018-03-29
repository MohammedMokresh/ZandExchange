package com.sarafizand.sarafizand.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sarafizand.sarafizand.GlobalVariables;
import com.sarafizand.sarafizand.R;
import com.sarafizand.sarafizand.activities.MainActivity;
import com.sarafizand.sarafizand.preference.PreferenceManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class BriefDialogFragment extends DialogFragment implements Button.OnClickListener {

    TextView amountTextView,feeTextView, fromCurrenciesTextView, toCurrenciesTextView, sellPriceTextView, recieveAmountTextView;
    Button doneButton;

    public static BriefDialogFragment fragment;


    public BriefDialogFragment() {
        // Required empty public constructor
    }

    public static BriefDialogFragment newInstance() {

        Bundle args = new Bundle();

        BriefDialogFragment fragment = new BriefDialogFragment();
        fragment.setArguments(args);
        return fragment;


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_brief_dialog, container, false);
        amountTextView = rootView.findViewById(R.id.amount_textview);
        fromCurrenciesTextView = rootView.findViewById(R.id.from_currencies_textView);
        toCurrenciesTextView = rootView.findViewById(R.id.to_currencies_textView);
        sellPriceTextView = rootView.findViewById(R.id.sell_price_textView);
        recieveAmountTextView = rootView.findViewById(R.id.recived_amount_textView);
        feeTextView=rootView.findViewById(R.id.fee_textView);
        doneButton = rootView.findViewById(R.id.done_button);
        amountTextView.setText(getResources().getString(R.string.amount) + PreferenceManager.getInstance().getAmount());
        fromCurrenciesTextView.setText(getResources().getString(R.string.from_currency) + PreferenceManager.getInstance().getFromCurrencies());
        toCurrenciesTextView.setText(getResources().getString(R.string.to_currency) + PreferenceManager.getInstance().getToCurrency());
        GlobalVariables.hideKeyboard(getActivity());
        if (PreferenceManager.getInstance().getFromCurrencies().equals("MYR")) {
            sellPriceTextView.setText(getResources().getString(R.string.buy_price) + PreferenceManager.getInstance().getBuy());
            if (PreferenceManager.getInstance().getAmount() <= 1000) {
                recieveAmountTextView.setText(getResources().getString(R.string.recieve_amount) + (PreferenceManager.getInstance().getAmount() / PreferenceManager.getInstance().getSell() - 15));
                feeTextView.setText(getResources().getString(R.string.fee)+ "15 IRR");
            } else if (PreferenceManager.getInstance().getAmount() > 1000) {
                recieveAmountTextView.setText(getResources().getString(R.string.recieve_amount) + (PreferenceManager.getInstance().getAmount() / PreferenceManager.getInstance().getSell()));
                feeTextView.setText(getResources().getString(R.string.fee)+ "0 IRR");
            }
        } else {
            sellPriceTextView.setText(getResources().getString(R.string.sell_price) + PreferenceManager.getInstance().getSell());
            switch (PreferenceManager.getInstance().getToCurrency()) {
                case "GBP":
                    if (PreferenceManager.getInstance().getAmount() < 3000) {
                        recieveAmountTextView.setText(getResources().getString(R.string.recieve_amount) + (PreferenceManager.getInstance().getAmount() / PreferenceManager.getInstance().getSell() - 20));
                        feeTextView.setText(getResources().getString(R.string.fee)+ "20 GBP");
                    }else {
                        recieveAmountTextView.setText(getResources().getString(R.string.recieve_amount) + (PreferenceManager.getInstance().getAmount() / PreferenceManager.getInstance().getSell()));
                        feeTextView.setText(getResources().getString(R.string.fee)+ "0 GBP");

                    }
                    break;
                case "USD":
                    if (PreferenceManager.getInstance().getAmount() <= 3000) {
                        recieveAmountTextView.setText(getResources().getString(R.string.recieve_amount) + (PreferenceManager.getInstance().getAmount() / PreferenceManager.getInstance().getSell() - 50));
                        feeTextView.setText(getResources().getString(R.string.fee)+ "50 USD");

                    } else if (PreferenceManager.getInstance().getAmount() > 3000) {
                        recieveAmountTextView.setText(getResources().getString(R.string.recieve_amount) + (PreferenceManager.getInstance().getAmount() / PreferenceManager.getInstance().getSell() - 40));
                        feeTextView.setText(getResources().getString(R.string.fee)+ "40 USD");

                    }
                    break;
                case "EUR":
                    if (PreferenceManager.getInstance().getAmount() <= 3000) {
                        recieveAmountTextView.setText(getResources().getString(R.string.recieve_amount) + (PreferenceManager.getInstance().getAmount() / PreferenceManager.getInstance().getSell() - 50));
                        feeTextView.setText(getResources().getString(R.string.fee)+ "50 EUR");

                    } else if (PreferenceManager.getInstance().getAmount() > 3000) {
                        recieveAmountTextView.setText(getResources().getString(R.string.recieve_amount) + (PreferenceManager.getInstance().getAmount() / PreferenceManager.getInstance().getSell() - 40));
                        feeTextView.setText(getResources().getString(R.string.fee)+ "40 EUR");

                    }
                    break;

                case "MYR":
                    if (PreferenceManager.getInstance().getAmount()<=1000){
                        recieveAmountTextView.setText(getResources().getString(R.string.recieve_amount) + (PreferenceManager.getInstance().getAmount() / PreferenceManager.getInstance().getSell() - 15));
                        feeTextView.setText(getResources().getString(R.string.fee)+ "15 MYR");

                    }else {
                        recieveAmountTextView.setText(getResources().getString(R.string.recieve_amount) + (PreferenceManager.getInstance().getAmount() / PreferenceManager.getInstance().getSell() ));
                        feeTextView.setText(getResources().getString(R.string.fee)+ "0 MYR");

                    }
                    break;

                case "AUD":
                    recieveAmountTextView.setText(getResources().getString(R.string.recieve_amount) + (PreferenceManager.getInstance().getAmount() / PreferenceManager.getInstance().getSell() - 30));
                    feeTextView.setText(getResources().getString(R.string.fee)+ "30 AUD");

                    break;
                case "CAD":
                    recieveAmountTextView.setText(getResources().getString(R.string.recieve_amount) + (PreferenceManager.getInstance().getAmount() / PreferenceManager.getInstance().getSell() - 30));
                    feeTextView.setText(getResources().getString(R.string.fee)+ "30 CAD");

                    break;
            }
        }


        setCancelable(false);

        doneButton.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        switch (i) {

            case R.id.done_button:
                dismiss();
                MainActivity.bottomNavigation.setSelectedIndex(0,true);
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


}
