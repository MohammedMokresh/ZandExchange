package com.sarafizand.sarafizand.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.sarafizand.sarafizand.GlobalVariables;
import com.sarafizand.sarafizand.R;
import com.sarafizand.sarafizand.utils.YourFragmentInterface;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainNewsPageFragment extends Fragment {
    MenuItem item;

    public static Toolbar toolbar;

    public static MainNewsPageFragment newInstance() {
        Bundle args = new Bundle();
        MainNewsPageFragment fragment = new MainNewsPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public MainNewsPageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_news_page, container, false);

        toolbar = (Toolbar) v.findViewById(R.id.toolbar);

        toolbar.setTitle("News");
        toolbar.inflateMenu(R.menu.news_toolbar_menu);
        Menu menu = toolbar.getMenu();
        GlobalVariables.hideKeyboard(getActivity());
        item = menu.findItem(R.id.categories);
        item.setVisible(true);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                NewsFragment.panelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                return false;
            }
        });
//        Toolbar.OnMenuItemClickListener
// Setup PagerAdapter
        final PagerAdapter pagerAdapter = new PagerAdapter(getChildFragmentManager());
        pagerAdapter.add(NewsFragment.newInstance(), "News");
        pagerAdapter.add(ZandNewsFragment.newInstance(), "Zand News");
        // Setup ViewPager
        final ViewPager     viewPager = (ViewPager) v.findViewById(R.id.pagertabhost);
        viewPager.setAdapter(pagerAdapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) v.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    toolbar.setTitle("News");
                    Menu menu = toolbar.getMenu();

                    item = menu.findItem(R.id.categories);
                    item.setVisible(true);
                    item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            NewsFragment.panelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                            return false;
                        }
                    });


                } else {
                    toolbar.setTitle("Zand News");

                    Menu menu = toolbar.getMenu();

                    item = menu.findItem(R.id.categories);
                    item.setVisible(false);


                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                YourFragmentInterface fragment = (YourFragmentInterface) pagerAdapter.instantiateItem(viewPager,position);
                if (fragment != null) {
                    fragment.fragmentBecameVisible();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return v;
    }




    public class PagerAdapter extends FragmentPagerAdapter {

        private ArrayList<String> mTitles;
        private ArrayList<Fragment> mFragments;

        public PagerAdapter(FragmentManager fm) {
            super(fm);
            mTitles = new ArrayList<>(2);
            mFragments = new ArrayList<>(2);
        }

        public void add(Fragment fragment, String title) {
            mTitles.add(title);
            mFragments.add(fragment);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public Fragment getItem(int position) {

            return mFragments.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        GlobalVariables.hideKeyboard(getActivity());
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GlobalVariables.hideKeyboard(getActivity());
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        GlobalVariables.hideKeyboard(getActivity());
    }


}
