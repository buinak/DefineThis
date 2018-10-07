package com.foreseer.definethis.UI.MainScreen;

import com.foreseer.definethis.Data.Entities.DefineThis.Word;
import com.foreseer.definethis.UI.MainScreen.RecyclerView.DefinitionModelImpl;
import com.foreseer.definethis.UI.MainScreen.RecyclerView.DefinitionPresenterImpl;
import com.foreseer.definethis.UI.MainScreen.RecyclerView.DefinitionRecyclerViewContract;

import org.apache.commons.lang3.StringUtils;


/**
 * Created by Konstantin "Foreseer" Buinak on 21.05.2017.
 */

public class MainPresenterImpl implements MainScreenContract.MainPresenter, MainScreenContract.MainModel.MainModelListener {
    private MainScreenContract.MainView view;
    private MainScreenContract.MainModel model;

    public MainPresenterImpl(MainScreenContract.MainView view) {
        this.view = view;
        model = new MainModelImpl(this);

        view.hideWordLayout();
    }

    @Override
    public void onWordEntered(String word) {
        model.onWordDefinitionRequested(word);
    }

    @Override
    public void onEditTextChanged(String text) {
        model.onTextChanged(text);

    }

    private void hideViewElements(){
        view.hideWordLayout();
    }

    @Override
    public void onDestroy() {
        view = null;
        model.finish();
        model = null;
    }


    private void onViewRequestFinished(){
        view.makeProgressBarGreen();
        view.hideProgressBar();

        view.showWordLayout();
    }

    @Override
    public void onWordDefinitionsReceived(Word word) {
        DefinitionRecyclerViewContract.DefinitionModel model = new DefinitionModelImpl(word.getDefinitions());
        DefinitionRecyclerViewContract.DefinitionPresenter presenter = new DefinitionPresenterImpl(model);
        view.setAdapter(presenter);

        view.setWordTextView(word.getWord());
        view.setPhoneticsTextView(word.getPhonetics().get(0));

        onViewRequestFinished();
    }

    @Override
    public void onEmptyRequestReceived() {
        view.hideProgressBar();
        hideViewElements();
        view.makeProgressBarGrey();
    }

    @Override
    public void onRequestStarted() {
        view.resetError();
        hideViewElements();
        view.showProgressBar();
    }

    @Override
    public void onWordNotFound(String word) {
        onError(new Exception("Word " + word + " does not have any definitions in the dictionary!"), true);
    }

    @Override
    public void onCorrectWord() {
        view.makeProgressBarGrey();
        hideViewElements();
    }

    @Override
    public void onIncorrectWord() {
        onError(new Exception("Incorrect word! Please, type one word to be defined."), true);
    }

    @Override
    public void onError(Exception error, boolean custom) {
        view.hideProgressBar();
        view.makeProgressBarGrey();
        if (!custom) {
            if (error.getMessage().contains("404")) {
                view.showError("Couldn't find such word!");
            } else {
                view.showError("Connection problems!");
            }
        } else {
            view.showError(error.getMessage());
        }
    }
}
