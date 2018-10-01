package com.foreseer.definethis.UI.MainScreen.View;


import com.foreseer.definethis.Data.Models.Word;

/**
 * Created by Konstantin "Foreseer" Buinak on 21.05.2017.
 */

public interface MainView {

    void showWord(Word word);
    void resetDefinitions();

    void showError(String error);
    void resetError();

    void showProgressBar();
    void hideProgressBar();
    void makeProgressBarGreen();
    void makeProgressBarGrey();
}
