package com.foreseer.definethis.HistoryScreen.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.foreseer.definethis.HistoryScreen.Presentation.HistoryPresenter;
import com.foreseer.definethis.HistoryScreen.Presentation.HistoryPresenterImpl;
import com.foreseer.definethis.R;
import com.foreseer.definethis.Storage.Models.Word;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryActivity extends AppCompatActivity implements HistoryView{

    private HistoryPresenter presenter;

    @BindView(R.id.recyclerview_history_screen)
    RecyclerView recyclerView;

    private LinearLayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ButterKnife.bind(this);

        presenter = new HistoryPresenterImpl(this);
    }

    @Override
    public void displayWords(List<Word> wordList) {

    }
}
