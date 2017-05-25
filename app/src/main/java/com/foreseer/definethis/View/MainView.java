package com.foreseer.definethis.View;

/**
 * Created by Konstantin "Foreseer" Buinak on 21.05.2017.
 */

public interface MainView {
    void showDefinition(String definition);
    void showPartOfSpeech(String partOfSpeech);
    void animate();

    void showError(String error);
    void resetError();

    void resetDefinitionTextView();

    void showProgressBar();
    void hideProgressBar();
    void makeProgressBarGreen();
    void makeProgressBarGrey();
}
