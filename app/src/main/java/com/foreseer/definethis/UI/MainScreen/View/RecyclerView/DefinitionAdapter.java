package com.foreseer.definethis.UI.MainScreen.View.RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foreseer.definethis.Data.Models.Definition;
import com.foreseer.definethis.Data.Models.Word;
import com.foreseer.definethis.R;

import java.util.List;

/**
 * Created by Konstantin "Foreseer" Buinak on 22.06.2017.
 */

public class DefinitionAdapter extends RecyclerView.Adapter<DefinitionHolder> {

    private Word word;

    private List<Definition> definitions;

    public DefinitionAdapter(Word word) {
        this.word = word;
        definitions = word.getDefinitions();
    }

    @Override
    public DefinitionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_main_screen_definition_item, parent, false);
        return new DefinitionHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(DefinitionHolder holder, int position) {
        Definition definition = definitions.get(position);
        holder.bindDefinition(definition);
    }

    @Override
    public int getItemCount() {
        return definitions.size();
    }

}
