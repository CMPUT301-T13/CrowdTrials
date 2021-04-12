package com.example.crowdtrials;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

/**
 * This class represents the Adapter for Pages of the Application
 */
public class PagerAdapter extends FragmentPagerAdapter {
    private int numOfTabs;
    public HomeFragment homeFragment;
    public subscriptions subscriptionsFragment;

    /**
     * This is the adapter for page fragment
     * @param fm
     * Fragment Manager
     * @param numOfTabs
     * The number of tabs
     */
    public PagerAdapter(FragmentManager fm,int numOfTabs){
        //TODO: Use the new FragmentStatePageAdapter Class
        super(fm);
        this.numOfTabs= numOfTabs;
    }



    /**
     * This method returns the associated fragment based on position
     * @param position
     * The position clicked by the user
     * @return
     * The fragment associated with the position
     */
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


    /**
     * This method returns the number of tabs
     * @return
     * Then number of tabs
     */
    @Override
    public int getCount() {
        return numOfTabs;
    }

    /**
     * This methods instantiates the fragment based on position
     * @param container
     * The container holding all Fragments
     * @param position
     * The position selected by user
     * @return
     * The fragment that was instantiated
     */
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
