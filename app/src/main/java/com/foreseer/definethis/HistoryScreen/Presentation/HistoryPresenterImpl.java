package com.foreseer.definethis.HistoryScreen.Presentation;

import com.foreseer.definethis.HistoryScreen.Model.HistoryInteractor;
import com.foreseer.definethis.HistoryScreen.Model.HistoryInteractorImpl;
import com.foreseer.definethis.HistoryScreen.View.HistoryView;
import com.foreseer.definethis.HistoryScreen.View.RecyclerView.ExpandableWord;
import com.foreseer.definethis.Storage.Models.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Konstantin "Foreseer" Buinak on 22.06.2017.
 */

public class HistoryPresenterImpl implements HistoryPresenter, HistoryInteractor.HistoryInteractorListener {

    private HistoryInteractor interactor;
    private HistoryView view;

    public HistoryPresenterImpl(HistoryView view) {
        this.view = view;
        interactor = new HistoryInteractorImpl(this);
        interactor.requestDefinitions();
    }

    @Override
    public void onDefinitionsReceived(List<ExpandableWord> words) {
        view.displayWords(words);
    }

    @Override
    public void onResetClicked() {
        view.displayPromptDialog();
    }

    @Override
    public void onResetConfirmed() {
        view.displayWords(new ArrayList<>());
        interactor.resetHistory();
    }
}
