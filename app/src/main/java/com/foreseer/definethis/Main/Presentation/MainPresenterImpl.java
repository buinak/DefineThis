package com.foreseer.definethis.Main.Presentation;

import com.foreseer.definethis.Main.Model.API.JSONSchema.Definition;
import com.foreseer.definethis.Main.Model.MainInteractor;
import com.foreseer.definethis.Main.Model.MainInteractorImpl;
import com.foreseer.definethis.Main.View.MainView;

import org.apache.commons.lang3.StringUtils;

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
        view.resetPartOfSpeechTextView();
        view.resetDefinitionTextView();
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
        view.showPartOfSpeech(definition.getPartOfSpeech());
        view.showDefinition(definition.getDefinition());
        viewFinish();
    }

    private void viewFinish(){
        view.animate();
        view.makeProgressBarGreen();
        view.hideProgressBar();
    }

    @Override
    public void onWordDefinitionsReceived(List<Definition> definitions) {
        StringBuilder builder = new StringBuilder();
        int i = 1;
        for (Definition definition : definitions){
            builder.append(i);
            builder.append(", ");
            builder.append(definition.getPartOfSpeech());
            builder.append(": ");
            builder.append(definition.getDefinition());
            builder.append(System.getProperty("line.separator"));
            i++;
        }
        view.showDefinition(builder.toString());
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
        view.resetDefinitionTextView();
        view.resetPartOfSpeechTextView();
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
