package com.example.crowdtrials;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PagerAdapter extends FragmentPagerAdapter {
    private int numOfTabs;
    public HomeFragment homeFragment;
    private subscriptions subscriptionsFragment;
    public PagerAdapter(FragmentManager fm,int numOfTabs){
        //TODO: Use the new FragmentStatePageAdapter Class
        super(fm);
        this.numOfTabs= numOfTabs;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new subscriptions();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment createdFragment = (Fragment) super.instantiateItem(container, position);
        // save the appropriate reference depending on position
        switch (position) {
            case 0:
                homeFragment = (HomeFragment) createdFragment;
                break;
            case 1:
                subscriptionsFragment = (subscriptions) createdFragment;
                break;
        }
        return createdFragment;
    }
}
