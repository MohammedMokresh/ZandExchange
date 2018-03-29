package com.sarafizand.sarafizand.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sarafizand.sarafizand.R;
import com.sarafizand.sarafizand.models.CurrenciesCategory;

import java.util.List;

/**
 * Created by Mohammed on 4/11/2017.
 */

public class CurrenciesCategoryAdapter  extends RecyclerView.Adapter<CurrenciesCategoryAdapter.MyViewHolder>  {
    private Context mContext;
    private List<CurrenciesCategory> curencieCategorysList;




    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView currencyCategoryName;

        public MyViewHolder(View view) {
            super(view);
            currencyCategoryName = (TextView)itemView.findViewById(R.id.CurrencyTitle);


        }
    }
    public CurrenciesCategoryAdapter(Context mContext, List<CurrenciesCategory> curencieCategorysList) {
        this.mContext = mContext;
        this.curencieCategorysList = curencieCategorysList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.currencies_grid_item, parent, false);

        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.currencyCategoryName.setText(curencieCategorysList.get(position).getCurrencyCategoryName());

    }

    @Override
    public int getItemCount() {
        return curencieCategorysList.size();
    }

}
