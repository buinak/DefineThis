package com.foreseer.definethis.HistoryScreen.View;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.foreseer.definethis.HistoryScreen.Presentation.HistoryPresenter;
import com.foreseer.definethis.HistoryScreen.Presentation.HistoryPresenterImpl;
import com.foreseer.definethis.HistoryScreen.SortType;
import com.foreseer.definethis.HistoryScreen.View.RecyclerView.ExpandableWord;
import com.foreseer.definethis.HistoryScreen.View.RecyclerView.HistoryRecyclerViewAdapter;
import com.foreseer.definethis.R;
import com.foreseer.definethis.Storage.Models.Word;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryActivity extends AppCompatActivity implements HistoryView {

    private HistoryPresenter presenter;

    @BindView(R.id.recyclerview_history_screen)
    RecyclerView recyclerView;

    @BindView(R.id.secondary_toolbar)
    Toolbar toolbar;

    private LinearLayoutManager layoutManager;
    private HistoryRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ButterKnife.bind(this);

        initializeToolbar();

        presenter = new HistoryPresenterImpl(this);

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
