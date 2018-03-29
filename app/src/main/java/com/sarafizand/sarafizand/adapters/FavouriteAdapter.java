package com.sarafizand.sarafizand.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sarafizand.sarafizand.R;
import com.sarafizand.sarafizand.activities.FavouriteDetailsActivity;
import com.sarafizand.sarafizand.models.Favourite;

import java.util.List;

/**
 * Created by Mohammed on 30/1/2018.
 */

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.MyViewHolder> {

    private Context mContext;
    private List<Favourite> favouriteList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView favouriteCardView;
        TextView benNameTextView;
        TextView benAccNumTextView;
        TextView bankNameTextView;
        TextView countryTextView;
        TextView currencyTextView;

        public MyViewHolder(View view) {
            super(view);
            favouriteCardView=itemView.findViewById(R.id.favouriteCardView);
            benNameTextView = itemView.findViewById(R.id.ben_name_textview);
            benAccNumTextView = itemView.findViewById(R.id.ben_acc_number_textView);
            bankNameTextView = itemView.findViewById(R.id.bank_textView);
            countryTextView = itemView.findViewById(R.id.country_textview);
            currencyTextView = itemView.findViewById(R.id.currency_textView);
        }
    }


    public FavouriteAdapter(Context mContext, List<Favourite> favouriteList) {
        this.mContext = mContext;
        this.favouriteList = favouriteList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favourite, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.benNameTextView.setText(mContext.getResources().getString(R.string.beneficiary_name1) + favouriteList.get(position).getBen_name());
        holder.benAccNumTextView.setText(mContext.getResources().getString(R.string.beneficiary_account_number1) + favouriteList.get(position).getBen_acc_no());
        holder.bankNameTextView.setText(mContext.getResources().getString(R.string.bank_name) + favouriteList.get(position).getBank());
        holder.countryTextView.setText(favouriteList.get(position).getCountry());
        holder.currencyTextView.setText(favouriteList.get(position).getTo_currency());
        holder.favouriteCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,favouriteList.get(position).getFavorite_id(),Toast.LENGTH_LONG).show();
                Intent i = new Intent(mContext.getApplicationContext(), FavouriteDetailsActivity.class);
                i.putExtra("fav_id",favouriteList.get(position).getFavorite_id());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                mContext.startActivity(i);

            }
        });



    }


    @Override
    public int getItemCount() {
        return favouriteList.size();
    }

}
