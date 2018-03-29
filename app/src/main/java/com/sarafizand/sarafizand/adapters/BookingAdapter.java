package com.sarafizand.sarafizand.adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sarafizand.sarafizand.R;
import com.sarafizand.sarafizand.models.Booking;
import com.sarafizand.sarafizand.utils.DialogUtil;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Mohammed on 11/2/2018.
 */

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.MyViewHolder> {

    private Context mContext;
    private List<Booking> curenciesList;
    private FragmentManager fragmentManager;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView sellPrice;
        TextView buyPrice;
        TextView currencyName;
        TextView LastUpdate;
        ImageView currencyIcon;
        TextView statusTextView;

        public MyViewHolder(View view) {
            super(view);
            cv = itemView.findViewById(R.id.currencyCustomCardView);
            sellPrice = itemView.findViewById(R.id.sellPrice);
            buyPrice = itemView.findViewById(R.id.buyPrice);
            currencyName = itemView.findViewById(R.id.currencyName);
            LastUpdate = itemView.findViewById(R.id.lastUpdate);
            currencyIcon = itemView.findViewById(R.id.flagIcon);
            statusTextView = itemView.findViewById(R.id.statusTextView);
        }
    }


    public BookingAdapter(Context mContext, List<Booking> curenciesList,FragmentManager fragmentManager) {
        this.mContext = mContext;
        this.curenciesList = curenciesList;
        this.fragmentManager=fragmentManager;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_booking, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.sellPrice.setText(mContext.getResources().getString(R.string.sell_price) + curenciesList.get(position).getCurrency().getSellPrice());
        holder.buyPrice.setText(mContext.getResources().getString(R.string.buy_price) + curenciesList.get(position).getCurrency().getBuyPrice());
        holder.currencyName.setText(curenciesList.get(position).getCurrency().getCurrencyName());
        holder.LastUpdate.setText(mContext.getResources().getString(R.string.last_update) + curenciesList.get(position).getCurrency().getLastUpdate());
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtil.showAskForCancelDialog(fragmentManager, String.valueOf(curenciesList.get(position).getId()));
            }
        });



        switch (curenciesList.get(position).getStatus()){
            case 0:
                holder.statusTextView.setText("active");
                break;
            case 1:
                holder.statusTextView.setText("Canceled by user");
                break;
            case 2:
                holder.statusTextView.setText("Canceled by system");
                break;

        }
//        holder.statusTextView.setText(curenciesList.get(position).getStatus()+"");
        String url = curenciesList.get(position).getCurrency().getFlagName();
        Picasso.with(mContext)
                .load(url)
                .placeholder(R.drawable.loadin_image)
                .error(R.drawable.error_image)
                .resize(400, 400)
                .centerCrop()
                .into(holder.currencyIcon, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                    }
                });
    }


    @Override
    public int getItemCount() {
        return curenciesList.size();
    }
}