package com.foreseer.definethis.Main.View;

import com.foreseer.definethis.Main.Model.API.JSONSchema.Definition;

import java.util.List;

/**
 * Created by Konstantin "Foreseer" Buinak on 21.05.2017.
 */

public interface MainView {

    void showDefinitions(List<Definition> definitionList);
    void resetDefinitions();

    void showError(String error);
    void resetError();

    void showProgressBar();
    void hideProgressBar();
    void makeProgressBarGreen();
    void makeProgressBarGrey();
}
