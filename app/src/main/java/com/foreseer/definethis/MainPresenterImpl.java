package com.foreseer.definethis;

import com.foreseer.definethis.API.JSONSchema.Result;

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
        if (word.equals("")){
            view.showError("Can't define nothing!");
            return;
        }
        if (StringUtils.isNumeric(word)){
            view.showError("Numbers not allowed!");
            return;
        }
        view.showProgressBar();
        model.onWordDefinitionRequested(word);
    }

    @Override
    public void onWordDefinitionReceived(String definition) {
        view.hideProgressBar();
        view.showDefinition(definition);
    }

    @Override
    public void onWordDefinitionsReceived(List<Result> results) {
        StringBuilder builder = new StringBuilder();
        int i = 1;
        for (Result result : results){
            builder.append(i);
            builder.append(", ");
            builder.append(result.getPartOfSpeech());
            builder.append(": ");
            builder.append(result.getDefinition());
            builder.append(System.getProperty("line.separator"));
            i++;
        }
        view.showDefinition(builder.toString());
        view.hideProgressBar();
    }

    @Override
    public void onWordNotFound(String word) {

    }

    @Override
    public void onError(String error) {
        view.hideProgressBar();
        view.showError(error);
    }
}
