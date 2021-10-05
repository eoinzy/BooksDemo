package com.eoinzy.booksdemo.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.eoinzy.booksdemo.R;
import com.eoinzy.booksdemo.models.BookSearch;
import com.eoinzy.booksdemo.ui.main.widgets.ResultsAdapter;

import java.util.Collections;
import java.util.List;

import timber.log.Timber;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;

    private SearchView searchView;
    private RecyclerView recyclerView;
    private ResultsAdapter adapter;
    private View emptyView;
    private MenuItem sortByAuthor, sortByTitle;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        recyclerView = view.findViewById(R.id.resultList);
        emptyView = view.findViewById(R.id.emptyMessage);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mViewModel.getBookSearchResults().observe(getViewLifecycleOwner(), this::populateUI);
    }

    private void populateUI(@NonNull BookSearch bookSearch) {
        Timber.d("Populating UI in MainFragment");
        emptyView.setVisibility(bookSearch.getItems() == null || bookSearch.getItems().size() == 0 ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(bookSearch.getItems() != null && bookSearch.getItems().size() > 0 ? View.VISIBLE : View.GONE);

        adapter = new ResultsAdapter(bookSearch.getItems());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);

        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String searchQuery) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchQuery) {
                performBookSearch(searchQuery);
                return true;
            }
        });

        sortByAuthor = menu.findItem(R.id.sort_author);
        sortByAuthor.setOnMenuItemClickListener(menuItem -> {
            sortData(SortBy.AUTHOR);
            return true;
        });

        sortByTitle = menu.findItem(R.id.sort_title);
        sortByTitle.setOnMenuItemClickListener(menuItem -> {
            sortData(SortBy.TITLE);
            return true;
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void performBookSearch(String searchQuery) {
        if (!searchQuery.isEmpty() && searchQuery.length() > 2) {
            mViewModel.performBookSearch(searchQuery);
        }
    }

    private void sortData(SortBy sortBy) {
        if (mViewModel.getBookSearchResults().getValue() != null) {
            Collections.sort(mViewModel.getBookSearchResults().getValue().getItems(), (lhs, rhs) -> {
                int result = 0;
                switch (sortBy) {
                    case AUTHOR:
                        List<String> authorsLhs = lhs.getVolumeInfo().getAuthors();
                        List<String> authorsRhs = lhs.getVolumeInfo().getAuthors();
                        if (authorsLhs != null && authorsRhs != null) {
                            result = authorsLhs.get(0).compareTo(authorsRhs.get(0));
                        }
                        break;
                    case TITLE:
                        String titleLhs = lhs.getVolumeInfo().getTitle();
                        String titleRhs = rhs.getVolumeInfo().getTitle();
                        if (titleLhs != null && titleRhs != null) {
                            result = titleLhs.compareTo(titleRhs);
                        }
                        break;
                }
                return result;
            });
            adapter.notifyDataSetChanged();
        }
    }

    enum SortBy {
        AUTHOR,
        TITLE
    }
}