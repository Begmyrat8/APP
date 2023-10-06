package com.example.myapplication.adaptor;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myapplication.fragments.CategoryFragment;
import com.example.myapplication.fragments.DictionaryFragment;
import com.example.myapplication.fragments.HistoryFragment;

public class MyViewPagerAdaptor extends FragmentStateAdapter {


    public MyViewPagerAdaptor(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new DictionaryFragment();
            case 1:
                return new CategoryFragment();
            case 2:
                return new HistoryFragment();
            default:
                return new DictionaryFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
