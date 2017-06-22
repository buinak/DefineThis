package com.foreseer.definethis.MainScreen.Presentation;

import com.foreseer.definethis.MainScreen.Model.API.JSONSchema.Definition;
import com.foreseer.definethis.MainScreen.Model.MainInteractor;
import com.foreseer.definethis.MainScreen.Model.MainInteractorImpl;
import com.foreseer.definethis.MainScreen.View.MainView;
import com.foreseer.definethis.Storage.Models.Word;
import com.foreseer.definethis.Storage.StorageHandler;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

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
        view.resetDefinitions();
        if (validateText(text)){
            model.onTextChanged(text);
        }
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

    @Override
    public void onWordDefinitionReceived(Definition definition) {
        List<Definition> list = new ArrayList<>();
        list.add(definition);

        view.showDefinitions(list);
        viewFinish();
    }

    private void viewFinish(){
        view.makeProgressBarGreen();
        view.hideProgressBar();
    }

    @Override
    public void onWordDefinitionsReceived(List<Definition> definitions) {
        view.showDefinitions(definitions);
        List<Word> words = StorageHandler.getAllWords();
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
        view.resetDefinitions();
        view.showProgressBar();
    }

    @Override
    public void onWordNotFound(String word) {
        onError("Word " + word + " does not have any definitions in the dictionary!", true);
    }

    @Override
    public void onIncorrectWord() {
        onError("Inoorrect word! Please, type one word to be defined.", true);
    }

    @Override
    public void onError(String error, boolean custom) {
        view.hideProgressBar();
        view.makeProgressBarGrey();
        if (!custom) {
            if (error.contains("404")) {
                view.showError("Couldn't find such word!");
            } else {
                view.showError("Connection problems!");
            }
        } else {
            view.showError(error);
        }
    }
}
