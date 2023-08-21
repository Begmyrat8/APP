package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.adaptor.CategoryViewPagerAdaptor;
import com.google.android.material.tabs.TabLayout;

public class Category extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    CategoryViewPagerAdaptor categoryViewPagerAdaptor;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        tabLayout = findViewById(R.id.categoryTabLayout);
        viewPager2 = findViewById(R.id.categoryView_pager);
        categoryViewPagerAdaptor = new CategoryViewPagerAdaptor(this);
        viewPager2.setAdapter(categoryViewPagerAdaptor);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });

    }
}