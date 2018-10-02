package com.foreseer.definethis.UI.HistoryScreen;

import com.foreseer.definethis.Data.Models.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Konstantin "Foreseer" Buinak on 22.06.2017.
 */

public class HistoryPresenterImpl implements HistoryScreenContract.HistoryPresenter, HistoryScreenContract.HistoryInteractor.HistoryInteractorListener {

    private HistoryScreenContract.HistoryInteractor interactor;
    private HistoryScreenContract.HistoryView view;

    public static final HistoryScreenContract.SortType DEFAULT_SORT = HistoryScreenContract.SortType.NEWEST;

    public HistoryPresenterImpl(HistoryScreenContract.HistoryView view) {
        this.view = view;
        HistoryScreenContract.SortType sortType = view.getLastSortedType();
        interactor = new HistoryInteractorImpl(this, view.getLastSortedType());

        interactor.requestDefinitions();
    }

    @Override
    public void onDefinitionsReceived(List<Word> words) {
        view.displayWords(words);
    }

    @Override
    public HistoryScreenContract.SortType returnDefaultSortType() {
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
    public void onSortClicked(HistoryScreenContract.SortType sortType) {
        view.saveLastSortedType(sortType);
        interactor.requestDefinitions(sortType);
    }

    @Override
    public void onSearchQueried(String query) {
        interactor.querySearch(query);
    }
}
