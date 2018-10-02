package com.foreseer.definethis.UI.MainScreen.View.RecyclerView;

/**
 * Created by Foreseer on 02-Oct-18.
 */

public interface DefinitionPresenter {
    int getRowCount();
    void onBindDefinitionHolder(DefinitionHolder holder, int position);
}
