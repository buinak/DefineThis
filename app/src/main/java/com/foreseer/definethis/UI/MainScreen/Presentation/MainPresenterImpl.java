package com.foreseer.definethis.UI.MainScreen.Presentation;

import com.foreseer.definethis.Data.Models.Word;
import com.foreseer.definethis.UI.MainScreen.Model.MainInteractor;
import com.foreseer.definethis.UI.MainScreen.Model.MainInteractorImpl;
import com.foreseer.definethis.UI.MainScreen.View.MainView;
import com.foreseer.definethis.UI.MainScreen.View.RecyclerView.DefinitionModel;
import com.foreseer.definethis.UI.MainScreen.View.RecyclerView.DefinitionModelImpl;
import com.foreseer.definethis.UI.MainScreen.View.RecyclerView.DefinitionPresenter;
import com.foreseer.definethis.UI.MainScreen.View.RecyclerView.DefinitionPresenterImpl;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

/**
 * Created by Konstantin "Foreseer" Buinak on 21.05.2017.
 */

public class MainPresenterImpl implements MainPresenter, MainInteractor.MainInteractorListener{
    private MainView view;
    private MainInteractor model;

    public MainPresenterImpl(MainView view) {
        this.view = view;
        model = new MainInteractorImpl(this);
    }

    @Override
    public void onWordEntered(String word) {
        if (validateText(word)) {
            model.onWordDefinitionRequested(word);
        }
    }

    @Override
    public void onEditTextChanged(String text) {
        view.makeProgressBarGrey();
        resetViewDefinitions();
        if (validateText(text)){
            model.onTextChanged(text);
        }
    }

    private void resetViewDefinitions(){
        DefinitionModel model = new DefinitionModelImpl(new ArrayList<>());
        DefinitionPresenter presenter = new DefinitionPresenterImpl(model);
        view.setAdapter(presenter);
    }

    @Override
    public void onDestroy() {
        view = null;
        model.onDestroy();
        model = null;
    }

    private boolean validateText(String text){
        /*if (text.equals("")){
            view.showError("Can't define nothing!");
            return false;
        }*/
        if (StringUtils.isNumeric(text)){
            view.showError("Numbers not allowed!");
            return false;
        }
        return true;
    }


    private void viewFinish(){
        view.makeProgressBarGreen();
        view.hideProgressBar();
    }

    @Override
    public void onWordDefinitionsReceived(Word word) {
        DefinitionModel model = new DefinitionModelImpl(word.getDefinitions());
        DefinitionPresenter presenter = new DefinitionPresenterImpl(model);
        view.setAdapter(presenter);
        viewFinish();
    }

    @Override
    public void onEmptyRequestReceived() {
        view.hideProgressBar();
        view.makeProgressBarGrey();
    }

    @Override
    public void onRequestStarted() {
        view.resetError();
        resetViewDefinitions();
        view.showProgressBar();
    }

    @Override
    public void onWordNotFound(String word) {
        onError(new Exception("Word " + word + " does not have any definitions in the dictionary!"), true);
    }

    @Override
    public void onIncorrectWord() {
        onError(new Exception("Inoorrect word! Please, type one word to be defined."), true);
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
