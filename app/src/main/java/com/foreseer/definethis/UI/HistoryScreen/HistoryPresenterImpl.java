package com.foreseer.definethis.UI.HistoryScreen;

import android.support.v7.widget.helper.ItemTouchHelper;

import com.foreseer.definethis.Data.Entities.DefineThis.Word;
import com.foreseer.definethis.UI.HistoryScreen.RecyclerView.SwipeToDeleteCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Konstantin "Foreseer" Buinak on 22.06.2017.
 */

public class HistoryPresenterImpl implements HistoryScreenContract.HistoryPresenter, HistoryScreenContract.HistoryModel.HistoryModelListener {

    private HistoryScreenContract.HistoryModel model;
    private HistoryScreenContract.HistoryView view;

    public static final HistoryScreenContract.SortType DEFAULT_SORT = HistoryScreenContract.SortType.NEWEST;

    public HistoryPresenterImpl(HistoryScreenContract.HistoryView view) {
        this.view = view;
        HistoryScreenContract.SortType sortType = view.getLastSortedType();
        model = new HistoryModelImpl(this, view.getLastSortedType());

        view.initializeRecyclerView(ItemTouchHelper.LEFT, (SwipeToDeleteCallback.SwipeToDeleteCallbackListener) model);
        model.requestDefinitions();
    }

    @Override
    public void onDefinitionsReceived(List<Word> words) {
        view.displayWords(words);
    }

    @Override
    public void onWordAlreadyExists(String wordString) {
        view.displayError("Word " + wordString + " already exists!");
    }

    @Override
    public void onWordAlreadyExists() {
        view.displayError("Some words already existed, they will not be added.");
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
        model.resetHistory();
    }

    @Override
    public void onSortClicked(HistoryScreenContract.SortType sortType) {
        view.saveLastSortedType(sortType);
        model.requestDefinitions(sortType);
    }

    @Override
    public void onUndoClicked() {
        model.requestUndo();
    }

    @Override
    public void onSearchQueried(String query) {
        model.querySearch(query);
    }

    @Override
    public void onDestroy() {
        model.finish();
        model = null;
        view = null;
    }
}
