package com.sarafizand.sarafizand.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import com.sarafizand.sarafizand.utils.DialogUtil;
import com.sarafizand.sarafizand.utils.EndlessRecyclerOnScrollListener;
import com.sarafizand.sarafizand.utils.YourFragmentInterface;
import com.sarafizand.sarafizand.utils.volleySingleton;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

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
public class NewsFragment extends Fragment implements YourFragmentInterface {
    public static SlidingUpPanelLayout panelLayout;
    CardView AllCategory;
    RelativeLayout Accidents, Economy, World, Society, Cultural, Sport, Technology, Political;
    int NewsFlag = 0;
    int loadMore = 0;
    boolean loadOneTime = false;
    View view;
    final int AllNewsF = 0, AccidentsF = 1, EconomyF = 2, WorldF = 3, SocietyF = 4, CulturalF = 5, SportF = 6, TechnologyF = 7, PoliticalF = 8;
    LinearLayoutManager mLayoutManager;

    private RecyclerView newsrecyclerView;
    private NewsAdapter newsadapter;
    private List<News> newsList= new ArrayList<>();
    private ProgressBar progressBar;

    public static NewsFragment newInstance() {
        
        Bundle args = new Bundle();
        
        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        GlobalVariables.hideKeyboard(getActivity());
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        try {
            GlobalVariables.hideKeyboard(getActivity());
        }catch (Exception e){}

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GlobalVariables.hideKeyboard(getActivity());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view=inflater.inflate(R.layout.fragment_news, container, false);
        newsrecyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);

        mLayoutManager = new LinearLayoutManager(getContext());
        newsrecyclerView.setLayoutManager(mLayoutManager);
        newsList= new ArrayList<>();
        InitNewEndless();
        newsadapter = new NewsAdapter(getContext(), newsList);
        SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(newsadapter);
        alphaAdapter.setDuration(500);
        newsrecyclerView.setAdapter(new AlphaInAnimationAdapter(alphaAdapter));
        progressBar =  view.findViewById(R.id.progressBar_cyclic);
        progressBar.setVisibility(View.GONE);

        GlobalVariables.hideKeyboard(getActivity());
        NewsFlag = AllNewsF;
        getAllNews1(0);
//        prepareAlbums();



        newsrecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                switch (NewsFlag){
                    case AllNewsF:
                        getAllNews(loadMore);
                        break;
                    case AccidentsF:
                        newsadapter = new NewsAdapter(getContext(), newsList);
                        SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(newsadapter);
                        alphaAdapter.setDuration(500);

                        getCategoryNews("Accidents_news",2, loadMore);

                        break;
                    case EconomyF:
                        getCategoryNews("Economy_news",3, loadMore);
                        break;
                    case WorldF:
                        getCategoryNews("World_news",4, loadMore);
                        break;
                    case SocietyF:
                        getCategoryNews("Society_news",5, loadMore);
                        break;
                    case CulturalF:
                        getCategoryNews("Cultural_news",6, loadMore);
                        break;
                    case SportF:
                        getCategoryNews("Sport_news",7, loadMore);
                        break;
                    case TechnologyF:
                        getCategoryNews("Technology_news",8, loadMore);
                        break;
                    case PoliticalF:
                        getCategoryNews("Political_news",9, loadMore);
                        break;
                }
            }
        });


        panelLayout = (SlidingUpPanelLayout) view.findViewById(R.id.sliding_layout);
        panelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        panelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {

                if(newState == SlidingUpPanelLayout.PanelState.COLLAPSED){
                    panelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
                }
            }
        });

        Accidents = (RelativeLayout)  view.findViewById(R.id.Accidents);
        Economy = (RelativeLayout)  view.findViewById(R.id.Economy);
        World = (RelativeLayout)  view.findViewById(R.id.World);
        Society = (RelativeLayout)  view.findViewById(R.id.Society);
        Cultural = (RelativeLayout)  view.findViewById(R.id.Cultural);
        Sport = (RelativeLayout)  view.findViewById(R.id.Sport);
        Technology = (RelativeLayout)  view.findViewById(R.id.Technology);
        Political = (RelativeLayout)  view.findViewById(R.id.Political);
        AllCategory = (CardView) view.findViewById(R.id.AllCategory);

        AllCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                panelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
                MainNewsPageFragment.toolbar.setTitle(R.string.all_news);
                newsList= new ArrayList<>();
                newsadapter = new NewsAdapter(getContext(), newsList);
                SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(newsadapter);
                alphaAdapter.setDuration(500);
                newsrecyclerView.setAdapter(new AlphaInAnimationAdapter(alphaAdapter));
                InitNewEndless();
                NewsFlag = AllNewsF;
                getAllNews1(0);
            }
        });

        Accidents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                panelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
                MainNewsPageFragment.toolbar.setTitle(R.string.accidents);
                newsList= new ArrayList<>();
                newsadapter = new NewsAdapter(getContext(), newsList);
                SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(newsadapter);
                alphaAdapter.setDuration(500);
                newsrecyclerView.setAdapter(new AlphaInAnimationAdapter(alphaAdapter));
                InitNewEndless();
                NewsFlag = AccidentsF;
                getCategoryNews1("Accidents_news",2, 0);
            }
        });

        Economy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                panelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
                MainNewsPageFragment.toolbar.setTitle(R.string.economy);
                newsList= new ArrayList<>();
                newsadapter = new NewsAdapter(getContext(), newsList);
                SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(newsadapter);
                alphaAdapter.setDuration(500);
                newsrecyclerView.setAdapter(new AlphaInAnimationAdapter(alphaAdapter));
                InitNewEndless();
                NewsFlag = EconomyF;
                getCategoryNews1("Economy_news",3, 0);
            }
        });

        World.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                panelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
                MainNewsPageFragment.toolbar.setTitle(R.string.world);
                newsList= new ArrayList<>();
                newsadapter = new NewsAdapter(getContext(), newsList);
                SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(newsadapter);
                alphaAdapter.setDuration(500);
                newsrecyclerView.setAdapter(new AlphaInAnimationAdapter(alphaAdapter));
                InitNewEndless();
                NewsFlag = WorldF;
                getCategoryNews1("World_news",4, 0);
            }
        });

        Society.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                panelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
                MainNewsPageFragment.toolbar.setTitle(R.string.society);
                newsList= new ArrayList<>();
                newsadapter = new NewsAdapter(getContext(), newsList);
                SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(newsadapter);
                alphaAdapter.setDuration(500);
                newsrecyclerView.setAdapter(new AlphaInAnimationAdapter(alphaAdapter));
                InitNewEndless();
                NewsFlag = SocietyF;
                getCategoryNews1("Society_news",5, 0);
            }
        });

        Cultural.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                panelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
                MainNewsPageFragment.toolbar.setTitle(R.string.cultural);
                newsList= new ArrayList<>();
                newsadapter = new NewsAdapter(getContext(), newsList);
                SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(newsadapter);
                alphaAdapter.setDuration(500);
                newsrecyclerView.setAdapter(new AlphaInAnimationAdapter(alphaAdapter));
                InitNewEndless();
                NewsFlag = CulturalF;
                getCategoryNews1("Cultural_news",6, 0);
            }
        });

        Sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                panelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
                MainNewsPageFragment.toolbar.setTitle(R.string.sport);
                newsList= new ArrayList<>();
                newsadapter = new NewsAdapter(getContext(), newsList);
                SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(newsadapter);
                alphaAdapter.setDuration(500);
                newsrecyclerView.setAdapter(new AlphaInAnimationAdapter(alphaAdapter));
                InitNewEndless();
                NewsFlag = SportF;
                getCategoryNews1("Sport_news",7, 0);
            }
        });

        Technology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                panelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
                MainNewsPageFragment.toolbar.setTitle(R.string.technology);
                newsList= new ArrayList<>();
                newsadapter = new NewsAdapter(getContext(), newsList);
                SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(newsadapter);
                alphaAdapter.setDuration(500);
                newsrecyclerView.setAdapter(new AlphaInAnimationAdapter(alphaAdapter));
                InitNewEndless();
                NewsFlag = TechnologyF;
                getCategoryNews1("Technology_news",8, 0);

            }
        });

        Political.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                panelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
                MainNewsPageFragment.toolbar.setTitle(R.string.political);
                newsList= new ArrayList<>();
                newsadapter = new NewsAdapter(getContext(), newsList);
                SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(newsadapter);
                alphaAdapter.setDuration(500);
                newsrecyclerView.setAdapter(new AlphaInAnimationAdapter(alphaAdapter));
                InitNewEndless();
                NewsFlag = PoliticalF;
                getCategoryNews1("Political_news",9, 0);
            }
        });



        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

    }


    private void getAllNews(int id) {

        StringRequest jss = new StringRequest(Request.Method.GET, ((GlobalVariables)getActivity().getApplication()).getGetAllNews()+id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                Toast.makeText(getContext(), response.toString(), Toast.LENGTH_LONG).show();
                        Log.e("res",response.toString());



                        try {
                            JSONObject res = new JSONObject(response);
//                            ((GlobalVariables)getActivity().getApplication()).setClient_token(res.getJSONObject("client_token").getString("client_token"));
                            PreferenceManager.getInstance().setClient_token(res.getJSONObject("client_token").getString("client_token"));
                            JSONObject newsresponseobject=res.getJSONObject("response");
                            JSONArray newsArray= newsresponseobject.getJSONArray("news");
                            for (int i=0;i<newsArray.length();i++){
                                JSONObject currentNews = newsArray.getJSONObject(i);
                                String newstitle = currentNews.getString("title");
                                String newsdesc = currentNews.getString("description");
                                String newsimage = currentNews.getString("image");
                                int newsID=currentNews.getInt("id");
                                int categoryId=currentNews.getInt("category_id");
                                String craetedAt=currentNews.getString("created_at");

                                News news = new News();
                                news.setNewsTitle(newstitle);
                                news.setDescription(newsdesc);
                                news.setNewsThumpnail(newsimage);
                                news.setCreatedAt(craetedAt);


                                newsList.add(news);


                                if(i == newsArray.length() - 1){
                                    loadMore = currentNews.getInt("id") - 1;

                                }


                                loadOneTime = true;



                            }
                            newsadapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                progressDialog.dismiss();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();

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

//        progressDialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
//        progressDialog.setMessage("Loading ...");
//
//        progressDialog.setCancelable(false);
//        progressDialog.setCanceledOnTouchOutside(false);
//
//        progressDialog.show();
    }
    private void getAllNews1(int id) {

        StringRequest jss = new StringRequest(Request.Method.GET, ((GlobalVariables)getActivity().getApplication()).getGetAllNews()+id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                Toast.makeText(getContext(), response.toString(), Toast.LENGTH_LONG).show();
                        Log.e("res",response.toString());



                        try {
                            JSONObject res = new JSONObject(response);
//                            ((GlobalVariables)getActivity().getApplication()).setClient_token(res.getJSONObject("client_token").getString("client_token"));
                            PreferenceManager.getInstance().setClient_token(res.getJSONObject("client_token").getString("client_token"));
                            JSONObject newsresponseobject=res.getJSONObject("response");
                            JSONArray newsArray= newsresponseobject.getJSONArray("news");
                            for (int i=0;i<newsArray.length();i++){
                                JSONObject currentNews = newsArray.getJSONObject(i);
                                String newstitle = currentNews.getString("title");
                                String newsdesc = currentNews.getString("description");
                                String newsimage = currentNews.getString("image");
                                int newsID=currentNews.getInt("id");
                                int categoryId=currentNews.getInt("category_id");
                                String craetedAt=currentNews.getString("created_at");


                                News news = new News();
                                news.setNewsTitle(newstitle);
                                news.setDescription(newsdesc);
                                news.setNewsThumpnail(newsimage);
                                news.setCreatedAt(craetedAt);

                                newsList.add(news);


                                if(i == newsArray.length() - 1){
                                    loadMore = currentNews.getInt("id") - 1;

                                }


                                loadOneTime = true;



                            }
                            YoYo.with(Techniques.SlideInUp)
                                    .duration(700)
                                    .playOn(view.findViewById(R.id.recycler_view));

                            newsadapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    DialogUtil.removeProgressDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                DialogUtil.removeProgressDialog();




            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " +PreferenceManager.getInstance().getClient_token());
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
        DialogUtil.showProgressDialog("Loading",getFragmentManager());
    }


    private void getCategoryNews(final String origin, final int category_id, int id) {

        StringRequest jss = new StringRequest(Request.Method.GET, ((GlobalVariables)getActivity().getApplication()).getGetNewsApi()+category_id+"/"+id,
                 new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(getContext(), response.toString(), Toast.LENGTH_LONG).show();
                Log.e("res",response.toString());



                try {
                    JSONObject res = new JSONObject(response);
//                    ((GlobalVariables)getActivity().getApplication()).setClient_token(res.getJSONObject("client_token").getString("client_token"));
                    PreferenceManager.getInstance().setClient_token(res.getJSONObject("client_token").getString("client_token"));
                    JSONObject newsresponseobject=res.getJSONObject("response");
                    JSONArray newsArray= newsresponseobject.getJSONArray("news");
                    for (int i=0;i<newsArray.length();i++){
                        JSONObject currentNews = newsArray.getJSONObject(i);
                        String newstitle = currentNews.getString("title");
                        String newsdesc = currentNews.getString("description");
                        String newsimage = currentNews.getString("image");
                        int newsID=currentNews.getInt("id");
                        int categoryId=currentNews.getInt("category_id");
                        String craetedAt=currentNews.getString("created_at");

                        News news = new News();
                        news.setNewsTitle(newstitle);
                        news.setDescription(newsdesc);
                        news.setNewsThumpnail(newsimage);
                        news.setCreatedAt(craetedAt);


                        newsList.add(news);


                        if(i == newsArray.length() - 1){
                            loadMore = currentNews.getInt("id") - 1;

                        }


                        loadOneTime = true;



                    }
                    newsadapter.notifyDataSetChanged();

            } catch (JSONException e) {
                         e.printStackTrace();
                     }
//                progressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();

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
//        progressDialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
//        progressDialog.setMessage("Loading ...");
//
//        progressDialog.setCancelable(false);
//        progressDialog.setCanceledOnTouchOutside(false);
//
//        progressDialog.show();
    }

    private void getCategoryNews1(final String origin, final int category_id, int id) {

        StringRequest jss = new StringRequest(Request.Method.GET, ((GlobalVariables)getActivity().getApplication()).getGetNewsApi()+category_id+"/"+id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                Toast.makeText(getContext(), response.toString(), Toast.LENGTH_LONG).show();
                        Log.e("res",response.toString());



                        try {
                            JSONObject res = new JSONObject(response);
//                            ((GlobalVariables)getActivity().getApplication()).setClient_token(res.getJSONObject("client_token").getString("client_token"));
                            PreferenceManager.getInstance().setClient_token(res.getJSONObject("client_token").getString("client_token"));
                            JSONObject newsresponseobject=res.getJSONObject("response");
                            JSONArray newsArray= newsresponseobject.getJSONArray("news");
                            for (int i=0;i<newsArray.length();i++){
                                JSONObject currentNews = newsArray.getJSONObject(i);
                                String newstitle = currentNews.getString("title");
                                String newsdesc = currentNews.getString("description");
                                String newsimage = currentNews.getString("image");
                                int newsID=currentNews.getInt("id");
                                int categoryId=currentNews.getInt("category_id");
                                String craetedAt=currentNews.getString("created_at");

                                News news = new News();
                                news.setNewsTitle(newstitle);
                                news.setDescription(newsdesc);
                                news.setNewsThumpnail(newsimage);
                                news.setCreatedAt(craetedAt);


                                newsList.add(news);


                                if(i == newsArray.length() - 1){
                                    loadMore = currentNews.getInt("id") - 1;

                                }


                                loadOneTime = true;



                            }
                            YoYo.with(Techniques.SlideInUp)
                                    .duration(700)
                                    .playOn(view.findViewById(R.id.recycler_view));

                            newsadapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                            DialogUtil.removeProgressDialog();


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                DialogUtil.removeProgressDialog();



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
        DialogUtil.showProgressDialog("Loading",getFragmentManager());
    }

    public void InitNewEndless(){
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

    }
}

