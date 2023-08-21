package com.example.myapplication.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Models.DictionaryFragmentViewModel;
import com.example.myapplication.Models.Word;
import com.example.myapplication.R;
import com.example.myapplication.adaptor.WordsAdapter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.Locale;
import java.util.Objects;

public class CategoryDetailFragment extends Fragment {

    private WordsAdapter wordsAdapter;

    private RecyclerView recyclerView;

    private BottomSheetBehavior mBottomSheetBehavior;
    private TextView def_Word, def_Value, def_Category;
    private TextToSpeech textToSpeech;
    private ImageView defBookmark;

    DictionaryFragmentViewModel viewModel;

    String language;

    public CategoryDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category_turkmen, container, false);
    }

    @Override
    public void onViewCreated(@androidx.annotation.NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            ((DetailsActivity) requireActivity()).loadLocale();
        } catch (IllegalStateException e) {
            Log.d("IllegalStateException", "onViewCreated: " + e.getMessage());
        }

        initBottomSheet(view);

        assert getArguments() != null;
        String lang = getArguments().getString("lang");

        recyclerView = view.findViewById(R.id.category_words_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        DetailsActivity activity = (DetailsActivity) getActivity();
        String categoryNumber = Objects.requireNonNull(activity).getMyData();


        def_Word = view.findViewById(R.id.bottom_sheet_word);
        def_Value = view.findViewById(R.id.bottom_sheet_value);
        def_Category = view.findViewById(R.id.bottom_sheet_category);

        ImageView defPronounce = view.findViewById(R.id.bottom_sheet_pronounce_word);
        defBookmark = view.findViewById(R.id.bottom_sheet_bookmark);

        viewModel = new ViewModelProvider(this).get(DictionaryFragmentViewModel.class);

        viewModel.getCategoryData(lang, categoryNumber).observe(getViewLifecycleOwner(), words -> {
            initRecycler();
            wordsAdapter.setWords(words, getActivity());
        });

        defPronounce.setOnClickListener(v -> {
            if (language.equals("0")) {
                String key = def_Word.getText().toString();
                textToSpeech.setSpeechRate(0.9f);
                textToSpeech.speak(key, TextToSpeech.QUEUE_FLUSH, null, null);
            } else {
                String key1 = def_Value.getText().toString();
                textToSpeech.setSpeechRate(0.9f);
                textToSpeech.speak(key1, TextToSpeech.QUEUE_FLUSH, null, null);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@androidx.annotation.NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(@androidx.annotation.NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });

        textToSpeech = new TextToSpeech(getContext(), status -> {
            if (status != TextToSpeech.ERROR) {
                textToSpeech.setLanguage(new Locale("ru", "RU"));
            }
        });

        ImageButton closeBottomSheet = view.findViewById(R.id.bottom_sheet_close);
        closeBottomSheet.setOnClickListener(v -> mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN));
    }

    @SuppressLint({"NotifyDataSetChanged", "ClickableViewAccessibility"})
    private void initRecycler() {
        wordsAdapter = new WordsAdapter(getContext(), mBottomSheetBehavior, 4, (position, word) -> {
            def_Word.setText(word.getWord());
            def_Category.setVisibility(View.INVISIBLE);
            language = word.getLang();

            def_Value.setText(word.getValue());

            Word bookmarkWord = viewModel.getWordFromBookmark(word.getWord());
            int isMark = bookmarkWord == null ? 0 : 1;
            defBookmark.setTag(isMark);

            int icon = bookmarkWord == null ? R.drawable.ic_bookmark_border_black_24dp : R.drawable.ic_bookmark_black_24dp;
            defBookmark.setImageResource(icon);

            defBookmark.setOnClickListener(v -> {

                int i = (int) defBookmark.getTag();
                if (i == 0) {
                    insertBookmark(new Bookmark(word.getTitle(), word.getLang(), word.getValue(), word.getCategory()));
                    defBookmark.setImageResource(R.drawable.ic_bookmark_black_24dp);
                    wordsAdapter.notifyDataSetChanged();
                    defBookmark.setTag(1);
                } else if (i == 1) {
                    viewModel.deleteOneBookmark(word.getWord());
                    defBookmark.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
                    wordsAdapter.notifyDataSetChanged();
                    defBookmark.setTag(0);
                }
            });
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(wordsAdapter);
    }

    public void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }

    private void initBottomSheet(View view) {

        View bottomSheet = view.findViewById(R.id.bottom_sheet);

        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.isHideable();
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            requireView().setFocusableInTouchMode(true);
            requireView().requestFocus();
            requireView().setOnKeyListener((v, keyCode, event) -> {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
                    if (mBottomSheetBehavior.getState() != BottomSheetBehavior.STATE_HIDDEN) {
                        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                    } else {
                        requireActivity().onBackPressed();
                    }
                    return true;
                }
                return false;
            });
        } catch (IllegalStateException e) {
            Log.d("IllegalStateException", "onViewCreated: " + e.getMessage());
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void insertBookmark(Bookmark bookmark) {

        viewModel.insertBookmark(bookmark);
    }

}
