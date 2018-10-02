package com.foreseer.definethis.UI.MainScreen.View;


import com.foreseer.definethis.Data.Models.Word;
import com.foreseer.definethis.UI.MainScreen.View.RecyclerView.DefinitionPresenter;

/**
 * Created by Konstantin "Foreseer" Buinak on 21.05.2017.
 */

public interface MainView {

    void setAdapter(DefinitionPresenter presenter);

    void showError(String error);
    void resetError();

    void showProgressBar();
    void hideProgressBar();
    void makeProgressBarGreen();
    void makeProgressBarGrey();
}
