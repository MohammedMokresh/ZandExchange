package com.sarafizand.sarafizand.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.sarafizand.sarafizand.GlobalVariables;
import com.sarafizand.sarafizand.R;
import com.sarafizand.sarafizand.adapters.NewsAdapter;
import com.sarafizand.sarafizand.models.News;
import com.sarafizand.sarafizand.preference.PreferenceManager;
import com.sarafizand.sarafizand.utils.EndlessRecyclerOnScrollListener;
import com.sarafizand.sarafizand.utils.YourFragmentInterface;
import com.sarafizand.sarafizand.utils.volleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class ZandNewsFragment extends Fragment implements YourFragmentInterface {
    private RecyclerView newsrecyclerView;
    private NewsAdapter newsadapter;
    private List<News> newsList = new ArrayList<>();
    int loadMore = 0;
    boolean loadOneTime = false;
    LinearLayoutManager mLayoutManager;
    private ProgressBar progressBar;
    View view;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(false);

    }

    public static ZandNewsFragment newInstance() {

        Bundle args = new Bundle();

        ZandNewsFragment fragment = new ZandNewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        GlobalVariables.hideKeyboard(getActivity());
    }
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        GlobalVariables.hideKeyboard(getActivity());
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GlobalVariables.hideKeyboard(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

         view = inflater.inflate(R.layout.fragment_zand_news, container, false);
        newsrecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        progressBar =  view.findViewById(R.id.progressBar_cyclic);
        progressBar.setVisibility(View.GONE);

        GlobalVariables.hideKeyboard(getActivity());
        return view;
    }

    private void getCategoryNews1(final int category_id, int id) {

        StringRequest jss = new StringRequest(Request.Method.GET, ((GlobalVariables) getActivity().getApplication()).getGetNewsApi() + category_id + "/" + id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                Toast.makeText(getContext(), response.toString(), Toast.LENGTH_LONG).show();
                        Log.e("res", response.toString());


                        try {
                            JSONObject res = new JSONObject(response);
//                            ((GlobalVariables) getActivity().getApplication()).setClient_token(res.getJSONObject("client_token").getString("client_token"));
                            PreferenceManager.getInstance().setClient_token(res.getJSONObject("client_token").getString("client_token"));
                            JSONObject newsresponseobject = res.getJSONObject("response");
                            JSONArray newsArray = newsresponseobject.getJSONArray("news");
                            for (int i = 0; i < newsArray.length(); i++) {
                                JSONObject currentNews = newsArray.getJSONObject(i);
                                String newstitle = currentNews.getString("title");
                                String newsdesc = currentNews.getString("description");
                                String newsimage = currentNews.getString("image");
                                int newsID = currentNews.getInt("id");
                                int categoryId = currentNews.getInt("category_id");
                                String craetedAt = currentNews.getString("created_at");

                                News news = new News();
                                news.setNewsTitle(newstitle);
                                news.setDescription(newsdesc);
                                news.setNewsThumpnail(newsimage);
                                news.setCreatedAt(craetedAt);


                                newsList.add(news);


                                if (i == newsArray.length() - 1) {
                                    loadMore = currentNews.getInt("id") - 1;

                                }

                                loadOneTime = true;


                            }
                            YoYo.with(Techniques.SlideInUp)
                                    .duration(700)
                                    .playOn(view.findViewById(R.id.recycler_view));

                            newsadapter.notifyItemRangeChanged(0, newsadapter.getItemCount());


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressBar.setVisibility(View.GONE);
                        getActivity(). getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();

                progressBar.setVisibility(View.GONE);
                getActivity(). getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + PreferenceManager.getInstance().getClient_token());
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                return headers;

            }

        };
        jss.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        volleySingleton.getInstance(getContext()).addToRequestQueue(jss);
        progressBar.setVisibility(View.VISIBLE);
        getActivity().  getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void getCategoryNews(final int category_id, int id) {

        StringRequest jss = new StringRequest(Request.Method.GET, ((GlobalVariables) getActivity().getApplication()).getGetNewsApi() + category_id + "/" + id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                Toast.makeText(getContext(), response.toString(), Toast.LENGTH_LONG).show();
                        Log.e("res", response.toString());


                        try {
                            JSONObject res = new JSONObject(response);
//                            ((GlobalVariables) getActivity().getApplication()).setClient_token(res.getJSONObject("client_token").getString("client_token"));
                            PreferenceManager.getInstance().setClient_token(res.getJSONObject("client_token").getString("client_token"));
                            JSONObject newsresponseobject = res.getJSONObject("response");
                            JSONArray newsArray = newsresponseobject.getJSONArray("news");
                            for (int i = 0; i < newsArray.length(); i++) {
                                JSONObject currentNews = newsArray.getJSONObject(i);
                                String newstitle = currentNews.getString("title");
                                String newsdesc = currentNews.getString("description");
                                String newsimage = currentNews.getString("image");
                                int newsID = currentNews.getInt("id");
                                int categoryId = currentNews.getInt("category_id");
                                String craetedAt = currentNews.getString("created_at");

                                News news = new News();
                                news.setNewsTitle(newstitle);
                                news.setDescription(newsdesc);
                                news.setNewsThumpnail(newsimage);
                                news.setCreatedAt(craetedAt);


                                newsList.add(news);


                                if (i == newsArray.length() - 1) {
                                    loadMore = currentNews.getInt("id") - 1;

                                }

                                loadOneTime = true;


                            }

                            newsadapter.notifyItemRangeChanged(0, newsadapter.getItemCount());


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();

//                progressDialog.dismiss();

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + PreferenceManager.getInstance().getClient_token());
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                return headers;

            }

        };
        jss.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        volleySingleton.getInstance(getContext()).addToRequestQueue(jss);
//        progressDialog = new ProgressDialog(getContext(), ProgressDialog.STYLE_SPINNER);
//        progressDialog.setMessage("Loading ...");
//
//        progressDialog.setCancelable(false);
//        progressDialog.setCanceledOnTouchOutside(false);
//
//        progressDialog.show();
    }


    public void InitNewEndless() {
        loadOneTime = false;
        clearNewsList();
        loadMore = 0;
        EndlessRecyclerOnScrollListener.current_page = 1;
        EndlessRecyclerOnScrollListener.loading = true;
        EndlessRecyclerOnScrollListener.previousTotal = 0;
    }

    public void clearNewsList() {
        int size = this.newsList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.newsList.remove(0);
            }

            this.newsadapter.notifyItemRangeRemoved(0, size);
        }
    }


    @Override
    public void fragmentBecameVisible() {
        mLayoutManager = new LinearLayoutManager(getContext());

        newsrecyclerView.setLayoutManager(mLayoutManager);
//                        newsrecyclerView.setItemAnimator(new DefaultItemAnimator());
        newsList = new ArrayList<>();
        InitNewEndless();
        newsadapter = new NewsAdapter(getContext(), newsList);
        SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(newsadapter);
        alphaAdapter.setDuration(500);
        newsrecyclerView.setAdapter(new AlphaInAnimationAdapter(alphaAdapter));

        getCategoryNews1(1, 0);

        newsrecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager) {
                                                 @Override
                                                 public void onLoadMore(int current_page) {

                                                     getCategoryNews(1, loadMore);
                                                 }
                                             }
        );

    }
}
