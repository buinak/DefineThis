package com.foreseer.definethis.UI.HistoryScreen.Presentation;

import com.foreseer.definethis.UI.HistoryScreen.Model.HistoryInteractor;
import com.foreseer.definethis.UI.HistoryScreen.Model.HistoryInteractorImpl;
import com.foreseer.definethis.UI.HistoryScreen.SortType;
import com.foreseer.definethis.UI.HistoryScreen.View.HistoryView;
import com.foreseer.definethis.UI.HistoryScreen.View.RecyclerView.ExpandableWord;

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
        SortType sortType = view.getLastSortedType();
        interactor = new HistoryInteractorImpl(this, view.getLastSortedType());

        interactor.requestDefinitions();
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
        view.saveLastSortedType(sortType);
        interactor.requestDefinitions(sortType);
    }

    @Override
    public void onSearchQueried(String query) {
        interactor.querySearch(query);
    }
}
