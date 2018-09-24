package com.foreseer.definethis.HistoryScreen.Presentation;

import com.foreseer.definethis.HistoryScreen.Model.HistoryInteractor;
import com.foreseer.definethis.HistoryScreen.Model.HistoryInteractorImpl;
import com.foreseer.definethis.HistoryScreen.SortType;
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

    public static final SortType DEFAULT_SORT = SortType.NEWEST;

    public HistoryPresenterImpl(HistoryView view) {
        this.view = view;
        interactor = new HistoryInteractorImpl(this);
        //request newest first by default
        interactor.requestDefinitions(DEFAULT_SORT);
    }

    @Override
    public void onDefinitionsReceived(List<ExpandableWord> words) {
        view.displayWords(words);
    }

    @Override
    public SortType returnDefaultSortType() {
        return DEFAULT_SORT;
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

    @Override
    public void onSortClicked(SortType sortType) {
        interactor.requestDefinitions(sortType);
    }

    @Override
    public void onSearchQueried(String query) {
        interactor.querySearch(query);
    }
}
