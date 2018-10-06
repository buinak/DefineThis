package com.foreseer.definethis.UI.HistoryScreen;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.foreseer.definethis.Data.Models.Word;
import com.foreseer.definethis.Application.DefineThisApplication;
import com.foreseer.definethis.R;
import com.foreseer.definethis.UI.HistoryScreen.RecyclerView.HistoryRecyclerViewAdapter;
import com.foreseer.definethis.UI.HistoryScreen.RecyclerView.SwipeToDeleteCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryActivity extends AppCompatActivity implements HistoryScreenContract.HistoryView {

    private HistoryScreenContract.HistoryPresenter presenter;

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
    public void displayWords(List<Word> wordList) {
        layoutManager = new LinearLayoutManager(this);
        adapter = new HistoryRecyclerViewAdapter(wordList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void displayPromptDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This will reset the entire history. " +
                "All the actions prior to the reset will be forgotten " +
                "and you will not be able to undo them anymore. Do you wish to continue?");
        builder.setNegativeButton("Cancel", (dialogInterface, i) -> {

        });
        builder.setPositiveButton("Clear history", ((dialogInterface, i) -> presenter.onResetConfirmed()));
        builder.show();
    }

    @Override
    public void displayError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void saveLastSortedType(HistoryScreenContract.SortType sortType) {
        String lastSorted = sortType.toString();
        getApplicationContext()
                .getSharedPreferences(DefineThisApplication.SETTINGS_FILE_NAME, MODE_PRIVATE)
                .edit()
                .putString(DefineThisApplication.SETTING_LAST_SORTED, lastSorted)
                .apply();
    }

    @Override
    public HistoryScreenContract.SortType getLastSortedType() {
        String s = getApplicationContext()
                .getSharedPreferences(DefineThisApplication.SETTINGS_FILE_NAME, MODE_PRIVATE)
                .getString(DefineThisApplication.SETTING_LAST_SORTED, null);

        if (s != null){
            return HistoryScreenContract.SortType.valueOf(s);
        } else {
            return HistoryScreenContract.SortType.NEWEST;
        }
    }


    private void initializeToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void initializeRecyclerView(int direction, SwipeToDeleteCallback.SwipeToDeleteCallbackListener listener) {
        new ItemTouchHelper(new SwipeToDeleteCallback(0, direction, listener))
                .attachToRecyclerView(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.history_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.action_reset):
                presenter.onResetClicked();
                return true;
            case (R.id.menuSortNewest):
                presenter.onSortClicked(HistoryScreenContract.SortType.NEWEST);
                return true;
            case (R.id.menuSortOldest):
                presenter.onSortClicked(HistoryScreenContract.SortType.OLDEST);
                return true;
            case (R.id.menuSortAtoZ):
                presenter.onSortClicked(HistoryScreenContract.SortType.A_TO_Z);
                return true;
            case (R.id.menuSortZtoA):
                presenter.onSortClicked(HistoryScreenContract.SortType.Z_TO_A);
                return true;
            case (R.id.action_undo):
                presenter.onUndoClicked();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
