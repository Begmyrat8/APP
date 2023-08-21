package com.example.myapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Models.CategoryModel;
import com.example.myapplication.R;
import com.example.myapplication.adaptor.CategoryAdaptor;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {

    RecyclerView categoryRecycler;
    CategoryAdaptor categoryAdaptor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        categoryRecycler = view.findViewById(R.id.categoryList);

        List<CategoryModel> categoryList = new ArrayList<>();
        categoryList.add(new CategoryModel(1,1,"colors","Colors","#605D5D"));
        categoryList.add(new CategoryModel(2,2,"fruit","Fruit","#FF6200EE"));
        categoryList.add(new CategoryModel(3,3,"hobby","Hobby","#FF2800"));
        categoryList.add(new CategoryModel(4,4,"clock","Clock","#FF03DAC5"));
        categoryList.add(new CategoryModel(5,5,"colors","Colors","#605D5D"));
        categoryList.add(new CategoryModel(6,6,"fruit","Fruit","#FF6200EE"));
        categoryList.add(new CategoryModel(7,7,"hobby","Hobby","#FF2800"));
        categoryList.add(new CategoryModel(8,8,"clock","Clock","#FF03DAC5"));
        categoryList.add(new CategoryModel(9,9,"colors","Colors","#605D5D"));

        setCategoryRecycler(categoryList);
    }

    private void setCategoryRecycler(List<CategoryModel> categoryList) {
        GridLayoutManager manager = new GridLayoutManager(getContext(),2,RecyclerView.VERTICAL,false);
        categoryRecycler.setLayoutManager(manager);

        categoryAdaptor = new CategoryAdaptor(getContext(), categoryList);
        categoryRecycler.setAdapter(categoryAdaptor);

    }
}