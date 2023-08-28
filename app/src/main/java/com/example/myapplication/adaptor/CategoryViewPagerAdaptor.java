package com.example.myapplication.adaptor;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myapplication.Category;
import com.example.myapplication.fragments.CategoryDetailFragment;
import com.example.myapplication.fragments.CategoryFragment;

public class CategoryViewPagerAdaptor extends FragmentStateAdapter {

    public CategoryViewPagerAdaptor(@NonNull Category fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return position == 1 ? new CategoryFragment() : new CategoryDetailFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

