package com.example.lukas.fip;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/*
Holds fragments for tabbed layout
Holds instance of Controller, all fragments call MainActivity methods

 */

public class MainActivity extends AppCompatActivity implements OverviewCompleteListener, InspectorCompleteListener{

    private static final String TAG = "MainActivity";
    private ViewPager viewPager;
    private SectionsPageAdapter spa;
    private FipController fCon;
    static Context mainContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fip_tabbed_main);

        mainContext = getApplicationContext();

        spa = new SectionsPageAdapter(getSupportFragmentManager());

        viewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        fCon = new FipController(this, getApplicationContext());



    }

    @Override
    public void onOverviewComplete() {

        fCon.initializeGUI(((Overview)spa.getFragment(0)));

    }

    @Override
    public void onInspectorComplete(){
        fCon.initializeGUI(((Inspector)spa.getFragment(1)));
    }

    boolean addExpense(String amount, String note){
        return fCon.addExpense(amount, note);
    }

    void changeDay(Fragment source, int direction){
        fCon.changeDay(source, direction);
    }

    void changeDay(Fragment source, String date){
        fCon.changeDay(source, date);
    }

    FipController getController(){
        return fCon;
    }


    private void setupViewPager(ViewPager vp){
        spa.addFragment(new Overview(), "Übersicht");
        spa.addFragment(new Inspector(), "Details");
        vp.setAdapter(spa);
    }


    class SectionsPageAdapter extends FragmentPagerAdapter{

        private final List<Fragment> FragmentList = new ArrayList<>();
        private final List<String> FragmentTitleList = new ArrayList<>();
        private final List<Fragment> instantiatedFragments = new ArrayList<>();

        public SectionsPageAdapter(FragmentManager fm){
            super(fm);
        }


        void addFragment(Fragment fragment, String title){
            FragmentList.add(fragment);
            FragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position){
            return FragmentTitleList.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentList.get(position);
        }

        @Override
        public int getCount() {
            return FragmentList.size();
        }

        @NonNull
        @Override
        public Fragment instantiateItem (ViewGroup container, int position){
            Fragment createdFragment = (Fragment) super.instantiateItem(container, position);
            instantiatedFragments.add(position, createdFragment);
            return createdFragment;
        }
        @Override
        public void destroyItem (ViewGroup container, int position, Object object){
            instantiatedFragments.remove(position);
            super.destroyItem(container, position, object);
        }

        Fragment getFragment(int position){
            return instantiatedFragments.get(position);
        }

    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onStop(){
        super.onStop();
        fCon.saveData();
    }



}
