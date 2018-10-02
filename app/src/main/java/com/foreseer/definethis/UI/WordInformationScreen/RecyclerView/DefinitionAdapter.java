package com.foreseer.definethis.UI.WordInformationScreen.RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foreseer.definethis.R;

/**
 * Created by Konstantin "Foreseer" Buinak on 22.06.2017.
 */

public class DefinitionAdapter extends RecyclerView.Adapter<DefinitionHolder> {

    private DefinitionRecyclerViewContract.DefinitionPresenter presenter;

    public DefinitionAdapter(DefinitionRecyclerViewContract.DefinitionPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public DefinitionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_main_screen_definition_item, parent, false);
        return new DefinitionHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(DefinitionHolder holder, int position) {
        presenter.onBindDefinitionHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return presenter.getRowCount();
    }

}
