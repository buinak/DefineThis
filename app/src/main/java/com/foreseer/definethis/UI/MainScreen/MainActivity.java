package com.foreseer.definethis.UI.MainScreen;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.foreseer.definethis.UI.HistoryScreen.HistoryActivity;
import com.foreseer.definethis.UI.MainScreen.RecyclerView.DefinitionRecyclerViewContract;
import com.foreseer.definethis.UI.MainScreen.RecyclerView.DefinitionAdapter;
import com.foreseer.definethis.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainScreenContract.MainView {


    @BindView(R.id.editText_word)
    EditText editText;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.tToolbar)
    Toolbar toolbar;

    /* RECYCLER VIEW */

    @BindView(R.id.recyclerView_definitions)
    RecyclerView recyclerView;

    // RECYCLER VIEW RELATED STUFF
    private LinearLayoutManager layoutManager;
    private DefinitionAdapter recyclerAdapter;

    private MainScreenContract.MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ButterKnife.bind(this);

        initToolbar();
        initializeRecyclerView();

        // Start searching for the word if enter on the keyboard is pressed
        editText.setOnKeyListener((v, keyCode, event) -> {
            // If the event is a key-down event on the "enter" button
            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                // Perform action on key press
                onWordEntered();
            }
            return false;
        });

        // Notify the presenter about any changes
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                resetError();
                presenter.onEditTextChanged(s.toString());
            }
        });

        // Hide keyboard if pressed outside the edittext
        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideSoftKeyboardAndDefocus(v);
            }
        });

        presenter = new MainPresenterImpl(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        presenter = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }



    @Override
    public void resetError(){
        editText.setError(null);
    }

    public void onWordEntered(){
        String text = editText.getText().toString();

        //Hides the keyboard
        View view = this.getCurrentFocus();
        if (view != null) {
            hideSoftKeyboardAndDefocus(view);
        }

        presenter.onWordEntered(text);
    }

    @Override
    public void setAdapter(DefinitionRecyclerViewContract.DefinitionPresenter presenter) {
        recyclerAdapter = new DefinitionAdapter(presenter);
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public void showError(String error) {
        //Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        editText.setError(error);
    }

    @Override
    public void showProgressBar() {
        progressBar.setIndeterminate(true);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setIndeterminate(false);
    }

    @Override
    public void makeProgressBarGreen() {
        progressBar.getIndeterminateDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        progressBar.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void makeProgressBarGrey() {
        progressBar.getIndeterminateDrawable().clearColorFilter();
        progressBar.getProgressDrawable().clearColorFilter();
    }

    /*
      UTILS METHODS
     */

    private void hideSoftKeyboardAndDefocus(View view){
        editText.clearFocus();

        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void initializeRecyclerView(){
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();

        if (ev.getAction() == MotionEvent.ACTION_DOWN &&
                !getLocationOnScreen(editText).contains(x, y)) {
            hideSoftKeyboardAndDefocus(editText);
        }

        return super.dispatchTouchEvent(ev);
    }

    protected Rect getLocationOnScreen(EditText mEditText) {
        Rect mRect = new Rect();
        int[] location = new int[2];

        mEditText.getLocationOnScreen(location);

        mRect.left = location[0];
        mRect.top = location[1];
        mRect.right = location[0] + mEditText.getWidth();
        mRect.bottom = location[1] + mEditText.getHeight();

        return mRect;
    }

    private void initToolbar(){
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_history:
                startHistoryActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startHistoryActivity(){
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }
}
