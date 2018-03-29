package com.sarafizand.sarafizand.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sarafizand.sarafizand.GlobalVariables;
import com.sarafizand.sarafizand.R;
import com.sarafizand.sarafizand.preference.PreferenceManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class SenderOrRecieverFragment extends Fragment {
    CardView senderCardView,RecieverCardView;

    public static SenderOrRecieverFragment newInstance() {

        Bundle args = new Bundle();

        SenderOrRecieverFragment fragment = new SenderOrRecieverFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public SenderOrRecieverFragment() {
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

        View v=inflater.inflate(R.layout.fragment_sender_or_reciever, container, false);
        senderCardView=v.findViewById(R.id.sender_card);
        RecieverCardView=v.findViewById(R.id.reciever_card);
        senderCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferenceManager.getInstance().setIsSender(true);
                FragmentTransaction ft1 =getActivity().getSupportFragmentManager().beginTransaction();
                ft1.replace(R.id.frame_layout2, TransActionsFragment.newInstance());
                ft1.addToBackStack(null);
                ft1.commit();



            }
        });
        RecieverCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferenceManager.getInstance().setIsSender(false);
                FragmentTransaction ft1 =getActivity().getSupportFragmentManager().beginTransaction();
                ft1.replace(R.id.frame_layout2, TransActionsFragment.newInstance());
                ft1.addToBackStack(null);
                ft1.commit();


            }
        });
        GlobalVariables.hideKeyboard(getActivity());

        return v;
    }

}
