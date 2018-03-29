package com.sarafizand.sarafizand.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sarafizand.sarafizand.GlobalVariables;
import com.sarafizand.sarafizand.R;
import com.sarafizand.sarafizand.preference.PreferenceManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseTransactionCategoryFragment extends Fragment {
    CardView myrExchangeCard,TTCard,shirazBranch,tehranBranch;


    public static ChooseTransactionCategoryFragment newInstance() {

        Bundle args = new Bundle();

        ChooseTransactionCategoryFragment fragment = new ChooseTransactionCategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public ChooseTransactionCategoryFragment() {
        // Required empty public constructor
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
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_choose_transaction_category, container, false);

        myrExchangeCard=v.findViewById(R.id.myr_exchange);
        TTCard=v.findViewById(R.id.TelegraphicTransfer);
        shirazBranch=v.findViewById(R.id.Shirazbranch);
        tehranBranch=v.findViewById(R.id.Tehranbranch);

        GlobalVariables.hideKeyboard(getActivity());
        myrExchangeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferenceManager.getInstance().setTransactionCategory("cat1");
                FragmentTransaction ft1 =getActivity().getSupportFragmentManager().beginTransaction();
                ft1.replace(R.id.frame_layout2, SenderOrRecieverFragment.newInstance());
                ft1.addToBackStack(null);
                ft1.commit();


            }
        });

        TTCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferenceManager.getInstance().setTransactionCategory("cat2");
                FragmentTransaction ft1 =getActivity().getSupportFragmentManager().beginTransaction();
                ft1.replace(R.id.frame_layout2, SenderOrRecieverFragment.newInstance());
                ft1.addToBackStack(null);
                ft1.commit();

            }
        });
        shirazBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferenceManager.getInstance().setTransactionCategory("cat3");
                FragmentTransaction ft1 =getActivity().getSupportFragmentManager().beginTransaction();
                ft1.replace(R.id.frame_layout2, SenderOrRecieverFragment.newInstance());
                ft1.addToBackStack(null);
                ft1.commit();

            }
        });
        tehranBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferenceManager.getInstance().setTransactionCategory("cat4");
                FragmentTransaction ft1 =getActivity().getSupportFragmentManager().beginTransaction();
                ft1.replace(R.id.frame_layout2, SenderOrRecieverFragment.newInstance());
                ft1.addToBackStack(null);
                ft1.commit();

            }
        });

        return v;
    }


}
