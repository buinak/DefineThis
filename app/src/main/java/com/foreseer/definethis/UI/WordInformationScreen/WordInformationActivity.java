package com.foreseer.definethis.UI.WordInformationScreen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.foreseer.definethis.R;
import com.foreseer.definethis.UI.WordInformationScreen.RecyclerView.DefinitionAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WordInformationActivity extends AppCompatActivity implements WordInformationContract.WordInformationView {

    @BindView(R.id.recyclerView_information)
    RecyclerView recyclerView;

    @BindView(R.id.textView_word)
    TextView textViewWord;

    @BindView(R.id.textView_phonetics)
    TextView textViewPhonetics;

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    private WordInformationContract.WordInformationPresenter presenter;

    public static final String WORD_DATA_TAG = "word";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_information);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        presenter = new WordInformationPresenterImpl(this,
                getIntent().getStringExtra(WORD_DATA_TAG));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void setWordViewText(String text) {
        textViewWord.setText(text);
    }

    @Override
    public void setPhoneticsViewText(String text) {
        textViewPhonetics.setText(text);
    }

    @Override
    public void setUpRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void setRecyclerViewAdapter(DefinitionAdapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
