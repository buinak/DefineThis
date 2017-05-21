package com.foreseer.definethis.View;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.foreseer.definethis.Presentation.MainPresenter;
import com.foreseer.definethis.Presentation.MainPresenterImpl;
import com.foreseer.definethis.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView {


    @BindView(R.id.editText_word)
    EditText editText;

    @BindView(R.id.textView_definition)
    TextView textView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

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
    public void resetError(){
        editText.setError(null);
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

    public void onWordEntered(){
        String text = editText.getText().toString();

        //Hides the keyboard
        View view = this.getCurrentFocus();
        if (view != null) {
            hideSoftKeyboardAndDefocus(view);
        }

        presenter.onWordEntered(text);
    }

    private void hideSoftKeyboardAndDefocus(View view){
        editText.clearFocus();

        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void showDefinition(String definition) {
        textView.setText(definition);
    }

    @Override
    public void showError(String error) {
        //Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        editText.setError(error);
    }

    @Override
    public void resetDefinitionTextView() {
        textView.setText("");
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
}
