package com.example.hotelmanagmentapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabsAccessAdapter extends FragmentPagerAdapter
{
    public TabsAccessAdapter( FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                TestFragment testFragment=new TestFragment();
                return testFragment;

                default:
                    return null;
        }

    }

    @Override
    public int getCount() {
        return 1;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position)
    {
        switch (position)
        {
            case 0:
               return "Gallery";
            default:
                return null;
        }
    }
}
