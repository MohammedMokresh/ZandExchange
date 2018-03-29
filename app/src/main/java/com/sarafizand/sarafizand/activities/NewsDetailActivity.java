package com.sarafizand.sarafizand.activities;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;


import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


import com.sarafizand.sarafizand.GlobalVariables;
import com.sarafizand.sarafizand.R;
import com.sarafizand.sarafizand.utils.AppBarStateChangeListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import me.yokeyword.swipebackfragment.SwipeBackActivity;
import me.yokeyword.swipebackfragment.SwipeBackLayout;


public class NewsDetailActivity extends SwipeBackActivity {
    TextView Description, Title;
    ImageView NewsImage;
    AppBarLayout appBarLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Description = (TextView) findViewById(R.id.Description);
        Description.setText(intent.getStringExtra("Description"));
        GlobalVariables.hideKeyboard(this);
        Title = (TextView) findViewById(R.id.Title);
        Title.setText(intent.getStringExtra("Title"));

        NewsImage = (ImageView) findViewById(R.id.NewsImage);
        Title.setText(intent.getStringExtra("Title"));

        String url = intent.getStringExtra("ImageURL");
        Picasso.with(getApplicationContext())
                .load(url)
                .placeholder(R.drawable.loadin_image)
                .error(R.drawable.error_image)
                .resize(1000,781)
                .centerCrop()
                .into(NewsImage, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                    }
                });

        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);

        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                Log.e("STATE", state.name());
                if(state.name() == "COLLAPSED"){
                    getSwipeBackLayout().setEdgeOrientation(SwipeBackLayout.EDGE_ALL);
                }else if(state.name() == "EXPANDED"){
//                    setDragEdge(SwipeBackLayout.DragEdge.TOP);
                    getSwipeBackLayout().setEdgeOrientation(SwipeBackLayout.EDGE_ALL);
                }
            }
        });



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


//    @Override
//    public void onViewPositionChanged(float fractionAnchor, float fractionScreen) {
//
//    }
}
