package com.foreseer.definethis.UI.HistoryScreen.View;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import com.foreseer.definethis.DefineThisApplication;
import com.foreseer.definethis.UI.HistoryScreen.Presentation.HistoryPresenter;
import com.foreseer.definethis.UI.HistoryScreen.Presentation.HistoryPresenterImpl;
import com.foreseer.definethis.UI.HistoryScreen.SortType;
import com.foreseer.definethis.UI.HistoryScreen.View.RecyclerView.ExpandableWord;
import com.foreseer.definethis.UI.HistoryScreen.View.RecyclerView.HistoryRecyclerViewAdapter;
import com.foreseer.definethis.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryActivity extends AppCompatActivity implements HistoryView {

    private HistoryPresenter presenter;

    @BindView(R.id.recyclerview_history_screen)
    RecyclerView recyclerView;

    @BindView(R.id.secondary_toolbar)
    Toolbar toolbar;

    @BindView(R.id.search_view_history)
    SearchView searchView;

    private LinearLayoutManager layoutManager;
    private HistoryRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ButterKnife.bind(this);

        initializeToolbar();
        initializeSearchView();

        presenter = new HistoryPresenterImpl(this);

    }

    private void initializeSearchView() {
        //makes the searchview clickable anywhere, not just on the search icon
        searchView.setOnClickListener(v -> searchView.setIconified(false));
        //on queries, send them to the presenter
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                presenter.onSearchQueried(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                presenter.onSearchQueried(s);
                return true;
            }
        });
    }


    @Override
    public void displayWords(List<ExpandableWord> wordList) {
        layoutManager = new LinearLayoutManager(this);

        adapter = new HistoryRecyclerViewAdapter(wordList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void displayPromptDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This will reset your search history. Do you wish to continue?");
        builder.setNegativeButton("Cancel", (dialogInterface, i) -> {

        });
        builder.setPositiveButton("Clear history", ((dialogInterface, i) -> presenter.onResetConfirmed()));
        builder.show();
    }

    @Override
    public void saveLastSortedType(SortType sortType) {
        String lastSorted = sortType.toString();
        getApplicationContext()
                .getSharedPreferences(DefineThisApplication.SETTINGS_FILE_NAME, MODE_PRIVATE)
                .edit()
                .putString(DefineThisApplication.SETTING_LAST_SORTED, lastSorted)
                .apply();
        System.out.println();
    }

    @Override
    public SortType getLastSortedType() {
        String s = getApplicationContext()
                .getSharedPreferences(DefineThisApplication.SETTINGS_FILE_NAME, MODE_PRIVATE)
                .getString(DefineThisApplication.SETTING_LAST_SORTED, null);

        if (s != null){
            return SortType.valueOf(s);
        } else {
            return SortType.NEWEST;
        }
    }


    private void initializeToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.secondary_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.action_reset):
                presenter.onResetClicked();
                return true;
            case (R.id.menuSortNewest):
                presenter.onSortClicked(SortType.NEWEST);
                return true;
            case (R.id.menuSortOldest):
                presenter.onSortClicked(SortType.OLDEST);
                return true;
            case (R.id.menuSortAtoZ):
                presenter.onSortClicked(SortType.A_TO_Z);
                return true;
            case (R.id.menuSortZtoA):
                presenter.onSortClicked(SortType.Z_TO_A);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
