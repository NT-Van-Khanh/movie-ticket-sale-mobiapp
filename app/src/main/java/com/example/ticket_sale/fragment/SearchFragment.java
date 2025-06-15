package com.example.ticket_sale.fragment;

import static com.google.android.material.internal.ViewUtils.showKeyboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ticket_sale.R;
import com.example.ticket_sale.adapter.MovieAdapter;
import com.example.ticket_sale.adapter.SearchHistoryAdapter;
import com.example.ticket_sale.mapper.MovieMapper;
import com.example.ticket_sale.model.Movie;
import com.example.ticket_sale.viewmodel.SearchMovieViewModel;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    private ImageView imgGoBack;
    private SearchView svMovies;
    private RecyclerView rcViewSearchMovies;
    private RecyclerView rcViewSearchHistory;
    private ProgressBar pbLoadSearchMovies;
    private View viewOverlay;

    private LinearLayout lnlSearchHistory;
    private LinearLayout lnlMoviesSearchResult;

    private SearchHistoryAdapter searchHistoryAdapter;
    private MovieAdapter movieAdapter;

    private List<Movie> searchedMovies;
    private List<String> searchHistories;

    private SearchMovieViewModel searchMovieViewModel;

    private final int MAX_SEARCH_HISTORY = 5;
    private final String SEARCH_HISTORY_PREFS = "sale_ticket_search_history_pref";
    private final String SEARCH_HISTORY_KEY = "search_histories";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        initViews(root);
        return root;
    }



    private void searchMoviesByApi(String keyword){
        showLoadingUI();
        searchMovieViewModel.searchMovies("0", keyword).observe(getViewLifecycleOwner(), response -> {
            if(response == null){
                Toast.makeText(getContext(), "Lỗi kết nối. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
            }else if(response.getStatusCode() != 200){
                String message = String.format(Locale.getDefault(),"Không thể lấy dữ liệu phim (%d)",response.getStatusCode());
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }else{
                searchedMovies = response.getData().getContent()
                        .stream()
                        .map(MovieMapper::toMovie)
                        .collect(Collectors.toList());
                if(searchedMovies.isEmpty()){
                    Toast.makeText(getContext(), "Không tìm thấy phim phù hợp với kết quả tìm kiếm!", Toast.LENGTH_LONG).show();
                }
                movieAdapter.setMovies(searchedMovies);
            }
            lnlMoviesSearchResult.setVisibility(View.VISIBLE);
            hideLoadingUI();
        });
    }


    private void initViews(View root) {
        this.imgGoBack = root.findViewById(R.id.imgGoBack);
        this.svMovies = root.findViewById(R.id.searchMovies);
        this.rcViewSearchMovies = root.findViewById(R.id.rcViewSearchMovies);
        this.rcViewSearchHistory = root.findViewById(R.id.rcViewSearchHistory);
        this.pbLoadSearchMovies = root.findViewById(R.id.pbLoadSeachMovies);
        this.viewOverlay = root.findViewById(R.id.viewOverlay);
        this.lnlSearchHistory = root.findViewById(R.id.lnlSearchHistory);
        this.lnlMoviesSearchResult = root.findViewById(R.id.lnlSearchMovies);
        this.lnlMoviesSearchResult.setVisibility(View.GONE);
        this.imgGoBack.setOnClickListener( v -> getParentFragmentManager().popBackStack());
        initSearchView();
        initSearchHistoryView();
        movieAdapter = new MovieAdapter( new ArrayList<>(), this::openMovieDetail);
        this.rcViewSearchMovies.setAdapter(movieAdapter);
        this.rcViewSearchMovies.setLayoutManager(new GridLayoutManager(getContext(), 2));
        searchMovieViewModel = new SearchMovieViewModel();
    }

    private void initSearchHistoryView() {
        SharedPreferences pref = requireContext().getSharedPreferences(SEARCH_HISTORY_PREFS,Context.MODE_PRIVATE);
        String searchHistoryJson = pref.getString(SEARCH_HISTORY_KEY, null);
        if(searchHistoryJson == null) {
            this.searchHistories = new ArrayList<>();
            lnlSearchHistory.setVisibility(View.GONE);
        }else{
            Gson gson = new Gson();
            this.searchHistories =  new ArrayList<>(Arrays.asList(gson.fromJson(searchHistoryJson , String[].class)));
            lnlSearchHistory.setVisibility(View.VISIBLE);
        }
        
        searchHistoryAdapter = new SearchHistoryAdapter(searchHistories, this::onSearchItemClick);
        rcViewSearchHistory.setAdapter(searchHistoryAdapter);
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(requireContext());
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);
        rcViewSearchHistory.setLayoutManager(flexboxLayoutManager);
    }

    private void initSearchView() {
        svMovies.setIconified(false);
        svMovies.clearFocus();
        svMovies.requestFocus();

        EditText searchEditText = svMovies.findViewById(androidx.appcompat.R.id.search_src_text);
        if (searchEditText != null) {
            handleKeyboardAndFocus(searchEditText);
            handleSearchAction(searchEditText);
        }
        //handle for clear button
        ImageView closeButton = svMovies.findViewById(androidx.appcompat.R.id.search_close_btn);
        closeButton.setOnClickListener(v -> {
            svMovies.setQuery("", false);
            if(!searchHistories.isEmpty()){
                lnlSearchHistory.setVisibility(View.VISIBLE);
               searchHistoryAdapter.setSearchHistories(searchHistories);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        saveKeyword();
    }

    private void handleSearchAction(EditText searchEditText) {
        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String keyword = svMovies.getQuery().toString().trim();
                lnlSearchHistory.setVisibility(View.GONE);
                if (!keyword.isEmpty()) {
                    searchMoviesByApi(keyword); // Gọi API tìm kiếm
                    addKeywordToSearchHistory(keyword);
                }
                return true;
            }
            return false;
        });

        svMovies.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.trim().isEmpty()) {
                    searchMoviesByApi(query.trim());
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Có thể dùng để gợi ý tìm kiếm realtime ở đây (optional)
                return false;
            }
        });
    }

    private void addKeywordToSearchHistory(String keyword) {
        searchHistories.remove(keyword);
        searchHistories.add(0, keyword);
        if (searchHistories.size() > MAX_SEARCH_HISTORY) {
            searchHistories.remove(MAX_SEARCH_HISTORY-1);
        }
    }

    private void saveKeyword() {
        SharedPreferences pref = requireContext().getSharedPreferences(SEARCH_HISTORY_PREFS,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String updatedJson = gson.toJson(searchHistories);
        pref.edit().putString(SEARCH_HISTORY_KEY, updatedJson).apply();
    }

    private void handleKeyboardAndFocus(EditText searchEditText ) {
        searchEditText.requestFocus();
        svMovies.postDelayed(() -> {
            InputMethodManager imm = (InputMethodManager) requireActivity()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showSoftInput(searchEditText, InputMethodManager.SHOW_IMPLICIT);
            }
        }, 200);
    }


    private void openMovieDetail(Movie movie) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("movie", movie);
        MovieDetailFragment detailFragment = new MovieDetailFragment();
        detailFragment.setArguments(bundle);

        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .addToBackStack(null)
                .commit();
    }

    private void showLoadingUI(){
        pbLoadSearchMovies.setVisibility(View.VISIBLE);
        viewOverlay.setVisibility(View.VISIBLE);
    }

    private void hideLoadingUI(){
        pbLoadSearchMovies.setVisibility(View.GONE);
        viewOverlay.setVisibility(View.GONE);
    }

    private void onSearchItemClick(int position) {
        String searchKeyword = searchHistories.get(position);
        Log.e("search", searchKeyword);
        svMovies.setQuery(searchKeyword, false);
    }
}