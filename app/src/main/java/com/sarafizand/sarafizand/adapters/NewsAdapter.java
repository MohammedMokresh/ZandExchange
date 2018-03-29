package com.sarafizand.sarafizand.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sarafizand.sarafizand.activities.NewsDetailActivity;
import com.sarafizand.sarafizand.R;
import com.sarafizand.sarafizand.models.News;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.tbuonomo.circleloading.CircleLoading;

import java.util.List;

/**
 * Created by Mohammed on 21/10/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    private Context mContext;
    private List<News> newsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView thumbnail;
        public TextView description;
        public TextView createdAt;
        CircleLoading loading;
        CardView cv;
        public MyViewHolder(View view) {
            super(view);
            cv = (CardView)view.findViewById(R.id.NewsCardView);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            description=(TextView) view.findViewById(R.id.description);
            createdAt=(TextView)view.findViewById(R.id.DateAndTime);
            loading = (CircleLoading) view.findViewById(R.id.imageLoadinCircle);

        }
    }


    public NewsAdapter(Context mContext, List<News>  newsList) {
        this.mContext = mContext;
        this.newsList = newsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final News news = newsList.get(position);
        holder.title.setText(news.getNewsTitle());
        holder.description.setText(news.getDescription());
        holder.createdAt.setText(news.getCreatedAt());
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(mContext, NewsDetailActivity.class);
                i2.putExtra("TimeAndDate",news.getCreatedAt());
                i2.putExtra("Title",news.getNewsTitle());
                i2.putExtra("Description",news.getDescription());
                i2.putExtra("ImageURL",news.getNewsThumpnail());
                mContext.startActivity(i2);

            }
        });


        Picasso.with(mContext)
                .load(news.getNewsThumpnail())
                .placeholder(R.drawable.loadin_image)
                .error(R.drawable.error_image)
                .resize(500,281)
                .centerCrop()
                .into(holder.thumbnail, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.loading.setVisibility(View.INVISIBLE);

                    }

                    @Override
                    public void onError() {
                        holder.loading.setVisibility(View.INVISIBLE);
                    }
                });


    }



    @Override
    public int getItemCount() {
        return newsList.size();
    }
}