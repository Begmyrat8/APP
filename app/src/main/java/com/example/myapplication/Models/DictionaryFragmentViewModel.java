package com.example.myapplication.Models;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.WordDict;
import com.example.myapplication.WordDictRepository;

import java.util.List;

public class DictionaryFragmentViewModel extends ViewModel {


    private MutableLiveData<List<WordDict>> mWords;


    private WordDictRepository modelRepository;

    public void init(Context context, int n, String i){
        if (mWords !=null){
            return;
        }
        modelRepository = WordDictRepository.getInstance();
        mWords = modelRepository.getDetails(context, n, i);
    }

    public LiveData<List<WordDict>> getWords(){
        return mWords;
    }

}
