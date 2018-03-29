package com.sarafizand.sarafizand.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sarafizand.sarafizand.activities.CurrenciesHistoryActivity;
import com.sarafizand.sarafizand.R;
import com.sarafizand.sarafizand.models.Curencies;
import com.sarafizand.sarafizand.preference.PreferenceManager;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


import java.util.List;



public class CurrenciesAdapter  extends RecyclerView.Adapter<CurrenciesAdapter.MyViewHolder> {

    private Context mContext;
    private List<Curencies> curenciesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView sellPrice;
        TextView buyPrice;
        TextView currencyName;
        TextView LastUpdate;
        ImageView currencyIcon;
        RelativeLayout currencyNotify;

        public MyViewHolder(View view) {
            super(view);
            cv = (CardView)itemView.findViewById(R.id.currencyCustomCardView);
            sellPrice = (TextView)itemView.findViewById(R.id.sellPrice);
            buyPrice = (TextView)itemView.findViewById(R.id.buyPrice);
            currencyName = (TextView)itemView.findViewById(R.id.currencyName);
            LastUpdate = (TextView) itemView.findViewById(R.id.lastUpdate);
            currencyIcon = (ImageView)itemView.findViewById(R.id.flagIcon);
            currencyNotify = (RelativeLayout) itemView.findViewById(R.id.currencyNotify);
        }
    }


    public CurrenciesAdapter(Context mContext, List<Curencies>  curenciesList) {
        this.mContext = mContext;
        this.curenciesList = curenciesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.currencie_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.sellPrice.setText(mContext.getResources().getString(R.string.sell_price)+curenciesList.get(position).getSellPrice());
        holder.buyPrice.setText(mContext.getResources().getString(R.string.buy_price)+ curenciesList.get(position).getBuyPrice());
        holder.currencyName.setText(curenciesList.get(position).getCurrencyName());
        holder.LastUpdate.setText(mContext.getResources().getString(R.string.last_update)+curenciesList.get(position).getLastUpdate());

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(mContext, CurrenciesHistoryActivity.class);
                i2.putExtra("currencyID",curenciesList.get(position).getCurrincyId());

                if (curenciesList.get(position).getCurrencyName().equals("حواله اعضا")){
                    Log.e("aa","ss");
                    PreferenceManager.getInstance().setIsMember(true);
                }
                else PreferenceManager.getInstance().setIsMember(false);
                mContext.startActivity(i2);

            }
        });

        switch (Integer.parseInt(curenciesList.get(position).getStatus())){
            case 0:
                holder.currencyNotify.setBackground(mContext.getResources().getDrawable(R.drawable.layout_bg_green));
                break;
            case 1:
                holder.currencyNotify.setBackground(mContext.getResources().getDrawable(R.drawable.layout_bg));
                break;
            case 2:
                holder.currencyNotify.setBackground(mContext.getResources().getDrawable(R.drawable.layout_bg_blue));
                break;
            default:
                holder.currencyNotify.setBackground(mContext.getResources().getDrawable(R.drawable.layout_bg_blue));
                break;
        }

        String url = curenciesList.get(position).getFlagName();
        Picasso.with(mContext)
                .load(url)
                .placeholder(R.drawable.loadin_image)
                .error(R.drawable.error_image)
                .resize(400,400)
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