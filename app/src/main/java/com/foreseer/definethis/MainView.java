package com.foreseer.definethis;

/**
 * Created by Konstantin "Foreseer" Buinak on 21.05.2017.
 */

public interface MainView {
    void showDefinition(String definition);
    void showError(String error);

    void showProgressBar();
    void hideProgressBar();
}
