package com.foreseer.definethis.UI.MainScreen.View.RecyclerView;

import com.foreseer.definethis.Data.Models.Definition;

import java.util.List;

/**
 * Created by Foreseer on 02-Oct-18.
 */

public class DefinitionPresenterImpl implements DefinitionPresenter
{

    private DefinitionModel model;

    public DefinitionPresenterImpl(DefinitionModel model) {
        this.model = model;
    }

    @Override
    public int getRowCount() {
        return model.getNumberOfElements();
    }

    @Override
    public void onBindDefinitionHolder(DefinitionHolder holder, int position) {
        Definition definition = model.getElement(position);

        holder.setDefinitionText(definition.getDefinition());
        if (definition.getExample() != null){
            String exampleString = "\"" + definition.getExample() + "\"";
            holder.setExamples(exampleString);
        } else {
            holder.hideExamples();
        }

        if (definition.getPartOfSpeech() != null){
            holder.setPartOfSpeech(definition.getPartOfSpeech());
        }
    }
}
