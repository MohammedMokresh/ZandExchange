package com.sarafizand.sarafizand.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sarafizand.sarafizand.R;
import com.sarafizand.sarafizand.models.Notifications;

import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.MyViewHolder> {
    Context context;
    private List<Notifications> notificationsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year;
        CardView notificationCardView;
        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            year = view.findViewById(R.id.year);
            notificationCardView=view.findViewById(R.id.notificationCardView);

        }

    }

    public NotificationsAdapter(Context context, List<Notifications> notificationsList) {
        this.notificationsList = notificationsList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notifications_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Notifications movie = notificationsList.get(position);
        holder.title.setText(movie.getTitle());
        holder.year.setText("At : "+movie.getYear());
    }

    @Override
    public int getItemCount() {
        return notificationsList.size();
    }
}